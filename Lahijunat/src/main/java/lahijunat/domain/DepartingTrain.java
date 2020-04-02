package lahijunat.domain;

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
    private final int station;
    private final int destination;
    private final Date scheduledTime;
    private final Date liveEstimateTime;
    private final String track;
    private final String causes;
    private final boolean cancelled;
    
    public DepartingTrain(String commuterLineId, int trainNumber, 
            int station, int destination, Date scheduledTime, Date liveEstimateTime, 
            String track, String causes, boolean cancelled) {
        this.commuterLineId = commuterLineId;
        this.trainNumber = trainNumber;
        this.station = station;
        this.destination = destination;
        this.scheduledTime = scheduledTime;
        this.track = track;
        this.causes = causes;
        this.cancelled = cancelled;
        if (liveEstimateTime.getTime() - scheduledTime.getTime() > 80000) {
            this.liveEstimateTime = liveEstimateTime;
        } else {
            this.liveEstimateTime = scheduledTime;
        }
    }
    
    public String getTime() {
        return hhmmString(scheduledTime);
    }
    
    public String getTrack() {
        return track;
    }
    
    public String getCommuterLineId() {
        return commuterLineId;
    }
    
    public int getTrainNumber() {
        return trainNumber;
    }
    
    /**
     * Metodi palauttaa peruutuksen syyt String muodossa.
     * 
     * @return peruutuksen syyt
     */
    public String getCauses() {
        if (causes.equals("[]")) {
            return "";
        }
        return causes;
    }
    
    /**
     * Metodi palauttaa junan pääteaseman koko nimen.
     * 
     * @return Aseman nimi
     */
    public String getDestination() {
        return Station.stationName(destination);
    }
    
    /**
     * Metodi palauttaa arvioidun lähtöajan Date muodossa jos se pokkeaa normaalista.
     * 
     * @return Päiväys
     */
    public String getEstimate() {
        if (this.scheduledTime != this.liveEstimateTime) {
            return hhmmString(liveEstimateTime);
        }
        return "";
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
        return Station.stationName(this.station) + " " + this.hhmmString(scheduledTime) + " " + this.commuterLineId;
    }
    
    @Override
    public int compareTo(DepartingTrain t) {
        return (int) (this.scheduledTime.getTime() - t.getScheduledTime().getTime());
    }  
    
    private String hhmmString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }
}
