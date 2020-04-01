import lahijunat.domain.DepartingTrain;
import org.junit.Test;
import static org.junit.Assert.*;
import lahijunat.domain.Station;
import lahijunat.domain.TrainStop;
import lahijunat.vrapi.FetchData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        DepartingTrain train=new DepartingTrain("U", 8389, 2, 1, dateFormat.parse("2020-03-30T15:36:00.000Z"), dateFormat.parse("2020-03-30T15:40:00.000Z"), "3", "Peruutettu syystä että", false);
        assertEquals("18:40",train.getEstimate());
    }    
    
    @Test
    public void DepartingTrainReturnsDestination() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        DepartingTrain train=new DepartingTrain("U", 8389, 2, 1, dateFormat.parse("2020-03-30T15:36:00.000Z"), dateFormat.parse("2020-03-30T15:40:00.000Z"), "3", "Peruutettu syystä että", false);
        assertEquals("Helsinki",train.getDestination());
    }
    
    @Test
    public void DepartingTrainReturnsTime() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        DepartingTrain train=new DepartingTrain("U", 8389, 2, 1, dateFormat.parse("2020-03-30T15:36:00.000Z"), dateFormat.parse("2020-03-30T15:40:00.000Z"), "3", "Peruutettu syystä että", false);
        assertEquals("18:36",train.getTime());
    }

    @Test
    public void DepartingTrainReturnsCauses() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        DepartingTrain train=new DepartingTrain("U", 8389, 2, 1, dateFormat.parse("2020-03-30T15:36:00.000Z"), dateFormat.parse("2020-03-30T15:40:00.000Z"), "3", "Peruutettu syystä että", false);
        assertEquals("Peruutettu syystä että",train.getCauses());
    }
    
    @Test
    public void TrainStopReturnsEstimate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        TrainStop train=new TrainStop(dateFormat.parse("2020-03-30T15:40:00.000Z"), dateFormat.parse("2020-03-30T15:36:00.000Z"),  " ", "Kauklahti");
        assertEquals("18:40",train.getEstimate());
    }
    
    @Test
    public void FetchDepartingTrainsReturnsNull() throws IOException, MalformedURLException, ParseException {
        JSONArray station=FetchData.departingTrainsFromStation(2);
        assertEquals(null,station);
    }
}
