/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

/**
 *
 * @author msi
 */
public class Relationnel {
    private int id;
    private String image;
    private String nom;
    private String description;
    private int id_categorie;

    public Relationnel(int id, String image, String nom, String description, int id_categorie) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.id_categorie = id_categorie;
    }

    public Relationnel(String image, String nom, String description, int id_categorie) {
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.id_categorie = id_categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    @Override
    public String toString() {
        return "Relationnel{" + "id=" + id + ", image=" + image + ", nom=" + nom + ", description=" + description + ", id_categorie=" + id_categorie + '}';
    }
    
    

    
    
}
