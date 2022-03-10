/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import pidev.Entities.Comptabilite;
import pidev.Entities.ComptabiliteType;
import pidev.Services.SComptabilite;
import pidev.Services.SComptabiliteType;
import pidev.Utils.Database;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author marie
 */
public class TypeComptabiliteController implements Initializable {

    @FXML
    private TextField tfType;
    @FXML
    private TextField tfMontant;
    @FXML
    private Button btSupprimer;
    @FXML
    private TextField tfRechercheType;
    @FXML
    private TableView<ComptabiliteType> tabType;
    @FXML
    private TableColumn<ComptabiliteType, Integer> col_Montant;
    @FXML
    private TableColumn<ComptabiliteType, String> col_Type;
    
    private final ObservableList<ComptabiliteType> data = FXCollections.observableArrayList();

    SComptabiliteType sc= new SComptabiliteType();
    @FXML
    private TableColumn<ComptabiliteType,Integer> idtab;
    @FXML
    private Label idlabel;
    @FXML
    private BarChart<String, Number> barChart;
    private Statement ste;
    private Connection con;

    SComptabilite scc = new SComptabilite();
    @FXML
    private Label labeltotalD;
    @FXML
    private Label labeltotalR;
    @FXML
    private AnchorPane InterfaceTypeComptabilite;
    @FXML
    private VBox vboxmainevent;
    @FXML
    private VBox vboxanimevent;
    @FXML
    private AnchorPane anchorcenter;
    @FXML
    private Button btAjouterType;
    @FXML
    private Button btModifierType;
    @FXML
    private Button btTotalD;
    @FXML
    private Button btTotalR;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showComptabiliteType();
        } catch (SQLException ex) {
            Logger.getLogger(TypeComptabiliteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        numeric();
    }    

    

    public void showComptabiliteType() throws SQLException{
        
        data.clear();
        ArrayList<ComptabiliteType> act = (ArrayList<ComptabiliteType>) sc.readAll();
        data.addAll(act);
        
        idtab.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_Montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        tabType.setItems(data);
        RechercheAV();  
       Stat();
    }
    
    public void Stat() throws SQLException{
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Répartition des Types");
            con = Database.getInstance().getConnection();
            ste = con.createStatement();
            ResultSet res = ste.executeQuery("select * from type_comptabilite");
            while(res.next()){
            series.getData().add(new XYChart.Data<>(res.getString(2), scc.calculer(res.getInt(1))));
        }        
        barChart.getData().addAll(series);

    }
    @FXML
    private void OnAjouterType(MouseEvent event) throws SQLException {
        if(Validchamp(tfType)&& !tfMontant.getText().isEmpty())
                {
         ComptabiliteType ct=new ComptabiliteType(tfType.getText(),(Integer.valueOf(tfMontant.getText())));
        SComptabiliteType sct =new  SComptabiliteType();
        ct.setType(tfType.getText());
        ct.setMontant(Integer.valueOf(tfMontant.getText()));
        sct.ajoutertype(ct);
        showComptabiliteType();
        
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Ajouter Type");
        tray.setMessage("Ajout avec succes");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndWait();
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


    @FXML
    private void OnSupprimerType(MouseEvent event) throws SQLException {
            tabType.setItems(data);
            ObservableList<ComptabiliteType> all,Single;
            all=tabType.getItems();
            Single=tabType.getSelectionModel().getSelectedItems();
            ComptabiliteType A = Single.get(0);
            sc.deletetype(A.getId());
            Single.forEach(all::remove);
            showComptabiliteType();
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Supprimer Type");
            tray.setMessage("Supprimer avec succes");
            tray.setNotificationType(NotificationType.WARNING);
            tray.showAndWait();

    }

    @FXML
    private void OnModifierType(MouseEvent event) throws SQLException {
        if(Validchamp(tfType)&& !tfMontant.getText().isEmpty())
        {
        ComptabiliteType ct=new ComptabiliteType(tfType.getText(),(Integer.valueOf(tfMontant.getText())));
        SComptabiliteType sct =new  SComptabiliteType();
        
        ct.setMontant(Integer.valueOf(tfMontant.getText()));
        ct.setType(tfType.getText());
        sct.updatetype(ct,Integer.valueOf(idlabel.getText()));
        showComptabiliteType();
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Modifier Type");
        tray.setMessage("Modifier avec succes");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();
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
    
    public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ComptabiliteType> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		tfRechercheType.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tmp -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (tmp.getType().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (String.valueOf(tmp.getMontant()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ComptabiliteType> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tabType.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tabType.setItems(sortedData);
    }

    @FXML
    private void LoadData(MouseEvent event) {
            ObservableList<ComptabiliteType> all,Single;
            all=tabType.getItems();
            Single=tabType.getSelectionModel().getSelectedItems();
            ComptabiliteType A = Single.get(0);
            tfType.setText(A.getType());
            tfMontant.setText(String.valueOf(A.getMontant()));
            idlabel.setText(String.valueOf(A.getId()));
    }
    
        private boolean Validchamp(TextField T){
        return !T.getText().isEmpty() && T.getLength() >2;
    }
        
        public void numeric(){
        // force the field to be numeric only
            tfMontant.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tfMontant.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }

    @FXML
    private void totaldepense(ActionEvent event) {
       labeltotalD.setText("Total : "+sc.calculerToTalDep());
    }

    @FXML
    private void totalrevenu(ActionEvent event) {
       labeltotalR.setText("Total : "+sc.calculerToTalRev());
    }

    @FXML
    private void onanimateevent(MouseEvent event) {
    }


}
