/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pidev.Entities.Event;
import pidev.Entities.Event_type;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        
    }

    @FXML
    private void OnModifier(ActionEvent event) {
    }

    @FXML
    private void Onsupprimer(ActionEvent event) {
    }
    
}
