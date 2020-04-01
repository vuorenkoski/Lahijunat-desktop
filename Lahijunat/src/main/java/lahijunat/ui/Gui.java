package lahijunat.ui;

import lahijunat.domain.DepartingTrain;
import lahijunat.domain.Station;

import java.io.File;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import lahijunat.data.StationTable;
import lahijunat.data.TrainTable;

/**
 * Lähijunat - graafinen käyttöliittymä.
 * @author Lauri Vuorenkoski
 */

public class Gui extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Label errorText = new Label(" ");
        
        // Setting up TabPane
        Tab stationTab = new Tab("Asema");
        Tab trainTab = new Tab("Juna");
        Tab helpTab = new Tab("Ohje");
        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(stationTab, trainTab, helpTab);

        // Setting up root BorderPane
        BorderPane rootView = new BorderPane();
        rootView.setCenter(tabPane);
        rootView.setBottom(errorText);
        Scene scene = new Scene(rootView, 900, 700);
        primaryStage.setTitle("VR lähijunien aikataulut");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Station search tab
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
                } catch (Exception ex) {
                    errorText.setText(ex.toString());
                }
            }
        });
        
        // Train details tab
        BorderPane trainDetailsView = new BorderPane();
        trainDetailsView.setPadding(new Insets(20));
        trainTab.setContent(trainDetailsView);
        Label trainNumber = new Label("Juna: ");
        trainNumber.setFont(new Font("Arial", 24));
        trainDetailsView.setTop(trainNumber);
        
        TrainTable trainTable = new TrainTable();
        trainDetailsView.setLeft(trainTable.getDataTable());
        stationTable.getDataTable().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    DepartingTrain train = (DepartingTrain) stationTable.getDataTable().getSelectionModel().getSelectedItem();
                    try {
                        trainTable.update(train.getTrainNumber());
                        trainTab.setText("Juna: " + train);
                        trainNumber.setText("Juna: " + train);
                        tabPane.getSelectionModel().select(trainTab);
                    } catch (Exception ex) {
                        errorText.setText(ex.toString());
                    }            
                }
            }
        });
        
        // Help tab
        TextFlow  helpView = new TextFlow();
        helpView.setPadding(new Insets(40));
        helpView.setMaxWidth(700);
        helpTab.setContent(helpView);
        try {
            Scanner fileReader = new Scanner(new File("ohje.txt"));
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                helpView.getChildren().add(new Text(data + "\n"));
            }
            fileReader.close();
        } catch (Exception ex) {
            errorText.setText(ex.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
