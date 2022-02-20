/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.sql.Date;
import pidev.Entities.*;
import pidev.Services.*;


/**
 *
 * @author Emna
 */
public class Pidev {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventService es = new EventService();
        EventTypeService ets = new EventTypeService();
        User u = new User("emna","taalouch","emna.taalouch@esprit.tn","123","client");
        User uu = new User("imen","taalouch","imen.taalouch@esprit.tn","123","responsable");
        Event_type et2 = new Event_type("hackaton");
        Event_type et3 = new Event_type("anniversaire");
        Event event1 = new Event("event one", "event description", "evmenot theme", new Date(2022-02-01), new Date(2022-02-01), EventStatusEnum.EventPending.toString(), u, uu, et2);
        //et2.setLibelle("curie");
        Event_type et4 = new Event_type("emna");
        ets.modifier(et4,2);
        
        
        
        
        
        
         
         
        
        
    }
    
}
