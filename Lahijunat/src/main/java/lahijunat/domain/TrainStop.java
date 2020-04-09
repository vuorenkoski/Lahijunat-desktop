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

    public void setLiveEstimateTime(Date liveEstimateTime) {
        this.liveEstimateTime = liveEstimateTime;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public String getEstimate() {
        if (this.liveEstimateTime != null) {
            return hhmmString(liveEstimateTime);
        }
        return "";
    }
    
    public String getTime() {
        if (this.scheduledTime != null) {
            return hhmmString(this.scheduledTime);
        }
        return "";
    }

    public String getNext() {
        return nextStation;
    }

    public String getStation() {
        return stationName;
    }
    
    private String hhmmString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }
    
}
