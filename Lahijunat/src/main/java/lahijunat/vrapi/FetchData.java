package lahijunat.vrapi;

import lahijunat.domain.Station;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Luokan metodeilla haetaan dataa VR:n avoimen datan rajapinnasta.
 * https://www.digitraffic.fi/rautatieliikenne/
 * @author Lauri Vuorenkoski
 */
public class FetchData {
    private static final String BASEADDRESS = "https://rata.digitraffic.fi/api/v1";

    /**
     * Metodi palauttaa datan junista, jotka lähtevät asemalta seuraavan kahden tunnin aikana.
     * @param uicCode Aseman koodi
     * @return Lähtevät junat -datalista (JSONArray)
     */
    public static JSONArray departingTrainsFromStation(int uicCode) throws MalformedURLException, IOException {
        if (Station.stationShortName(uicCode).equals("---")) {
            return null;
        }
        
        // Määritellään urlissa että otetaan junat määritellyltä jotka lähtevät asemalta kahden tunnin sisään.
        // Asematieto lisätään urliin lyhytnimenä, esim Helsinki=HEL
        String url = "/live-trains/station/" +
                URLEncoder.encode(Station.stationShortName(uicCode), "UTF-8") +
                "?minutes_before_departure=120&minutes_after_departure=0" +
                "&minutes_before_arrival=120&minutes_after_arrival=0" +
                "&train_categories=Commuter";
        JSONArray data = new JSONArray(getData(url));
        return data;
    }
       
    /**
     * Metodi palauttaa datan junan aikautalusta.
     * @param trainNumber Junan numero
     * @return Junan aikataulu -data (JSONObject)
     */
    public static JSONObject trainTimeTable(int trainNumber) throws MalformedURLException, IOException {               
        JSONArray data = new JSONArray(getData("/trains/latest/" + trainNumber));
        return data.getJSONObject(0);
    }
    
    /**
     * Metodi palauttaa datan kaikkien junien koordinaateista.
     * @return Junien koordinaatit (JSONArray)
     */
    public static JSONArray allTrainsCoordinates() throws MalformedURLException, IOException {
        return new JSONArray(getData("/train-locations/latest?bbox=24,60,25,62"));
    }
    
    /**
     * Metodi palauttaa datan junan koordinaateista. Lista, jossa vain yksi rivi.
     * @param trainNumber Junan numero
     * @return Junan koordinaatit (JSONArray)
     */
    public static JSONArray trainCoordinates(int trainNumber) throws MalformedURLException, IOException {
        return new JSONArray(getData("/train-locations/latest/" + trainNumber));
    }
    
    private static String getData(String address) throws MalformedURLException, IOException {
        URL url = new URL(BASEADDRESS + address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Accept-Encoding", "gzip");
        Scanner urlReader = null;
        if ("gzip".equals(con.getContentEncoding())) {
            urlReader = new Scanner(new GZIPInputStream(con.getInputStream()));
        }
        else {
            urlReader = new Scanner(con.getInputStream());
        }
        return urlReader.nextLine();
    }
}
