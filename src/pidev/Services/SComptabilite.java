/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import pidev.Entities.Comptabilite;
import java.sql.SQLException;
import java.sql.*;
import java.sql.PreparedStatement;
import pidev.Utils.Database;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pidev.Entities.ComptabiliteType;



/**
 *
 * @author marie
 */
public class SComptabilite implements IComptabilite<Comptabilite>  {
    
private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    public SComptabilite() {
        con = Database.getInstance().getConnection();

    }

    @Override
    public void ajouterComptabilite(Comptabilite t)  {
        
        String requeteInsert = "INSERT INTO comptabilite ( libelle , description , date, id_type) VALUES (?,?,?,?)";
        try {
            pst = con.prepareStatement(requeteInsert);
            pst.setString(1,t.getLibelle());
            pst.setString(2,t.getDescription());
            pst.setDate(3,t.getDate());
            pst.setInt(4,t.getId_type());
            pst.executeUpdate();
            System.out.println("comptabilite ajoute!!");
        } 
       
        catch (SQLException ex) {
           Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
       @Override
    public void deleteComptabilite(int id) throws SQLException {
        try {
            String requete = " delete from comptabilite where id='"+id+"'" ;
            pst = con.prepareStatement(requete);
              ste=con.createStatement();
            ste.executeUpdate(requete);
            System.out.println("comptabilite supprimee!!");
            
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
  public void updateComptabilite(Comptabilite t,int id) throws SQLException {
        try {
            String requete = " update comptabilite set `libelle`=? , `description`=? , `date`=?, `id_type`=?   where `id`= ?"  ;
            pst = con.prepareStatement(requete);
            pst.setString(1,t.getLibelle());
            pst.setString(2,t.getDescription());
            pst.setDate(3,  t.getDate());
            pst.setInt(4,t.getId_type()); 
            pst.setInt(5,id); 
            pst.executeUpdate();
            System.out.println("comptabilite modifiee!!");
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Comptabilite> readAll() throws SQLException {
    List<Comptabilite> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from comptabilite");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String lib=rs.getString("libelle");
               String description=rs.getString(3);
               Date date=rs.getDate("date");
               int id_type=rs.getInt("id_type");
               SComptabiliteType sc = new SComptabiliteType();
               ComptabiliteType tmp = sc.getById(id_type);
               Comptabilite c=new Comptabilite(id, lib , description, date, id_type);
               c.setType(tmp.getType());
     arr.add(c);
     }
    return arr;
    }
    
    public List<Comptabilite> getTrier() throws SQLException {
    List<Comptabilite> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from comptabilite ORDER BY date DESC");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String type=rs.getString("libelle");
               String description=rs.getString(3);
               Date date=rs.getDate("date");
               int id_type=rs.getInt("id_type");
               Comptabilite p=new Comptabilite(id, type, description, date,id_type);
     arr.add(p);
     }
    return arr;
    }

  public Comptabilite getByLibelle(String n) {
          Comptabilite a = null;
         String requete = " select* from comptabilite  where (libelle like '"+n+"%')" ;
        try {
           
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new Comptabilite(res.getInt(1),res.getString(2),res.getString(3),res.getDate(4),res.getInt(5));}
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }

    public Comptabilite getById(Comptabilite c) {
          Comptabilite a = null;
         String requete = " select* from comptabilite  where id='"+c.getId()+"'" ;
        try {
           
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new Comptabilite(res.getInt(1),res.getString(2),res.getString(3),res.getDate(4),res.getInt(5));}
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }

    public int getLatestComptabiliteId() throws SQLException {
        int idComptabilite = 0;
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT MAX(id) from comptabilite;");
        while (rs.next()) {
            idComptabilite = rs.getInt(1);
        
        }
        return idComptabilite;}

    public boolean verifExistance(Comptabilite c) {

        int res = 0;
        String sql = "";
         boolean t=true;

        try {
            sql= "select * from comptabilite where libelle=?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1,c.getLibelle());

            ResultSet rs = pt.executeQuery();
            t = rs.next();
            System.out.println(t);
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return t;
    } 
    
    
      public int calculer(int idtype) {
          int l = 0 ;
         String requete = "SELECT COUNT(*) FROM comptabilite WHERE id_type= "+idtype ;
        try {
           
           Statement st =con.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          String chaine = String.valueOf(rs.getString(1));
           l=Integer.parseInt(chaine);
            System.out.println(l);}
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return l ;
    }   


   
}






