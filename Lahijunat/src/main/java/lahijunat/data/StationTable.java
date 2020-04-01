package lahijunat.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lahijunat.domain.DepartingTrain;
import lahijunat.vrapi.FetchData;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Lauri Vuorenkoski
 */
public class StationTable {
    private TableView dataTable;
    private ArrayList<DepartingTrain> trainArray;
    private int uicCode;

    public StationTable() {
        this.formatDataTable();
        this.trainArray = new ArrayList<>();
        uicCode = 0;
    }
    
    public void update(int uicCode) throws MalformedURLException, IOException, ParseException {
        if (uicCode != 0) {
            this.uicCode = uicCode;
        }
        this.trainArray.clear();
        JSONArray data = FetchData.departingTrainsFromStation(this.uicCode);
        for (int i = 0; i < data.length(); i++) {
            DepartingTrain train = this.parseTrain(this.uicCode, data.getJSONObject(i));
            if (train != null && train.getScheduledTime().compareTo(new Date(System.currentTimeMillis())) > 0) {
                trainArray.add(train);
            }
        }
        // Sortataan junat lähtöajan mukaan
        Collections.sort(this.trainArray);

        // Siirretaan data taulukkoon
        this.dataTable.getItems().clear();
        this.dataTable.getItems().addAll(FXCollections.observableArrayList(this.trainArray));
    }
    
    private DepartingTrain parseTrain(int uicCode, JSONObject data) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Jokaisen datarivin aluksi on yleisät tietoa junasta
        String commuterLineId = data.getString("commuterLineID");
        int trainNumer = data.getInt("trainNumber");
        Date scheduledTime = new Date();
        Date liveEstimateTime = new Date();
        String track = "";
        String causes = "";
        int destination = 0;
        boolean cancelled = false;

        // Tämän jälkeen on tiedot kaikista asemista jossa kyseinen juna pysähtyy, tuloaika ja lähtöaika
        // Otetaan talteen vaan kyseisen aseman lähtöaika ja pääteasema sekä pääteaseman saapumisaika.
        JSONArray timeTableRows = data.getJSONArray("timeTableRows");           
        for (int j = 0; j < timeTableRows.length(); j++) {
            JSONObject row = timeTableRows.getJSONObject(j);
            if (row.getInt("stationUICCode") == uicCode && row.getString("type").equals("DEPARTURE") && row.getBoolean("trainStopping")) {
                scheduledTime = dateFormat.parse(row.getString("scheduledTime"));
                if (row.has("liveEstimateTime")) {
                    liveEstimateTime = dateFormat.parse(row.getString("liveEstimateTime"));
                } else {
                    liveEstimateTime = scheduledTime;
                }
                cancelled = row.getBoolean("cancelled");
                track = row.getString("commercialTrack");
                causes = row.getJSONArray("causes").toString();                    
            }
            destination = row.getInt("stationUICCode");
        }

        // Otetaan juna listaan mukaan jos sille on määritelty raide ja se ei ole vielä lähtenyt asemalta
        if (track.equals("") == false)  {
            return new DepartingTrain(commuterLineId, trainNumer, uicCode, destination, 
                    scheduledTime, liveEstimateTime, track, causes, cancelled);
        }
        return null;
    }

    public int getUicCode() {
        return uicCode;
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
        
        TableColumn trainTrackColumn = new TableColumn("Raide");
        trainTrackColumn.setMaxWidth(50);
        trainTrackColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("track"));
        
        TableColumn trainIdColumn = new TableColumn("Juna");
        trainIdColumn.setMaxWidth(50);
        trainIdColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("commuterLineId"));
        
        TableColumn trainDestinationColumn = new TableColumn("Pääteasema");
        trainDestinationColumn.setMinWidth(120);
        trainDestinationColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("destination"));
        
        TableColumn trainNumberColumn = new TableColumn("Numero");
        trainNumberColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, Integer>("trainNumber"));
        
        TableColumn trainCausesColumn = new TableColumn("Peruutuksen syy");
        trainCausesColumn.setMinWidth(430);
        trainCausesColumn.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>("causes"));        
        
        this.dataTable = new TableView();
        this.dataTable.getColumns().addAll(trainTimeColumn, trainEstimateColumn, trainTrackColumn, trainIdColumn, 
                trainDestinationColumn, trainNumberColumn, trainCausesColumn);
    }
}
