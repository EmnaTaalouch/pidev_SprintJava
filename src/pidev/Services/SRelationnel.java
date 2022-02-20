
package pidev.Services;

import Entite.Relationnel;
import java.sql.SQLException;
import java.sql.*;
import java.sql.PreparedStatement;
import Utils.DataBase;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author House
 */
public class SRelationnel implements Irelationnel<Relationnel> {

    private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    public SRelationnel() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Relationnel t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO Relationnel ( `nom`, `description`, `image`, `id_categorie`) VALUES ('" + t.getNom()+ "', '" + t.getDescription() + "', '" + t.getImage()+ "','"+ t.getId_categorie() +"');";
        try {
            ste=con.createStatement();
            int executeUpdate = ste.executeUpdate(requeteInsert);
            
        } catch (SQLException ex) {
            Logger.getLogger(SRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /* public void ajouter1(Clubs p) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `gestion_des_ecoles`.`club` ( `nom`, `categorie`, `descrition`) VALUES ( ?, ?, ?);");
    pre.setString(1, p.getNom());
    pre.setString(2, p.getPrenom());
    pre.setInt(3, p.getAge());
    pre.executeUpdate();
    }*/
    
        @Override
    public void delete(int id) throws SQLException {
        try {
            String requete = " delete from Relationnel where id='"+id+"'" ;
            pst = con.prepareStatement(requete);
              ste=con.createStatement();
            ste.executeUpdate(requete);
            
        } catch (SQLException ex) {
            Logger.getLogger(SRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    @Override
    public void update(Relationnel t,int id) throws SQLException {
        try {
            String requete = " update Relationnel set nom=? , description=? , image=?, id_categorie=?   where id='"+id+"'"  ;
            pst = con.prepareStatement(requete);
            pst.setString(1,t.getNom());
            pst.setString(2,t.getDescription());
            pst.setString(3,t.getImage());
            pst.setInt(4,t.getId_categorie());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Relationnel> readAll() throws SQLException {
    List<Relationnel> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Relationnel");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String type=rs.getString("nom");
               String description=rs.getString(3);
               String image=rs.getString("image");
               int id_categorie=rs.getInt("id_categorie");
               Relationnel p=new Relationnel(id, type, description, image,id_categorie);
     arr.add(p);
     }
    return arr;
    }
    
    public List<Relationnel> getTrier() throws SQLException {
    List<Relationnel> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Relationnel ORDER BY type DESC");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String type=rs.getString("nom");
               String description=rs.getString(3);
               String image=rs.getString("image");
               int id_categorie=rs.getInt("id_categorie");
               Relationnel p=new Relationnel(id, type, description, image,id_categorie);
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
            {a=new Relationnel(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5));}
        } catch (SQLException ex) {
            Logger.getLogger(SRelationnel.class.getName()).log(Level.SEVERE, null, ex);
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
            {a=new Relationnel(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5));}
        } catch (SQLException ex) {
            Logger.getLogger(SRelationnel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }
  

}
