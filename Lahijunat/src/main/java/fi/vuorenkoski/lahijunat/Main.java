package fi.vuorenkoski.lahijunat;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lähijunat - main class
 * @author Lauri Vuorenkoski
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<DepartingTrain> trains=new ArrayList<>();
        
        Scanner lukija=new Scanner(System.in);
        System.out.println("Anna asema (esim. Kauklahti):");
        int station=Station.StationUICode(lukija.nextLine());
        if (station==-1) {
            System.out.println("Asemaa ei löydy");
        } else {
            try {
                trains=FetchData.DepartingTrainsFromStation(station);
                for (DepartingTrain train:trains) {
                    System.out.println(train);
                }
            } catch (Exception ex) {
                System.out.println("Virhe:"+ex.toString());
            }
        }        
    }
    
}
