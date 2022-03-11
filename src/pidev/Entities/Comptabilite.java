/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;


/**
 *
 * @author acer
 */
public class Comptabilite {
    private int id;
    private String libelle;
    private String description;
    private java.sql.Date date;
    private int id_type;
    private String type;

    public Comptabilite(int id, String libelle, String description, Date date, int id_type) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.date = date;
        this.id_type = id_type;
    }

    public Comptabilite(String libelle, String description, Date date, int id_type) {
        this.libelle = libelle;
        this.description = description;
        this.date = date;
        this.id_type = id_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Comptabilite{" + "id=" + id + ", libelle=" + libelle + ", description=" + description + ", date=" + date + ", id_type=" + id_type + ", type=" + type + '}';
    }

    
       
    
}
