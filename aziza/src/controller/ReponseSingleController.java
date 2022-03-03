/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Reclamation;
import model.Reponse;
import service.ReponseService;
/**
 *
 * @author adem
 */
public class ReponseSingleController implements Initializable  {

    @FXML
    private TableColumn<Reponse, Integer> Col_id_Rec;
    @FXML
    private TableColumn<Reponse, String> col_text;
    @FXML
    private TextArea ta_text;
    @FXML
    private TableView<Reponse> tv_reponse;

    @FXML
    private void afficherReponse(MouseEvent event) {
        Reponse r = tv_reponse.getSelectionModel().getSelectedItem();
        ta_text.setText(r.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showRepo();
    }
    public void showRepo(){
        System.out.println(ReclamationController.rec_id_for_rep);
        ObservableList<Reponse> list = new ReponseService().afficherReponse(ReclamationController.rec_id_for_rep);
        Col_id_Rec.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id"));
        col_text.setCellValueFactory(new PropertyValueFactory<Reponse, String>("text"));
        tv_reponse.setItems(list);
    }

    @FXML
    private void back(ActionEvent event) throws IOException{
        Parent etab = FXMLLoader.load(getClass().getResource("/view/Reclamation.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    
}
