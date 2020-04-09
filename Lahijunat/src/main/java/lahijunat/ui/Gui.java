package lahijunat.ui;

import lahijunat.domain.DepartingTrain;
import lahijunat.domain.Station;
import lahijunat.data.StationTable;
import lahijunat.data.TrainMap;
import lahijunat.data.TrainTable;

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

        // Setting up TabPane
        //
        Tab stationTab = new Tab("Asema");
        Tab trainTab = new Tab("Juna");
        Tab mapTab = new Tab("Kartta");
        Tab helpTab = new Tab("Ohje");
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(stationTab, trainTab, mapTab, helpTab);

        // Setting up root BorderPane
        //
        BorderPane rootView = new BorderPane();
        rootView.setCenter(tabPane);
        rootView.setBottom(errorText);
        Scene scene = new Scene(rootView, 900, 700);
        primaryStage.setTitle("VR lähijunien aikataulut");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        StationTable stationTable = new StationTable();
        this.constructStationView(stationTab, stationTable, errorText);
        this.constructTrainTab(trainTab, stationTable, tabPane, errorText);
        this.constructMapView(mapTab, errorText);
        this.constructHelpView(helpTab, errorText);
    }

    public static void main(String[] args) {
        launch(args);
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
                    trainsMap.update();
                } catch (Exception ex) {
                    errorText.setText(ex.toString());
                }                  
            }
        })); 
        tenSecondPeriod.setCycleCount(Timeline.INDEFINITE);
        mapTab.setOnSelectionChanged((Event t) -> {
            if (mapTab.isSelected()) {
                try {
                    trainsMap.update();
                } catch (Exception ex) {
                    errorText.setText(ex.toString());
                }
                tenSecondPeriod.play();
            } else {
                tenSecondPeriod.stop();
            }
        });
    }
    
    private void constructTrainTab(Tab trainTab, StationTable stationTable, TabPane tabPane, Label errorText) {
        BorderPane trainDetailsView = new BorderPane();
        trainDetailsView.setPadding(new Insets(20));
        trainTab.setContent(trainDetailsView);
        Label trainNumber = new Label("Juna: ");
        trainNumber.setFont(new Font("Arial", 24));
        trainDetailsView.setTop(trainNumber);
        
        TrainTable trainTable = new TrainTable();
        TrainMap trainMap = new TrainMap(0);
        trainDetailsView.setLeft(trainTable.getDataTable());
        trainDetailsView.setCenter(trainMap.getTrainMap());
        stationTable.getDataTable().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    DepartingTrain train = (DepartingTrain) stationTable.getDataTable().getSelectionModel().getSelectedItem();
                    try {
                        trainTable.update(train.getTrainNumber());
                        trainMap.setUicCode(train.getTrainNumber());
                        trainMap.update();
                        trainTab.setText("Juna: " + train);
                        trainNumber.setText("Juna: " + train);
                        tabPane.getSelectionModel().select(trainTab);
                    } catch (Exception ex) {
                        errorText.setText(ex.toString());
                    }            
                }
            }
        });
        
        Timeline twoMinutePeriod = new Timeline(new KeyFrame(Duration.seconds(120), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    trainMap.update();
                    trainTable.update(0);
                } catch (Exception ex) {
                    errorText.setText(ex.toString());
                }                  
            }
        })); 
        twoMinutePeriod.setCycleCount(Timeline.INDEFINITE);
        trainTab.setOnSelectionChanged((Event t) -> {
            if (trainTab.isSelected()) {
                try {
                    trainMap.update();
                    trainTable.update(0);
                } catch (Exception ex) {
                    errorText.setText(ex.toString());
                }
                twoMinutePeriod.play();
            } else {
                twoMinutePeriod.stop();
            }
        });
    }
    
    private void constructStationView(Tab stationTab, StationTable stationTable, Label errorText) {
        BorderPane stationView = new BorderPane();
        stationView.setPadding(new Insets(20));
        stationTab.setContent(stationView);
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(Station.stationList()));
        comboBox.setPromptText("valitse asema");
        stationView.setTop(comboBox);
 
        stationView.setCenter(stationTable.getDataTable());
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    stationTable.update(Station.stationUICode(comboBox.getValue().toString()));
                    stationTab.setText("Asema: " + comboBox.getValue().toString());
                } catch (Exception ex) {
                    errorText.setText(ex.toString());
                }
            }
        });
    }
    
    private void constructHelpView(Tab helpTab, Label errorText) {
        TextFlow  helpView = new TextFlow();
        helpView.setPadding(new Insets(40));
        helpView.setMaxWidth(700);
        helpTab.setContent(helpView);
        helpView.getChildren().add(new Text(this.helpText()));
    }
    
    private String helpText() {
        return "" +
                "Asema -välilehti: Valitse asema pudotusvalikosta. Kun asema -välilehdelle kaksoisklikkaa junaa, junan aikautaulutiedot tulevat Juna -välilehdelle.\n" +
                "\n" +
                "Juna -välilehti: Valitun junan aikataulutiedot. Mikäli juna on myöhässä, näkyy rivilla myös arvioitu lähtöaika. Junan seuraava asema on merkitty X-merkillä. Junan sijainti näkyy kartalla punaisena pisteenä. Tiedot päivittyvät kahden minuutin välein.\n" +
                "\n" +
                "Kartta -välilehti: Kaikki lähijunat näkyvät kartalla punaisina pisteinä. Tiedot päivittyvät 15 sekunnin välein.\n" +
                "\n" +
                "Lähijunat -sovellus\nv. 9.4.2020\nrelease 5\n" +
                "\n" +
                "datalähde: https://www.digitraffic.fi/rautatieliikenne/\n" +
                "\n" +
                "lauri.vuorenkoski@helsinki.fi\n";
    }
}
