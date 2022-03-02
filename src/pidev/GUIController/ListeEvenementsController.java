/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.Entities.Event;
import pidev.Services.EventService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ListeEvenementsController implements Initializable {

    @FXML
    private TableView<Event> tabevent;
    @FXML
    private TableColumn<Event, String> colnom;
    @FXML
    private TableColumn<Event, String> coltype;
    @FXML
    private TableColumn<Event, String> colclient;
    @FXML
    private TableColumn<Event, String> coldated;
    @FXML
    private TableColumn<Event, String> coldatef;
    @FXML
    private TableColumn<Event, String> colstatus;
    
    EventService es = new EventService();
    @FXML
    private TextField searchfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        colclient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        coldated.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        coldatef.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("event_status"));
        tabevent.getItems().setAll(es.afficher());
        popupModifierInterface();
        chercher();
    }    

    @FXML
    private void OnAjoutEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AjouterEventResponsable.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void OnActualiserEvent(ActionEvent event) {
        //tabevent.getItems().clear();
        tabevent.getItems().setAll(es.afficher());
    }
    
    
    
    
    void popupModifierInterface() {
        
        
        tabevent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Event t = tabevent.getSelectionModel().getSelectedItem();
                ModifInterface(t);
            }
        });   
    }
    
    void ModifInterface(Event t) {
          try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifierEventResponsable.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            ModifierEventResponsableController mrc = loader.getController();
            mrc.setMainController(this);
            mrc.setData(t);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    void chercher() {
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            tabevent.getItems().setAll(es.rechercher(newValue));
        });
    }
    
}
