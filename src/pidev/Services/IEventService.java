/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import java.util.List;

/**
 *
 * @author Emna
 */
public interface IEventService<T> {
    
    public void ajouter(T t);
    
    public void reserverEvent(T t);
    
    public void modifier(T t,int id);
    
    public void accepterRefuserEvent(String status,int id_responsable,int id);
    
    public void supprimer(int id);
    
    public T afficherEventById(int id);
    
    public List<T> afficher();
    
    public List<T> rechercher(String x);
    
    public List<T> historiqueEventbyClient(int id_client);
    
    public List<T> afficherEventbyResponsable(int id_responsable);
    
}
