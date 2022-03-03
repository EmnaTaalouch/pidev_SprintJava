/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author adem
 */
public class Evaluation {
    private int id;
    private String commentaire;
    private float rate;
    private Date date_evaluation;
    private int  id_client;
    private int id_evenement;

    public Evaluation(int id, String commentaire, float rate, Date date_evaluation, int id_client, int id_evenement) {
        this.id = id;
        this.commentaire = commentaire;
        this.rate = rate;
        this.date_evaluation = date_evaluation;
        this.id_client = id_client;
        this.id_evenement = id_evenement;
    }

    public Evaluation(String commentaire, float rate, Date date_evaluation, int id_client, int id_evenement) {
        this.commentaire = commentaire;
        this.rate = rate;
        this.date_evaluation = date_evaluation;
        this.id_client = id_client;
        this.id_evenement = id_evenement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Date getDate_evaluation() {
        return date_evaluation;
    }

    public void setDate_evaluation(Date date_evaluation) {
        this.date_evaluation = date_evaluation;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }
    
    
    
    
    
}
