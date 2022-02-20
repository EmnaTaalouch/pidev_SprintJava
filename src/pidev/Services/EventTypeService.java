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
public class EventTypeService implements IEventTypeService<Event_type>{
    
    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;
    
    
    public EventTypeService(){
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public void ajouter(Event_type t) {
         String req = "Insert into event_type (libelle)  values (?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getLibelle());
            pst.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Event_type t,int id) {
        
        String req = "update event_type set libelle =? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
             pst.setString(1,t.getLibelle());
             pst.setInt(2,id);
            pst.executeUpdate();    
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
       String req = "delete from event_type where id = "+id+" ";
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
    public Event_type afficherEvent_type_ById(int id) {
      String req = "select * from event_type where id = "+id;
        Event_type e_type = new Event_type();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                e_type=new Event_type(rs.getInt(1), rs.getString(2));
            }
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e_type
;    }

    @Override
    public List<Event_type> afficher() {
    String req = "select * from event_type ";
        List<Event_type> list_event_type = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list_event_type.add(new Event_type(rs.getInt(1), rs.getString(2)));
            }
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event_type;
    }

    @Override
    public List<Event_type> rechercher(String x) {
    String req = "select * from event_type where libelle like '%"+x+"%'  ";
        List<Event_type> list_event_type = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list_event_type.add(new Event_type(rs.getInt(1), rs.getString(2)));
            }
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event_type;
    }
    
}
