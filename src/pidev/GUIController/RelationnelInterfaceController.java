/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import pidev.Entities.Categorie;
import pidev.Entities.Pdf;
import pidev.Entities.Relationnel;
import pidev.Services.ServiceCategorie;
import pidev.Services.ServiceRelationnel;
import pidev.Utils.Database;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class RelationnelInterfaceController implements Initializable {

    @FXML
    private TextField affiche;
    @FXML
    private Button uploadbutton;
    @FXML
    private ComboBox<String> ComboCategorie;
    @FXML
    private TableView<Relationnel> tableRelationnel;
    @FXML
    private TableColumn<Relationnel, Integer> idt;
    @FXML
    private TableColumn<Relationnel, String> desct;
    @FXML
    private TableColumn<Relationnel, String> nomt;
    @FXML
    private TableColumn<Relationnel, ImageView> imaget;
    @FXML
    private TableColumn<Relationnel, String> categoriet;

    private Statement ste;
    private Connection con;
    private final ObservableList<Relationnel> data = FXCollections.observableArrayList();

    ServiceCategorie sc = new ServiceCategorie();
    ServiceRelationnel sr = new ServiceRelationnel();
    ObservableList<String> listRole = FXCollections.observableArrayList();
    
    @FXML
    private TextField recherche;
    @FXML
    private TextField nomtxt;
    @FXML
    private TextField desctxt;
    @FXML
    private Label idlabel;
    @FXML
    private Rating stars;
    @FXML
    private TableColumn<Relationnel, Double> ratingt;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            fillcombo();
        } catch (SQLException ex) {
            Logger.getLogger(RelationnelInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Aff();
        RechercheAV();
        idlabel.setVisible(false);
        nomtxt.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z*")) {
         nomtxt.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
        }
    });

    }    
    
    public void fillcombo() throws SQLException{     
        List<Categorie> list = sc.readAll();
        for (Categorie aux : list)
        {
          listRole.add(aux.getRole());
        }
        ComboCategorie.setItems(listRole);
    }

    
    public void Aff(){
                                try {
            con = Database.getInstance().getConnection();
            ste = con.createStatement();
            data.clear();

            ResultSet rs = ste.executeQuery("select * from relationnel");
            while(rs.next()){
                Relationnel f = new Relationnel(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6));
                                
                File file = new File(rs.getString(4));
                Image image = new Image(file.toURI().toString());
                
                ImageView imageView =new ImageView(image);
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);

                f.setImg(imageView);                

                Categorie d = sc.getById(f.getId_categorie());
                f.setCategorie(d.getRole());
                data.add(f);
            }
        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
            idt.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomt.setCellValueFactory(new PropertyValueFactory<>("nom"));
            desct.setCellValueFactory(new PropertyValueFactory<>("description"));
            imaget.setCellValueFactory(new PropertyValueFactory<>("img"));
            categoriet.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            ratingt.setCellValueFactory(new PropertyValueFactory<>("rating"));
            tableRelationnel.setItems(data);

    }
    public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Relationnel> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(relation -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (relation.getCategorie().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (String.valueOf(relation.getNom()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Relationnel> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableRelationnel.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableRelationnel.setItems(sortedData);
    }

    @FXML
    private void Uploadfile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        String path = fc.showOpenDialog(uploadbutton.getScene().getWindow()).getPath();
        affiche.setText(path);

    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException, IOException {
        if(Validchamp(nomtxt) && Validchamp(desctxt) && Validchamp(affiche))
        {
        File f = new File(affiche.getText());
        Categorie cat= sc.getByName(ComboCategorie.getValue());

        Relationnel r = new Relationnel(nomtxt.getText(), desctxt.getText(),affiche.getText(), cat.getId(),stars.getRating());
        sr.ajouter(r);
        
        Files.copy(Paths.get(affiche.getText()),Paths.get("C:\\Users\\Malek Boughanmi\\Downloads\\"+f.getName()),REPLACE_EXISTING);
        Aff();
        RechercheAV();
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Ajout");
        tray.setMessage("Ajouté avec succès");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();
        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");

        alert.showAndWait();
        }
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
             tableRelationnel.setItems(data);

             ObservableList<Relationnel> allrelationnels,Singlerelationnel ;
             allrelationnels=tableRelationnel.getItems();
             Singlerelationnel=tableRelationnel.getSelectionModel().getSelectedItems();
             Relationnel A = Singlerelationnel.get(0);
             sr.delete(A);
             Singlerelationnel.forEach(allrelationnels::remove);
             Aff();
             RechercheAV();
             TrayNotification tray = new TrayNotification();
        tray.setTitle("Supprimer");
        tray.setMessage("Supprimé avec succès");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();
    }

    @FXML
    private void Modifier(ActionEvent event) throws SQLException, IOException {
        if(Validchamp(nomtxt) && Validchamp(desctxt) && Validchamp(affiche))
        {
        File f = new File(affiche.getText());
        Categorie cat= sc.getByName(ComboCategorie.getValue());

        Relationnel r = new Relationnel(Integer.valueOf(idlabel.getText()),nomtxt.getText(), desctxt.getText(),affiche.getText(), cat.getId(),stars.getRating());
        sr.update(r);
        
        Files.copy(Paths.get(affiche.getText()),Paths.get("C:\\Users\\Malek Boughanmi\\Downloads\\"+f.getName()),REPLACE_EXISTING);
        Aff();
        RechercheAV();
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Modifier");
        tray.setMessage("Modifié avec succès");
        tray.setNotificationType(NotificationType.WARNING);
        tray.showAndWait();
        }
        else
        {
            
        }        
    }
    
    private boolean Validchamp(TextField T){
        return !T.getText().isEmpty() && T.getLength() > 2;
    }

    @FXML
    private void LoadModif(MouseEvent event) {
             ObservableList<Relationnel> allrelationnels,Singlerelationnel ;
             allrelationnels=tableRelationnel.getItems();
             Singlerelationnel=tableRelationnel.getSelectionModel().getSelectedItems();
             Relationnel A = Singlerelationnel.get(0);
             
             nomtxt.setText(A.getNom());
             desctxt.setText(A.getDescription());
             affiche.setText(A.getImage());
             ComboCategorie.setValue(A.getCategorie());
             
             idlabel.setText(String.valueOf(A.getId()));
       
    }

    @FXML
    private void PDFbtn(ActionEvent event) throws FileNotFoundException, SQLException, DocumentException {
             ObservableList<Relationnel> allrelationnels,Singlerelationnel ;
             allrelationnels=tableRelationnel.getItems();
             Singlerelationnel=tableRelationnel.getSelectionModel().getSelectedItems();
             Relationnel A = Singlerelationnel.get(0);
             
                Pdf p = new Pdf();
                p.add("Relationnel",A.getNom(), A.getDescription(),A.getCategorie());
    }

    
}
