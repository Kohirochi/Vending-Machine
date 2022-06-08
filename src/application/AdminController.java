package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class AdminController implements Initializable  {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Pane products, settings, modalWindow;
	@FXML
	private Button productButton, settingButton, editProfileButton, saveProfileButton, exitButton, logoutButton, updateButton, closeButton;
	@FXML
	private Button addButton1, addButton2, addButton3, addButton4, addButton5, addButton6, addButton7, addButton8, addButton9, addButton10;
	@FXML
	private Button removeButton1, removeButton2, removeButton3, removeButton4, removeButton5, removeButton6, removeButton7, removeButton8, removeButton9, removeButton10;
	@FXML
	private Button editButton1, editButton2, editButton3, editButton4, editButton5, editButton6, editButton7, editButton8, editButton9, editButton10;
	@FXML
	private ImageView productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10;
	@FXML
	private Label productId1, productId2, productId3, productId4, productId5, productId6, productId7, productId8, productId9, productId10;
	@FXML
	private Label productName1, productName2, productName3, productName4, productName5, productName6, productName7, productName8, productName9, productName10;
	@FXML
	private Label price1, price2, price3, price4, price5, price6, price7, price8, price9, price10;
	@FXML
	private Label stock1, stock2, stock3, stock4, stock5, stock6, stock7, stock8, stock9, stock10;
	@FXML
	private Label fileName, filePath;
	@FXML
	private Label productNameError, priceError, stockError, usernameError, passwordError;
	@FXML
	private ImageView logo, imageUploaded, productButtonIcon, settingButtonIcon, logoutButtonIcon, exitButtonIcon;
	@FXML
	private TextField productIdField, productNameField, priceField, stockField, adminID, adminUsername, adminPassword;
	
	
	// Show specific pane when user use the side navigation bar
	public void navigationBar(ActionEvent event) {
		if (event.getSource() == productButton) {
			products.toFront();
			loadProduct();
		}else if (event.getSource() == settingButton) {
			settings.toFront();
		}
	}
	
	// set icon
	public void setNavBarIcon() {
		String productIconPath = "file:./image/product_white.png/";
		productButtonIcon.setImage(new Image(productIconPath));
		
		String settingIconPath = "file:./image/setting_white.png/";
		settingButtonIcon.setImage(new Image(settingIconPath));
		
		String logoutIconPath = "file:./image/logout_white.png/";
		logoutButtonIcon.setImage(new Image(logoutIconPath));
		
		String exitIconPath = "file:./image/power-button.png/";
		exitButtonIcon.setImage(new Image(exitIconPath));
		
		String logoPath = "file:./image/vend.png/";
		logo.setImage(new Image(logoPath));
	}
		
	// Extract product name, price, number of stock and image name for each product from the file and load them
	public void loadProduct() {
		try {
			ArrayList<Drink> drinks = VendController.extractProducts("./txt/product.txt");
			Label[] productIdLabels = {productId1, productId2, productId3, productId4, productId5, productId6, productId7, productId8, productId9, productId10};
			ImageView[] imageViews = {productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10};
			Label[] productNameLabels = {productName1, productName2, productName3, productName4, productName5, productName6, productName7, productName8, productName9, productName10};
			Label[] priceLabels = {price1, price2, price3, price4, price5, price6, price7, price8, price9, price10};
			Label[] stockLabels = {stock1, stock2, stock3, stock4, stock5, stock6, stock7, stock8, stock9, stock10};
			for(int i = 0; i < productNameLabels.length; i++) {
				productIdLabels[i].setText(drinks.get(i).id);
				productNameLabels[i].setText(drinks.get(i).name);
				priceLabels[i].setText(drinks.get(i).price);
				stockLabels[i].setText(Integer.toString(drinks.get(i).numberOfStock));
				
				// Display product image
				String imageName = drinks.get(i).imageFileName;
				String imagePath = "file:./image/product/" + imageName;
				imageViews[i].setImage(new Image(imagePath));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Product file not found");
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setNavBarIcon();
		loadProduct();
		
		// Set mouse event on navigation bar button
		buttonHover(productButton, productButtonIcon, "product_white.png", "product.png", "#1E2022");
		buttonHover(settingButton, settingButtonIcon, "setting_white.png", "setting.png", "#1E2022");
		buttonHover(logoutButton, logoutButtonIcon, "logout_white.png", "logout.png", " #191136");
		
		// Set mouse event on close button
		closeButtonEvent();
		
		// Set change opacity property to each button
		Button[] buttons = {editProfileButton, saveProfileButton, exitButton, updateButton};
		for (Button button : buttons ) {
			VendController.setChangeOpacity(button);
		}
		
		
	}
	
	// Set mouse event on navigation bar button
	public void buttonHover(Button button, ImageView imageView, String originalIconFileName, String iconToBeChangeFileName, String originalBackgroundColor) {
		Image originalIcon = new Image("file:./image/" + originalIconFileName);
		Image iconToBeChange = new Image("file:./image/" + iconToBeChangeFileName);
		
		button.setOnMouseEntered(mouseEvent -> {
			button.setStyle("-fx-background-color: #FFFFFF; ");
			button.setStyle("-fx-text-fill: black; ");
			imageView.setImage(iconToBeChange);
		});
		
		button.setOnMouseExited(mouseEvent -> {
			imageView.setImage(originalIcon);
			button.setStyle("-fx-text-fill: white; ");
			button.setStyle("-fx-background-color: " + originalBackgroundColor + ";");
		});
	}
	
	
	// set close button mouse event 
	public void closeButtonEvent() {
		closeButton.setOnMouseEntered(mouseEvent -> {
			closeButton.setStyle("-fx-text-fill: white; -fx-background-color: #FF4545; ");
		});
		
		closeButton.setOnMouseExited(mouseEvent -> {
			closeButton.setStyle("-fx-text-fill: #aaaaaa; -fx-background-color: white; ");	
		});
		
	}
	
	
	// Add stock for the specific product
	public void addStock(ActionEvent event) throws IOException {
		String[] productDetails = getSelectedProductDetails(event);
		if (productDetails != null) {
			String productId = productDetails[0];
			String productName = productDetails[1];
			String price = productDetails[2];
			String stockLeft = productDetails[3];
			String imageName = productDetails[4];
			
			// Pop up text input dialog for user to input and accept the response
			Optional<String> dialogResponse = textInputDialog("Enter the amount to be added", "Amount:", event);
			
			if (dialogResponse.isPresent()) {
				String amountToAdd = dialogResponse.get().trim();
				if(Validation.amountInput(amountToAdd, event)) {
					String updatedStock = calculateStock("add", amountToAdd, stockLeft);
					String newProductDetail = String.join(";", productId, productName, price, updatedStock, imageName);
					updateFile(productId, newProductDetail, "./txt/product.txt");
					loadProduct();  // Refresh the table
				}
			}
		}
	}
	
	// Extract image name from image URL which obtained from ImageView
	public static String getImageName(ImageView productImage) {
		String productURL = productImage.getImage().getUrl();
		String imageName = productURL.substring(productURL.lastIndexOf("/") + 1, productURL.length());
		return imageName;
	}
	
	// Get the name, price and number of stock of the product selected
	public String[] getSelectedProductDetails(ActionEvent event){
		// Initialize a new array
		String[] productDetails = new String[5];
		
		// Group all action button for each product. HastSet is used to improve performance
		final Set<Button> actionButton1 = new HashSet<Button>(Arrays.asList(addButton1, removeButton1, editButton1));
		final Set<Button> actionButton2 = new HashSet<Button>(Arrays.asList(addButton2, removeButton2, editButton2));
		final Set<Button> actionButton3 = new HashSet<Button>(Arrays.asList(addButton3, removeButton3, editButton3));
		final Set<Button> actionButton4 = new HashSet<Button>(Arrays.asList(addButton4, removeButton4, editButton4));
		final Set<Button> actionButton5 = new HashSet<Button>(Arrays.asList(addButton5, removeButton5, editButton5));
		final Set<Button> actionButton6 = new HashSet<Button>(Arrays.asList(addButton6, removeButton6, editButton6));
		final Set<Button> actionButton7 = new HashSet<Button>(Arrays.asList(addButton7, removeButton7, editButton7));
		final Set<Button> actionButton8 = new HashSet<Button>(Arrays.asList(addButton8, removeButton8, editButton8));
		final Set<Button> actionButton9 = new HashSet<Button>(Arrays.asList(addButton9, removeButton9, editButton9));
		final Set<Button> actionButton10 = new HashSet<Button>(Arrays.asList(addButton10, removeButton10, editButton10));
		
		// Get the action button clicked
		Button buttonClicked = (Button) event.getSource();
		
		@SuppressWarnings("rawtypes")
		Set[] actionButtons = {actionButton1, actionButton2, actionButton3, actionButton4, actionButton5, actionButton6, actionButton7, actionButton8 ,actionButton9, actionButton10};
		Label[] productIdLabels = {productId1, productId2, productId3, productId4, productId5, productId6, productId7, productId8, productId9, productId10};
		ImageView[] imageViews = {productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10};
		Label[] productNameLabels = {productName1, productName2, productName3, productName4, productName5, productName6, productName7, productName8, productName9, productName10};
		Label[] priceLabels = {price1, price2, price3, price4, price5, price6, price7, price8, price9, price10};
		Label[] stockLabels = {stock1, stock2, stock3, stock4, stock5, stock6, stock7, stock8, stock9, stock10};
		
		// Store the product details into the array
		for (int i = 0; i < actionButtons.length; i++) {
			if (actionButtons[i].contains(buttonClicked)) {
				productDetails[0] = productIdLabels[i].getText();
				productDetails[1] = productNameLabels[i].getText();
				productDetails[2] = priceLabels[i].getText();
				productDetails[3] = stockLabels[i].getText();
				productDetails[4] = getImageName(imageViews[i]);
				
				return productDetails;
			}
		}
		return null;
	}
	
	
	// Pop up a text input dialog and return user input
	public Optional<String> textInputDialog(String headerText, String contentText, ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		
		// Center the dialog in the center of the application
		dialog.initOwner(((Node)event.getSource()).getScene().getWindow());
		Optional<String> response = dialog.showAndWait();
		return response;
	}
	
	// Perform calculation based on the method selected
	public String calculateStock(String method, String amount, String stockLeft) {
		int stock = 0;
		if(method.equals("add")) {
			stock = Integer.parseInt(stockLeft) + Integer.parseInt(amount);
		} else if (method.equals("remove")) {
			stock = Integer.parseInt(stockLeft) - Integer.parseInt(amount);
		}
		return Integer.toString(stock);
	}
	
	
	// Update the file with new detail
	public static void updateFile(String name, String newDetail, String filePath) throws IOException {
		List<String> newDetails = new ArrayList<>();
		for (String line : Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			String[] details = line.split(";");
		    if (details[0].contains(name)) {
		    	newDetails.add(line.replace(line, newDetail));
		    } else {
		    	newDetails.add(line);
		    }
		}
		Files.write(Paths.get(filePath), newDetails, StandardCharsets.UTF_8);
	}
	
	public void removeStock(ActionEvent event) throws IOException {
		String[] productDetails = getSelectedProductDetails(event);
		if (productDetails != null) {
			String productId = productDetails[0];
			String productName = productDetails[1];
			String price = productDetails[2];
			String stockLeft = productDetails[3];
			String imageName = productDetails[4];
			
			// Pop up text input dialog for user to input and accept the response
			Optional<String> dialogResponse = textInputDialog("Enter the amount to be removed", "Amount:", event);
			
			if (dialogResponse.isPresent()) {
				String amountToRemove = dialogResponse.get().trim();
				if(Validation.amountInput(amountToRemove, event)) {
					String updatedStock = calculateStock("remove", amountToRemove, stockLeft);
					if (Validation.stock(updatedStock, event)) {
						String newProductDetail = String.join(";",productId, productName, price, updatedStock, imageName);
						updateFile(productId, newProductDetail, "./txt/product.txt");
						loadProduct();  // Refresh the table
					}
				}
			}
		}
	}
	
	
	// Show product details of the specific product to user
	public void loadProductDetail(ActionEvent event) {
		String[] productDetails = getSelectedProductDetails(event);
		if (productDetails != null) {
			String productId = productDetails[0];
			String productName = productDetails[1];
			String price = productDetails[2];
			String stockLeft = productDetails[3];
			String imageName = productDetails[4];
			
			productIdField.setText(productId);
			productNameField.setText(productName);
			priceField.setText(price);
			stockField.setText(stockLeft);
			fileName.setText(imageName);
			fileName.setVisible(true);
		}
	}
	
	
	// Open product details for user to edit
	public void openModal(ActionEvent event) {
		modalWindow.toFront();
		loadProductDetail(event);
	}

	
	// Close product details
	public void closeModal(ActionEvent event) {
		productNameError.setVisible(false);
		priceError.setVisible(false);
		stockError.setVisible(false);
		modalWindow.toBack();
	}

	
	// Open file chooser for admin to choose
	public void openFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		setExtensionFilter(fc);
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			String fileFullName = selectedFile.getName();
			
			// Show user the chosen file name
			fileName.setText(fileFullName);
			fileName.setVisible(true);
			
			// Get path of the image file
			String imagePath = selectedFile.getAbsolutePath();
			
			// Set the image to the image view using image path
			imageUploaded.setImage(new Image(imagePath));
		}
	}
	
	
	// Filter the file extension uploaded by user
	public void setExtensionFilter(FileChooser file) {
		file.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("PNG Files", "*.png"),
				new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));	
	}
	
	
	// Update all product details when update button clicked
	public void updateProductDetails(ActionEvent event) throws IOException {
		String productId = productIdField.getText();
		String productName = productNameField.getText();
		String price = priceField.getText();
		String stock = stockField.getText();
		String imageName = fileName.getText();
		
		// Product Details Validation
		Boolean validateName = Validation.productName(productId, productName, productNameError);
		Boolean validatePrice = Validation.price(price, priceError);
		Boolean validateStock = Validation.stock(stock, stockError);
		
		if(validateName && validatePrice && validateStock) {
			// Form a new line with new product details
			Double doublePrice = Double.parseDouble(price);
			String newProductDetail = String.join(";", productId, productName, String.format("%.2f", doublePrice), stock, imageName);
			updateFile(productId, newProductDetail, "./txt/product.txt");
			if (!Validation.isImageEmpty(imageUploaded)) {
				saveImageToFile();
			}
			modalWindow.toBack(); // Close Modal
			loadProduct();  // Refresh the table
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Update Dialog");
			alert.setHeaderText("Update Product Details Successfully.");
			
			alert.initOwner(((Node)event.getSource()).getScene().getWindow());
			alert.showAndWait();
		}
	}
	
	// Save the image user select to a file
	public void saveImageToFile() {
		// Get the image from the image view
		Image imageToBeSaved = imageUploaded.getImage();
		
		String fileFullName = fileName.getText();
		
		// Extract file extension from file name
		String fileExtension = fileFullName.substring(fileFullName.lastIndexOf(".") + 1, fileFullName.length());
		
		// Create new file to be save
		File file = new File("./image/product/" + fileFullName);
		
		// ImageIO.write : Writes an image using an arbitrary ImageWriter that supports the given format to a File
		// SwingFXUtils.fromFXImage : Snapshots the specified JavaFX Image object and stores a copy of its pixels into a BufferedImage object
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(imageToBeSaved, null), fileExtension, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// Display admin details
	public void loadAdminDetails(Admin admin) {
		adminID.setText(admin.adminID);
		adminUsername.setText(admin.username);
		adminPassword.setText(admin.password);
	}
	
	
	// Enable users to edit when they click on edit button
	public void editProfile(ActionEvent event) {
		adminUsername.setEditable(true);
		adminPassword.setEditable(true);
		adminUsername.requestFocus();
		
		saveProfileButton.setVisible(true);
		editProfileButton.setVisible(false);
		
	}
	
	
	// Save profile detail into file when save button is clicked
	public void saveProfile(ActionEvent event) throws IOException {
		String admin_id = adminID.getText();
		String username = adminUsername.getText();
		String password = adminPassword.getText();
		
		if (Validation.username(admin_id, username, usernameError) && Validation.password(password, passwordError)) {
			String newAdminDetail = String.join(";", admin_id, username, password);
			
			updateFile(admin_id, newAdminDetail, "./txt/admin.txt");
			
			adminUsername.setEditable(false);
			adminPassword.setEditable(false);
			
			saveProfileButton.setVisible(false);
			editProfileButton.setVisible(true);
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Update Dialog");
			alert.setHeaderText("Update Profile Successfully.");
			alert.initOwner(((Node)event.getSource()).getScene().getWindow());
			alert.showAndWait();
		}
	}
	
	
	// Log out from admin account
	public void logOut(ActionEvent event) throws IOException {
		
		// Switch to Vend.fxml
		root = FXMLLoader.load(getClass().getResource("Vend.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Log out Dialog");
		alert.setHeaderText("Log out Successfully");
		alert.initOwner(stage);
		alert.showAndWait();
	}
		
		
	// Exit the program
	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
