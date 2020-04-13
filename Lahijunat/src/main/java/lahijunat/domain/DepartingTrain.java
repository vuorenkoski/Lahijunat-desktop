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

    /**
     * Metodi asettaa junan pääteaseman
     * @param destination pääteaseman nimi
     */
    public void setDestination(int destination) {
        this.destination = destination;
    }

    /**
     * Metodi asettaa aikautalun mukaisen lähtöajan
     * @param scheduledTime Aikataulun mukainen lähtöaika (Date)
     */
    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * Metodi asettaa arvioidun lähtöajanajan
     * @param liveEstimateTime Arvioitu lähtöaika
     */
    public void setLiveEstimateTime(Date liveEstimateTime) {
        this.liveEstimateTime = liveEstimateTime;
    }

    /**
     * Metodi asettaa lähtöraiteen.
     * @param track Lähtöraide
     */
    public void setTrack(String track) {
        this.track = track;
    }

    /**
     * Metodi asettaa pertuuksen syyt
     * @param causes Peruutuksen syyt (String)
     */
    public void setCauses(String causes) {
        this.causes = causes;
    }

    /**
     * Metodi asettaa onko juna peruttu
     * @param cancelled Peruttu (Boolean)
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    /**
     * metodi oalauttaa junan aikautalun mukaisen lähtöajan
     * @return Lähtöaika (String hh:mm)
     */
    public String getTime() {
        return hhmmString(scheduledTime);
    }
    
    /**
     * Metodi palauttaa lähtöraiteen
     * @return lähtöraide (String)
     */
    public String getTrack() {
        return track;
    }
    
    /**
     * Metodi palauttaa junan symbolin
     * @return Symboli (String)
     */
    public String getCommuterLineId() {
        return commuterLineId;
    }
    
    /**
     * Metodi palauttaa junan numeron
     * @return Junan junmero
     */
    public int getTrainNumber() {
        return trainNumber;
    }
    
    /**
     * Metodi palauttaa peruutuksen syyt String muodossa.
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
     * Metodi palauttaa arvioidun lähtöajan hh:mm. Metodi palauttaa tyhjän, jos arvioitua aikaa ei ole asetettu tai
     * jos se ei poikkea aikautalun mukaisesta aikataulusta.
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
