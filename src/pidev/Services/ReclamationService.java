/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import pidev.Utils.Database;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pidev.Entities.Reclamation;

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
        Reclamation rec=null;
            String req="select* from reclamation where id_client = "+id_client+"";          
            ObservableList <Reclamation> list=FXCollections.observableArrayList();
         try {
            ste=connection.createStatement();
            rs=ste.executeQuery(req);
            while(rs.next()){
                rec = new Reclamation(rs.getInt("id"),rs.getString("description"), rs.getDate("date_reclamation"), rs.getString("image"),rs.getInt("id_client") );
                              
                File file = new File(rec.getImage());
                Image image = new Image(file.toURI().toString());
                
                ImageView imageView =new ImageView(image);
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                rec.setImg(imageView);                
                list.add(rec);
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
        Reclamation rec = null;
            String req="select* from reclamation ";          
            ObservableList <Reclamation> list=FXCollections.observableArrayList();
         try {
            ste=connection.createStatement();
            rs=ste.executeQuery(req);
            while(rs.next()){
                
                rec = new Reclamation(rs.getInt("id"),rs.getString("description"), rs.getDate("date_reclamation"), rs.getString("image"),rs.getInt("id_client") );
                              
                File file = new File(rec.getImage());
                Image image = new Image(file.toURI().toString());
                
                ImageView imageView =new ImageView(image);
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                rec.setImg(imageView);                
                list.add(rec);
            }
        } catch (SQLException ex) {
             Logger.getLogger(Reclamation.class.getName()).log(Level.SEVERE, null, ex);
        }   
            return list;
    }
}
