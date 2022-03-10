/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author marie
 */
public interface IComptabiliteType <T> {
    
    void ajoutertype(T t) ;
    void deletetype(int id) throws SQLException;
    void updatetype(T t,int id) throws SQLException;
    List<T> readAll() throws SQLException;
    
}
