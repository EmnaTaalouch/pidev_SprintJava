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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class DemandesEventController implements Initializable {

    @FXML
    private TableView<?> tabdem;
    @FXML
    private TableColumn<?, ?> colnom;
    @FXML
    private TableColumn<?, ?> coltype;
    @FXML
    private TableColumn<?, ?> colclient;
    @FXML
    private TableColumn<?, ?> coldated;
    @FXML
    private TableColumn<?, ?> coldatef;
    @FXML
    private TableColumn<?, ?> colstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnAccepterEvent(ActionEvent event) {
    }

    @FXML
    private void OnRefuserEvent(ActionEvent event) {
    }
    
}
