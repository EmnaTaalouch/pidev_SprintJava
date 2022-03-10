/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import pidev.Utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.Entities.EventForRate;
import pidev.Entities.Evaluation;
import pidev.Entities.Reponse;

/**
 *
 * @author adem
 */
public class EvaluationService {

    private PreparedStatement pst;
    private Statement ste;
    private Statement steR;

    private Statement ste1;
    private ResultSet rs1;
    private PreparedStatement pst1;

    private Connection connection;
    private ResultSet rs;
    private ResultSet rsR;

    public EvaluationService() {
        connection = Database.getInstance().getConnection();
    }
    public ObservableList<EventForRate> readAllEvents() {
        String req = "select* from event";
        ObservableList<EventForRate> list = FXCollections.observableArrayList();

        double myRate = 0;
        try {
            ste = connection.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                String req1 = "select AVG(rate) as avg  from evaluation where id_evenement= " + rs.getInt("id") + "";
                try {
                    ste1 = connection.createStatement();
                    rs1 = ste1.executeQuery(req1);
                    while (rs1.next()) {
                        myRate = rs1.getDouble("avg");
                    }
                } catch (Exception e) {
                    Logger.getLogger(EventForRate.class.getName()).log(Level.SEVERE, null, e);
                }

                list.add(new EventForRate(rs.getInt("id"), rs.getString("nom_event"), rs.getString("event_description"), rs.getString("event_theme"), rs.getDate("date_debut"), rs.getDate("date_fin"), "" + rs.getInt("id_responsable"), (float) myRate));
            };
        } catch (SQLException ex) {
            Logger.getLogger(EventForRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void insertEvaluation(Evaluation e, int id_client) {

                
            
            String req = "insert into evaluation (commentaire,rate,date_evaluation,id_client,id_evenement) values (?,?,?,?,?)";
            try {
                pst = connection.prepareStatement(req);
                java.sql.Date sqlDate = new java.sql.Date(e.getDate_evaluation().getTime());

                pst.setString(1, e.getCommentaire());
                pst.setFloat(2, e.getRate());
                pst.setDate(3, sqlDate);
                pst.setInt(4, id_client);
                pst.setInt(5, e.getId_evenement());

                pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public ObservableList<Evaluation> readAllComments(int id_evenement)
    {
        String req = "select* from evaluation where id_evenement = "+id_evenement+"";
        ObservableList<Evaluation> list = FXCollections.observableArrayList();

        try {
            ste = connection.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
list.add(new Evaluation(rs.getInt("id"), rs.getString("commentaire"), rs.getFloat("rate"), rs.getDate("date_evaluation"), rs.getInt("id_client"), rs.getInt("id_evenement")));             
            };
        } catch (SQLException ex) {
            Logger.getLogger(EventForRate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
