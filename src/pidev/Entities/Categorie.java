/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entite;

/**
 *
 * @author msi
 */
public class Categorie {
    private int id;
    private String role;

    public Categorie(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public Categorie(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", role=" + role + '}';
    }
    
    
    
}
