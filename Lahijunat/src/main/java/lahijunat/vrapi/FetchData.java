package lahijunat.vrapi;

import lahijunat.domain.Station;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Luokka jonka metodeilla haetaan dataa VR:n avoimen datan rajapinnasta.
 * @author Lauri Vuorenkoski
 */
public class FetchData {
    private static final String BASEADDRESS = "https://rata.digitraffic.fi/api/v1";

    /**
     * Metodi palauttaa datan junista jotka lähtevät asemalta seuraavan kahden tunnin aikana.
     * 
     * @param UICCode
     * @return Lähtevät junat -datalista (JSONArray)
     */
    public static JSONArray departingTrainsFromStation(int uicCode) throws MalformedURLException, IOException, ParseException {
        if (Station.stationShortName(uicCode).equals("---")) {
            return null;
        }
        
        // Määritellään urlissa että otetaan junat määritellyltä jotka lähtevät asemalta kahden tunnin sisään.
        // Asematieto lisätään urliin lyhytnimenä, esim Helsinki=HEL
        URL url = new URL(BASEADDRESS + "/live-trains/station/" +
                URLEncoder.encode(Station.stationShortName(uicCode), "UTF-8") +
                "?minutes_before_departure=120&minutes_after_departure=0" +
                "&minutes_before_arrival=120&minutes_after_arrival=0" +
                "&train_categories=Commuter");
        Scanner urlReader = new Scanner(url.openStream());
        JSONArray data = new JSONArray(urlReader.nextLine());
        return data;
    }
       
    /**
     * Metodi palauttaa datan junan aikautalusta.
     * 
     * @param trainNumber
     * @return Junan aikataulu -data (JSONObject)
     */
    public static JSONObject trainTimeTable(int trainNumber) throws MalformedURLException, IOException, ParseException {               
        URL url = new URL(BASEADDRESS + "/trains/latest/" + trainNumber);
        Scanner urlReader = new Scanner(url.openStream());
        JSONArray data = new JSONArray(urlReader.nextLine());
        return data.getJSONObject(0);
    }
}