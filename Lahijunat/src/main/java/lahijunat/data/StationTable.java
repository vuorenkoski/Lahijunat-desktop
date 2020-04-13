package lahijunat.data;

import lahijunat.domain.DepartingTrain;
import lahijunat.vrapi.FetchData;

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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Luokka kuvaa yhdeltä asemalta lähtevien junien tietoja.
 * @author Lauri Vuorenkoski
 */
public class StationTable {
    private TableView dataTable;
    private ArrayList<DepartingTrain> trainArray;
    private int uicCode;

    public StationTable() {
        this.formatDataTable();
        this.trainArray = new ArrayList<>();
        this.uicCode = 0;
    }
    
    /**
     * Metodi päivittää asemalta lähtevien junien tiedot. 
     * Mikäli aseman koodi on 0, käytetään haussa aiempaa asemaa. Mikäli aseman koodi on 0 ja aiempaa asemaa ei ole määritelty
     * metodi ei tee mitään.
     * @param uicCode aseman koodi
     */
    public void update(int uicCode) throws MalformedURLException, IOException, ParseException {
        if (uicCode != 0) {
            this.uicCode = uicCode;
        }
        if (this.uicCode == 0) {
            return;
        }
        JSONArray data = FetchData.departingTrainsFromStation(this.uicCode);
        this.updateData(data, new Date(System.currentTimeMillis()));
    }
    
    /**
     * Metodi päivittää asemalta lähtevien junien tiedot. Julkinen testausta varten.
     * @param data Aseman data json muodossa
     * @param currentTime aika jota aiempia lähteneitä junia ei huomioida
     */    
    public void updateData(JSONArray data, Date currentTime) throws ParseException {
        this.trainArray.clear();
        for (int i = 0; i < data.length(); i++) {
            DepartingTrain train = this.parseTrain(this.uicCode, data.getJSONObject(i));
            if (train.getScheduledTime() != null && train.getScheduledTime().compareTo(currentTime) > 0) {
                trainArray.add(train);
            }
        }
        Collections.sort(this.trainArray);

        // Siirretaan data taulukkoon
        this.dataTable.getItems().clear();
        this.dataTable.getItems().addAll(FXCollections.observableArrayList(this.trainArray));
    }
    
    /**
     * Metodilla voi vaihtaa aseman koodia
     * @param uicCode aseman koodi
     */
    public void setUicCode(int uicCode) {
        this.uicCode = uicCode;
    }
    
    /**
     * Metodi palauttaa aseman koodin. 
     * @return aseman koodi (int)
     */
    public int getUicCode() {
        return uicCode;
    }

    /**
     * Metodi palauttaa taulukon jossa asemalta lähtevät junat. 
     * @return Taulukko lähtevistä junista (TableView)
     */
    public TableView getDataTable() {
        return dataTable;
    }
    
    private DepartingTrain parseTrain(int uicCode, JSONObject data) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        DepartingTrain train = new DepartingTrain(data.getString("commuterLineID"), data.getInt("trainNumber"), uicCode);

        JSONArray timeTableRows = data.getJSONArray("timeTableRows");           
        for (int j = 0; j < timeTableRows.length(); j++) {
            JSONObject row = timeTableRows.getJSONObject(j);
            if (row.getInt("stationUICCode") == uicCode && row.getString("type").equals("DEPARTURE") && row.getBoolean("trainStopping")) {
                train.setScheduledTime(dateFormat.parse(row.getString("scheduledTime")));
                if (row.has("liveEstimateTime")) {
                    train.setLiveEstimateTime(dateFormat.parse(row.getString("liveEstimateTime")));
                }
                train.setCancelled(row.getBoolean("cancelled"));
                train.setTrack(row.getString("commercialTrack"));
                train.setCauses(row.getJSONArray("causes").toString());                    
            }
            train.setDestination(row.getInt("stationUICCode"));
        }

        return train;
        
    }
    
    private void formatDataTable() {
        TableColumn trainTimeColumn = addColumn("Lähtö", 50, "time");
        TableColumn trainEstimateColumn = addColumn("Arvio", 50, "estimate");
        TableColumn trainTrackColumn = addColumn("Raide", 50, "track");
        TableColumn trainIdColumn = addColumn("Juna", 50, "commuterLineId");
        TableColumn trainDestinationColumn = addColumn("Pääteasema", 120, "destination");
        TableColumn trainNumberColumn = addColumn("Numero", 75, "trainNumber");
        TableColumn trainCausesColumn = addColumn("Peruutettu ja syy", 430, "causes");
        
        this.dataTable = new TableView();
        this.dataTable.getColumns().addAll(trainTimeColumn, trainEstimateColumn, trainTrackColumn, trainIdColumn, 
                trainDestinationColumn, trainNumberColumn, trainCausesColumn);
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
