package lahijunat.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Luokalla voi vaihtaa junaasemien koodeja, nimiä ja lyhytnimiä toisikseen.
 * @author Lauri Vuorenkoski
 */
public class Station {

    private static final List<String> NAMES = Arrays.asList("Aviapolis", "Espoo", "Helsinki", "Hiekkaharju", "Huopalahti", "Ilmala",
            "Jorvas", "Kannelmäki", "Kauklahti", "Kauniainen", "Kera", "Kilo", "Kirkkonummi", "Kivistö", "Koivuhovi", "Käpylä",
            "Leinelä", "Lentoasema", "Leppävaara", "Louhela", "Malmi", "Malminkartano", "Martinlaakso", "Masala", "Myyrmäki",
            "Mäkkylä", "Oulunkylä", "Pasila", "Pitäjänmäki", "Pohjois-Haaga", "Puistola", "Pukinmäki", "Tapanila", "Tikkurila",
            "Tolsa", "Tuomarila", "Valimo", "Vantaankoski", "Vehkala", "Kerava", "Lahti", "Riihimäki", "Siuntio", "Tampere");
    
    private static final List<Integer> CODES = Arrays.asList(1331, 66, 1, 556, 72, 9, 578, 658, 65, 67, 621, 580, 63, 1330, 675,
            977, 1333, 1332, 68, 661, 17, 659, 662, 64, 660, 693, 15, 10, 69, 657, 553, 551, 552, 18, 830, 579, 847, 839, 1337,
            20, 100, 40, 576, 160);
    
    private static final List<String> SHORTNAMES = Arrays.asList("AVP", "EPO", "HKI", "HKH", "HPL", "ILA", "JRS", "KAN", "KLH", "KNI",
            "KEA", "KIL", "KKN", "KTÖ", "KVH", "KÄP", "LNÄ", "LEN", "LPV", "LOH", "ML", "MLO", "MRL", "MAS", "MYR", "MÄK", "OLK", "PSL",
            "PJM", "POH", "PLA", "PMK", "TNA", "TKL", "TOL", "TRL", "VMO", "VKS", "VEH", "KE", "LH", "RI", "STI", "TPE");
    
    private static final List<String> METROLIPOLITANSTATIONS = Arrays.asList("Aviapolis", "Espoo", "Helsinki", "Hiekkaharju", "Huopalahti", "Ilmala",
            "Kannelmäki", "Kauklahti", "Kauniainen", "Kera", "Kilo", "Kivistö", "Koivuhovi", "Käpylä",
            "Leinelä", "Lentoasema", "Leppävaara", "Louhela", "Malmi", "Malminkartano", "Martinlaakso", "Myyrmäki",
            "Mäkkylä", "Oulunkylä", "Pasila asema", "Pitäjänmäki", "Pohjois-Haaga", "Puistola", "Pukinmäki", "Tapanila", "Tikkurila",
            "Tuomarila", "Valimo", "Vantaankoski", "Vehkala");
    
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
     * Metodi palauttaa listan sellaisten juna-asemien nimistä, jotka sijaitsevat pääkaupunkiseudulla.
     * @return Asemalista aakkosjärjestyksessä
     */    
    public static ArrayList<String> stationList() {
        ArrayList<String> list = new ArrayList<String>(METROLIPOLITANSTATIONS);
        return list;
    }

}
