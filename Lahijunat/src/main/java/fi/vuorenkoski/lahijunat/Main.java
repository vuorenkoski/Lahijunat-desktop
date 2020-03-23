package fi.vuorenkoski.lahijunat;

import fi.vuorenkoski.lahijunat.domain.DepartingTrain;
import fi.vuorenkoski.lahijunat.domain.Station;
import fi.vuorenkoski.lahijunat.json.FetchData;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lähijunat - main class
 * @author Lauri Vuorenkoski
 */
public class Main {

    public static void main(String[] args) {
        
        Scanner lukija=new Scanner(System.in);
        System.out.println("Anna asema (esim. Kauklahti):");
        int station=Station.StationUICode(lukija.nextLine());
        if (station==-1) {
            System.out.println("Asemaa ei löydy");
        } else {
            try {
                ArrayList<DepartingTrain> trains=FetchData.DepartingTrainsFromStation(station);
                trains.forEach((train) -> {
                    System.out.println(train);
                });
            } catch (IOException | ParseException ex) {
                System.out.println("Virhe:"+ex.toString());
            }
        }        
    }
    
}
