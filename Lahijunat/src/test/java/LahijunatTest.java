import lahijunat.data.StationTable;
import lahijunat.domain.DepartingTrain;
import lahijunat.domain.Station;
import lahijunat.vrapi.FetchData;
import lahijunat.data.SearchTable;
import lahijunat.data.TrainMap;
import lahijunat.domain.SearchItem;
import lahijunat.domain.TrainStop;

import java.io.File;
import java.io.FileWriter;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;

import javafx.scene.control.TableView;

/**
 * Yksikkötestejä Lähijunat -sovellukselle
 * @author lauri
 */
public class LahijunatTest {
    
    public LahijunatTest() {

    }
  
    @Before
    public void setUp() {

    }
    
    // Domain -paketti
    //
    @Test
    public void UnknownStationName() {
        int UIcode = Station.stationUICode("Turku");
        assertEquals(-1,UIcode);
    }
    
    @Test
    public void HiekkaharjuOk() {
        int UIcode = Station.stationUICode("Hiekkaharju");
        assertEquals(556,UIcode);
    }
    
    @Test
    public void HelsinkiOk() {
        String station = Station.stationName(1);
        assertEquals("Helsinki",station);
    }
    
    @Test
    public void UnknownShortNameOk() {
        String station = Station.stationShortName(2);
        assertEquals("---",station);
    }

    @Test
    public void DepartingTrainReturnsEstimate() throws ParseException {
        DepartingTrain train = departingTrainExample();
        assertEquals("18:40",train.getEstimate());
    }    
    
    @Test
    public void DepartingTrainReturnsDestination() throws ParseException {
        DepartingTrain train = departingTrainExample();
        assertEquals("Helsinki",train.getDestination());
    }
    
    @Test
    public void DepartingTrainReturnsTime() throws ParseException {
        DepartingTrain train = departingTrainExample();
        assertEquals("18:36",train.getTime());
    }

    @Test
    public void DepartingTrainReturnsCauses() throws ParseException {
        DepartingTrain train = departingTrainExample();
        train.setCancelled(true);
        train.setCauses("Peruutettu syystä että");
        assertEquals("Peruttu: Peruutettu syystä että",train.getCauses());
    }
    
    @Test
    public void TrainStopReturnsEstimate() throws ParseException {
        DepartingTrain train = departingTrainExample();
        assertEquals("18:40",train.getEstimate());
    }

    @Test
    public void SearchItemReturnsInfo() throws ParseException {
        SearchItem item = new SearchItem("8485", "Espoo 18:07 U");
        assertEquals("Espoo 18:07 U",item.getTrainInfo());
    }
    
    @Test
    public void TrainStopReturnTime() throws ParseException {
        TrainStop item = trainStopExample();
        assertEquals("21:30",item.getTime());
    }    

    @Test
    public void TrainStopReturnEstimate() throws ParseException {
        TrainStop item = trainStopExample();
        assertEquals("21:35",item.getEstimate());
    }    

    @Test
    public void TrainStopReturnStation() throws ParseException {
        TrainStop item = trainStopExample();
        assertEquals("Kauklahti",item.getStation());
    }    
    
    // vrapi -paketti
    //
    @Test
    public void FetchDepartingTrainsReturnsNull() throws IOException, MalformedURLException, ParseException {
        JSONArray station = FetchData.departingTrainsFromStation(2);
        assertEquals(null,station);
    }
    
    @Test
    public void FetchDepartingTrainsReturnsData() throws IOException, MalformedURLException, ParseException {
        JSONArray station = FetchData.departingTrainsFromStation(1);
        assertNotEquals(null,station);
    }

    @Test
    public void FetchTrainTableReturnsData() throws IOException, MalformedURLException, ParseException {
        JSONObject station = FetchData.trainTimeTable(8706);
        assertNotEquals(null,station);
    }

    @Test
    public void FetchTrainsMapReturnsData() throws IOException, MalformedURLException, ParseException {
        JSONArray data = FetchData.allTrainsCoordinates();
        assertNotEquals(null,data);
    }

    @Test
    public void FetchTrainCoordinatesReturnsData() throws IOException, MalformedURLException, ParseException {
        JSONArray data = FetchData.trainCoordinates(8706);
        assertNotEquals(null,data);
    }
    
    // data -paketti
    //
    
    @Test
    public void CreateMap() throws IOException, MalformedURLException, ParseException {
        TrainMap map = new TrainMap(0);
        map.setTrainNumber(8706);
        assertNotEquals(null,map.getTrainMap());
    }

//    @Test
//    public void SearchListInit() throws IOException, MalformedURLException, ParseException {
//        File file = new File("koe.csv");
//        file.delete();
//        SearchTable list = new SearchTable();
//        list.init("koe.csv");
//        assertNotEquals(null,list.getSearches());
//    }
//    
//    @Test
//    public void SearchListAdd() throws IOException, MalformedURLException, ParseException {
//        SearchTable list = new SearchTable();
//        list.init("koe.csv");
//        list.add("8485", "Espoo 18:07 U");
//        assertEquals("Espoo 18:07 U",list.getSearches().get(0).getTrainInfo());
//    }
//
//    @Test
//    public void SearchListAdd2() throws IOException, MalformedURLException, ParseException {
//        SearchTable list = new SearchTable();
//        list.init("koe.csv");
//        list.add("8485", "Espoo 18:07 U");
//        list.add("8363", "Espoo 16:52 E");
//        assertNotEquals("Espoo 18:07 U",list.getSearches().get(1).getTrainInfo());
//    }    
        
//    @Test
//    public void ParseDepartingTrains() throws IOException, MalformedURLException, ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//
//        Scanner fileReader = new Scanner(new File("src/test/java/asema.json"));
//        JSONArray data = new JSONArray(fileReader.nextLine());
//        StationTable stationTable = new StationTable();
//        stationTable.setUicCode(Station.stationUICode("Kauklahti"));
//        stationTable.updateData(data, dateFormat.parse("2020-04-06T18:30:00.000Z"));
//        TableView tableView = stationTable.getDataTable();
//        assertNotEquals(null,stationTable.getDataTable());
//    }
    
    
    private DepartingTrain departingTrainExample () throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        DepartingTrain train=new DepartingTrain("U", 8389, 2);
        train.setCancelled(false);
        train.setDestination(1);
        train.setCauses("[]");
        train.setScheduledTime(dateFormat.parse("2020-03-30T15:36:00.000Z"));
        train.setLiveEstimateTime(dateFormat.parse("2020-03-30T15:40:00.000Z"));
        train.setTrack("3");
        return train;
    }
    
    private TrainStop trainStopExample () throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        TrainStop item = new TrainStop(dateFormat.parse("2020-04-06T18:30:00.000Z"), "Kauklahti");
        item.setLiveEstimateTime(dateFormat.parse("2020-04-06T18:35:00.000Z"));
        item.setNextStation("X");
        return item;
    }
}
