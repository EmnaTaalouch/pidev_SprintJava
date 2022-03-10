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
    private ComboBox cRole;
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
        ObservableList<String> list = FXCollections.observableArrayList("Admin","Responsable","Client");
       cRole.setItems(list);
    }    
    
    
    
/*
    /// -- 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    */
/*
    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }
*/
/*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    public LoginController() {
        con = ConnectionUtil.conDB();
    }
*/

    //we gonna use string to check for status
    @FXML
    private String onSeConnecter() {
        String status = "Success";
        String login = tf1.getText();
        String password = tf2.getText();
        String role = (String) cRole.getSelectionModel().getSelectedItem();
        if(login.isEmpty() || password.isEmpty() || role.isEmpty() ) {
            setLblError(Color.TOMATO, "Veuillez remplir les champs vides");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM user Where login = ? and password = ? and role = ?";
            try {
                PreparedStatement pst = cnx.prepareStatement(sql);
                pst.setString(1, login);
                pst.setString(2, password);
                pst.setString(3, role);
                ResultSet rst = pst.executeQuery();
                if (!rst.next()) {
                    setLblError(Color.TOMATO, "Entrez un correct login/Password/role");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Connexion réussie..Redirection..");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        if(status == "Success" && role=="Admin")
        {
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
        }
        if(status == "Success" && role=="Responsable")
        {
        try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ResponsableInterface.fxml"));
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
        if(status == "Success" && role=="Client")
        {
        try {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ClientInterface.fxml"));
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
    private void Select(ActionEvent event) {
         String s =cRole.getSelectionModel().getSelectedItem().toString();
    }
    @FXML
    private void Retour(ActionEvent event) throws IOException{
        Parent etab = FXMLLoader.load(getClass().getResource("First.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void onPwd(ActionEvent event) throws Exception {
        Random rand = new Random();
        int code = rand.nextInt();
        MailPassword.sendMail("imeeennne@gmail.com",code);
        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPassword.fxml"));
        
            AnchorPane rootLayout = (AnchorPane) loader.load();
            NewPasswordController npc = loader.getController();
        npc.setCode(code);
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
    

