/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import pidev.Entities.Relationnel;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.sql.PreparedStatement;
import pidev.Utils.Database;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author msi
 */
public class ServiceRelationnel implements IService<Relationnel> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    public ServiceRelationnel() {
        con = Database.getInstance().getConnection();

    }

    @Override
    public void ajouter(Relationnel a) throws SQLException {
        try {
        PreparedStatement PS = con.prepareStatement("INSERT INTO `Relationnel` (`nom`, `description`, `image`, `id_categorie`, `rating`) VALUES (?, ?, ?, ?, ? );");
        PS.setString(1, a.getNom());
        PS.setString(2, a.getDescription());
        PS.setString(3, a.getImage());
        PS.setInt(4, a.getId_categorie());
        PS.setDouble(5, a.getRating());
        PS.executeUpdate();
        
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @Override
    public void delete(Relationnel t) throws SQLException {
        try {
            String requete = " delete from Relationnel where id='"+t.getId()+"'" ;
            pst = con.prepareStatement(requete);
              ste=con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    @Override
    public void update(Relationnel t) throws SQLException {
        try {
            String requete = " update Relationnel set nom=? , description=? , image=? , id_categorie=?, rating=?   where id='"+t.getId()+"'"  ;
            pst = con.prepareStatement(requete);
            pst.setString(1,t.getNom());
            pst.setString(2,t.getDescription());
            pst.setString(3,t.getImage());
            pst.setInt(4,t.getId_categorie());
            pst.setDouble(5,t.getRating());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Relationnel> readAll() throws SQLException {
    List<Relationnel> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Relationnel");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String nom=rs.getString("nom");
               String description=rs.getString(3);
               String image=rs.getString("image");
               int idcategorie=rs.getInt(5);
               float rating=rs.getFloat(6);
               Relationnel p=new Relationnel(id, nom, description, image,idcategorie,rating);
     arr.add(p);
     }
    return arr;
    }
    
    @Override
    public List<Relationnel> getTrier() throws SQLException {
    List<Relationnel> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Relationnel ORDER BY nom DESC");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String nom=rs.getString("nom");
               String description=rs.getString(3);
               String image=rs.getString("image");
               int idcategorie=rs.getInt(5);
               float rating=rs.getFloat(6);
               Relationnel p=new Relationnel(id, nom, description, image,idcategorie,rating);
     arr.add(p);
     }
    return arr;
    }

  public Relationnel getByName(String n) {
          Relationnel a = null;
         String requete = " select* from Relationnel  where (nom like '"+n+"%')" ;
        try {
           
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new Relationnel(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getDouble(6));}
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }


    public Relationnel getById(Relationnel f) {
          Relationnel a = null;
         String requete = " select* from Relationnel  where id='"+f.getId()+"'" ;
        try {
           
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new Relationnel(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getDouble(6));}
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }


}
