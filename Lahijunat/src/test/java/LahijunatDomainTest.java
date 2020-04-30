import java.text.DateFormat;
import lahijunat.domain.DepartingTrain;
import lahijunat.domain.SearchItem;
import lahijunat.domain.Station;
import lahijunat.domain.TrainStop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Yksikkötestejä Lähijunat -sovelluksen Domain -pakkaukselle
 * @author Lauri Vuorenkoski
 */
public class LahijunatDomainTest {
    
    public LahijunatDomainTest() {
    }
    
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
