import lahijunat.data.StationTable;
import lahijunat.domain.Station;
import lahijunat.data.SearchTable;
import lahijunat.data.TrainMap;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TimeZone;
import javafx.application.Application;
import org.json.JSONArray;

import lahijunat.data.TrainTable;
import lahijunat.domain.TrainStop;
import org.json.JSONObject;
import org.junit.BeforeClass;

/**
 * Yksikkötestejä Lähijunat -sovelluksen Data -pakkaukselle
 * @author Lauri Vuorenkoski
 */
public class LahijunatDataTest {
    
    public LahijunatDataTest() {
    }
  
    @BeforeClass
    public static void setUp() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(MockApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }
    
   
    @Test
    public void CreateMap() throws IOException, MalformedURLException, ParseException {
        // Haetaan data junasta joka seuraavaksi lähtee Kauklahden asemalta
        StationTable stationTable = new StationTable();
        stationTable.update(65);
        
        TrainMap map = new TrainMap(0);
        map.setTrainNumber(stationTable.getTrainArray().get(0).getTrainNumber());
        assertNotEquals(null,map.getTrainMap());
    }

    @Test
    public void SearchListInit() throws IOException, MalformedURLException, ParseException {
        File file = new File("koe.csv");
        file.delete();
        SearchTable list = new SearchTable();
        list.init("koe.csv");
        assertEquals("-",list.getSearches().get(0).getTrainInfo());
    }
    
    @Test
    public void SearchListAdd() throws IOException, MalformedURLException, ParseException {
        SearchTable list = new SearchTable();
        list.init("koe.csv");
        list.add("8485", "Espoo 18:07 U");
        assertEquals("Espoo 18:07 U",list.getSearches().get(0).getTrainInfo());
    }

    @Test
    public void SearchListAdd2() throws IOException, MalformedURLException, ParseException {
        SearchTable list = new SearchTable();
        list.init("koe.csv");
        list.add("8485", "Espoo 18:07 U");
        list.add("8363", "Espoo 16:52 E");
        assertEquals("Espoo 18:07 U",list.getSearches().get(1).getTrainInfo());
    }    
        
    @Test
    public void ParseDepartingTrainsFirstOk() throws IOException, MalformedURLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Scanner fileReader = new Scanner(new File("src/test/java/asema.json"));
        JSONArray data = new JSONArray(fileReader.nextLine());
        StationTable stationTable = new StationTable();
        stationTable.setUicCode(Station.stationUICode("Kauklahti"));
        stationTable.updateData(data, dateFormat.parse("2020-04-06T18:30:00.000Z"));
        assertEquals("21:35",stationTable.getTrainArray().get(0).getTime());
    }
    
    @Test
    public void ParseDepartingTrainsCountOk() throws IOException, MalformedURLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Scanner fileReader = new Scanner(new File("src/test/java/asema.json"));
        JSONArray data = new JSONArray(fileReader.nextLine());
        StationTable stationTable = new StationTable();
        stationTable.setUicCode(Station.stationUICode("Kauklahti"));
        stationTable.updateData(data, dateFormat.parse("2020-04-06T18:30:00.000Z"));
        assertEquals(10,stationTable.getTrainArray().size());
    }    

    @Test
    public void ParseTrainFirstOk() throws IOException, MalformedURLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Scanner fileReader = new Scanner(new File("src/test/java/junaTestData.json"));
        JSONArray data = new JSONArray(fileReader.nextLine());
        TrainTable trainTable = new TrainTable();
        trainTable.updateData(data.getJSONObject(0));
        assertEquals("Helsinki",trainTable.getStations().get(0).getStation());
    }    

    @Test
    public void ParseCancelledTrainOk() throws IOException, MalformedURLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Scanner fileReader = new Scanner(new File("src/test/java/junaPeruttu.json"));
        JSONArray data = new JSONArray(fileReader.nextLine());
        TrainTable trainTable = new TrainTable();
        trainTable.updateData(data.getJSONObject(0));
        TrainStop trainStop = (TrainStop) trainTable.getDataTable().getItems().get(0);
        assertEquals("PERUTTU",trainStop.getStation());
    }
    
    @Test
    public void ParseLateTrainOk() throws IOException, MalformedURLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Scanner fileReader = new Scanner(new File("src/test/java/junaMyohassa.json"));
        JSONArray data = new JSONArray(fileReader.nextLine());
        TrainTable trainTable = new TrainTable();
        trainTable.updateData(data.getJSONObject(0));
        TrainStop trainStop = (TrainStop) trainTable.getDataTable().getItems().get(5);
        assertEquals("21:25",trainStop.getEstimate());
    }  
}
