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
import model.Reclamation;

/**
 *
 * @author adem
 */
public class ReclamationService {
    private PreparedStatement pst;
    private Statement ste ;
    private Connection connection;
    private ResultSet rs;
    public ReclamationService() {
        connection=Database.getInstance().getConnection();
    }
    public ObservableList <Reclamation> readAll(int id_client){
            String req="select* from reclamation where id_client = "+id_client+"";          
            ObservableList <Reclamation> list=FXCollections.observableArrayList();
         try {
            ste=connection.createStatement();
            rs=ste.executeQuery(req);
            while(rs.next()){
                list.add(new Reclamation(rs.getInt("id"),rs.getString("description"), rs.getDate("date_reclamation"), rs.getString("image"),rs.getInt("id_client") ));
            }
        } catch (SQLException ex) {
             Logger.getLogger(Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }   
            return list;
    }
    public void ajouterReclamation(Reclamation r){
        String req="insert into reclamation (description,image,date_reclamation,id_client) values (?,?,?,?)";
        try {
            pst=connection.prepareStatement(req);
            java.sql.Date sqlDate=new java.sql.Date(r.getDate_reclamation().getTime());
         
            pst.setString(1, r.getDescription());
            pst.setString(2,r.getImage());
            pst.setDate(3,sqlDate);
            pst.setInt(4,r.getId_client());
          
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void modifierReclamation(Reclamation r){
        String req="update reclamation set description = ? , image = ? , date_reclamation = ? where id = ?";
        try {
            pst=connection.prepareStatement(req);
            java.sql.Date sqlDate=new java.sql.Date(r.getDate_reclamation().getTime());
            pst.setString(1, r.getDescription());
            pst.setString(2,r.getImage());
            pst.setDate(3,sqlDate);
            pst.setInt(4,r.getId());
          
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void deleteReclamation(String id){
        String req = "delete from reclamation where id = ?";
        try {
            pst=connection.prepareStatement(req);
            pst.setString(1, id);
            
            pst.executeUpdate();
          
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ObservableList <Reclamation> readAllForAdmin(){
            String req="select* from reclamation ";          
            ObservableList <Reclamation> list=FXCollections.observableArrayList();
         try {
            ste=connection.createStatement();
            rs=ste.executeQuery(req);
            while(rs.next()){
                list.add(new Reclamation(rs.getInt("id"),rs.getString("description"), rs.getDate("date_reclamation"), rs.getString("image"),rs.getInt("id_client") ));
            }
        } catch (SQLException ex) {
             Logger.getLogger(Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }   
            return list;
    }
}
