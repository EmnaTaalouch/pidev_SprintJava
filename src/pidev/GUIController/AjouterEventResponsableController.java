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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
public class AjouterEventResponsableController implements Initializable {

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
    private Button ajouter;
    @FXML
    private DatePicker fieldDateD;
    @FXML
    private DatePicker fieldDateF;
    @FXML
    private ComboBox<User> fieldClient;
    @FXML
    private ComboBox<Event_type> fieldType;
    @FXML
    private CheckBox fieldStatus;
    @FXML
    private AnchorPane anchorpane;
    
    EventService es = new EventService();
    EventTypeService ets = new EventTypeService();
    
    private String  listview,Path;
    @FXML
    private Button upload;
    @FXML
    private ImageView image;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fieldClient.getItems().setAll(es.afficherclient());
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
                || fieldClient.getValue() == null
                || fieldDateD.getValue() == null
                || fieldDateF.getValue() == null
                || fieldNom.getText().trim().isEmpty()
                || fieldTheme.getText().trim().isEmpty()
                || fieldNbr.getText().trim().isEmpty()
                || fieldLieu.getText().trim().isEmpty()
                || fieldDescription.getText().trim().isEmpty()
                ) {
            afficherAlert("Tous les champs doivent ??tre remplis");
            return false;
        }

        if (fieldDateD.getValue().compareTo(fieldDateF.getValue()) > 0) {
            afficherAlert("Date fin doit ??tre sup??rieur ou ??gal ?? la date de debut");
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
    private void OnAjout(ActionEvent event) throws IOException {
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
            String PathTo= "C:\\Users\\Emna\\Documents\\GitHub\\pidev_SprintJava\\src\\Assets\\Uploads\\"+listview; 
            File org=new File(Path);
            File news=new File(PathTo);
            Files.copy(org.toPath(), news.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        if(fieldStatus.getText().equals("Priv??"))
            t.setEvent_status(EventStatusEnum.Priv??.toString());
        else
            t.setEvent_status(EventStatusEnum.Publique.toString());
        System.out.println(fieldClient.getSelectionModel().getSelectedItem());
        t.setId_client(fieldClient.getSelectionModel().getSelectedItem());
        t.setId_type(fieldType.getSelectionModel().getSelectedItem());
        User responsable = new User();
        responsable.setId(2);
        t.setId_responsable(responsable);

        es.ajouter(t);
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close(); 
        }
       
        
    }
    
    void algo() {
        fieldStatus.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    fieldStatus.setText("Priv??");
                }
                else {
                    fieldStatus.setText("Publique");
                }
            }
        });
    }
    
  

    @FXML
    private void OnUpload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        fc.setInitialDirectory(home);
    fc.getExtensionFilters().addAll(
            new ExtensionFilter("image Files","*.png;*.jpeg;*.jpg;*.gif")      
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
