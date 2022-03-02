/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pidev.Entities.Event;
import pidev.Entities.Event_type;
import pidev.Entities.DemandeStatusEnum;
import pidev.Entities.User;
import pidev.Utils.Database;
/**
 *
 * @author Emna
 */
public class EventService implements IEventService<Event>{

    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;

    public EventService() {
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public void ajouter(Event t) {
           String req = "Insert into event (nom_event,event_description,event_theme,date_debut,date_fin,event_status,demande_status,id_client,id_responsable,id_type,nbr_participants,lieu)  values (?,?,?,?,?,?,?,?,?,?,?,?)";
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
            
            pst.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Event t,int id) {
        String req = "update event set nom_event =?,event_description =?,event_theme =?,date_debut =?,date_fin =?,id_type =?,nbr_participants=?,lieu=?,id_client=? where id =? ";
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
            pst.setInt(10, id);
            pst.executeUpdate();
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "delete from event where id = "+id+" ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Event afficherEventById(int id) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id   where id = "+id;
        Event e = new Event();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
               User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
               User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                e= new Event();
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
               
                
            }
        }  
        catch (SQLException ex) {
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
                User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
                User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                Event e= new Event();
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
                list_event.add(e);
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }
    
    public List<Event> afficherevenementbydemandestatus(String x) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where e.demande_status='"+x+"'  ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
                User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                Event e= new Event();
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
                list_event.add(e);
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Event> rechercher(String x) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where e.nom_event like '%"+x+"%' or e.event_theme like '%"+x+"%'  or u.nom like '%"+x+"%' ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
               User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                Event e= new Event();
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
                list_event.add(e);           }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }
    public List<Event> rechercherstatuspending(String x) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where e.demande_status ='DemandePending' and (e.nom_event like '%"+x+"%' or e.event_theme like '%"+x+"%'  or u.nom like '%"+x+"%') ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
               User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                Event e= new Event();
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
                list_event.add(e);           }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public void reserverEvent(Event t) {
           String req = "Insert into event (nom_event,event_description,event_theme,date_debut,date_fin,event_status,demande_status,id_client,id_type)  values (?,?,?,?,?,?,?,?,?)";
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
            pst.setInt(10, t.getId_type().getId());
            
            pst.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
        public void modifierreservation(Event t,int id) {
        String req = "update event set nom_event =?,event_description =?,event_theme =?,date_debut =?,date_fin =?,id_type =?,nbr_participants=?,lieu=? where id =? ";
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
            pst.setInt(9, id);
            pst.executeUpdate();
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void accepterRefuserEvent(String status,int id_responsable,int id) {
           // demande_status : Accepted or Refused
                String req = "update event set demande_status =?,id_responsable=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, status);
            pst.setInt(2, id_responsable);
            pst.setInt(3, id);
            pst.executeUpdate();
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Event> historiqueEventbyClientetStatus(int id_client,String x) {
                String req = "select e.*,et.*,uu.* from event as e join event_type as et on e.id_type=et.id join user as  uu on e.id_responsable=uu.id where e.id_client ='"+id_client+"' and e.demande_status ='"+x+"' ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {    
               User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
               Event e= new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                list_event.add(e);
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }
    public List<Event> rechercheEventbyClientetStatus(int id_client,String x,String search) {
                String req = "select e.*,et.*,uu.* from event as e join event_type as et on e.id_type=et.id join user as  uu on e.id_responsable=uu.id where e.id_client ='"+id_client+"' and e.demande_status ='"+x+"' and (e.nom_event like '%"+search+"%' or e.event_theme like '%"+search+"%') ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {    
               User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
               Event e= new Event();
                e.setId(rs.getInt("e.id"));
                e.setNom_event(rs.getString("e.nom_event"));
                e.setEvent_description(rs.getString("e.event_description"));
                e.setEvent_theme(rs.getString("e.event_theme"));
                e.setDate_debut(rs.getDate("e.date_debut"));
                e.setDate_fin(rs.getDate("e.date_fin"));
                e.setEvent_status(rs.getString("e.event_status"));
                e.setDemande_status(rs.getString("e.demande_status"));
                e.setId_responsable(responsable);
                e.setId_type(et);
                e.setNbr_participants(rs.getInt("e.nbr_participants"));
                e.setLieu(rs.getString("e.lieu"));
                list_event.add(e);
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Event> afficherEventbyResponsable(int id_responsable) {
                String req = "select e.*,et.*,u.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id  where e.id_responsable= "+id_responsable;
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                Event e= new Event();
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
                list_event.add(e);
            }
        }  
        catch (SQLException ex) {
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
                User client = new User(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("login"),rs.getString("password"),rs.getString("role"));  
                list_event.add(client);
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    
}
