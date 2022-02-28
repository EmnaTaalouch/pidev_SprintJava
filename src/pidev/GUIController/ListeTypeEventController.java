/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pidev.Entities.*;
import pidev.Services.EventTypeService;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class ListeTypeEventController implements Initializable {

    @FXML
    private TableView<Event_type> tab;
    @FXML
    private TableColumn<Event_type, String> col;
    @FXML
    private Button ajouter;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private TextField fieldLibelle;
    
    EventTypeService ets = new EventTypeService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        supprimer.setVisible(false);
        modifier.setVisible(false);
        col.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tab.getItems().addAll(ets.afficher());
        onSelect();
    }    

    @FXML
    private void onAjout(ActionEvent event) {
        ets.ajouter(new Event_type(fieldLibelle.getText()));
        tab.getItems().clear();
        tab.getItems().addAll(ets.afficher());
    }

    @FXML
    private void OnSupprime(ActionEvent event) {
        Event_type e = tab.getSelectionModel().getSelectedItem();
        ets.supprimer(e.getId());
        tab.getItems().clear();
        tab.getItems().addAll(ets.afficher());
        fieldLibelle.clear();
        ajouter.setVisible(true);
        supprimer.setVisible(false);
        modifier.setVisible(false);
    }

    @FXML
    private void Onmodife(ActionEvent event) {
        Event_type e = tab.getSelectionModel().getSelectedItem();
        ets.modifier(new Event_type(fieldLibelle.getText()), e.getId());
        tab.getItems().clear();
        tab.getItems().addAll(ets.afficher());
        fieldLibelle.clear();
        ajouter.setVisible(true);
        supprimer.setVisible(false);
        modifier.setVisible(false);
    }
    
    void onSelect() {
        tab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ajouter.setVisible(false);
                supprimer.setVisible(true);
                modifier.setVisible(true);
                Event_type e = tab.getSelectionModel().getSelectedItem();
                fieldLibelle.clear();
                fieldLibelle.setText(e.getLibelle());
                
            }
        });
    }

    
}
