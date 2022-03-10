/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import pidev.Entities.Categorie;
import pidev.Services.ServiceCategorie;
import pidev.Utils.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class CategorieInterfaceController implements Initializable {

    @FXML
    private Button btajout;
    @FXML
    private TextField inputRole;
    @FXML
    private TableView<Categorie> tableCategorie;
    @FXML
    private TableColumn<Categorie, Integer> idt;
    @FXML
    private TableColumn<Categorie, String> rolet;
    
    private Statement ste;
    private Connection con;
    private final ObservableList<Categorie> data = FXCollections.observableArrayList();
    
    ServiceCategorie sc = new ServiceCategorie();
    @FXML
    private Label labelcalcul;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Aff();

    }    

    @FXML
    private void Ajoutercategorie(ActionEvent event) throws SQLException {
        Categorie d = new Categorie(inputRole.getText());
        sc.ajouter(d);        
        Aff();
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Ajouter");
        tray.setMessage("Ajouté avec succès");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();

    }

    @FXML
    private void Change_Role(TableColumn.CellEditEvent event) throws SQLException {
     Categorie tab_CategorieSelected = tableCategorie.getSelectionModel().getSelectedItem();
     tab_CategorieSelected.setRole(event.getNewValue().toString());
     sc.update(tab_CategorieSelected);
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Modifier");
        tray.setMessage("Modifié avec succès");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();

    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
             tableCategorie.setItems(data);

             ObservableList<Categorie> allCategories,SingleCategorie ;
             allCategories=tableCategorie.getItems();
             SingleCategorie=tableCategorie.getSelectionModel().getSelectedItems();
             Categorie A = SingleCategorie.get(0);
             sc.delete(A);
             SingleCategorie.forEach(allCategories::remove);
             Aff();
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Supprimer");
        tray.setMessage("Supprimé avec succès");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();
    }

    public void Aff() {
                        try {
            con = Database.getInstance().getConnection();
            ste = con.createStatement();
            data.clear();

            ResultSet res = ste.executeQuery("select * from Categorie");
            while(res.next()){
                Categorie f= new Categorie(res.getInt(1),res.getString(2));
                data.add(f);
            }

        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
            idt.setCellValueFactory(new PropertyValueFactory<>("id"));
            rolet.setCellValueFactory(new PropertyValueFactory<>("role"));
            
            tableCategorie.setItems(data);
            tableCategorie.setEditable(true);
            rolet.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void Calculer(ActionEvent event) {
                     
        ObservableList<Categorie> allCategories,SingleCategorie ;
             allCategories=tableCategorie.getItems();
             SingleCategorie=tableCategorie.getSelectionModel().getSelectedItems();
             Categorie A = SingleCategorie.get(0);
             
             labelcalcul.setText("Total : "+String.valueOf(sc.calculer(A.getId())));
        

    }
    
}
    