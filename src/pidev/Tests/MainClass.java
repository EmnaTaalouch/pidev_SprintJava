/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Tests;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.event.ActionEvent;
import pidev.Entities.Comptabilite;
import pidev.Entities.ComptabiliteType;
import pidev.Services.SComptabilite;
import pidev.Services.SComptabiliteType;
import pidev.Utils.Database;



/**
 *
 * @author marie
 */
public class MainClass {
    

    public static void main(String[] args) throws SQLException {
        Database mc = Database.getInstance();
        Database mc2 = Database.getInstance();
        System.out.println(mc.hashCode()+" - "+ mc2.hashCode());
        SComptabilite ccd = new SComptabilite();
        SComptabiliteType tcd = new SComptabiliteType();
            Date d1= Date.valueOf("2022-02-02");
       
        Comptabilite c1= new Comptabilite("caisse","dd",d1,8);
        ComptabiliteType t1= new ComptabiliteType ("revenu",500);
        
    
     // ccd.ajouterComptabilite(c1);
      // ccd.deleteComptabilite(9);
       //ccd.updateComptabilite(c1, 2);
     // tcd.ajoutertype(t1);
//      tcd.deletetype(8);
    // tcd.updatetype(t1, 6);
    
    
    

    
    }
}