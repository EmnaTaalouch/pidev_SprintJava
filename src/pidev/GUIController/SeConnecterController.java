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
import java.sql.ResultSet;
import java.util.Random;
import java.util.ResourceBundle;
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
import pidev.Utils.Database;
import pidev.GUIController.MailPassword;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class SeConnecterController implements Initializable {

    private Connection cnx;
    @FXML
    private Hyperlink Pwd;
    @FXML
    private Button btRetour;
    @FXML
    private Label lblErrors;
    @FXML
    private TextField tf1;
    @FXML
    private PasswordField tf2;
    @FXML
    private Button tfSeConnecter;

    public SeConnecterController() {
        cnx = Database.getInstance().getConnection();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private String onSeConnecter() {
        String status = "Success";
        String login = tf1.getText();
        String password = tf2.getText();
        if (login.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Veuillez remplir les champs vides");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM user Where login = ? and password = ?";
            try {
                PreparedStatement pst = cnx.prepareStatement(sql);
                pst.setString(1, login);
                pst.setString(2, password);
                ResultSet rst = pst.executeQuery();
                if (!rst.next()) {
                    setLblError(Color.TOMATO, "Entrez un correct login/Password");
                    status = "Error";
                } else {
                    User.currentUser=new User(rst.getInt("id"),rst.getString("nom"),rst.getString("prenom"),rst.getString("login"),rst.getString("password"),rst.getString("role"));
                    switch (rst.getString("role")) {
                        case "client":
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("ClientInterface.fxml"));
                                AnchorPane rootLayout = (AnchorPane) loader.load();
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setMaximized(true);
                                Scene scene = new Scene(rootLayout);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                            break;

                        case "responsable":
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("ResponsableInterface.fxml"));
                                AnchorPane rootLayout = (AnchorPane) loader.load();
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setMaximized(true);
                                Scene scene = new Scene(rootLayout);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                            break;

                        case "admin":
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("Admin.fxml"));
                                AnchorPane rootLayout = (AnchorPane) loader.load();
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                Scene scene = new Scene(rootLayout);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Choix incorrect");
                            break;
                    }

                    setLblError(Color.GREEN, "Connexion réussie..Redirection..");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
      

        return status;
    }

    private void setLblError(Color color, String text) {
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



    @FXML
    private void Retour(ActionEvent event) throws IOException {
        Parent etab = FXMLLoader.load(getClass().getResource("First.fxml"));
        Scene scene = new Scene(etab);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void onPwd(ActionEvent event) throws Exception {
        Random rand = new Random();
        int code = rand.nextInt();
        MailPassword.sendMail("imeeennne@gmail.com", code);
        Parent etab = FXMLLoader.load(getClass().getResource("NewPassword.fxml"));
        Scene scene = new Scene(etab);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
    }
}
