/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import java.util.List;

/**
 *
 * @author acer
 */
public interface IComptabilite <T> {
    public void ajouterComptabilite(T c);
    public void supprimerComptabilite(T c);
    public void modifierComptabilite(T c);
    public void afficherComptabiliteById(T c);
    public List<T> comptabiliteList();
     
    
}
