package fi.vuorenkoski.lahijunat.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Metodilla voi vaihtaa junaasemien koodeja, nimiä ja lyhytnimiä toisikseen.
 * @author lauri
 */
public class Station {

    private static final List<String> names=Arrays.asList("Aviapolis","Espoo","Helsinki","Hiekkaharju","Huopalahti","Ilmala"
            ,"Jorvas","Kannelmäki","Kauklahti","Kauniainen","Kera","Kilo","Kirkkonummi","Kivistö","Koivuhovi","Käpylä"
            ,"Leinelä","Lentoasema","Leppävaara","Louhela","Malmi","Malminkartano","Martinlaakso","Masala","Myyrmäki"
            ,"Mäkkylä","Oulunkylä","Pasila","Pitäjänmäki","Pohjois-Haaga","Puistola","Pukinmäki","Tapanila","Tikkurila"
            ,"Tolsa","Tuomarila","Valimo","Vantaankoski","Vehkala","Kerava","Lahti","Riihimäki","Siuntio","Tampere");
    
    private static final List<Integer> codes=Arrays.asList(1331,66,1,556,72,9,578,658,65,67,621,580,63,1330,675,977,1333,1332
            ,68,661,17,659,662,64,660,693,15,10,69,657,553,551,552,18,830,579,847,839,1337,20,100,40,576,160);
    
    private static final List<String> shortNames=Arrays.asList("AVP","EPO","HKI","HKH","HPL","ILA","JRS","KAN","KLH","KNI"
            ,"KEA","KIL","KKN","KTÖ","KVH","KÄP","LNÄ","LEN","LPV","LOH","ML","MLO","MRL","MAS","MYR","MÄK","OLK","PSL"
            ,"PJM","POH","PLA","PMK","TNA","TKL","TOL","TRL","VMO","VKS","VEH","KE","LH","RI","STI","TPE");
    
    /**
     * Metodi palauttaa juna-aseman koko nimen kun sille annetaan parametrina UIC koodi.Mikäli asemaa ei löydy listalta, palautetaan "undefined".
     * @param UICCode
     * @return Aseman koko nimi
     */
    public static String StationName(int UICCode) {
        int index=codes.indexOf(UICCode);
        if (index==-1) return ("undefined");
        return names.get(index);
    }
    
    /**
     * Metodi palauttaa juna-aseman lyhytnimen kun sille annetaan parametrina UIC koodi.Mikäli asemaa ei löydy listalta, palautetaan "---"
     * @param UICCode
     * @return Aseman lyhenne
     */
    public static String StationShortName(int UICCode) {
        int index=codes.indexOf(UICCode);
        if (index==-1) return ("---");
        return shortNames.get(index);
    }
    
    /**
     * Metodi palauttaa juna-aseman UIC koodin  kun sille annetaan parametrina koko nimi.Mikäli asemaa ei löydy listalta, palautetaan -1.
     * @param Name
     * @return Aseman UIcode
     */
    public static int StationUICode(String Name) {
        int index=names.indexOf(Name);
        if (index==-1) return -1;
        return codes.get(index);
    }

}
