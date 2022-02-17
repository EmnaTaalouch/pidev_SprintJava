/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import pidev.Entities.Event;
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
    }

    @Override
    public void modifier(Event t) {
    }

    @Override
    public void supprimer(int id) {
    }

    @Override
    public Event afficherEventById(int id) {
        return null;
    }

    @Override
    public List<Event> afficher() {
        return null;
    }

    @Override
    public List<Event> rechercher(String x) {
        return null;
    }

    
}
