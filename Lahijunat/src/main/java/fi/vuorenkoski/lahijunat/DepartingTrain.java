package fi.vuorenkoski.lahijunat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Luokka kuvaa yhtä asemalta lähtevää junaa.
 * @author Lauri Vuorenkoski
 */
public class DepartingTrain implements Comparable<DepartingTrain> {
    private final String commuterLineId;
    private final int trainNumber;
    private final int destination;
    private final Date scheduledTime;
    private final Date liveEstimateTime;
    private final String track;
    private final String causes;
    private final boolean cancelled;
    
    public DepartingTrain(String commuterLineId, int trainNumber, 
            int destination, Date scheduledTime, Date liveEstimateTime, 
            String track, String causes, boolean cancelled) {
        this.commuterLineId = commuterLineId;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.scheduledTime = scheduledTime;
        this.track = track;
        this.causes = causes;
        this.cancelled = cancelled;
        if (liveEstimateTime.getTime()-scheduledTime.getTime()>120000) {
            this.liveEstimateTime=liveEstimateTime;
        } else {
            this.liveEstimateTime=scheduledTime;
        }
    }
    
    private String hhmmString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }
    
    /**
     * Metodi palauttaa aikataulun mukaisen lähtöajan Date muodossa.
     * 
     * @return Päiväys
     */
    public Date getScheduledTime() {
        return scheduledTime;
    }
    
    @Override
    public String toString() {
        if (this.cancelled) return ""+this.commuterLineId
                +this.hhmmString(scheduledTime)+" "
                +Station.StationName(this.destination)+" PERUTTU";
        if (this.scheduledTime!=this.liveEstimateTime) return ""
                +this.commuterLineId+" "+this.track+" "
                +this.hhmmString(scheduledTime)+"-->"
                +this.hhmmString(liveEstimateTime)+" "
                +Station.StationName(this.destination);
        return ""+this.commuterLineId+" "+this.track+" "
                +this.hhmmString(scheduledTime)+" "
                +Station.StationName(this.destination);
    }
    
    @Override
    public int compareTo(DepartingTrain t) {
        return (int) (this.scheduledTime.getTime()-t.getScheduledTime().getTime());
    }  
}
