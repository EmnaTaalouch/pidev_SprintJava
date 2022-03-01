/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class HistoriqueEventController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<?> tablehistorique;
    @FXML
    private TableColumn<?, ?> nomfield;
    @FXML
    private TableColumn<?, ?> themefield;
    @FXML
    private TableColumn<?, ?> typefield;
    @FXML
    private TableColumn<?, ?> datedfield;
    @FXML
    private TableColumn<?, ?> dateffield;
    @FXML
    private TableColumn<?, ?> lieufield;
    @FXML
    private TextField searchfield;
    @FXML
    private ComboBox<?> demandefield;
    @FXML
    private Button btnreserver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onReserverAction(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ReserverEventClient.fxml"));
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
