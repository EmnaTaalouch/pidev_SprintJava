/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.Entities.DemandeStatusEnum;
import pidev.Entities.Event;
import pidev.Entities.Event_type;
import pidev.Services.EventService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class HistoriqueEventController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Event> tablehistorique;
    @FXML
    private TableColumn<Event, String> nomfield;
    @FXML
    private TableColumn<Event, String> themefield;
    @FXML
    private TableColumn<Event, Event_type> typefield;
    @FXML
    private TableColumn<Event, String> datedfield;
    @FXML
    private TableColumn<Event, String> dateffield;
    @FXML
    private TableColumn<Event, String> lieufield;
    @FXML
    private TextField searchfield;
    @FXML
    private ComboBox<String> demandefield;
    @FXML
    private Button btnreserver;
    
    EventService es = new EventService();
    @FXML
    private Button btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nomfield.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        themefield.setCellValueFactory(new PropertyValueFactory<>("event_theme"));
        typefield.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        datedfield.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateffield.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        lieufield.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        tablehistorique.getItems().setAll(es.historiqueEventbyClientetStatus(1,DemandeStatusEnum.DemandePending.toString()));
        ArrayList e = new ArrayList();
        e.add(DemandeStatusEnum.DemandePending.toString());
        e.add(DemandeStatusEnum.DemandeAccepted.toString());
        e.add(DemandeStatusEnum.DemandeRefused.toString());
        demandefield.getItems().setAll(e);
        demandefield.getSelectionModel().selectFirst();;
        OnSelectStatus();
        chercher();
        popupModifierInterface();
        
    }    

    @FXML
    private void onReserverAction(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ReserverEventClient.fxml"));
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
    
    void OnSelectStatus() {
        demandefield.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tablehistorique.getItems().setAll(es.historiqueEventbyClientetStatus(1,demandefield.getValue()));
            }
        });
    }
    
    void chercher() {
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            tablehistorique.getItems().setAll(es.rechercheEventbyClientetStatus(1,demandefield.getValue(),newValue));
        });
    }

    @FXML
    private void onActualiserAction(ActionEvent event) {
        tablehistorique.getItems().setAll(es.historiqueEventbyClientetStatus(1,demandefield.getValue()));
    }
    
    
    
        void popupModifierInterface() {
        
        
        tablehistorique.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tablehistorique.getSelectionModel().getSelectedItem()!=null) {
                Event t = tablehistorique.getSelectionModel().getSelectedItem();
                if(t.getDemande_status().equals(DemandeStatusEnum.DemandePending.toString()))
                ModifInterface(t);
                }
            }
        });   
    }
    
    void ModifInterface(Event t) {
          try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifierReservationEventClien.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            ModifierReservationEventClienController mrc = loader.getController();
            mrc.setMainController(this);
            mrc.setData(t);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
