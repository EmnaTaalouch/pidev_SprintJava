/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pidev.Entities.Reclamation;
import pidev.Services.ReclamationService;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import net.glxn.qrgen.QRCode;
import pidev.Entities.ComptabiliteType;
import pidev.Entities.Reponse;
import pidev.Services.ReponseService;

/**
 *
 * @author hp
 */
public class ReclamationController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tf_id;
    @FXML
    private TextArea ta_desc;
    @FXML
    private DatePicker dp_date;
    @FXML
    private ImageView iv_image;
    @FXML
    private TableColumn<Reclamation, Integer> col_id;
    @FXML
    private TableColumn<Reclamation, Date> col_date;
    @FXML
    private TableColumn<Reclamation, String> col_desc;
    @FXML
    private TableColumn<Reclamation, ImageView> col_image;

    ReclamationService rs = new ReclamationService();
    @FXML
    private TableView<Reclamation> tv_reclamation;
    @FXML
    private TextField tf_image;
    @FXML
    private Button button_image;
    @FXML
    private Button but_insert;
    @FXML
    private Button but_update;
    @FXML
    private Button but_delete;
    @FXML
    private Button clear;

    public static int rec_id_for_rep;
    @FXML
    private TableView<Reponse> tv_reponse;
    @FXML
    private TableColumn<Reponse, Integer> Col_id_Rec;
    @FXML
    private TableColumn<Reponse, String> col_text;
    @FXML
    private TextField recherche;
    
    private final ObservableList<Reclamation> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iv_image.setImage(new Image("/Assets/Images/upload.jpg"));
        showReclamations();
    }
    public void showReclamations() {
        list.clear();
        ObservableList<Reclamation> lista = new ReclamationService().readAll(1);//we statically set the client id to just show his reclamations
        list.setAll(lista);
        col_id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        col_desc.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<Reclamation, Date>("date_reclamation"));
        col_image.setCellValueFactory(new PropertyValueFactory<Reclamation, ImageView>("img"));
        tv_reclamation.setItems(list);
        RechercheAV();
    }

    public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reclamation> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tmp -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (tmp.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (String.valueOf(tmp.getId()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tv_reclamation.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tv_reclamation.setItems(sortedData);
    }    
    @FXML
    private void addReclamation(MouseEvent event) throws ParseException, IOException {
        File f = new File(tf_image.getText());

        System.out.println(dp_date.getValue().getYear());
        Date d = java.sql.Date.valueOf(dp_date.getValue());
        Reclamation r = new Reclamation(ta_desc.getText(), d, tf_image.getText(), 1);// i set the client statically, it has to be changed later in the integration 
        rs.ajouterReclamation(r);
        showReclamations();
//        Files.copy(Paths.get(tf_image.getText()),Paths.get("C:\\Users\\Malek Boughanmi\\Downloads\\"+f.getName()),REPLACE_EXISTING);
        //SendMail.sendMail("wajih.mejri@esprit.tn", "Demande Approuvé", tab_Demandeselected.getNom()+" "+tab_Demandeselected.getPrenom()+" à etait approuvée");
        QRcode(r);
    }
    
    public static String projectPath = System.getProperty("user.dir").replace("\\", "/");
    private void QRcode(Reclamation r) throws FileNotFoundException, IOException {
        String contenue = "Description : " + r.getDescription()+ "\n" + "Date: " + r.getDate_reclamation().toString(); 
        ByteArrayOutputStream out = QRCode.from(contenue).to(net.glxn.qrgen.image.ImageType.JPG).stream();
        File f = new File(projectPath + "\\src\\qr\\" + r.getDate_reclamation().toString()+ ".jpg");
        FileOutputStream fos = new FileOutputStream(f); //creation du fichier de sortie
        fos.write(out.toByteArray()); //ecrire le fichier du sortie converter
        fos.flush(); // creation final

    }
    @FXML
    private void uploadImage(MouseEvent event) {
        FileChooser fc = new FileChooser();
        String path = fc.showOpenDialog(button_image.getScene().getWindow()).getPath();
        tf_image.setText(path);
        File file = new File(tf_image.getText());
        Image image = new Image(file.toURI().toString());
        iv_image.setImage(image);

    }
    @FXML
    private void updateReclamation(MouseEvent event) {
        Date d = java.sql.Date.valueOf(dp_date.getValue());
        Reclamation r = new Reclamation(Integer.parseInt(tf_id.getText()), ta_desc.getText(), d, tf_image.getText(), 1);// i set the client statically, it has to be changed later in the integration 

        rs.modifierReclamation(r);
        showReclamations();
    }
    @FXML
    private void deleteReclamation(MouseEvent event) {
        rs.deleteReclamation(tf_id.getText());
        showReclamations();
    }
    @FXML
    private void tableClicked(MouseEvent event) {
        Reclamation rec = tv_reclamation.getSelectionModel().getSelectedItem();
        tf_id.setText(Integer.toString(rec.getId()));
        tf_image.setText(rec.getImage());
        File file = new File(tf_image.getText());
        Image image = new Image(file.toURI().toString());
        iv_image.setImage(image);
        ta_desc.setText(rec.getDescription());
        int year = Integer.parseInt(rec.getDate_reclamation().toString().substring(0, 4));
        int month = Integer.parseInt(rec.getDate_reclamation().toString().substring(5,7));
        int day= Integer.parseInt(rec.getDate_reclamation().toString().substring(8,10));

        dp_date.setValue(LocalDate.of(year, month, day));
        showRepo(rec.getId());

    }
    
        public void showRepo(int id){
        ObservableList<Reponse> list = new ReponseService().afficherReponse(id);
        Col_id_Rec.setCellValueFactory(new PropertyValueFactory<Reponse, Integer>("id"));
        col_text.setCellValueFactory(new PropertyValueFactory<Reponse, String>("text"));
        tv_reponse.setItems(list);
    }

    @FXML
    private void clearFields(javafx.event.ActionEvent event) {
        tf_id.setText("");
        tf_image.setText("");
        ta_desc.setText("");
        dp_date.setValue(null);
        iv_image.setImage(null);
    }
    
}
