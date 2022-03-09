/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author hp
 */
public class EventForRate {
    
    private int id;
    private String nom_event ;
    private String event_description;
    private String event_theme;
    private Date date_debut;
    private Date date_fin;
    private String responsable;
    private float rate;

    public EventForRate(int id, String nom_event, String event_description, String event_theme, Date date_debut, Date date_fin, String responsable, float rate) {
        this.id = id;
        this.nom_event = nom_event;
        this.event_description = event_description;
        this.event_theme = event_theme;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.responsable = responsable;
        this.rate = rate;
    }

    public EventForRate(String nom_event, String event_description, String event_theme, Date date_debut, Date date_fin, String responsable, float rate) {
        this.nom_event = nom_event;
        this.event_description = event_description;
        this.event_theme = event_theme;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.responsable = responsable;
        this.rate = rate;
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

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
    
    
}
