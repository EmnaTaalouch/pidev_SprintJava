/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Evaluation;
import model.EventForRate;
import org.controlsfx.control.Rating;
import service.EvaluationService;

/**
 *
 * @author adem
 */
public class EvaluationController  implements Initializable{

    @FXML
    private TableView<EventForRate> tv_event;
    @FXML
    private TableColumn<EventForRate, String> col_nomEvent;
    @FXML
    private TableColumn<EventForRate, String> col_themeEvent;
    @FXML
    private TableColumn<EventForRate, Date> col_dateDebutEvent;
    @FXML
    private TableColumn<EventForRate, Date> col_dateFinEvent;
    @FXML
    private TableColumn<EventForRate, String> col_ResponsableEvent;
    @FXML
    private TableColumn<EventForRate, Float> col_rateEvent;
    @FXML
    private Button evaluation_btn;
    @FXML
    private TextArea ta_commentaire;
    @FXML
    private Label eventID_label;
    
    
    @FXML
    private Label labelAjRep;
    @FXML
    private Label labelAjRep1;

    public static int id_evenement;

    @FXML
    private TextField tf_idEv;
    @FXML
    private Rating rate;
    @FXML
    private TableView<Evaluation> tv_comm;
    @FXML
    private TableColumn<Evaluation, String> col_comment;
    @FXML
    private TableColumn<EventForRate, Integer> col_IdEvent;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       rate.setRating(0);
        showEvents();
        labelAjRep.setVisible(true);

    }
    
    
    public void showEvents(){
        ObservableList<EventForRate> list = new EvaluationService().readAllEvents();
        System.out.println(list.size());
        col_IdEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, Integer>("id"));
        col_nomEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, String>("nom_event"));
        col_themeEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, String>("event_theme"));
        col_dateDebutEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, Date>("date_debut"));
        col_dateFinEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, Date>("date_fin"));
        col_ResponsableEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, String>("responsable"));
        col_rateEvent.setCellValueFactory(new PropertyValueFactory<EventForRate, Float>("rate"));


        tv_event.setItems(list);
    }

    
    @FXML
    private void tableClicked(MouseEvent event) {
        EventForRate  ev = tv_event.getSelectionModel().getSelectedItem();
        //System.out.println("aa");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(ev.getDate_debut());  
        strDate = dateFormat.format(ev.getDate_fin());  
        eventID_label.setText(""+ev.getId());
        tf_idEv.setText(""+ev.getId());
        ObservableList<Evaluation> list = new EvaluationService().readAllComments(ev.getId());
        //System.out.println(list.get(0).getCommentaire());
        col_comment.setCellValueFactory(new PropertyValueFactory<Evaluation, String>("commentaire"));
        tv_comm.setItems(list);

    }

    @FXML
    private void addEvaluation(MouseEvent event) {
        int idEvent =Integer.parseInt(eventID_label.getText()); 

        Date now =new  Date(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Evaluation e = new Evaluation(1,ta_commentaire.getText(),(float)rate.getRating(), now, 1, idEvent);//client id 1 statically
        new EvaluationService().insertEvaluation(e,1);
        showEvents();
        ta_commentaire.setText("");
        rate.setRating(0);
    }   

    @FXML
    private void goToReclamation(ActionEvent event) throws IOException{
        Parent etab = FXMLLoader.load(getClass().getResource("/view/Reclamation.fxml"));      
        Scene scene = new Scene(etab);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
