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
    private int destination;
    private Date scheduledTime;
    private Date liveEstimateTime;
    private String track;
    private String causes;
    private boolean cancelled;
    
    public DepartingTrain(String commuterLineId, int trainNumber, int station) {
        this.commuterLineId = commuterLineId;
        this.trainNumber = trainNumber;
        this.station = station;
        this.destination = 0;
        this.scheduledTime = null;
        this.track = "";
        this.causes = "";
        this.cancelled = false;
        this.liveEstimateTime = null;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setLiveEstimateTime(Date liveEstimateTime) {
        this.liveEstimateTime = liveEstimateTime;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
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
        if (this.cancelled)  {
            if (this.causes.equals("[]")) {
                return "Peruttu";
            }
            return "Peruttu: " + this.causes;
        }
        return "";
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
        if (this.liveEstimateTime != null && !this.hhmmString(scheduledTime).equals(this.hhmmString(this.liveEstimateTime))) {
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
