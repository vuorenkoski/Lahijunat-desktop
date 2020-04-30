package lahijunat.data;

import lahijunat.domain.DepartingTrain;
import lahijunat.domain.Station;
import lahijunat.domain.TrainStop;
import lahijunat.vrapi.FetchData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private boolean cancelled;

    public TrainTable() {
        this.commuterLineID = "";
        this.trainNumber = 0;
        this.stations = new ArrayList<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.formatDataTable();
    }
    
    /**
     * Metodi palauttaa junan symbolin
     * @return Symboli (kirjain)
     */
    public String getCommuterLineID() {
        return commuterLineID;
    }

    /**
     * Metodi palauttaa junan aikataulun taulukkona
     * @return Aikataulu (TableView)
     */
    public TableView getDataTable() {
        return dataTable;
    }

    public ArrayList<TrainStop> getStations() {
        return stations;
    }
        
    /**
     * Metodi päivittää junan aikataulun. 
     * Mikäli junan koodi on 0, junaa ei muuteta. Mikäli koodi on 0, ja junaa ei ole aiemmin
     * määritetty, metodi ei tee mitään.
     * @param trainNumber aseman koodi
     */
    public void update(int trainNumber) throws IOException, MalformedURLException, ParseException {
        if (trainNumber != 0) {
            this.trainNumber = trainNumber;
        }
        if (this.trainNumber == 0) {
            return;
        }
        JSONObject data = FetchData.trainTimeTable(this.trainNumber);
        this.updateData(data);
    }
    
    /**
     * Metodi päivittää junan aikataulun. Julkinen testausta varten.
     * @param data Aseman data json muodossa
     */   
    public void updateData(JSONObject data) throws IOException, MalformedURLException, ParseException {   
        this.commuterLineID = data.getString("commuterLineID");
        this.cancelled = data.getBoolean("cancelled");
        this.stations.clear();
        JSONArray timeTableRows = data.getJSONArray("timeTableRows");
        this.nextStationPassed = false;
        
        int i;
        for (i = 0; i < timeTableRows.length() - 1; i++) {
            this.addStation(timeTableRows.getJSONObject(i), false);
        }
        this.addStation(timeTableRows.getJSONObject(i), true);
        
        if (!this.nextStationPassed) {
            stations.get(0).setNextStation("X");
        }
        this.updateTable();
    }
    
    private void updateTable() {
        this.dataTable.getItems().clear();
        if (this.cancelled) {
            this.dataTable.getItems().add(new TrainStop(null, "PERUTTU"));
        } else { 
            this.dataTable.getItems().addAll(FXCollections.observableArrayList(this.stations));
        }
    }
    
    /**
     * Seuraavan aseman lisääminen junan aikatauluun.
     * 
     * @param Rivi VR datasta, onko kyse pääteasemasta
     */
    private void addStation(JSONObject row, boolean lastStop) throws ParseException {
        if ((row.getString("type").equals("DEPARTURE") || lastStop) && row.getBoolean("trainStopping")) {
            TrainStop trainStop = new TrainStop(dateFormat.parse(row.getString("scheduledTime")), Station.stationName(row.getInt("stationUICCode")));
            
            if (row.has("liveEstimateTime")) {
                if (!nextStationPassed) {
                    nextStationPassed = true;
                    trainStop.setNextStation("X");
                }
            }
            
            if (row.has("differenceInMinutes") && row.getInt("differenceInMinutes") > 1) {
                if (row.has("liveEstimateTime")) {
                    trainStop.setLiveEstimateTime(dateFormat.parse(row.getString("liveEstimateTime")));
                }
                if (row.has("actualTime")) {
                    trainStop.setLiveEstimateTime(dateFormat.parse(row.getString("actualTime")));
                }
            }

            stations.add(trainStop);
        }
    }

    private void formatDataTable() {
        TableColumn trainTimeColumn = addColumn("Lähtö", 50, "time");
        TableColumn trainEstimateColumn = addColumn("Arvio", 50, "estimate");
        TableColumn trainNextColumn = addColumn(" ", 20, "next");
        TableColumn trainStationColumn = addColumn("Asema", 120, "station");
        
        this.dataTable = new TableView();
        this.dataTable.getColumns().addAll(trainTimeColumn, trainEstimateColumn, trainNextColumn, trainStationColumn);
        this.dataTable.setMaxWidth(245);
    }
    
    private TableColumn addColumn(String title, int width, String data) {
        TableColumn column = new TableColumn(title);
        column.setMinWidth(width);
        column.setMaxWidth(width);
        column.setSortable(false);
        column.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>(data));
        return column;
    }
}
