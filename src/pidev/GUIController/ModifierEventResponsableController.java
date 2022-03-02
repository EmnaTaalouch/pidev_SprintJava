/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import pidev.Services.EventTypeService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ModifierEventResponsableController implements Initializable {

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
    private Button Modifier;
    @FXML
    private DatePicker fieldDateD;
    @FXML
    private DatePicker fieldDateF;
    @FXML
    private ComboBox<User> fieldClient;
    @FXML
    private ComboBox<Event_type> fieldType;
    @FXML
    private CheckBox fieldStatus;
    @FXML
    private Button Supprimer;
    @FXML
    private AnchorPane pane;
    
    ListeEvenementsController lec;
    @FXML
    private Text label;
    
    EventService es = new EventService();
    EventTypeService ets = new EventTypeService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        algo();
        label.setVisible(false);
        fieldType.getItems().addAll(ets.afficher());
        fieldClient.getItems().addAll(es.afficherclient());
    }  
    
        public void afficherAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean testSaisie() {
        if (
                fieldType.getValue() == null
                || fieldClient.getValue() == null
                || fieldDateD.getValue() == null
                || fieldDateF.getValue() == null
                || fieldNom.getText().trim().isEmpty()
                || fieldTheme.getText().trim().isEmpty()
                || fieldNbr.getText().trim().isEmpty()
                || fieldLieu.getText().trim().isEmpty()
                || fieldDescription.getText().trim().isEmpty()
                ) {
            afficherAlert("Tous les champs doivent être remplis");
            return false;
        }

        if (fieldDateD.getValue().compareTo(fieldDateF.getValue()) > 0) {
            afficherAlert("Date fin doit être supérieur ou égal à la date de debut");
            return false;
        }
        try {
            Double num = Double.parseDouble(fieldNbr.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Champs Nombre Participants invalide doit etre un nombre");
            return false;
        }
        return true;
    }
    
    void setMainController(ListeEvenementsController l) {
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
        fieldClient.setValue(t.getId_client());
        fieldType.setValue(t.getId_type());
        
    }

    @FXML
    private void OnModifier(ActionEvent event) {
        if(testSaisie()) {
           Event t = new Event();
        t.setNom_event(fieldNom.getText());
        t.setEvent_theme(fieldTheme.getText());
        t.setNbr_participants(Integer.valueOf(fieldNbr.getText()));
        t.setLieu(fieldLieu.getText());
        t.setEvent_description(fieldDescription.getText());
        t.setDate_debut(Date.valueOf(fieldDateD.getValue()));
        t.setDate_fin(Date.valueOf(fieldDateF.getValue()));
        if(fieldStatus.getText().equals("Privé"))
            t.setEvent_status(EventStatusEnum.Privé.toString());
        else
            t.setEvent_status(EventStatusEnum.Publique.toString());
        t.setId_client(fieldClient.getSelectionModel().getSelectedItem());
        t.setId_type(fieldType.getSelectionModel().getSelectedItem());
        User responsable = new User();
        responsable.setId(2);
        t.setId_responsable(responsable);
        es.modifier(t,Integer.valueOf(label.getText()));
         Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();         
        }
    }

    @FXML
    private void OnSupprimer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez vous supprimer cet evenement");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                es.supprimer(Integer.valueOf(label.getText()));
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.close();
            } 
        
    }
    
    
    void algo() {
        fieldStatus.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    fieldStatus.setText("Privé");
                }
                else {
                    fieldStatus.setText("Publique");
                }
            }
        });
    }
    
}
