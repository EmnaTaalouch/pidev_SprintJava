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
    private Label label_date;
    @FXML
    private Label label_description;
    @FXML
    private ImageView img_reclamation;
    @FXML
    private Label label_clientId;
    @FXML
    private TextArea tf_descReclamation;
    @FXML
    private TextField tf_date;
    @FXML
    private TextField tf_idClient;
    @FXML
    private TextField tf_idReclamation;
    @FXML
    private Label rec_label;
    @FXML
    private Button btn_ReponseConfirm;

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
        img_reclamation.setImage(new Image(rec.getImage()));
        tf_descReclamation.setText(rec.getDescription());
        tf_idClient.setText(""+rec.getId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(rec.getDate_reclamation());  
        tf_date.setText(strDate);
        
        img_reclamation.setVisible(true);
        tf_descReclamation.setVisible(true);
        tf_idClient.setVisible(true);
        tf_date.setVisible(true);
        
        label_clientId.setVisible(true);
        label_date.setVisible(true);
        label_description.setVisible(true);
        rec_label.setVisible(true);
        
        
    }

    @FXML
    private void addReponse(MouseEvent event) {
        Reponse r = new Reponse(ta_reponse.getText(), Integer.parseInt(tf_idReclamation.getText()));
        rs.addReponse(r);
        ta_reponse.setText("");
    }

    
}
