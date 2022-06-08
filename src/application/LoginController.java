package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label usernameError, passwordError;
	@FXML
	private Button loginButton, backButton;
	@FXML
	private ImageView backgroundImage;
	
	// Switch back to vending machine page
	public void switchToVend(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Vend.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	// Read file and extract admin details into an arraylist
	public static ArrayList<Admin> extractAdmin(String filePath) throws FileNotFoundException{
		File file = new File(filePath);
		if (file.exists()) {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			ArrayList<Admin> admins = new ArrayList<Admin>();
			try {
				while((line=br.readLine()) != null) {
					String[] detail = line.split(";");
					String adminID = detail[0];
					String username = detail[1];
					String password = detail[2];
					Admin admin = new Admin(adminID, username, password);
					admins.add(admin);	
				}
				br.close();
				return admins;
			} catch (IOException e) {
				System.out.println("Failed to extract data from admin file");;
			}
		}
		return null;
	}

	
	// Get admin id using username entered by user
	public String getAdminID(String username) throws IOException {
		String adminID = null;
		for (String line : Files.readAllLines(Paths.get("./txt/admin.txt"), StandardCharsets.UTF_8)) {
		    if (line.contains(username)) {
		    	String[] detail = line.split(";");
				adminID = detail[0];
				break;
		    }
		}
		return adminID;
	}
		
		
	// Login
	public void login(ActionEvent event) throws IOException {
		String loginUsername = username.getText().trim();
		String loginPassword = password.getText();
		
		if(Validation.login(loginUsername, loginPassword, usernameError, passwordError)) {
			String adminID = getAdminID(loginUsername);
			Admin admin = new Admin(adminID, loginUsername, loginPassword);
			
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("Admin.fxml"));
		    root = loader.load();
		    scene = new Scene(root);
		    
		    // Pass admin details into Admin Panel
		    AdminController adminController = loader.getController();
		    adminController.loadAdminDetails(admin);

		    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		    stage.setScene(scene);
		    stage.show();
			
			
		}		
	}
	
	// set background image
	public void setBackgroundImage() {
		String imagePath = "file:./image/adminBg.jpg/";
		backgroundImage.setImage(new Image(imagePath));
	}
	
	// Run immediately when the scene open
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setBackgroundImage();
		
		// Set change opacity property to each button
		Button[] buttons = {loginButton, backButton};
		for (Button button : buttons ) {
			VendController.setChangeOpacity(button);
		}
	}
}
