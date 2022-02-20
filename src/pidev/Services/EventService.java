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
           String req = "Insert into event (nom_event,event_description,event_theme,date_debut,date_fin,event_status,id_client,id_responsable,id_type)  values (?,?,?,?,?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getNom_event());
            pst.setString(1, t.getEvent_description());
            pst.setString(1, t.getEvent_theme());
            pst.setDate(1, t.getDate_debut());
            pst.setDate(1, t.getDate_fin());
            pst.setString(1, t.getEvent_status());
            pst.setInt(1, t.getId_client().getId());
            pst.setInt(1, t.getId_responsable().getId());
            pst.setInt(1, t.getId_type().getId());
            
            pst.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Event t,int id) {
        String req = "update event set nom_event =?,event_description =?,event_theme =?,date_debut =?,date_fin =?,event_status =?,id_type =? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getNom_event());
            pst.setString(2, t.getEvent_description());
            pst.setString(3, t.getEvent_theme());
            pst.setDate(4, t.getDate_debut());
            pst.setDate(5, t.getDate_fin());
            pst.setString(6, t.getEvent_status());
            pst.setInt(7, t.getId_type().getId());
            pst.setInt(8, id);
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
                e=new Event(rs.getInt(1), rs.getString(2) , rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getDate(6) , rs.getString(7) , client ,responsable, et);
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
                list_event.add(new Event(rs.getInt(1), rs.getString(2) , rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getDate(6) , rs.getString(7) , client ,responsable, et));
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Event> rechercher(String x) {
        String req = "select e.*,et.*,u.*,uu.* from event as e join event_type as et on e.id_type=et.id  join user as u on e.id_client=u.id join user as  uu on e.id_responsable=uu.id where nom_event like '%"+x+"%'  ";
        List<Event> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User client = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role")); 
               User responsable = new User(rs.getInt("uu.id"),rs.getString("uu.nom"),rs.getString("uu.prenom"),rs.getString("uu.login"),rs.getString("uu.password"),rs.getString("uu.role"));
                Event_type et = new Event_type(rs.getInt("et.id"),rs.getString("et.libelle"));
                list_event.add(new Event(rs.getInt(1), rs.getString(2) , rs.getString(3) , rs.getString(4) , rs.getDate(5) , rs.getDate(6) , rs.getString(7) , client ,responsable, et));
           }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    
}
