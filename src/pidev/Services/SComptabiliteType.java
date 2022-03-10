/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;
import pidev.Entities.ComptabiliteType;
import java.sql.SQLException;
import java.sql.*;
import java.sql.PreparedStatement;
import pidev.Utils.Database;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author marie
 */
public class SComptabiliteType implements IComptabiliteType<ComptabiliteType>{
    private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    public SComptabiliteType() {
        con = Database.getInstance().getConnection();

    }
    @Override
    public void ajoutertype(ComptabiliteType t)  {
        
        String requeteInsert = "INSERT INTO type_comptabilite ( type,montant) VALUES (?,?)";
        try {
            pst = con.prepareStatement(requeteInsert);

            pst.setString(1,t.getType());
            pst.setInt(2,t.getMontant());
            pst.executeUpdate();
            System.out.println(" type comptabilite ajoute!!");
        } 
       
        catch (SQLException ex) {
           Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        @Override
    public void deletetype(int id) throws SQLException {
        try {
            String requete = " delete from type_comptabilite where id="+id ;
            pst = con.prepareStatement(requete);
              ste=con.createStatement();
            ste.executeUpdate(requete);
            System.out.println(" type comptabilite supprimee!!");
            
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }    }
    
    
    @Override
  public void updatetype(ComptabiliteType t,int id) throws SQLException {
        try {
            String requete = " update type_comptabilite set type=? , montant=?   where id='"+id+"'"  ;
            pst = con.prepareStatement(requete);
            pst.setString(1,t.getType());
            pst.setInt(2,t.getMontant()); 
            pst.executeUpdate();
            System.out.println(" type comptabilite modifiee!!");
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
  @Override
    public List<ComptabiliteType> readAll() throws SQLException {
    List<ComptabiliteType> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from type_comptabilite");
     while (rs.next()) {                
               int id=rs.getInt("id");
               String type=rs.getString("type");
               int montant=rs.getInt("montant");
               ComptabiliteType ct= new ComptabiliteType (id,type,montant);
     arr.add(ct);
     }
    return arr;
    }

  
 /* public int NbDepense() throws SQLException {
        int nb = 0;
        try {
            ste = con.createStatement();

            ResultSet rs = ste.executeQuery("SELECT COUNT(*)as nb FROM type_comptabilite WHERE type like 'depense'");

            rs.next();
            nb = rs.getInt("nb");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nb;
    }
  public int NbRevenu() throws SQLException {
        int nbr = 0;
        try {
            ste = con.createStatement();

            ResultSet rs = ste.executeQuery("SELECT COUNT(*)as nbr FROM type_comptabilite WHERE type like 'revenu'");

            rs.next();
            nbr = rs.getInt("nbr");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nbr;
    }*/



/*public float TotalDepense() throws SQLException {
        float total_depense = 0;
        try {

            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT SUM(montant) As total_depense FROM type_comptabilite where type like 'depense'");

            rs.next();
            total_depense = rs.getFloat("total_depense");
                    
             
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return total_depense;

    }
public float TotalRevenu() throws SQLException {
        float total_revenu = 0;
        try {

            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT SUM(montant) As total_depense FROM type_comptabilite where type like 'reveneu'");

            rs.next();
            total_revenu = rs.getFloat("total_revenu");
                    
             
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return total_revenu;

    }
*/
    public ComptabiliteType getById(int id_type) {
          ComptabiliteType a = null;
         String requete = " select* from type_comptabilite where id="+id_type ;
        try {
           
           Statement stm = con.createStatement();
            ResultSet rs =stm.executeQuery(requete);
            if (rs.next())
            {
               int id=rs.getInt("id");
               String type=rs.getString("type");
               int montant=rs.getInt("montant");
               a= new ComptabiliteType (id,type,montant);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SComptabiliteType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }

    public ComptabiliteType getByName(String value) {
ComptabiliteType a = null;
         String requete = " select* from type_comptabilite where type='"+value+"'" ;
        try {
           
           Statement stm = con.createStatement();
            ResultSet rs =stm.executeQuery(requete);
            if (rs.next())
            {
               int id=rs.getInt("id");
               String type=rs.getString("type");
               int montant=rs.getInt("montant");
               a= new ComptabiliteType (id,type,montant);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SComptabiliteType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
    }
    public String calculerToTalDep() {
          String l ="" ;
         String requete = "SELECT SUM(montant) FROM type_comptabilite where type like 'depense%'";
        try {
           
           Statement st =con.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          l = String.valueOf(rs.getString(1));
            System.out.println(l);}
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return l ;
    }

    public String calculerToTalRev() {
          String l = "" ;
         String requete = "SELECT SUM(montant) FROM type_comptabilite where type like 'revenu%'";
        try {
           
           Statement st =con.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          l = String.valueOf(rs.getString(1));
            System.out.println(l);}
        } catch (SQLException ex) {
            Logger.getLogger(SComptabilite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return l ;
    }

}
