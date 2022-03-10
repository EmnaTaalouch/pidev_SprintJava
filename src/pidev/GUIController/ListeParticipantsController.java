/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pidev.Entities.EventUser;
import pidev.Services.EventService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ListeParticipantsController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Text id_event;
    @FXML
    private VBox mainvbox;

    EventService es = new EventService();

    ParticiperEventClientController lec;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    void setMainController(ParticiperEventClientController l) {
        lec = l;
    }

    void setData(String id) {
        id_event.setText(id);
        id_event.setVisible(false);
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        for (EventUser t : es.afficherlisteclientparticipant(Integer.valueOf(id_event.getText()))) {
            Text diver = new Text(" \n  \n");
            HBox mainHbox = new HBox();
            Text nom = new Text("Nom : " + t.getId_user().getNom());
            nom.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text prenom = new Text("     Prenom : " + t.getId_user().getPrenom());
            prenom.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            mainHbox.getChildren().addAll(nom, prenom);
            mainvbox.getChildren().add(mainHbox);
            mainvbox.getChildren().add(diver);
        }
        sp.setContent(mainvbox);
        anchor.getChildren().add(sp);
    }

}
