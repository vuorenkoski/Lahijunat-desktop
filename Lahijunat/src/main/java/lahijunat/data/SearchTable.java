package lahijunat.data;

import lahijunat.domain.DepartingTrain;
import lahijunat.domain.SearchItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Luokka kuvaa tehtyjä hakuja.
 * @author Lauri Vuorenkoski
 */
public class SearchTable {
    private TableView dataTable;
    private ArrayList<SearchItem> searches;
    private File file;

    public SearchTable() {
        this.searches = new ArrayList<>();
        this.formatDataTable();
        this.file = null;
    }
    
    /**
     * Metodi alustaa hakulistan. 
     * Luo tiedoston jolle hakutulokset tallennetaan mikäli sellaista ei ole. Jos sellainen on,
     * se lataa aiemmat haut muistiin.
     * @param fileName Hakutulokset sisältävän tiedoston nimi
     */
    public void init(String fileName) throws IOException {
        this.file = new File(fileName);
        if (!file.exists()) {
            for (int i = 0; i < 20; i++) {
                this.searches.add(new SearchItem("0", "-"));
            }
            this.save();
        } else { 
            this.load();
        }
        this.updateTable();
    }
    
    /**
     * Metodi lisää haun listalle. 
     * Haku lisätään muistissa olevalle listalle. Jos hakuja on yli 20, vanhin pudotetaan pois.
     * Päivitetty lista tallennetaan tiedostoon
     * @param trainNumber junan numero (String)
     * @param trainInfo Junan tiedot (eli pysähdypaikka, aika ja kirjain)
     */
    public void add(String trainNumber, String trainInfo) throws IOException {
        Boolean contains = false;
        for (SearchItem search:this.searches) {
            if (search.getTrainInfo().equals(trainInfo)) {
                contains = true;
            }
        }
        if (!contains) {
            for (int i = 19; i > 0; i--) {
                this.searches.set(i, this.searches.get(i - 1));
            }
            this.searches.set(0, new SearchItem(trainNumber, trainInfo));
            this.save();
            this.updateTable();
        }
    }

    /**
     * palauttaa tämän hetkisen taulukon.
     * @return taulukko
     */
    public TableView getDataTable() {
        return dataTable;
    }

    /**
     * Palauttaa listan hauista.
     * @return lista
     */
    public ArrayList<SearchItem> getSearches() {
        return searches;
    }
    
    private void load() throws FileNotFoundException {
        this.searches.clear();
        Scanner fileReader = new Scanner(file); 
        while (fileReader.hasNextLine()) {
            String[] line = fileReader.nextLine().split(",");
            this.searches.add(new SearchItem(line[0], line[1]));
        }
        fileReader.close();
    }
    
    private void save() throws IOException {
        FileWriter writer = new FileWriter(this.file);
        for (SearchItem search:this.searches) {
            writer.write(search.getTrainNumber() + "," + search.getTrainInfo() + "\n");
        }
        writer.close();
    }
    
    private void updateTable() {
        this.dataTable.getItems().clear();
        for (SearchItem search:this.searches) {
            if (!search.getTrainNumber().equals("0")) {
                this.dataTable.getItems().add(search);
            }
        }
    }
    
    private void formatDataTable() {
        TableColumn trainInfoColumn = addColumn("Juna", 170, "trainInfo");
        TableColumn trainNumberColumn = addColumn("Numero", 70, "trainNumber");
        
        this.dataTable = new TableView();
        this.dataTable.getColumns().addAll(trainInfoColumn, trainNumberColumn);
        this.dataTable.setMaxWidth(245);
    }
    
    private TableColumn addColumn(String title, int width, String data) {
        TableColumn column = new TableColumn(title);
        column.setMinWidth(width);
        column.setMaxWidth(width);
        column.setSortable(false);
        column.setCellValueFactory(new PropertyValueFactory<DepartingTrain, String>(data));
        return column;
    }
}
