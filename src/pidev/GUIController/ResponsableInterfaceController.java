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
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    @FXML
    private VBox vboxmainevent;
    @FXML
    private VBox vboxanimevent;
    Boolean actionevent = false;
    @FXML
    private VBox vboxmainevent1;
    @FXML
    private VBox vboxmainevent11;
    @FXML
    private VBox vboxmainevent12;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vboxmainevent.getChildren().remove(vboxanimevent);
        
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

    @FXML
    private void onanimateevent(MouseEvent event) {
        if (actionevent==false) {
            vboxmainevent.getChildren().add(vboxanimevent);
            actionevent=true;
        }
        else {
            vboxmainevent.getChildren().remove(vboxanimevent);
            actionevent=false;
        }
    }

    @FXML
    private void ongestioncomptabilite(MouseEvent event) {
           try {
        FXMLLoader loader = new FXMLLoader();
        Stage stage = new Stage();
        stage.setTitle("Gestion Comptabilite");
            loader.setLocation(getClass().getResource("MenuComptabilite.fxml"));
            
            BorderPane rootLayout = (BorderPane) loader.load();
            
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(rootLayout);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ongestionreclamation(MouseEvent event) {
                try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MenuReclamation.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion Type Reclamation");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMaximized(true);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ongestionrelationnel(MouseEvent event) {
          try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Menu.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion Relationnel");
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(rootLayout);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
