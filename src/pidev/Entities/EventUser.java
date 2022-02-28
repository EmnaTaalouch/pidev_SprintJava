/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;
import pidev.Entities.*;

/**
 *
 * @author Emna
 */
public class EventUser {
    
    private User id_user;
    private Event id_event;

    public EventUser() {
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Event getId_event() {
        return id_event;
    }

    public void setId_event(Event id_event) {
        this.id_event = id_event;
    }

    @Override
    public String toString() {
        return "EventUser{" + "id_user=" + id_user + ", id_event=" + id_event + '}';
    }
    
    
    
    
}
