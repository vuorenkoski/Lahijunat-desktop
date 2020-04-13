package lahijunat.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Luokka kuvaa yhden junan yhtä pysähdystä.
 * @author Lauri Vuorenkoski
 */
public class TrainStop {
    private final Date scheduledTime;
    private final String stationName;
    private Date liveEstimateTime;
    private String nextStation;

    public TrainStop(Date scheduledTime, String stationName) {
        this.scheduledTime = scheduledTime;
        this.stationName = stationName;
        this.nextStation = "";
        this.liveEstimateTime = null;
    }

    /**
     * Metodi asettaa arvioidun lähtöajan.
     * @param liveEstimateTime arvioitu lähtöaika
     */
    public void setLiveEstimateTime(Date liveEstimateTime) {
        this.liveEstimateTime = liveEstimateTime;
    }

    /**
     * Metodi asettaa merkin aseman kohdalle listaan. Merkin "X" asettamaninen tarkoittaa
     * että juna pysähtyy seuraavaksi tälla asemalla.
     * @param nextStation
     */
    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    /**
     * Metodi palauttaa arvioidun lähtöajan hh:mm muodossa
     * @return Arivoitu lähtöaika
     */
    public String getEstimate() {
        if (this.liveEstimateTime != null) {
            return hhmmString(liveEstimateTime);
        }
        return "";
    }
    
    /**
     * Metodi palauttaa aikataulun mukaisen lähtöajan hh:mm muodossa. Mikäli tätä ei ole 
     * asetettu, metodi palauttaa tyhjän merkkijonon.
     * @return Aikataulun mukainen lähtöaika
     */
    public String getTime() {
        if (this.scheduledTime != null) {
            return hhmmString(this.scheduledTime);
        }
        return "";
    }

    /**
     * Metodi palauttaa mahdollisen "Seuraava asema" -merkin.
     * @return "" tai "X"
     */
    public String getNext() {
        return nextStation;
    }

    /**
     * Metodi palauttaa aseman koko nimen
     * @return Aseman nimi
     */
    public String getStation() {
        return stationName;
    }
    
    private String hhmmString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }
    
}
