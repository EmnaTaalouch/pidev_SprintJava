/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;
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
    @FXML
    private ComboBox<?> fieldType1;
    @FXML
    private Button upload;
    @FXML
    private ImageView image;
    
    private String  listview,Path;

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
    
    
        public void afficherAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean testSaisie() {
        if (
                fieldType.getValue() == null
                || fieldDateD.getValue() == null
                || fieldDateF.getValue() == null
                || fieldNom.getText().trim().isEmpty()
                || fieldTheme.getText().trim().isEmpty()
                || fieldNbr.getText().trim().isEmpty()
                || fieldLieu.getText().trim().isEmpty()
                || fieldDescription.getText().trim().isEmpty()
                ) {
            afficherAlert("Tous les champs doivent être remplis");
            return false;
        }

        if (fieldDateD.getValue().compareTo(fieldDateF.getValue()) > 0) {
            afficherAlert("Date fin doit être supérieur ou égal à la date de debut");
            return false;
        }
        try {
            Double num = Double.parseDouble(fieldNbr.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Champs Nombre Participants invalide doit etre un nombre");
            return false;
        }
        return true;
    }


    
    
    @FXML
    private void OnReserve(ActionEvent event) throws IOException {
        if(testSaisie()) {
         Event t = new Event();
        t.setNom_event(fieldNom.getText());
        t.setEvent_theme(fieldTheme.getText());
        t.setNbr_participants(Integer.valueOf(fieldNbr.getText()));
        t.setLieu(fieldLieu.getText());
        t.setEvent_description(fieldDescription.getText());
        t.setDate_debut(Date.valueOf(fieldDateD.getValue()));
        t.setDate_fin(Date.valueOf(fieldDateF.getValue()));
         if(listview==null){
            t.setImage_event("defaultimage.png");
        }
        else {
            t.setImage_event(listview);
            String PathTo= "C:\\Users\\Emna\\Documents\\GitHub\\pidev_SprintJava-\\src\\Assets\\Uploads\\"+listview; 
            File org=new File(Path);
            File news=new File(PathTo);
            Files.copy(org.toPath(), news.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
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

    @FXML
    private void OnUpload(ActionEvent event) {       
                FileChooser fc = new FileChooser();
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        fc.setInitialDirectory(home);
    fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("image Files","*.png;*.jpeg;*.jpg;*.gif")      
    );
    File selectedFiles = fc.showOpenDialog(null);
    if (selectedFiles !=null){
        
            try {
                listview=selectedFiles.getName();
                Path=selectedFiles.getAbsolutePath();
                image.setImage(new Image(new FileInputStream(Path)));
                System.out.println(listview);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AjouterEventResponsableController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
        System.out.println("file is not valid");
    }
    }
    
}
