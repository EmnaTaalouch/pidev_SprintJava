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
import java.util.List;
import pidev.Entities.Event_type;
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
    
    
    public void EventTypeService(){
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public void ajouter(Event_type t) {
         String req = "Insert into event_type (libelle)  values (?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getLibelle());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Event_type t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Event_type afficherEventById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Event_type> afficher() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Event_type> rechercher(String x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
