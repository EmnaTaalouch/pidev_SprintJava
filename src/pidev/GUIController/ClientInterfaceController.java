/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ClientInterfaceController implements Initializable {

    @FXML
    private AnchorPane clientinterfacepane;
    @FXML
    private AnchorPane centerpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    void afficher(String fxml) {
        try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource(fxml));
            centerpane.getChildren().removeAll();
            centerpane.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
   }

    @FXML
    private void OnAccueilClick(MouseEvent event) {
        afficher("ParticiperEventClient.fxml");
    }

    @FXML
    private void OnEventClick(MouseEvent event) {
        afficher("HistoriqueEvent.fxml");
    }
    
}
