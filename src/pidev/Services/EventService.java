/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.filechooser.FileSystemView;
import pidev.Entities.Event;
import pidev.Entities.Event_type;
import pidev.Entities.DemandeStatusEnum;
import pidev.Entities.EventUser;
import pidev.Entities.User;
import pidev.Utils.Database;

/**
 *
 * @author Emna
 */
public class EventService implements IEventService<Event> {

    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;
    private final static String ACCOUNT_SID = "ACb3d0e0eced06ffd7beec5ff336b6b389";
    private final static String AUTH_TOKEN = "48a2ce5415323b81c317213fa6a39dab";

    public EventService() {
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public void ajouter(Event t) {
        String req = "Insert into event (nom_event,event_description,event_theme,date_debut,date_fin,event_status,demande_status,id_client,id_responsable,id_type,nbr_participants,lieu,image_event)  values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getNom_event());
            pst.setString(2, t.getEvent_description());
            pst.setString(3, t.getEvent_theme());
            pst.setDate(4, t.getDate_debut());
            pst.setDate(5, t.getDate_fin());
            pst.setString(6, t.getEvent_status());
            pst.setString(7, DemandeStatusEnum.DemandePending.toString());
            pst.setInt(8, t.getId_client().getId());
            pst.setInt(9, t.getId_responsable().getId());
            pst.setInt(10, t.getId_type().getId());
            pst.setInt(11, t.getNbr_participants());
            pst.setString(12, t.getLieu());
            pst.setString(13,t.getImage_event());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void participer(int id_user, int id_event) {
        String req = "Insert into event_user (id_user,id_event)  values (?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, id_user);
            pst.setInt(2, id_event);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void decremente(int id) {
        String req = "update event set nbr_participants=nbr_participants-1 where id =?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Boolean verifier(int id_user, int id_event) {
        String req = "select count(*) as same from event_user where id_user=" + id_user + " and id_event=" + id_event;
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public void modifier(Event t, int id) {
        String req = "update event set nom_event =?,event_description =?,event_theme =?,date_debut =?,date_fin =?,id_type =?,nbr_participants=?,lieu=?,id_client=?,image_event=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getNom_event());
            pst.setString(2, t.getEvent_description());
            pst.setString(3, t.getEvent_theme());
            pst.setDate(4, t.getDate_debut());
            pst.setDate(5, t.getDate_fin());
            pst.setInt(6, t.getId_type().getId());
            pst.setInt(7, t.getNbr_participants());
            pst.setString(8, t.getLieu());
            pst.setInt(9, t.getId_client().getId());
            pst.setString(10, t.getImage_event());
            pst.setInt(11, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "delete from event where id = " + id + " ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Event afficherEventById(int id) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id   where id = " + id;
        Event e = new Event();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                User responsable = new User(rs.getInt("uu.id"), rs.getString("uu.nom"), rs.getString("uu.prenom"), rs.getString("uu.login"), rs.getString("uu.password"), rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }

    @Override
    public List<Event> afficher() {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id  ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                User responsable = new User(rs.getInt("uu.id"), rs.getString("uu.nom"), rs.getString("uu.prenom"), rs.getString("uu.login"), rs.getString("uu.password"), rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public List<Event> afficherevenementbydemandestatus(String x) {
        String req = "select e.*,et.*,u.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id  where e.demande_status='" + x + "'  ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public List<Event> afficherpublicevenement() {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where e.demande_status='DemandeAccepted' and e.event_status='Publique'  ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                User responsable = new User(rs.getInt("uu.id"), rs.getString("uu.nom"), rs.getString("uu.prenom"), rs.getString("uu.login"), rs.getString("uu.password"), rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Event> rechercher(String x) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where e.nom_event like '%" + x + "%' or e.event_theme like '%" + x + "%'  or u.nom like '%" + x + "%' ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                User responsable = new User(rs.getInt("uu.id"), rs.getString("uu.nom"), rs.getString("uu.prenom"), rs.getString("uu.login"), rs.getString("uu.password"), rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public List<Event> rechercherstatuspending(String x) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where e.demande_status ='DemandePending' and (e.nom_event like '%" + x + "%' or e.event_theme like '%" + x + "%'  or u.nom like '%" + x + "%') ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                User responsable = new User(rs.getInt("uu.id"), rs.getString("uu.nom"), rs.getString("uu.prenom"), rs.getString("uu.login"), rs.getString("uu.password"), rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public void reserverEvent(Event t) {
        String req = "Insert into event (nom_event,event_description,event_theme,date_debut,date_fin,event_status,demande_status,id_client,id_type,nbr_participants,lieu,image_event)  values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getNom_event());
            pst.setString(2, t.getEvent_description());
            pst.setString(3, t.getEvent_theme());
            pst.setDate(4, t.getDate_debut());
            pst.setDate(5, t.getDate_fin());
            pst.setString(6, t.getEvent_status());
            pst.setString(7, DemandeStatusEnum.DemandePending.toString());
            pst.setInt(8, t.getId_client().getId());
            pst.setInt(9, t.getId_type().getId());
            pst.setInt(10, t.getNbr_participants());
            pst.setString(11, t.getLieu());
            pst.setString(12, t.getImage_event());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifierreservation(Event t, int id) {
        String req = "update event set nom_event =?,event_description =?,event_theme =?,date_debut =?,date_fin =?,id_type =?,nbr_participants=?,lieu=?,image_event=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getNom_event());
            pst.setString(2, t.getEvent_description());
            pst.setString(3, t.getEvent_theme());
            pst.setDate(4, t.getDate_debut());
            pst.setDate(5, t.getDate_fin());
            pst.setInt(6, t.getId_type().getId());
            pst.setInt(7, t.getNbr_participants());
            pst.setString(8, t.getLieu());
            pst.setString(9, t.getImage_event());
            pst.setInt(10, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void accepterRefuserEvent(String status, int id_responsable, int id) {
        // demande_status : Accepted or Refused
        String req = "update event set demande_status =?,id_responsable=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, status);
            pst.setInt(2, id_responsable);
            pst.setInt(3, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Event> historiqueEventbyClientetStatus(int id_client, String x) {
        String req = "select e.*,et.* from event as e join event_type as et on e.id_type=et.id  where e.id_client ='" + id_client + "' and e.demande_status ='" + x + "' ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {

                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public List<Event> rechercheEventbyClientetStatus(int id_client, String x, String search) {
        String req = "select e.*,et.* from event as e join event_type as et on e.id_type=et.id  where e.id_client ='" + id_client + "' and e.demande_status ='" + x + "' and (e.nom_event like '%" + search + "%' or e.event_theme like '%" + search + "%') ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Event> afficherEventbyResponsable(int id_responsable) {
        String req = "select e.*,et.*,u.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id  where e.id_responsable= " + id_responsable;
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                Event_type et = new Event_type(rs.getInt("et.id"), rs.getString("et.libelle"));
                Event e = new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_client(client);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                e.setImage_event(rs.getString("e.image_event"));
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }
    
        public List<EventUser> afficherlisteclientparticipant(int id_event) {
        String req = "select u.* from event_user e join user u on e.id_user=u.id where e.id_event=" + id_event;
        List<EventUser> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"), rs.getString("u.nom"), rs.getString("u.prenom"), rs.getString("u.login"), rs.getString("u.password"), rs.getString("u.role"));
                EventUser e = new EventUser();
                e.setId_user(client);
                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public List<User> afficherclient() {
        String req = "select * from user where role='client' ";
        List<User> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("login"), rs.getString("password"), rs.getString("role"));
                list_event.add(client);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public void sendsms(String str, String nomevent,String dated,String datef) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+216" + str), // To number
                new PhoneNumber("+15038324523"), // From number
                "vous avez participé a l evenement " + nomevent +" De   "+dated+"   Jusqu'à   "+datef // SMS body
        ).create();
        System.out.println(message.getSid());
    }

    public void sendmail(String recepient, String subj, String desc) throws AddressException, MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        String from = "eventplanningesprit@gmail.com";
        String mdp = "Faithoverfear*1";
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, mdp);
            }
        });
        javax.mail.Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(RecipientType.TO, new InternetAddress(recepient));
        message.setSubject(subj);
        message.setText(desc);
        Transport.send(message);
        System.out.println("send ok");
    }
    
    public void exportpdf(List<Event> l,AnchorPane pane) throws FileNotFoundException, DocumentException {

              Document doc = new Document();
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(home);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files (.pdf)", "*.pdf"),
                new FileChooser.ExtensionFilter("HTML files (.html)", "*.html")
        );
        File saveFile = fileChooser.showSaveDialog(pane.getScene().getWindow());
        if(saveFile!=null) {
        PdfWriter.getInstance(doc,new FileOutputStream(saveFile.getAbsolutePath()));
        doc.open();   
         for ( Event p :l)
         {             
        doc.add(new Paragraph("Nom Evenement :"+p.getNom_event()));
        doc.add(new Paragraph("Theme Evenement :"+p.getEvent_theme()));
        doc.add(new Paragraph("Type Evenement  :"+p.getId_type().getLibelle()));
        doc.add(new Paragraph("Status Evenement  :"+p.getEvent_status()));
        doc.add(new Paragraph("Demande Status Evenement  :"+p.getDemande_status()));
        doc.add(new Paragraph("De   "+p.getDate_debut().toString()+"   Jusqu'a   "+p.getDate_fin().toString()));
        doc.add(new Paragraph("Lieu Evenement  :"+p.getLieu()));
        doc.add(new Paragraph("Nombre Participant  :"+p.getNbr_participants()));
        doc.add(new Paragraph("=========================================================================="));
         }  
        doc.close();           
        }
    }

}
