/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pidev.Entities.Event;
import pidev.Entities.User;
import pidev.Services.EventService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ParticiperEventClientController implements Initializable {

    @FXML
    private VBox paneevent;
    @FXML
    private AnchorPane anchor;

    EventService es = new EventService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        afficherPublicEvent(paneevent);
        sp.setContent(paneevent);
        anchor.getChildren().add(sp);

    }

    HBox cardTemplate(String stringnom, String stringtype, String stringtheme, String stringlieu, String stringdated, String stringdatef, String stringparticipant, String image_event, int id_event) {
        InputStream stream = null;
        HBox mainHbox = new HBox();
        try {

            VBox imgbtnVBox = new VBox();
            ImageView img = new ImageView();
            stream = new FileInputStream("C:\\Users\\Emna\\Documents\\GitHub\\pidev_SprintJava\\src\\Assets\\Uploads\\" + image_event);
            Image image = new Image(stream);
            //Creating the image view
            img.setImage(image);
            img.setFitHeight(130);
            img.setFitWidth(130);
            Button btnParticipe = new Button("Participer");
            btnParticipe.setPrefSize(130, 30);
            btnParticipe.setStyle("-fx-background-color : pink");
            /*
            Button btnParticipeListe = new Button("Liste des Participants");
            btnParticipeListe.setPrefSize(130, 30);
            btnParticipeListe.setStyle("-fx-background-color : white");
             */
            VBox infoVBox = new VBox();
            infoVBox.setSpacing(5);
            Text nomEvent = new Text("Nom Event : " + stringnom);
            nomEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text typeEvent = new Text("Type Event: " + stringtype);
            typeEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text themeEvent = new Text("Theme Event : " + stringtheme);
            themeEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text lieuEvent = new Text("Lieu Event : " + stringlieu);
            lieuEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text dateEvent = new Text("De   " + stringdated + "   Jusqu'??   " + stringdatef);
            dateEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text nbrPartEvent = new Text("Participants : " + stringparticipant);
            nbrPartEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.ITALIC, 20));
            nbrPartEvent.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    popupListeParticipant(String.valueOf(id_event));

                }
            });
            infoVBox.getChildren().addAll(nomEvent, typeEvent, themeEvent, lieuEvent, dateEvent, nbrPartEvent);
            btnParticipe.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (es.verifier(User.currentUser.getId(), id_event)) {
                        System.out.println(User.currentUser.getId());
                        es.participer(User.currentUser.getId(), id_event);
                        es.decremente(id_event);
                        es.sendsms("53328112", stringnom, stringdated, stringdatef);
                        afficherPublicEvent(paneevent);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Participation");
                        alert.setHeaderText(null);
                        alert.setContentText("vous avez particip?? a cet evenement");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Participation");
                        alert.setHeaderText(null);
                        alert.setContentText("Oh Oh , Tu as deja particip?? a cet evenenemet");
                        alert.showAndWait();
                    }

                }
            });
            infoVBox.setPadding(new Insets(0, 0, 0, 20));
            imgbtnVBox.getChildren().addAll(img, btnParticipe);
            mainHbox.getChildren().addAll(imgbtnVBox, infoVBox);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParticiperEventClientController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(ParticiperEventClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mainHbox;
    }

    void afficherPublicEvent(VBox v) {
        paneevent.getChildren().clear();
        for (Event t : es.afficherpublicevenement()) {
            Text diver = new Text(" \n  \n");
            v.getChildren().add(cardTemplate(t.getNom_event(), t.getId_type().getLibelle(), t.getEvent_theme(), t.getLieu(), t.getDate_debut().toString(), t.getDate_fin().toString(), String.valueOf(t.getNbr_participants()), t.getImage_event(), t.getId()));
            v.getChildren().add(diver);
        }
    }

    void popupListeParticipant(String id_event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ListeParticipants.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            ListeParticipantsController lpc = loader.getController();
            lpc.setMainController(this);
            lpc.setData(id_event);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
