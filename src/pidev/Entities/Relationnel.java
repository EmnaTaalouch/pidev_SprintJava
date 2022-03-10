/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;

import javafx.scene.image.ImageView;

/**
 *
 * @author msi
 */
public class Relationnel {
    private int id;
    private String nom;
    private String description;
    private String image;
    private ImageView img;
    private int id_categorie;
    private String categorie;
    private double rating;

    public Relationnel(int id, String nom, String description, String image, int id_categorie, double rating) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.id_categorie = id_categorie;
        this.rating = rating;
    }

    public Relationnel(String nom, String description, String image, int id_categorie, double rating) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.id_categorie = id_categorie;
        this.categorie = categorie;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Relationnel{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", image=" + image + ", id_categorie=" + id_categorie + ", categorie=" + categorie + '}';
    }

    


}
