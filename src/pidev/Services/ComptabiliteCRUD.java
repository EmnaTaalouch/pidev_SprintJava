/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import edu.db3a13.entities.Comptabilite;
import edu.db3a13.interfaces.IComptabilite;
import edu.db3a13.tools.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.db3a13.entities.User;
import edu.db3a13.entities.Comp_type;
/**
 *
 * @author acer
 */


public class ComptabiliteCRUD implements IComptabilite<Comptabilite>{

    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;

    public ComptabiliteCRUD() {
        cnx = Database.getInstance().getConnection();
    }
    // @Override
    public void ajouterComtabilite(Comptabilite c) {
           String req = "Insert into comptabilite (libelle_type,description_comp,date_comp,id_responsable,montant)  values (?,?,?,?,?)";
        try {
            pst.setString(1, c.getLibelle());
            pst.setString(2, c.getDescription_comp());
            pst.setDate(3, (java.sql.Date) c.getDate_comp());
            pst.setInt(4, c.getId_responsable().getId());
            pst.setInt(5, c.getMontant());
            
            
            pst.executeUpdate();
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //@Override
    public void modifierComtabilite(Comptabilite c,int id_comp) {
        String req = "update comptabilite set libelle_type=?,description_comp=?,date_comp=?,id_responsable=?,montant=? where id_comp =? ";
        try {
            pst.setString(1, c.getLibelle());
            pst.setString(2, c.getDescription_comp());
            pst.setDate(3, (java.sql.Date) c.getDate_comp());
            pst.setInt(4, c.getId_responsable().getId());
            pst.setInt(5, c.getMontant());
            pst.setInt(6, id_comp);
            pst.executeUpdate();
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    //@Override
    public void supprimerComptabilite(int id_comp) {
        String req = "delete from comptabilite where id_comp = "+id_comp+" ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    //@Override
    public Comptabilite afficherComptabiliteById(int id_comp) {
        String req = "select c.*,ct.*,u.* from comptabilite as c join nom_type as ct on c.id_type=ct.id  join user as u on c.id_responsable=u.id   where id_comp = "+id_comp;
        Comptabilite c = new Comptabilite();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
          
               User responsable = new User(rs.getInt("u.id"),rs.getString("u.nom"),rs.getString("u.prenom"),rs.getString("u.login"),rs.getString("u.password"),rs.getString("u.role"));
                Comp_type ct = new Comp_type(rs.getInt("ct.id"),rs.getString("ct.nom"));
                c=new Comptabilite( rs.getInt(1),rs.getString(2) , rs.getString(3)  , rs.getDate(4) , rs.getInt(5)  ,responsable, ct);
            }
        }  
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }
    /*
    @Override
    public void ajouterComptabilite(Comptabilite c) {
        try {
            String requete="INSERT INTO comptabilite (libelle_type,description_comp,date_comp)"+
                    "VALUES ('"+c.getLibelle()+"','"+c.getDescription_comp()+"','"+c.getDate_comp()+"') ";
            Statement st = Database.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("comptabilite ajoute!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }*/
   
 /*   public void ajouterComptabilite2(Comptabilite c) 
    {
        try {
            String requete="INSERT INTO user (libelle_type,description_comp,date_comp)"+
                    "VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, c.getLibelle());
            pst.setString(2, c.getDescription_comp());
            pst.setDate(3, c.getDate_comp());
            pst.executeUpdate();
            System.out.println("comptabilite ajoute!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    @Override
    public void modifierComptabilite(Comptabilite c) {
        try {
            String requete = "update from user where id= ? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, c.getId_comp());
            pst.executeUpdate();
            System.out.println("comptabilite modifie!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

   */ 

    
    
    
    /*
 // @Override
    public void supprimerComptabilite(int id_comp) {
         try {
            String requete = "Delete from comptabilite where id_comp="+id_comp+"";
            PreparedStatement pst = Database.getInstance().getCnx().prepareStatement(requete);
           // pst.setInt(1, c.getId_comp());
            pst.executeUpdate();
            System.out.println("comptabilite supprime!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
*/
   
    
    
    
   /* @Override
    public List<Comptabilite> comptabiliteList() {
        List<Comptabilite> myList= new ArrayList();
        try {
            String requete = "SELECT * FROM comptabilite";
            Statement st = Database.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next())
            {
                Comptabilite c = new Comptabilite();
                c.setId_comp(rs.getInt(1));
                c.setLibelle(rs.getString("Libelle"));
                c.setDescription_comp(rs.getString("Description"));
                c.setDate_comp(rs.getDate("Date"));
                myList.add(c);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
        

    }*/

    @Override
    public void ajouterComptabilite(Comptabilite c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerComptabilite(Comptabilite c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifierComptabilite(Comptabilite c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Comptabilite> comptabiliteList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void afficherComptabiliteById(Comptabilite c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
