/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Entities;



/**
 *
 * @author marie
 */
public class ComptabiliteType {
    

    private int id;
    private int montant;
    private String type;

    
    

    public ComptabiliteType(int id,String type,int montant)
    {
        this.id=id;
        this.montant=montant;
        this.type=type;
    }

    public ComptabiliteType(String type,int montant) {
        this.montant=montant;
        this.type=type;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public String toString() {
        return "type_comptabilite{" + "id=" + id + ", type=" + type + ", montant=" + montant + '}';
    }

   
    
    
}

