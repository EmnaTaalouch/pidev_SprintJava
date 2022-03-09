/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Reponse;
import model.Reclamation;
import service.ReclamationService;
import service.ReponseService;

/**
 * FXML Controller class
 *
 * @author adem
 */
public class ReponseController implements Initializable {

    @FXML
    private TableView<Reclamation> tv_reclamation;
    @FXML
    private TableColumn<Reclamation, Integer> col_id;
    @FXML
    private TableColumn<Reclamation, Date> col_date;
    @FXML
    private TableColumn<Reclamation, String> col_desc;
    @FXML
    private TableColumn<Reclamation, String> col_image;
    @FXML
    private TableColumn<Reclamation, Integer> col_clientId;
    @FXML
    private TextArea ta_reponse;
    @FXML
    private TextField tf_idReclamation;
    @FXML
    private Button btn_ReponseConfirm;
    @FXML
    private TableView<Reponse> tv_reponse;
    @FXML
    private TableColumn<Reponse, Integer> Col_id_Rec;
    @FXML
    private TableColumn<Reponse, String> col_text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showReclamation();
    }   
    ReponseService rs =new ReponseService();

    public void showReclamation(){
        ObservableList<Reclamation> list = new ReclamationService().readAllForAdmin();//we statically set the client id to just show his reclamations
        col_id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        col_desc.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date_reclamation"));
        col_image.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("image"));
        col_clientId.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id_client"));

        tv_reclamation.setItems(list);
    }
    @FXML
    private void tableClicked(MouseEvent event) {
        Reclamation rec = tv_reclamation.getSelectionModel().getSelectedItem();
        
        tf_idReclamation.setText(Integer.toString(rec.getId()));
        
        
        showRepo(rec.getId());

    }
    
        public void showRepo(int id){
        ObservableList<Reponse> list = new ReponseService().afficherReponse(id);
        Col_id_Rec.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id"));
        col_text.setCellValueFactory(new PropertyValueFactory<Reponse, String>("text"));
        tv_reponse.setItems(list);
    }

    @FXML
    private void addReponse(MouseEvent event) {
        Reponse r = new Reponse(ta_reponse.getText(), Integer.parseInt(tf_idReclamation.getText()));
        rs.addReponse(r);
        ta_reponse.setText("");
    }

    @FXML
    private void afficherReponse(MouseEvent event) {
    }

    
}
