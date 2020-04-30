import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import javafx.application.Application;
import lahijunat.data.StationTable;
import lahijunat.vrapi.FetchData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * Yksikkötestejä Lähijunat -sovelluksen Vrapi -pakkaukselle
 * @author Lauri Vuorenkoski
 */
public class LahijunatVrapiTest {
    
    public LahijunatVrapiTest() {
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
        // Haetaan data junasta joka seuraavaksi lähtee Kauklahden asemalta
        StationTable stationTable = new StationTable();
        stationTable.update(65);
        JSONObject station = FetchData.trainTimeTable(stationTable.getTrainArray().get(0).getTrainNumber());
        assertNotEquals(null,station);
    }

    @Test
    public void FetchTrainsMapReturnsData() throws IOException, MalformedURLException, ParseException {
        JSONArray data = FetchData.allTrainsCoordinates();
        assertNotEquals(null,data);
    }

    @Test
    public void FetchTrainCoordinatesReturnsData() throws IOException, MalformedURLException, ParseException {
        // Haetaan data junasta joka seuraavaksi lähtee Kauklahden asemalta
        StationTable stationTable = new StationTable();
        stationTable.update(65);
        JSONArray data = FetchData.trainCoordinates(stationTable.getTrainArray().get(0).getTrainNumber());
        assertNotEquals(null,data);
    }
}
