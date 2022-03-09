/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Reclamation;
import service.ReclamationService;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Reponse;
import service.ReponseService;

/**
 *
 * @author adem
 */
public class ReclamationController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tf_id;
    @FXML
    private TextArea ta_desc;
    @FXML
    private DatePicker dp_date;
    @FXML
    private ImageView iv_image;
    @FXML
    private TableColumn<Reclamation, Integer> col_id;
    @FXML
    private TableColumn<Reclamation, Date> col_date;
    @FXML
    private TableColumn<Reclamation, String> col_desc;
    @FXML
    private TableColumn<Reclamation, ImageView> col_image;

    ReclamationService rs = new ReclamationService();
    @FXML
    private TableView<Reclamation> tv_reclamation;
    @FXML
    private TextField tf_image;
    @FXML
    private Button button_image;
    @FXML
    private Button but_insert;
    @FXML
    private Button but_update;
    @FXML
    private Button but_delete;
    @FXML
    private Button clear;
    @FXML
    private Button btn_affReponse;

    public static int rec_id_for_rep;
    @FXML
    private TableView<Reponse> tv_reponse;
    @FXML
    private TableColumn<Reponse, Integer> Col_id_Rec;
    @FXML
    private TableColumn<Reponse, String> col_text;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iv_image.setImage(new Image("/img/upload.jpg"));
        showReclamations();
    }
    public void showReclamations() {
        ObservableList<Reclamation> list = new ReclamationService().readAll(1);//we statically set the client id to just show his reclamations
        col_id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        col_desc.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date_reclamation"));
        col_image.setCellValueFactory(new PropertyValueFactory<Reclamation, ImageView>("img"));
        tv_reclamation.setItems(list);
    }
    @FXML
    private void addReclamation(MouseEvent event) throws ParseException {

        System.out.println(dp_date.getValue().getYear());
        Date d = java.sql.Date.valueOf(dp_date.getValue());
        Reclamation r = new Reclamation(ta_desc.getText(), d, tf_image.getText(), 1);// i set the client statically, it has to be changed later in the integration 
        rs.ajouterReclamation(r);
        showReclamations();
    }
    @FXML
    private void uploadImage(MouseEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println(selectedFile.getPath());
            Image image = new Image(selectedFile.toURI().toString());
            
            iv_image.setImage(image);
            tf_image.setText(selectedFile.toURI().toString());
        }
    }
    @FXML
    private void updateReclamation(MouseEvent event) {
        //System.out.println(dp_date.getValue().getYear());
        Date d = java.sql.Date.valueOf(dp_date.getValue());
        Reclamation r = new Reclamation(Integer.parseInt(tf_id.getText()), ta_desc.getText(), d, tf_image.getText(), 1);// i set the client statically, it has to be changed later in the integration 

        rs.modifierReclamation(r);
        showReclamations();
    }
    @FXML
    private void deleteReclamation(MouseEvent event) {
        rs.deleteReclamation(tf_id.getText());
        showReclamations();
    }
    @FXML
    private void tableClicked(MouseEvent event) {
        Reclamation rec = tv_reclamation.getSelectionModel().getSelectedItem();
        tf_id.setText(Integer.toString(rec.getId()));
        tf_image.setText(rec.getImage());
        ta_desc.setText(rec.getDescription());
        int year = Integer.parseInt(rec.getDate_reclamation().toString().substring(0, 4));
        int month = Integer.parseInt(rec.getDate_reclamation().toString().substring(5,7));
        int day= Integer.parseInt(rec.getDate_reclamation().toString().substring(8,10));

        
        
        dp_date.setValue(LocalDate.of(year, month, day));
     //   iv_image.setImage(new Image(rec.getImage()));
        showRepo(rec.getId());

    }
    
        public void showRepo(int id){
        ObservableList<Reponse> list = new ReponseService().afficherReponse(id);
        Col_id_Rec.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id"));
        col_text.setCellValueFactory(new PropertyValueFactory<Reponse, String>("text"));
        tv_reponse.setItems(list);
    }

    @FXML
    private void clearFields(javafx.event.ActionEvent event) {
        tf_id.setText("");
        tf_image.setText("");
        ta_desc.setText("");
        dp_date.setValue(null);
        iv_image.setImage(null);
    }

     @FXML
    private void afficherReponse(MouseEvent event) throws IOException {
        rec_id_for_rep = Integer.parseInt(tf_id.getText());
        Parent etab = FXMLLoader.load(getClass().getResource("/view/ReponseSingle.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    
}
