package lahijunat.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Luokka kuvaa yhden junan yhtä pysähdystä.
 * @author Lauri Vuorenkoski
 */
public class TrainStop {
    private final Date liveEstimateTime;
    private final Date scheduledTime;
    private final String nextStation;
    private final String stationName;

    public TrainStop(Date liveEstimateTime, Date scheduledTime, String nextStation, String stationName) {
        this.liveEstimateTime = liveEstimateTime;
        this.scheduledTime = scheduledTime;
        this.nextStation = nextStation;
        this.stationName = stationName;
    }
    
    public String getEstimate() {
        if (this.liveEstimateTime != null) {
            return hhmmString(liveEstimateTime);
        }
        return "";
    }
    
    public String getTime() {
        return hhmmString(scheduledTime);
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
