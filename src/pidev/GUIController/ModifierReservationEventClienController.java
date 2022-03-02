/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pidev.Entities.Event;
import pidev.Entities.EventStatusEnum;
import pidev.Entities.Event_type;
import pidev.Entities.User;
import pidev.Services.EventService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ModifierReservationEventClienController implements Initializable {

    @FXML
    private TextField fieldNom;
    @FXML
    private TextField fieldTheme;
    @FXML
    private TextField fieldNbr;
    @FXML
    private TextField fieldLieu;
    @FXML
    private TextArea fieldDescription;
    @FXML
    private DatePicker fieldDateD;
    @FXML
    private DatePicker fieldDateF;
    @FXML
    private ComboBox<Event_type> fieldType;
    @FXML
    private RadioButton btnprivé;
    @FXML
    private RadioButton btnpublic;
    @FXML
    private Button btnmodif;
    @FXML
    private Button btnsupp;
    @FXML
    private Text label;
    
    HistoriqueEventController lec;
    @FXML
    private AnchorPane pane;
    
    EventService es = new EventService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        label.setVisible(false);
        algo();
    }  
    
     void setMainController(HistoriqueEventController l) {
        lec= l;
    }
    void setData(Event t) {
        
        label.setText(String.valueOf(t.getId()));
        fieldNom.setText(t.getNom_event());
        fieldTheme.setText(t.getEvent_theme());
        fieldNbr.setText(String.valueOf(t.getNbr_participants()));
        fieldLieu.setText(t.getLieu());
        fieldDescription.setText(t.getEvent_description());
        fieldDateD.setValue(t.getDate_debut().toLocalDate());
        fieldDateF.setValue(t.getDate_fin().toLocalDate());
        fieldType.setValue(t.getId_type());
        if(t.getEvent_status().equals(EventStatusEnum.Privé.toString())) {
           btnprivé.setSelected(true);
           btnpublic.setSelected(false);
        } else {
            btnprivé.setSelected(false);
           btnpublic.setSelected(true);
        }
        
    }

    @FXML
    private void OnModifier(ActionEvent event) {
         Event t = new Event();
        t.setNom_event(fieldNom.getText());
        t.setEvent_theme(fieldTheme.getText());
        t.setNbr_participants(Integer.valueOf(fieldNbr.getText()));
        t.setLieu(fieldLieu.getText());
        t.setEvent_description(fieldDescription.getText());
        t.setDate_debut(Date.valueOf(fieldDateD.getValue()));
        t.setDate_fin(Date.valueOf(fieldDateF.getValue()));
        if(btnprivé.isSelected() && !btnpublic.isSelected())
            t.setEvent_status(EventStatusEnum.Privé.toString());
        else
            t.setEvent_status(EventStatusEnum.Publique.toString());
        User client = new User();
        client.setId(1);
        t.setId_client(client);
        t.setId_type(fieldType.getSelectionModel().getSelectedItem());
        es.modifierreservation(t,Integer.valueOf(label.getText()));
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Onsupprimer(ActionEvent event) {
        es.supprimer(Integer.valueOf(label.getText()));
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    
    void algo() {
        btnprivé.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                btnprivé.setSelected(newValue);
                btnpublic.setSelected(!newValue);
            }
        });
        btnpublic.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                btnpublic.setSelected(newValue);
                btnprivé.setSelected(!newValue);
            }
        });
    }
    
}
