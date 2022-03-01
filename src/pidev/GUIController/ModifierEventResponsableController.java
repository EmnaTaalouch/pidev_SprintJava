/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private ComboBox<?> fieldClient;
    @FXML
    private ComboBox<?> fieldType;
    @FXML
    private CheckBox fieldStatus;
    @FXML
    private Button Supprimer;
    @FXML
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        algo();
    }    

    @FXML
    private void OnModifier(ActionEvent event) {
    }

    @FXML
    private void OnSupprimer(ActionEvent event) {
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
