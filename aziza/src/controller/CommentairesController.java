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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Evaluation;
import model.EventForRate;
import service.EvaluationService;
/**
 * FXML Controller class
 *
 * @author adem
 */
public class CommentairesController implements Initializable {

    @FXML
    private TableView<Evaluation> tv_comm;
    @FXML
    private TableColumn<Evaluation, String> col_comment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Evaluation> list = new EvaluationService().readAllComments(EvaluationController.id_evenement);
        System.out.println(list.get(0).getCommentaire());
        col_comment.setCellValueFactory(new PropertyValueFactory<Evaluation, String>("commentaire"));
        tv_comm.setItems(list);
        
    }    

    @FXML
    private void goBack(ActionEvent event) throws IOException{
        Parent etab = FXMLLoader.load(getClass().getResource("/view/Evaluation.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
}
