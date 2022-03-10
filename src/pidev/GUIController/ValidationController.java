/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pidev.Entities.User;
import pidev.Services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class ValidationController implements Initializable {

    @FXML
    private PasswordField pNew;
    @FXML
    private Button btValider;
    @FXML
    private TextField tfLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onValider(ActionEvent event) {
        UserCRUD ucd =new UserCRUD();

        User u = new User();
        u.setLogin(tfLogin.getText());
        u.setPassword(pNew.getText());
        ucd.modifierUserPassword(u);
    }
    
}
