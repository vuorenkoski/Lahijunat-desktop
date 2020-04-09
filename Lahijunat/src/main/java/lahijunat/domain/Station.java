package lahijunat.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;

/**
 * Luokalla voi vaihtaa junaasemien koodeja, nimiä ja lyhytnimiä toisikseen.
 * @author Lauri Vuorenkoski
 */
public class Station {

    // Lista sisältää pääkaupunkiseudun juna-asemat sekä lähijunien pääteasemat
    private static final List<String> NAMES = Arrays.asList("Aviapolis", "Espoo", "Helsinki", "Hiekkaharju", "Huopalahti", "Ilmala",
            "Jorvas", "Kannelmäki", "Kauklahti", "Kauniainen", "Kera", "Kilo", "Kirkkonummi", "Kivistö", "Koivuhovi", "Käpylä",
            "Leinelä", "Lentoasema", "Leppävaara", "Louhela", "Malmi", "Malminkartano", "Martinlaakso", "Masala", "Myyrmäki",
            "Mäkkylä", "Oulunkylä", "Pasila", "Pitäjänmäki", "Pohjois-Haaga", "Puistola", "Pukinmäki", "Tapanila", "Tikkurila",
            "Tolsa", "Tuomarila", "Valimo", "Vantaankoski", "Vehkala", "Kerava", "Lahti", "Riihimäki", "Siuntio", "Tampere");
    
    // Lista sisältää pääkaupunkiseudun juna-asemat sekä lähijunien pääteasemat
    private static final List<Integer> CODES = Arrays.asList(1331, 66, 1, 556, 72, 9, 578, 658, 65, 67, 621, 580, 63, 1330, 675,
            977, 1333, 1332, 68, 661, 17, 659, 662, 64, 660, 693, 15, 10, 69, 657, 553, 551, 552, 18, 830, 579, 847, 839, 1337,
            20, 100, 40, 576, 160);

    // Lista sisältää pääkaupunkiseudun juna-asemat sekä lähijunien pääteasemat    
    private static final List<String> SHORTNAMES = Arrays.asList("AVP", "EPO", "HKI", "HKH", "HPL", "ILA", "JRS", "KAN", "KLH", "KNI",
            "KEA", "KIL", "KKN", "KTÖ", "KVH", "KÄP", "LNÄ", "LEN", "LPV", "LOH", "ML", "MLO", "MRL", "MAS", "MYR", "MÄK", "OLK", "PSL",
            "PJM", "POH", "PLA", "PMK", "TNA", "TKL", "TOL", "TRL", "VMO", "VKS", "VEH", "KE", "LH", "RI", "STI", "TPE");
    
    private static final List<String> METROLIPOLITANSTATIONS = Arrays.asList("Aviapolis", "Espoo", "Helsinki", "Hiekkaharju", "Huopalahti", "Ilmala",
            "Kannelmäki", "Kauklahti", "Kauniainen", "Kera", "Kilo", "Kivistö", "Koivuhovi", "Käpylä",
            "Leinelä", "Lentoasema", "Leppävaara", "Louhela", "Malmi", "Malminkartano", "Martinlaakso", "Myyrmäki",
            "Mäkkylä", "Oulunkylä", "Pasila asema", "Pitäjänmäki", "Pohjois-Haaga", "Puistola", "Pukinmäki", "Tapanila", "Tikkurila",
            "Tuomarila", "Valimo", "Vantaankoski", "Vehkala");
    
    private static final List<Integer> LONGITUDE = Arrays.asList(518, 217, 503, 611, 455, 482, 439, 162, 292, 317, 344, 408, 263, 508, 601, 530, 375,
            415, 573, 423, 414, 416, 404, 529, 495, 421, 445, 598, 555, 592, 606, 243, 437, 410, 405);
    
    private static final List<Integer> LATITUDE = Arrays.asList(185, 86, 53, 184, 99, 89, 120, 70, 92, 97, 98, 195, 88, 101, 203, 196, 100, 151, 132, 130,
            159, 142, 102, 110, 79, 104, 111, 157, 123, 144, 173, 87, 103, 166, 176);
    
    /**
     * Metodi palauttaa juna-aseman koko nimen kun sille annetaan parametrina UIC koodi.Mikäli asemaa ei löydy listalta, palautetaan "undefined".
     * @param UICCode
     * @return Aseman koko nimi
     */
    public static String stationName(int uicCode) {
        int index = CODES.indexOf(uicCode);
        if (index == -1) {
            return ("undefined");
        }
        return NAMES.get(index);
    }
    
    /**
     * Metodi palauttaa juna-aseman lyhytnimen kun sille annetaan parametrina UIC koodi.Mikäli asemaa ei löydy listalta, palautetaan "---"
     * @param UICCode
     * @return Aseman lyhenne
     */
    public static String stationShortName(int uicCode) {
        int index = CODES.indexOf(uicCode);
        if (index == -1) {
            return ("---");
        }
        return SHORTNAMES.get(index);
    }
    
    /**
     * Metodi palauttaa juna-aseman UIC koodin  kun sille annetaan parametrina koko nimi.Mikäli asemaa ei löydy listalta, palautetaan -1.
     * @param Name
     * @return Aseman UIcode
     */
    public static int stationUICode(String name) {
        int index = NAMES.indexOf(name);
        if (index == -1) {
            return -1;
        }
        return CODES.get(index);
    }
    
    /**
     * Metodi palauttaa listan pääkaupunkiseudun juna-asemien nimistä.
     * @return Asemalista aakkosjärjestyksessä
     */    
    public static ArrayList<String> stationList() {
        ArrayList<String> list = new ArrayList<String>(METROLIPOLITANSTATIONS);
        return list;
    }
    
    /**
     * Metodi palauttaa listan pääkaupunkiseudun koordinaateista.
     * @return lista koordinaateista mukautettuna pikseleihin.
     */ 
    public static ArrayList<Pair<Integer, Integer>> coordinates() {
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
        for (int i = 0; i < LATITUDE.size(); i++) {
            list.add(new Pair<>(LONGITUDE.get(i), LATITUDE.get(i)));
        }
        return list;
    }

}
