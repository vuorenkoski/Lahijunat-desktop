import java.io.File;
import lahijunat.domain.DepartingTrain;
import org.junit.Test;
import static org.junit.Assert.*;
import lahijunat.domain.Station;
import lahijunat.vrapi.FetchData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.TimeZone;
import lahijunat.data.StationTable;
import org.json.JSONArray;

/**
 * Yksikkötestejä Lähijunat -sovellukselle
 * @author lauri
 */
public class LahijunatTest {
    
    public LahijunatTest() {
    }
  
    @Test
    public void UnknownStationName() {
        int UIcode=Station.stationUICode("Turku");
        assertEquals(-1,UIcode);
    }
    
    @Test
    public void HiekkaharjuOk() {
        int UIcode=Station.stationUICode("Hiekkaharju");
        assertEquals(556,UIcode);
    }
    
    @Test
    public void HelsinkiOk() {
        String station=Station.stationName(1);
        assertEquals("Helsinki",station);
    }
    
    @Test
    public void UnknownShortNameOk() {
        String station=Station.stationShortName(2);
        assertEquals("---",station);
    }

    @Test
    public void DepartingTrainReturnsEstimate() throws ParseException {
        DepartingTrain train=departingTrainExample();
        assertEquals("18:40",train.getEstimate());
    }    
    
    @Test
    public void DepartingTrainReturnsDestination() throws ParseException {
        DepartingTrain train=departingTrainExample();
        assertEquals("Helsinki",train.getDestination());
    }
    
    @Test
    public void DepartingTrainReturnsTime() throws ParseException {
        DepartingTrain train=departingTrainExample();
        assertEquals("18:36",train.getTime());
    }

    @Test
    public void DepartingTrainReturnsCauses() throws ParseException {
        DepartingTrain train=departingTrainExample();
        train.setCancelled(true);
        train.setCauses("Peruutettu syystä että");
        assertEquals("Peruttu: Peruutettu syystä että",train.getCauses());
    }
    
    @Test
    public void TrainStopReturnsEstimate() throws ParseException {
        DepartingTrain train=departingTrainExample();
        assertEquals("18:40",train.getEstimate());
    }
    
    @Test
    public void FetchDepartingTrainsReturnsNull() throws IOException, MalformedURLException, ParseException {
        JSONArray station=FetchData.departingTrainsFromStation(2);
        assertEquals(null,station);
    }
    
//    @Test
//    public void FetchDepartingTrains() throws IOException, MalformedURLException, ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

//        Scanner fileReader = new Scanner(new File("src/test/java/asema.json"));
//        JSONArray data = new JSONArray(fileReader.nextLine());
//        StationTable stationTable = new StationTable();
//        stationTable.setUicCode(Station.stationUICode("Kauklahti"));
//        stationTable.updateData(data, dateFormat.parse("2020-04-06T18:30:00.000Z"));
//        assertNotEquals(null,stationTable);
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
}
