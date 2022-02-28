/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

import java.sql.Date;

/**
 *
 * @author Emna
 */
public class Event {
    private int id;
    private String nom_event;
    private String event_description;
    private String event_theme;
    private Date date_debut;
    private Date date_fin;
    private String event_status;
    private User id_client;
    private User id_responsable;
    private Event_type id_type;

    public Event(String nom_event, String event_description, String event_theme, Date date_debut, Date date_fin, String event_status, User id_client, User id_responsable, Event_type id_type) {
        this.nom_event = nom_event;
        this.event_description = event_description;
        this.event_theme = event_theme;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.event_status = event_status;
        this.id_client = id_client;
        this.id_responsable = id_responsable;
        this.id_type = id_type;
    }

    public Event(int id, String nom_event, String event_description, String event_theme, Date date_debut, Date date_fin, String event_status, User id_client, User id_responsable, Event_type id_type) {
        this.id = id;
        this.nom_event = nom_event;
        this.event_description = event_description;
        this.event_theme = event_theme;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.event_status = event_status;
        this.id_client = id_client;
        this.id_responsable = id_responsable;
        this.id_type = id_type;
    }
    public Event(int id, String nom_event, String event_description, String event_theme, Date date_debut, Date date_fin, String event_status, User id_client, Event_type id_type) {
        this.id = id;
        this.nom_event = nom_event;
        this.event_description = event_description;
        this.event_theme = event_theme;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.event_status = event_status;
        this.id_client = id_client;
        
        this.id_type = id_type;
    }
    

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_theme() {
        return event_theme;
    }

    public void setEvent_theme(String event_theme) {
        this.event_theme = event_theme;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }

    public User getId_client() {
        return id_client;
    }

    public void setId_client(User id_client) {
        this.id_client = id_client;
    }

    public User getId_responsable() {
        return id_responsable;
    }

    public void setId_responsable(User id_responsable) {
        this.id_responsable = id_responsable;
    }

    public Event_type getId_type() {
        return id_type;
    }

    public void setId_type(Event_type id_type) {
        this.id_type = id_type;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", nom_event=" + nom_event + ", event_description=" + event_description + ", event_theme=" + event_theme + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", event_status=" + event_status + ", id_client=" + id_client + ", id_responsable=" + id_responsable + ", id_type=" + id_type + '}';
    }
    
   
}
