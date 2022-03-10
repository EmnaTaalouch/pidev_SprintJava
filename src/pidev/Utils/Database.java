/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database {
    

    private Connection connection;
    static Database instance;
    
    private Database(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/pidev", "root", "");
            System.out.println("connexion établie");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static Database getInstance(){
        if(instance == null)
            instance = new Database();
       return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
}
