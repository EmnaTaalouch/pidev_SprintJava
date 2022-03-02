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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

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



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        for (int i = 0; i < 2; i++) {
            Text diver = new Text(" \n ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- \n");
            paneevent.getChildren().add(cardTemplate("mochi party","brd","theme","hammamet","2/2/22","3/2/22","2"));
            paneevent.getChildren().add(diver);
            
        }
        sp.setContent(paneevent); 
        anchor.getChildren().add(sp);
        
    }    
    
    HBox cardTemplate(String stringnom,String stringtype,String stringtheme,String stringlieu,String stringdated,String stringdatef,String stringparticipant ) {
        InputStream stream = null;
        HBox mainHbox = new HBox();
        try {
            
            VBox imgbtnVBox = new VBox();
            ImageView img = new ImageView();
            stream = new FileInputStream("C:\\Users\\Emna\\Documents\\GitHub\\pidev_SprintJava\\src\\Assets\\Images\\event.png");
            Image image = new Image(stream);
            //Creating the image view
            img.setImage(image);
            img.setFitHeight(150);
            img.setFitWidth(150);
            Button btnParticipe = new Button("Participer");
            btnParticipe.setPrefSize(120, 30);
            btnParticipe.setStyle("-fx-background-color : pink");
            VBox infoVBox = new VBox();
            infoVBox.setSpacing(5);
            Text nomEvent = new Text("Nom Event : "+stringnom);
            nomEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text typeEvent = new Text("Type Event: "+stringtype);
            typeEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text themeEvent = new Text("Theme Event : "+stringtheme);
            themeEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text lieuEvent = new Text("Lieu Event : "+stringlieu);
            lieuEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text dateEvent = new Text("De   "+stringdated+"   Jusqu'à   "+stringdatef);
            dateEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            Text nbrPartEvent = new Text("Participants : "+stringparticipant);
            nbrPartEvent.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            infoVBox.getChildren().addAll(nomEvent,typeEvent,themeEvent,lieuEvent,dateEvent,nbrPartEvent);
            imgbtnVBox.getChildren().addAll(img,btnParticipe);
            mainHbox.getChildren().addAll(imgbtnVBox,infoVBox);
           
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
    
}
