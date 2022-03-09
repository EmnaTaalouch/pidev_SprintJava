/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import javafx.scene.image.ImageView;

/**
 *
 * @author hp
 */
public class Reclamation {
    private int id;
    private  String description;
    private Date date_reclamation;
    private String image;
    private int id_client;
    private ImageView img;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public Reclamation(int id, String description, Date date_reclamation, String image, int id_client) {
        this.id = id;
        this.description = description;
        this.date_reclamation = date_reclamation;
        this.image = image;
        this.id_client = id_client;
    }

    public Reclamation(String description, Date date_reclamation, String image, int id_client) {
        this.description = description;
        this.date_reclamation = date_reclamation;
        this.image = image;
        this.id_client = id_client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(Date date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    
    
    
}
