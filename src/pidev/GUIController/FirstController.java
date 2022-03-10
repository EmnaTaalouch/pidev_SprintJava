/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class FirstController implements Initializable {

    @FXML
    private Button buttonSignIn;
    @FXML
    private Button buttonSignUp;
    private Parent fxml;
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onSignIn(ActionEvent event) {
                   try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource("SeConnecter.fxml"));
            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
       
    }    

    @FXML
    private void onSignUp(ActionEvent event) {
           try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource("Sinscrire.fxml"));
            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
