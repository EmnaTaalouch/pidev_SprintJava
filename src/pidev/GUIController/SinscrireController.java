/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pidev.Entities.User;
import pidev.Services.UserCRUD;
import pidev.Utils.Database;
import pidev.GUIController.JavaMailUtil;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class SinscrireController implements Initializable {
     private Connection cnx;
    @FXML
    private Label labelnom;
    @FXML
    private Label lblErrors;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfLogin;
    private TextField tfPassword;
    @FXML
    private Button buttonSinscrire;
    @FXML
    private ComboBox cRole;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btBack;
    public SinscrireController() {
        cnx = Database.getInstance().getConnection();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       ObservableList<String> list = FXCollections.observableArrayList("admin","responsable","client");
       cRole.setItems(list);
    }  
    
    @FXML
    private void Select(ActionEvent event) {
        String s =cRole.getSelectionModel().getSelectedItem().toString();
    }
    
    @FXML
    public String onSinscrire() throws Exception {
         UserCRUD ucd = new UserCRUD();
        User u = new User(tfNom.getText(),tfPrenom.getText(),tfLogin.getText(),pfPassword.getText(), (String) cRole.getSelectionModel().getSelectedItem());
        
       String status = "Success";
       String nom = tfNom.getText();
       String prenom = tfPrenom.getText();
        String login = tfLogin.getText();
        String password = pfPassword.getText();
        String role = (String) cRole.getSelectionModel().getSelectedItem();
        Pattern err = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
 
    //    if(err.matcher(tf.getText()).matches() &&  !tf.getText().isEmpty()  )
        if(nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty()|| role.isEmpty()
                || !  err.matcher(login).matches() ) {
            
            setLblError(Color.TOMATO, "Attention erreur ");
            status = "Error";
        } 
 /*       else if (ControleSaisie.isString(nom)||ControleSaisie.isString(prenom)||ControleSaisie.valiemail(login)
||ControleSaisie.validPasswor(password))
           
            {
            setLblError(Color.TOMATO, "champs ERRONEE");
            status = "Error";
        } */else   {
        ucd.ajouterUser(u);
        JavaMailUtil.sendMail("imeeennne@gmail.com"); 
        } 
      
        return status;  
       
    }
    
     public void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
     

    private void onRetour(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("First.fxml"));
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
    
    
/*
    private void verifusername(KeyEvent event) {
        UserCRUD ucd = new UserCRUD();
        if (ucd.chercherUtilisateurBylogin(tfNom.getText()) == true) {
            labelnom.setText("Nom d'utilisateur existe déja");
            boolean verificationUserName = false;
        }
        if (ucd.chercherUtilisateurBylogin(tfNom.getText()) == false) {
            labelnom.setText("Nom d'utilisateur n'existe pas");
            boolean verificationUserName = true;
        }

    }
    
    @FXML
    private void verifEmail(KeyEvent event) {

        if (myServices.chercherUtilisateurByEmail(email.getText()) == true) {
            labelEmail.setText("Email Existe déja");
            verificationUserEmail = false;
        }
        if (myServices.chercherUtilisateurByEmail(email.getText()) == false) {//alphanumerique@alphanumerique.com
            //{ici longeur  }
            //debut ^
            //fin $
            String email_pattern = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+" + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
            Pattern pattern = Pattern.compile(email_pattern);
            Matcher matcher = pattern.matcher(email.getText());

            if (matcher.matches()) {       //if   matcher ne contient pas la format   
                labelEmail.setVisible(false);
                labelEmail.setText("Email valide !");
                verificationUserEmail = true;

            } else {
                labelEmail.setVisible(true);
                labelEmail.setText("Email Format invalide !");
                // JOptionPane.showMessageDialog(null, "Email Format invalide");
                verificationUserEmail = false;

            }
        }

    }
     @FXML
    private void ConfirmEmail(KeyEvent event) {

        if (email.getText().equals(ccnfirmation_email.getText())) {
            labelconfEmail.setText("les deux email sont confondu");
            verificationUserConfirmEmail = true;
        } else {
            labelconfEmail.setText("Verifier votre email");
            verificationUserConfirmEmail = false;
        }

    }
     @FXML
    private void controlMdp(KeyEvent event) {

        String PAS = password.getText().trim();
 
        if (PAS.length() >= 6) {// Check for Digits in password
//•	Contains at least 1 numeric digit
            labelpasswordlength.setText("longeur just");
            verificationUserConfirmpasword = true;

            for (int i = 0; i < PAS.length(); i++) {
                char ch = PAS.charAt(i);

                if (Character.isDigit(ch)) {// Check for Digits in password
//•	Contains at least 1 numeric digit
                    labelcontainsDigit.setText("Contient un nombre");
                    containsDigit = true;
                }

                if (Character.isLetter(ch) && Character.isLowerCase(ch)) {// Check for Letters in password
//•	Contains at least 1 lower letter character
                    labelcontainsLowerCaseLetter.setText("Contient une lettre minus");
                    containsLowerCaseLetter = true;

                }

                if (Character.isLetter(ch) && Character.isUpperCase(ch)) {// Check for Letters in password
//•	Contains at least 1 upper letter character
                    labelpasswordcontainsUpperCaseLetter.setText("Contient une lettre majus");
                    containsUpperCaseLetter = true;

                }
                if (ch == '!' || ch == '@' || ch == '#' || ch == '$' || ch == '%' || ch == '^' || ch == '&' || ch == '*') {
//•	Contains at least 1 special character from the set: !@#$%^&*
                    labelpasswordcontainsSpecialCharacter.setText("Contient un lettre sepcial");
                    containsSpecialCharacter = true;

                }
                System.out.println(containsUpperCaseLetter + "containsUpperCaseLetter\n" + containsLowerCaseLetter + "containsLowerCaseLetter\n"
                        + containsDigit + "containsDigit\n" + containsSpecialCharacter + "containsSpecialCharacter\n\n\n");

                if (containsUpperCaseLetter && containsLowerCaseLetter && containsDigit && containsSpecialCharacter) {
                    labelpassword.setText("Mot de passe valide!");

                    verificationUserpasword = true;
                }

            }
        } else {
            labelpasswordlength.setText("Il faut 6 caractere");
            labelpassword.setText("Mot de passe  invalide!");
            length = false;
            verificationUserpasword = false;
            labelpasswordcontainsSpecialCharacter.setText("");
            containsSpecialCharacter = false;
            labelpasswordcontainsUpperCaseLetter.setText("");
            containsUpperCaseLetter = false;
            labelcontainsLowerCaseLetter.setText("");
            containsLowerCaseLetter = false;
            labelcontainsDigit.setText("");
            containsDigit = false;
        }

    }
    @FXML
    private void ConfirmMDP(KeyEvent event) {
        
      
        if (password.getText().equals(Confirmation_password.getText())) {
            labelConfirmationMdp.setText("Mot de passe vide!");
            verificationUserConfirmpasword = true;
        } else {
            labelConfirmationMdp.setText("Verifier votre mot de passe");
            verificationUserConfirmpasword = false;
        }

    }
    //verification 3
    public boolean verifconfirMail() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Confirmez l'ajout de l'utilisateur");
        dialog.setHeaderText("Un mail a été envoyer au l'utlisateur avec l'username et mot de passe");
        dialog.setContentText("Entre oui pour la confirmation:");
        Optional<String> result = dialog.showAndWait();
        if (result.get().equals("oui")) {

            if (result.get().equals("oui")) {
                return true;
            }
        } else {
            return verifconfirMail();
        }
        return false;
    }
    
    
 */   
    /*
    private void verifNom(KeyEvent event) {
        
        
        int nbNonChar = 0;
            for (int i = 1; i < tfNom.getText().trim().length(); i++) {
                char ch = tfNom.getText().charAt(i);
                if (!Character.isLetter(ch)) {
                    nbNonChar++;
                }
            }

            if (nbNonChar == 0 && tfNom.getText().trim().length() >=3) {
            boolean verificationUserName = true;
            } else {
            boolean verificationUserName = false;
            }
    }
    
   
   private void verifPrenom(KeyEvent event) {
   int nbNonChar = 0;
            for (int i = 1; i < prenom.getText().trim().length(); i++) {
                char ch = prenom.getText().charAt(i);
                if (!Character.isLetter(ch)) {
                    nbNonChar++;
                }
            }

            if (nbNonChar == 0 && prenom.getText().trim().length() >=3) {
            nomCheck.setImage(new Image("/pidev/bonplan/ressources/img/checkMark.jpg"));
            
            verificationUserPrenom = true;
            } else {
              usernameCheck.setImage(new Image("/pidev/bonplan/ressources/img/checkMark.jpg"));
//                erreur = erreur + ("Pas de caractere permit dans le telephone\n");
                 verificationUserPrenom = false;

            }
    }
*/
/*
   private void Retour(ActionEvent event) throws IOException{
        Parent etab = FXMLLoader.load(getClass().getResource("First.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
*/

    @FXML
    private void Back(ActionEvent event) throws IOException {
        Parent etab = FXMLLoader.load(getClass().getResource("First.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
   
    
}
