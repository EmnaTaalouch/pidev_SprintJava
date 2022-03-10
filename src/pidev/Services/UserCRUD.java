/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import java.sql.Connection;
import pidev.Entities.User;
import pidev.Utils.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pidev.Entities.Event;

/**
 *
 * @author acer
 */
public class UserCRUD implements IUser <User> {
    private Connection cnx;
    public UserCRUD() {
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public void ajouterUser(User u) {
        try {
            String requete="INSERT INTO user (id,nom,prenom,login,password,role)"+
                    "VALUES ('"+u.getId()+"','"+u.getNom()+"','"+u.getPrenom()+"','"+u.getLogin()+"','"+u.getPassword()+"','"+u.getRole()+"') ";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("user ajoute!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public void ajouterUser2(User u) 
    {
        try {
            String requete="INSERT INTO user (nom,prenom,login,password,role)"+
                    "VALUES (?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getLogin());
            pst.setString(4, u.getPassword());
            pst.setString(5, u.getRole());
            pst.executeUpdate();
            System.out.println("user ajoute!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    

    @Override
    public void supprimerUser(int id) {
         try {
            String requete = "Delete from user where id="+id+"";
            PreparedStatement pst = cnx.prepareStatement(requete);
        //    pst.setInt(1, u.getId_user());
            pst.executeUpdate();
            System.out.println("user supprime!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
     @Override
    public void modifierUser(User u,int id)  {
        try {
            String requete = "update user set nom=?,prenom =?,login =?,password =?,role=? where id='"+id+"'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst = cnx.prepareStatement(requete);
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getLogin());
            pst.setString(4, u.getPassword());
            pst.setString(5, u.getRole());    
            pst.executeUpdate();
            System.out.println("user modifiee!!");
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifierUserPassword(User u)  {
        try {
            String requete = "update user set password =? where login='"+u.getLogin()+"'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst = cnx.prepareStatement(requete);
            pst.setString(1, u.getPassword());
            //pst.setString(2, u.getLogin());
          //  pst.setString(2, u.getPrenom());
         //   pst.setString(3, u.getLogin());
          //  pst.setString(2, u.getPassword());
        //    pst.setString(3, u.getRole());    
            pst.executeUpdate();
            System.out.println("password modifiee!!");
            
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    

    @Override
    public List<User> usersList() {
        List<User> myList= new ArrayList();
        try {
            String requete = "SELECT * FROM user";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next())
            {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setLogin(rs.getString("login"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                myList.add(u);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
        

    }
    /*
    public boolean chercherUtilisateurBylogin(String s) {
        User user = null;
        String req = "select * from user where nom =?";
        PreparedStatement preparedStatement;
        try {
         preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                  user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (user == null) {
            return false;
        }
        return true;
    }

    */
    
}
