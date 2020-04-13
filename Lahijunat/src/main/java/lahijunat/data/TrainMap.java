package lahijunat.data;

import lahijunat.domain.Station;
import lahijunat.vrapi.FetchData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONArray;

/**
 * Luokka kuvaa junia kartalla.
 * @author Lauri Vuorenkoski
 */
public class TrainMap {
    Canvas trainMap;
    int trainNumber;

    public TrainMap(int trainNumber) {
        this.trainNumber = trainNumber;
        this.trainMap = new Canvas(650, 450);
    }
     
    /**
     * Metodilla asetetaan junan numero jonka paikka kartalla näytetään. Mikäli junan numeroksi annetaan 0,
     * näytetään kartalla kaikki lähijunat. Jos arvoksi annetaan -1, näytetään vain kartta.
     * @param trainNumber Junan numero
     * @throws IOException
     */
    public void setTrainNumber(int trainNumber) throws IOException {
        this.trainNumber = trainNumber;
        this.update();
    }

    /**
     * Metodilla päivitetään junan/junien nykyinen sijainti
     * @throws MalformedURLException
     * @throws IOException
     */
    public void update() throws MalformedURLException, IOException {
        this.trainMap.getGraphicsContext2D().clearRect(0, 0, trainMap.getWidth(), trainMap.getHeight());
        this.insertStations();
        if (this.trainNumber < 0) {
            return;
        }
        JSONArray data;
        if (this.trainNumber == 0) {
            data = FetchData.allTrainsCoordinates();
        } else {
            data = FetchData.trainCoordinates(trainNumber);
        }
        trainMap.getGraphicsContext2D().setFill(Color.RED);
        for (int i = 0; i < data.length(); i++) {
            int x = (int) ((data.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").getDouble(0) * 1000) - 24438);
            int y = (int) ((data.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").getDouble(1) * 1000) - 60119);
            trainMap.getGraphicsContext2D().fillOval((int) (x - 130) * 1.2, (int) (450 - y * 2) * 1.2, 5, 5);  
        }
    }
    
    /**
     * Metodi palauttaa kartan.
     * @return kartta
     */
    public Canvas getTrainMap() {
        return trainMap;
    }

    private void insertStations() {
        ArrayList<Pair<Integer, Integer>> coordinates = Station.coordinates();
        trainMap.getGraphicsContext2D().setFill(Color.BLACK);
        for (int i = 0; i < coordinates.size(); i++) {
            this.trainMap.getGraphicsContext2D().fillOval((int) (coordinates.get(i).getKey() - 130) * 1.2,
                    (int) (450 - coordinates.get(i).getValue() * 2) * 1.2, 7, 7);
        }
        trainMap.getGraphicsContext2D().fillText("Helsinki", 390, 415);
        trainMap.getGraphicsContext2D().fillText("Espoo", 60, 335);
        trainMap.getGraphicsContext2D().fillText("Tikkurila", 510, 125);
        trainMap.getGraphicsContext2D().fillText("Kivistö", 290, 75);
    }
}
