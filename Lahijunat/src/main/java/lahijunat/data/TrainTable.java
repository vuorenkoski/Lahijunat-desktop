package lahijunat.data;

/**
 *
 * @author Lauri Vuorenkoski
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lahijunat.domain.DepartingTrain;
import lahijunat.domain.Station;
import lahijunat.domain.TrainStop;
import lahijunat.vrapi.FetchData;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Luokka kuvaa yhden junan aikautalua.
 * @author Lauri Vuorenkoski
 */
public class TrainTable {
    private TableView dataTable;
    private String commuterLineID;
    private ArrayList<TrainStop> stations;
    private boolean nextStationPassed;
    private final DateFormat dateFormat;
    private int trainNumber;

    public TrainTable() {
        this.commuterLineID = "";
        this.trainNumber = 0;
        this.stations = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.formatDataTable();
    }
    
    public void update(int trainNumber) throws IOException, MalformedURLException, ParseException {
        if (trainNumber != 0) {
            this.trainNumber = trainNumber;
        }
        JSONObject data = FetchData.trainTimeTable(this.trainNumber);
        this.commuterLineID = data.getString("commuterLineID");
        this.stations.clear();
        JSONArray timeTableRows = data.getJSONArray("timeTableRows");
        this.nextStationPassed = false;
        
        int i;
        for (i = 0; i < timeTableRows.length() - 1; i++) {
            this.addStation(timeTableRows.getJSONObject(i), false);
        }
        this.addStation(timeTableRows.getJSONObject(i), true);
        
        // Siirretaan data taulukkoon
        this.dataTable.getItems().clear();
        this.dataTable.getItems().addAll(FXCollections.observableArrayList(this.stations));
    }
    
    /**
     * Seuraavan aseman lisääminen junan aikatauluun.
     * 
     * @param Rivi VR datasta, onko kyse pääteasemasta
     */
    private void addStation(JSONObject row, boolean lastStop) throws ParseException {
        if ((row.getString("type").equals("DEPARTURE") || lastStop) && row.getBoolean("trainStopping")) {
            String nextStation = "";
            Date liveEstimateTime = null;
            
            if (row.has("liveEstimateTime")) {
                if (!nextStationPassed) {
                    nextStationPassed = true;
                    nextStation = "X";
                }
            }
            
            if (row.has("differenceInMinutes") && row.getInt("differenceInMinutes") > 1) {
                if (row.has("liveEstimateTime")) {
                    liveEstimateTime = dateFormat.parse(row.getString("liveEstimateTime"));
                }
                if (row.has("actualTime")) {
                    liveEstimateTime = dateFormat.parse(row.getString("actualTime"));
                }
            }

            stations.add(new TrainStop(liveEstimateTime, dateFormat.parse(row.getString("scheduledTime")), 
                    nextStation, Station.stationName(row.getInt("stationUICCode"))));
        }
    }
    
    /**
     * Palauttaa junan kirjaimen
     * @return Kirjain
     */
    public String getCommuterLineID() {
        return commuterLineID;
    }

    public TableView getDataTable() {
        return dataTable;
    }

    private void formatDataTable() {
        TableColumn trainTimeColumn = new TableColumn("Lähtö");
        trainTimeColumn.setMaxWidth(50);
        trainTimeColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("time"));
        
        TableColumn trainEstimateColumn = new TableColumn("Arvio");
        trainEstimateColumn.setMaxWidth(50);
        trainEstimateColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("estimate"));
        
        TableColumn trainNextColumn = new TableColumn(" ");
        trainNextColumn.setMaxWidth(20);
        trainNextColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("next"));        

        TableColumn trainStationColumn = new TableColumn("Asema");
        trainStationColumn.setMinWidth(120);
        trainStationColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("station"));
        
        this.dataTable = new TableView();
        this.dataTable.getColumns().addAll(trainTimeColumn, trainEstimateColumn, trainNextColumn, trainStationColumn);
    }
}
