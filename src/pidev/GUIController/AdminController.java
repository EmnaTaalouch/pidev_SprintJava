
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidev.Entities.User;
import pidev.Services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author acer
 */

public class AdminController implements Initializable {

    @FXML
    private TableView<User> tabUsers;
    @FXML
    private TableColumn<User,String> col_id;
    @FXML
    private TableColumn<User,String> col_nom;
    @FXML
    private TableColumn<User,String> col_prenom;
    @FXML
    private TableColumn<User,String> col_login;
    @FXML
    private TableColumn<User,String> col_password;
    @FXML
    private TableColumn<User,String> col_role;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSupprimer;
    @FXML
    private TextField rechercher;
    private final ObservableList<User> data = FXCollections.observableArrayList();
    
    UserCRUD ucd = new UserCRUD();
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfRole;
    @FXML
    private Label idlabel;
    
    
    
    

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println(User.currentUser.getLogin());
            // TODO
            showUsers();
            LoadModif();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
        
    public void showUsers() throws SQLException{
        
        data.clear();
         UserCRUD d = new UserCRUD();
        ArrayList<User> act = (ArrayList<User>) d.usersList();
        data.addAll(act);
        tabUsers.setItems(data);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));

       // TabComptabilite.setItems(list);
        RechercheAV();
    }
    
        public void RechercheAV(){
        FilteredList<User> filteredData = new FilteredList<>(data, b -> true);
		rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tmp -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (tmp.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; 
				} else if (String.valueOf(tmp.getId()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; 
			});
		});
		
		SortedList<User> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(tabUsers.comparatorProperty());
		
		tabUsers.setItems(sortedData);
    }


        
    
        private boolean Validchamp(TextField T){
        return !T.getText().isEmpty() && T.getLength() >2;
    }

    

     void LoadModif() {
             tabUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                     User u = tabUsers.getSelectionModel().getSelectedItem();
                     tfNom.setText(u.getNom());
                     tfPassword.setText(u.getPassword());
                     tfPrenom.setText(u.getPrenom());
                     tfRole.setText(u.getRole());
                     tfLogin.setText(u.getLogin());
                     idlabel.setText(String.valueOf(u.getId()));
                }
            });
           
            
    }

    @FXML
    private void OnModifier(ActionEvent event) throws SQLException {
          if(Validchamp(tfLogin)&&  !tfPassword.getText().isEmpty())
        {
        UserCRUD ucd =new UserCRUD();

        User u = new User();
        u.setLogin(tfLogin.getText());
        u.setPassword(tfPassword.getText());
        u.setNom(tfNom.getText());
        u.setPrenom(tfPrenom.getText());
        u.setRole(tfRole.getText());
        ucd.modifierUser(u,Integer.valueOf(idlabel.getText()));
        showUsers();
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");
        alert.showAndWait();
        }
    }
     @FXML
    private void onSupprimer(ActionEvent event) throws SQLException {
        ucd.supprimerUser((Integer.valueOf(idlabel.getText())));
        showUsers();
       
    }
    
    
}


