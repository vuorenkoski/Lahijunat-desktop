package lahijunat.domain;

/**
 * Luokka kuvaa yhtä talletettua hakua.
 * @author Lauri Vuorenkoski
 */
public class SearchItem {
    private String trainInfo;
    private String trainNumber;

    public SearchItem(String trainNumber, String trainInfo) {
        this.trainInfo = trainInfo;
        this.trainNumber = trainNumber;
    }

    public String getTrainInfo() {
        return trainInfo;
    }

    public String getTrainNumber() {
        return trainNumber;
    }
    
    
}
