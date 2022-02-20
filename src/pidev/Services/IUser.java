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
public interface IUser <T> {
    public void ajouterUser(T u);
    public void supprimerUser(int id);
    public void modifierUser(T u,int id);
    public List<T> usersList();
     
    
}
