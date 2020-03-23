package fi.vuorenkoski.lahijunat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.json.JSONArray;

/**
 * Luokka jonka metodeilla haetaan dataa VR:n avoimen datan rajapinnasta.
 * @author Lauri Vuorenkoski
 */
public class FetchData {
    private static final String baseAddress="https://rata.digitraffic.fi/api/v1";

    /**
     * Metodi palauttaa listan junista jotka lähtevät asemalta seuraavan kahden tunnin aikana.
     * 
     * @param UICCode
     * @return Lähtevät junat
     * @throws java.net.MalformedURLException
     * @throws java.text.ParseException
     */
    public static ArrayList<DepartingTrain> DepartingTrainsFromStation(int UICCode) 
            throws MalformedURLException, IOException, ParseException {
        if (Station.StationShortName(UICCode).equals("---")) return null;
        ArrayList<DepartingTrain> trainArray=new ArrayList<>();          
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date currentTime=new Date(System.currentTimeMillis());
        
        // Määritellään urlissa että otetaan junat määritellyltä jotka lähtevät asemalta kahden tunnin sisään.
        // Asematieto lisätään urliin lyhytnimenä, esim Helsinki=HEL
        URL url=new URL(baseAddress+"/live-trains/station/"
                +URLEncoder.encode(Station.StationShortName(UICCode),"UTF-8")
                +"?minutes_before_departure=120&minutes_after_departure=0"
                +"&minutes_before_arrival=120&minutes_after_arrival=0"
                +"&train_categories=Commuter");
        Scanner tiedostonLukija = new Scanner(url.openStream());
        JSONArray data=new JSONArray(tiedostonLukija.nextLine());
        
        for (int i=0;i<data.length();i++) {
            // Jokaisen datarivin aluksi on yleisät tietoa junasta
            String commuterLineId=data.getJSONObject(i).getString("commuterLineID");
            int trainNumer=data.getJSONObject(i).getInt("trainNumber");
            Date scheduledTime=new Date();
            Date liveEstimateTime=new Date();
            String track="";
            String causes="";
            int destination=0;
            boolean cancelled=false;

            // Tämän jälkeen on tiedot kaikista asemista jossa kyseinen juna pysähtyy, tuloaika ja lähtöaika
            // Otetaan talteen vaan kyseisen aseman lähtöaika ja pääteasema sekä pääteaseman saapumisaika.
            JSONArray timeTableRows=data.getJSONObject(i).getJSONArray("timeTableRows");           
            for (int j=0;j<timeTableRows.length();j++) {
                if (timeTableRows.getJSONObject(j).getInt("stationUICCode")==UICCode
                        && timeTableRows.getJSONObject(j)
                                .getString("type").equals("DEPARTURE")
                        && timeTableRows.getJSONObject(j)
                                .getBoolean("trainStopping")) {
                    scheduledTime=dateFormat.parse(timeTableRows
                            .getJSONObject(j).getString("scheduledTime"));
                    if (timeTableRows.getJSONObject(j).has("liveEstimateTime")) {
                        liveEstimateTime=dateFormat.parse(timeTableRows
                                .getJSONObject(j).getString("liveEstimateTime"));
                    } else liveEstimateTime=scheduledTime;
                    cancelled=timeTableRows.getJSONObject(j).getBoolean("cancelled");
                    track=timeTableRows.getJSONObject(j).getString("commercialTrack");
                    causes=timeTableRows.getJSONObject(j).getJSONArray("causes").toString();                    
                }
                destination=timeTableRows.getJSONObject(j).getInt("stationUICCode");
            }
            
            // Otetaan juna listaan mukaan jos sille on määritelty raide ja se ei ole vielä lähtenyt asemalta
            if (track.equals("")==false && scheduledTime.compareTo(currentTime)>0) 
                trainArray.add(new DepartingTrain(commuterLineId, trainNumer, 
                        destination, scheduledTime, liveEstimateTime, track, 
                        causes, cancelled));
        }
        // Sortataan junat lähtöajan mukaan
        return trainArray.stream().sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
