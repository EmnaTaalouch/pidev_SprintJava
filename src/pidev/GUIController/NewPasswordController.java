/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.Entities.User;
import pidev.GUIController.MailPassword;
import pidev.Services.UserCRUD;
import pidev.Utils.Database;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class NewPasswordController implements Initializable {
     private Connection cnx;
    @FXML
    private Button btReturn;
    @FXML
    private Label idlabel;
    public NewPasswordController() {
        cnx = Database.getInstance().getConnection();
    }
    

    @FXML
    private TextField tfCode;
  
    @FXML
    private Button btValider;
    private int code;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setCode(int code){
        this.code=code;
    }
    @FXML
    private void onValider(ActionEvent event) {
        
       if(  tfCode.getText().isEmpty() )
        {
       
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");
        alert.showAndWait();
        }
       
       
        else
        {
            System.out.println(this.code);
            if(this.code == Integer.parseInt(tfCode.getText())){
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Code validee!");
        alert.showAndWait();
                try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Validation.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
           
       }
             
        
        }
       
    }
    

    @FXML
    private void Return(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeConnecter.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
