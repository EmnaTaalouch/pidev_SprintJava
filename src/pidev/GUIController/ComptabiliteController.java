/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pidev.Entities.Comptabilite;
import pidev.Entities.ComptabiliteType;
import pidev.Services.SComptabilite;
import pidev.Services.SComptabiliteType;
import pidev.Utils.Database;

/**
 * FXML Controller class
 *
 * @author marie
 */
public class ComptabiliteController implements Initializable {

    @FXML
    private Button btAjouter;
    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfDescriptionComp;
    @FXML
    private DatePicker DateComp;
    @FXML
    private Button btModifier;
    @FXML
    private Button btSupprimer;
    @FXML
    private AnchorPane Comptabilite;
    @FXML
    private ComboBox<String> fieldType;
    ObservableList<String> listdType = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Comptabilite, String> col_Libelle;
    @FXML
    private TableColumn<Comptabilite, java.sql.Date> col_DateComp;
    @FXML
    private TableColumn<Comptabilite, String> col_Description;
    @FXML
    private TableColumn<Comptabilite, String> col_Type;
    @FXML
    private TableView<Comptabilite> TabComptabilite;
    @FXML
    private TableColumn<Comptabilite, Integer> idt;
    
    @FXML
    private TextField rechercher;
    @FXML
    private Label idlabel;
    private final ObservableList<Comptabilite> data = FXCollections.observableArrayList();

    SComptabiliteType sct = new SComptabiliteType();
    SComptabilite sc = new SComptabilite();
    
        
private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showComptabilite();
            fillcombo();
        } catch (SQLException ex) {
            Logger.getLogger(ComptabiliteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
        public void fillcombo() throws SQLException{     
        List<ComptabiliteType> list = sct.readAll();
        for (ComptabiliteType aux : list)
        {
          listdType.add(aux.getType());
        }
        fieldType.setItems(listdType);
    }


    

    
    public void showComptabilite() throws SQLException{
        
        data.clear();
        SComptabilite c =new SComptabilite();
        ArrayList<Comptabilite> act = (ArrayList<Comptabilite>) c.readAll();
        data.addAll(act);
        TabComptabilite.setItems(data);
 

        idt.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_Libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        col_DateComp.setCellValueFactory(new PropertyValueFactory<>("Date"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("Description"));

       // TabComptabilite.setItems(list);
        RechercheAV();
    }
    
        public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Comptabilite> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tmp -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (tmp.getType().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (String.valueOf(tmp.getId()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Comptabilite> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TabComptabilite.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		TabComptabilite.setItems(sortedData);
    }

    @FXML
    private void OnAjout(MouseEvent event) throws SQLException {
        if(Validchamp(tfLibelle)&& !tfDescriptionComp.getText().isEmpty())
        {
        Date d = java.sql.Date.valueOf(DateComp.getValue());
        SComptabilite sc =new  SComptabilite();
        SComptabiliteType sct =new  SComptabiliteType();
        ComptabiliteType tmp = sct.getByName(fieldType.getValue());
        Comptabilite c = new Comptabilite(tfLibelle.getText(),tfDescriptionComp.getText(), d, tmp.getId());
        sc.ajouterComptabilite(c);
        showComptabilite();
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");
        alert.showAndWait();
        }    }



    @FXML
    private void OnModifier(MouseEvent event) throws SQLException {
        if(Validchamp(tfLibelle)&& !tfDescriptionComp.getText().isEmpty())
        {
        Date d = java.sql.Date.valueOf(DateComp.getValue());
        SComptabilite sc =new  SComptabilite();
        ComptabiliteType tmp = sct.getByName(fieldType.getValue());

        Comptabilite c = new Comptabilite(tfLibelle.getText(),tfDescriptionComp.getText(), d,tmp.getId() );
        sc.updateComptabilite(c,Integer.valueOf(idlabel.getText()));
        showComptabilite();
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");
        alert.showAndWait();
        }
    }
    
        private boolean Validchamp(TextField T){
        return !T.getText().isEmpty() && T.getLength() >2;
    }

    @FXML
    private void OnSupprimer(MouseEvent event) throws SQLException {
            TabComptabilite.setItems(data);
            ObservableList<Comptabilite> all,Single;
            all=TabComptabilite.getItems();
            Single=TabComptabilite.getSelectionModel().getSelectedItems();
            Comptabilite A = Single.get(0);
            sc.deleteComptabilite(A.getId());
            Single.forEach(all::remove);
            showComptabilite();
    }
    
    


    private void Back(ActionEvent event) throws IOException{
        Parent etab = FXMLLoader.load(getClass().getResource("Main.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void LoadModif(MouseEvent event) {
            TabComptabilite.setItems(data);
            ObservableList<Comptabilite> all,Single;
            all=TabComptabilite.getItems();
            Single=TabComptabilite.getSelectionModel().getSelectedItems();
            Comptabilite A = Single.get(0);
            
             fieldType.setValue(A.getType());
             idlabel.setText(String.valueOf(A.getId()));
             LocalDate localDate = LocalDate.parse(A.getDate().toString());
             DateComp.setValue(localDate);
             tfLibelle.setText(A.getLibelle());
             tfDescriptionComp.setText(A.getDescription());


    }

    @FXML
    private void exportexcel(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < TabComptabilite.getColumns().size(); j++) {
            row.createCell(j).setCellValue(TabComptabilite.getColumns().get(j).getText());
        }

        for (int i = 0; i < TabComptabilite.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < TabComptabilite.getColumns().size(); j++) {
                if(TabComptabilite.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(TabComptabilite.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        workbook.write(fileOut);
        fileOut.close();

    }

    
}

    
    

   