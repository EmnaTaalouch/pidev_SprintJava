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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
public class ReserverEventClientController implements Initializable {

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
    private AnchorPane anchorpane;
    @FXML
    private Button btnreserver;
    @FXML
    private RadioButton btnprivé;
    @FXML
    private RadioButton btnpublic;
    
    EventService es = new EventService();
    EventTypeService ets = new EventTypeService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnprivé.setSelected(true);
        btnpublic.setSelected(false);
        fieldType.getItems().setAll(ets.afficher());
        algo();
    }    

    
    
    @FXML
    private void OnReserve(ActionEvent event) {
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
        es.reserverEvent(t);
        Stage stage = (Stage) anchorpane.getScene().getWindow();
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
