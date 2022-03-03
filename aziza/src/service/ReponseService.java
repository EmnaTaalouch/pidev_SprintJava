/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import aziza.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reponse;
/**
 *
 * @author adem
 */
public class ReponseService {
    private PreparedStatement pst;
    private Statement ste;
    private Statement steR;
    private ResultSet rs;


    private Connection connection;
    public ReponseService() {
        connection=Database.getInstance().getConnection();
    }
    public void addReponse(Reponse r){
        String req="insert into reponse (text,id_reclamation) values (?,?)";
        try {
            pst=connection.prepareStatement(req);
         
            pst.setString(1, r.getText());
            pst.setInt(2,r.getId_reclamation());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.ALL.SEVERE, null, ex);
        }
    }
    public ObservableList<Reponse> afficherReponse(int id_reclamtion){
         String req="select * from reponse where id_reclamation = "+id_reclamtion+"";   
         System.out.println(req);
            ObservableList <Reponse> list=FXCollections.observableArrayList();
         try {
            ste=connection.createStatement();
            rs=ste.executeQuery(req);
            while(rs.next()){
                list.add(new Reponse(rs.getInt("id"),rs.getString("text"),rs.getInt("id_reclamation")));
            }
        } catch (SQLException ex) {
             Logger.getLogger(Reponse.class.getName()).log(Level.SEVERE, null, ex);
        }   
         return list;
    }
}
