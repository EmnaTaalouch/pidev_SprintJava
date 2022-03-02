/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pidev.Entities.DemandeStatusEnum;
import pidev.Entities.Event;
import pidev.Entities.User;
import pidev.Services.EventService;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Emna
 */
public class DemandesEventController implements Initializable {

    @FXML
    private TableView<Event> tabdem;
    @FXML
    private TableColumn<Event, String> colnom;
    @FXML
    private TableColumn<Event, String> coltype;
    @FXML
    private TableColumn<Event, String> colclient;
    @FXML
    private TableColumn<Event, String> coldated;
    @FXML
    private TableColumn<Event, String> coldatef;
    @FXML
    private TableColumn<Event, String> colstatus;
    
    EventService es = new EventService();
    @FXML
    private TextField searchfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                colnom.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        colclient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        coldated.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        coldatef.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("event_status"));
        tabdem.getItems().setAll(es.afficherevenementbydemandestatus(DemandeStatusEnum.DemandePending.toString()));
        chercher();
    }    

    @FXML
    private void OnAccepterEvent(ActionEvent event) throws MessagingException {
        if(tabdem.getSelectionModel().getSelectedItem()!=null) {
        Event t = tabdem.getSelectionModel().getSelectedItem();
        User responsable = new User(2,"bb","bb","bb","bb","responsable");
        es.accepterRefuserEvent(DemandeStatusEnum.DemandeAccepted.toString(), responsable.getId(), t.getId());
        sendmail("emna.taalouch@esprit.tn","Acceptation de l'event","votre demande a l evenement " + t.getNom_event() + " a ete acceptée");
        tabdem.getItems().setAll(es.afficherevenementbydemandestatus(DemandeStatusEnum.DemandePending.toString()));
        }
    }

    @FXML
    private void OnRefuserEvent(ActionEvent event) throws MessagingException {
        if(tabdem.getSelectionModel().getSelectedItem()!=null) {
        Event t = tabdem.getSelectionModel().getSelectedItem();
        User responsable = new User(2,"bb","bb","bb","bb","responsable");
        es.accepterRefuserEvent(DemandeStatusEnum.DemandeRefused.toString(), responsable.getId(), t.getId());
        sendmail("emna.taalouch@esprit.tn","Acceptation de l'event","votre demande a l evenement " + t.getNom_event() + " a ete refusée");
        tabdem.getItems().setAll(es.afficherevenementbydemandestatus(DemandeStatusEnum.DemandePending.toString()));
        }
        
    }
    
    void chercher() {
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            tabdem.getItems().setAll(es.rechercherstatuspending(newValue));
        });
    }
    
    void sendmail(String recepient,String subj,String desc) throws AddressException, MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587"); 
        String from ="eventplanningesprit@gmail.com";
        String mdp = "Faithoverfear*1";
        Session session = Session.getInstance(prop,new Authenticator() {
            @Override
            protected  PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from, mdp);
              }
            });
        Message message= new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject(subj);
            message.setText(desc);
        Transport.send(message);
        System.out.println("send ok");
    }
    
}
