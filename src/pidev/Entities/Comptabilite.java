/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

import java.util.Date;

/**
 *
 * @author acer
 */
public class Comptabilite {
    private int id_comp;
    private String libelle_type;
    private String description_comp;
    private Date date_comp;
    private int montant;
    private User id_responsable;
    
    
    public Comptabilite()
    {
        
    }
    public Comptabilite(int id_comp,String libelle_type, String description_comp, Date date_comp,User id_responsable, int montant)
    {
        this.id_comp=id_comp;
        this.libelle_type=libelle_type;
        this.description_comp=description_comp;
        this.date_comp=date_comp;
        this.id_responsable = id_responsable;
        this.montant=montant;
           
    }

    public Comptabilite(String libelle_type, String description_comp, Date date_comp,User id_responsable, int montant) {
        this.libelle_type=libelle_type;
        this.description_comp=description_comp;
        this.date_comp=date_comp;
        this.id_responsable = id_responsable;
        this.montant=montant;
    }

    public Comptabilite(int aInt, String string, String string0, java.sql.Date date, int aInt0, User responsable, Comp_type ct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


  
    public int getId_comp() {
        return id_comp;
    }

    public String getLibelle() {
        return libelle_type;
    }

    public String getDescription_comp() {
        return description_comp;
    }

    public Date getDate_comp() {
        return date_comp;
    }
    public User getId_responsable() {
        return id_responsable;
    }

     public int getMontant() {
        return montant;
    }
    
    public void setId_responsable(User id_responsable) {
        this.id_responsable = id_responsable;
    }
   

    public void setId_comp(int id_comp) {
        this.id_comp = id_comp;
    }

    public void setLibelle(String libelle_type) {
        this.libelle_type = libelle_type;
    }

    public void setDescription_comp(String description_comp) {
        this.description_comp = description_comp;
    }

    public void setDate_comp(Date date_comp) {
        this.date_comp = date_comp;
    }
    public void setMontant(int montant) {
        this.montant = montant;
    }



    @Override
    public String toString() {
        return "Comptabilite{" + "id_comp=" + id_comp + ", libelle_type=" + libelle_type + ", description_comp=" + description_comp + ", date_comp=" + date_comp + ", id_responsable=" + id_responsable + ", montant=" + montant +'}';
    }
    
    
    
    
}
