import fi.vuorenkoski.lahijunat.DepartingTrain;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import fi.vuorenkoski.lahijunat.Station;
import fi.vuorenkoski.lahijunat.FetchData;
import java.util.ArrayList;

/**
 * Yksikkötestejä Lähijunat -sovellukselle
 * @author lauri
 */
public class LahijunatTest {
    
    public LahijunatTest() {
    }
  
    @Test
    public void UnknownStationName() {
        int UIcode=Station.StationUICode("Turku");
        assertEquals(-1,UIcode);
    }
    
    @Test
    public void HiekkaharjuOk() {
        int UIcode=Station.StationUICode("Hiekkaharju");
        assertEquals(556,UIcode);
    }
    
    @Test
    public void HelsinkiOk() {
        String station=Station.StationName(1);
        assertEquals("Helsinki",station);
    }
    
    @Test
    public void UnknownShortNameOk() {
        String station=Station.StationShortName(2);
        assertEquals("---",station);
    }
    
}
