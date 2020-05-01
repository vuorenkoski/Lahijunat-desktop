package lahijunat.ui;

import lahijunat.domain.DepartingTrain;
import lahijunat.domain.Station;
import lahijunat.domain.SearchItem;
import lahijunat.data.StationTable;
import lahijunat.data.TrainMap;
import lahijunat.data.TrainTable;
import lahijunat.data.SearchTable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Lähijunat - graafinen käyttöliittymä.
 * @author Lauri Vuorenkoski
 */
public class Gui extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Label errorText = new Label(" ");
        BorderPane rootView = new BorderPane();
        rootView.setCenter(this.constructTabView(errorText));
        rootView.setBottom(errorText);
        Scene scene = new Scene(rootView, 900, 700);
        primaryStage.setTitle("VR lähijunien aikataulut");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private TabPane constructTabView(Label errorText) {
        Tab stationTab = new Tab("Asema");
        Tab trainTab = new Tab("Juna");
        Tab mapTab = new Tab("Kartta");
        Tab searchTab = new Tab("Aiemmat haut");
        Tab helpTab = new Tab("Ohje");
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(stationTab, trainTab, mapTab, searchTab, helpTab);
        
        SearchTable searchTable = this.constructSearchView(searchTab, errorText);
        StationTable stationTable = this.constructStationView(stationTab, errorText);
        this.constructTrainTab(trainTab, stationTable, searchTable, tabPane, errorText);
        this.constructMapView(mapTab, errorText);
        this.constructHelpView(helpTab, errorText);
        
        return tabPane;
    }
    
    private void constructMapView(Tab mapTab, Label errorText) {
        BorderPane mapView = new BorderPane();
        mapView.setPadding(new Insets(20));
        mapTab.setContent(mapView);
        TrainMap trainsMap = new TrainMap(0);
        mapView.setCenter(trainsMap.getTrainMap());
        Timeline tenSecondPeriod = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (mapTab.isSelected()) {
                        trainsMap.update();
                    }
                    errorText.setText("");
                } catch (Exception ex) {
                    errorText.setText(errorMessage(ex));
                }                  
            }
        })); 
        tenSecondPeriod.setCycleCount(Timeline.INDEFINITE);
        tenSecondPeriod.play();
        mapTab.setOnSelectionChanged((Event t) -> {
            if (mapTab.isSelected()) {
                tenSecondPeriod.jumpTo(Duration.seconds(9));
            }
        });
    }
    
    private void constructTrainTab(Tab trainTab, StationTable stationTable, SearchTable searchTable, TabPane tabPane, Label errorText) {
        BorderPane trainDetailsView = new BorderPane();
        trainDetailsView.setPadding(new Insets(20));
        trainTab.setContent(trainDetailsView);
        Label trainNumber = new Label("Juna: ");
        trainNumber.setFont(new Font("Arial", 24));
        trainDetailsView.setTop(trainNumber);
        
        TrainTable trainTable = new TrainTable();
        TrainMap trainMap = new TrainMap(-1);
        trainDetailsView.setLeft(trainTable.getDataTable());
        trainDetailsView.setCenter(trainMap.getTrainMap());
        
        // EventHandler asemalistalle
        stationTable.getDataTable().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    DepartingTrain train = (DepartingTrain) stationTable.getDataTable().getSelectionModel().getSelectedItem();
                    if (train != null) {
                        try {
                            trainTable.update(train.getTrainNumber());
                            trainMap.setTrainNumber(train.getTrainNumber());
                            trainMap.update();
                            trainTab.setText("Juna: " + train);
                            trainNumber.setText("Juna: " + train);
                            tabPane.getSelectionModel().select(trainTab);
                            searchTable.add(String.valueOf(train.getTrainNumber()), train.toString());
                            errorText.setText("");
                        } catch (Exception ex) {
                            errorText.setText(errorMessage(ex));
                        }      
                    }
                }
            }
        });
        
        // EventHandler hakulistalle
        searchTable.getDataTable().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    SearchItem item = (SearchItem) searchTable.getDataTable().getSelectionModel().getSelectedItem();
                    if (item != null) {
                        try {
                            trainTable.update(Integer.valueOf(item.getTrainNumber()));
                            trainMap.setTrainNumber(Integer.valueOf(item.getTrainNumber()));
                            trainMap.update();
                            trainTab.setText("Juna: " + item.getTrainInfo());
                            trainNumber.setText("Juna: " + item.getTrainInfo());
                            tabPane.getSelectionModel().select(trainTab);
                            errorText.setText("");
                        } catch (Exception ex) {
                            errorText.setText(errorMessage(ex));
                        }        
                    }
                }
            }
        });
        
        Timeline twoMinutePeriod = new Timeline(new KeyFrame(Duration.seconds(120), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (trainTab.isSelected()) {
                    try {
                        trainMap.update();
                        trainTable.update(0);
                        errorText.setText("");
                    } catch (Exception ex) {
                        errorText.setText(errorMessage(ex));
                    }            
                }
            }
        })); 
        twoMinutePeriod.setCycleCount(Timeline.INDEFINITE);
        twoMinutePeriod.play();
    }
    
    private StationTable constructStationView(Tab stationTab, Label errorText) {
        BorderPane stationView = new BorderPane();
        stationView.setPadding(new Insets(20));
        stationTab.setContent(stationView);
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(Station.stationList()));
        comboBox.setPromptText("valitse asema");
        stationView.setTop(comboBox);
 
        StationTable stationTable = new StationTable();

        stationView.setCenter(stationTable.getDataTable());
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    stationTable.update(Station.stationUICode(comboBox.getValue().toString()));
                    stationTab.setText("Asema: " + comboBox.getValue().toString());
                    errorText.setText("");
                } catch (Exception ex) {
                    errorText.setText(errorMessage(ex));
                }
            }
        });
        return stationTable;
    }
    
    private SearchTable constructSearchView(Tab searchTab, Label errorText) {
        BorderPane searchView = new BorderPane();
        searchView.setPadding(new Insets(20));
        searchTab.setContent(searchView);
        SearchTable searchTable = new SearchTable();
        searchView.setLeft(searchTable.getDataTable());
        try {
            searchTable.init("searches.csv");
            errorText.setText("");
        } catch (Exception ex) {
            errorText.setText(errorMessage(ex));
        }  
        return searchTable;
    }
    
    private void constructHelpView(Tab helpTab, Label errorText) {
        TextFlow helpView = new TextFlow();
        helpView.setPadding(new Insets(40));
        helpView.setMaxWidth(700);
        helpTab.setContent(helpView);
        helpView.getChildren().add(new Text(this.helpText()));
    }
    
    private String errorMessage(Exception ex) {
        System.out.println(ex.toString());
        if (ex.toString().equals("java.net.UnknownHostException: rata.digitraffic.fi")) {
            return "VIRHE: Ei yhteyttä datalähteeseen (rata.digitraffic.fi).";
        }

        if (ex.toString().equals("org.json.JSONException: JSONArray[0] not found.")) {
            return "VIRHE: Haettua tietoa ei löytynyt.";
        }
        
        if (ex.toString().startsWith("java.io.FileNotFoundException")) {
            return "VIRHE: tiedoston luominen ei onnistunut (aiemmpien hakujen tallennus).";
        }
        return "VIRHE: " + ex.toString();
    }
    
    private String helpText() {
        return "" +
                "Asema -välilehti: Valitse pääkaupunkiseudulla oleva asema pudotusvalikosta. Kun asema -välilehdellä kaksoisklikkaa junaa, junan aikautaulutiedot tulevat Juna -välilehdelle.\n" +
                "\n" +
                "Juna -välilehti: Välilehdellä näkyy valitun junan aikataulutiedot. Mikäli juna on myöhässä, näkyy rivilla myös arvioitu lähtöaika. Junan seuraava asema on merkitty X-merkillä. Junan sijainti näkyy kartalla punaisena pisteenä. Tiedot päivittyvät kahden minuutin välein.\n" +
                "\n" +
                "Kartta -välilehti: Kaikki lähijunat näkyvät kartalla punaisina pisteinä. Tiedot päivittyvät 10 sekunnin välein.\n" +
                "\n" +
                "Haku -välilehti: Kun asema -välilehdellä haetaan junan aikataulutiedot, tallentuu haku automaattisesti hakulistalle. Listalla näkyy 20 viimeistä hakua. Kaksoisklikkaamalla listalla olevaa hakua, tulee junan tiedot juna -välilehdelle.\n" +
                "\n" +
                "Virheilmoitukset tulostuvat sovelluksen alareunaan.\n" +
                "\n" +
                "Lähijunat -sovellus 1.0\nv. 1.5.2020\nrelease 7\n" +
                "\n" +
                "datalähde: https://www.digitraffic.fi/rautatieliikenne/\n" +
                "\n" +
                "lauri.vuorenkoski@helsinki.fi\n";
    }
}
