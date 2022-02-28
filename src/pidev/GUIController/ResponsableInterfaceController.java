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
public class ResponsableInterfaceController implements Initializable {

    @FXML
    private AnchorPane responsableinterface;
    @FXML
    private AnchorPane anchorcenter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    void afficher(String fxml) {
        try {
            Parent p;
            p = FXMLLoader.load(getClass().getResource(fxml));
            anchorcenter.getChildren().removeAll();
            anchorcenter.getChildren().setAll(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
   }


    

    @FXML
    private void AfficherGestionUtilisateur(MouseEvent event) {
        afficher("ListeUtilisateurs.fxml");
    }

    @FXML
    private void AfficherDemandesEvents(MouseEvent event) {
        afficher("DemandesEvent.fxml");
    }

    @FXML
    private void AfficherListeEvents(MouseEvent event) {
        afficher("ListeEvenements.fxml");
    }

    @FXML
    private void AfficherTypeEvents(MouseEvent event) {
        afficher("ListeTypeEvent.fxml");
    }
    
}
