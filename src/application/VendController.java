package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VendController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private AnchorPane vendPage;
	@FXML
	private ImageView productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10;
	@FXML
	private Label Price1, Price2, Price3, Price4, Price5, Price6, Price7, Price8, Price9, Price10, vendResponse;
	@FXML
	private RadioButton rButton1, rButton2, rButton3, rButton4, rButton5, rButton6, rButton7, rButton8, rButton9, rButton10;
	@FXML
	private Pane productContainer1, productContainer2;
	@FXML
	private TextField moneyInput, changeOutput;
	@FXML
	private ToggleGroup product;
	@FXML
	private Button purchaseButton, clearButton, exitButton, adminButton;
	
	
	// Read file and extract all the product into an arraylist
	public static ArrayList<Drink> extractProducts(String filePath) throws FileNotFoundException{
		File file = new File(filePath);
		if (file.exists()) {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			ArrayList<Drink> drinks = new ArrayList<Drink>();
			try {
				while((line=br.readLine()) != null) {
					String[] item = line.split(";");
					Drink drink = new Drink(item[0], item[1],item[2], item[3], item[4]);
					drinks.add(drink);	
				}
				br.close();
				return drinks;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to extract data from product file");;
			}
		}
		return null;
	}
	
	
	// Display image for each product
	public void setImage() throws FileNotFoundException {
		ArrayList<Drink> drinks = extractProducts("./txt/product.txt");
		ImageView[] imageViews = {productImage1, productImage2, productImage3, productImage4, productImage5, productImage6, productImage7, productImage8, productImage9, productImage10};
		for(int i = 0; i < imageViews.length; i++) {
			String imageName = drinks.get(i).imageFileName;
			String imagePath = "file:./image/product/" + imageName;
			imageViews[i].setImage(new Image(imagePath));
		}
	}
	
	
	// Display price for each product
	public void setPrice() {
		try {
			ArrayList<Drink> drinks = extractProducts("./txt/product.txt");
			Label[] labels = {Price1, Price2, Price3, Price4, Price5, Price6, Price7, Price8, Price9, Price10};
			for(int i = 0; i < labels.length; i++) {
				labels[i].setText("RM" + drinks.get(i).price);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Product file not found");
			
		}
	}
	
	// Change opacity of button when hover
		public static void setChangeOpacity(Button button) {
			button.setOnMouseEntered(mouseEvent -> {
				button.setOpacity(0.7);
			});
			button.setOnMouseExited(mouseEvent -> {
				button.setOpacity(1);
			});
		}
		
	// Run immediately when the scene open
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setPrice();
		
		try {
			setImage();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Product file not found");
		}
		
		// Set change opacity property to each button
		Button[] buttons = {purchaseButton, clearButton, exitButton, adminButton};
		for (Button button : buttons ) {
			setChangeOpacity(button);
		}
	}
	
	
	// Switch to admin login page
	public void switchToLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	// Check the stock of the product when user select it and clear the change output and response area
	public void checkAvailability(ActionEvent event) throws FileNotFoundException{
		changeOutput.clear();
		vendResponse.setText("");
		
		String[] drinkDetails = getSelectedDrinkDetails();
		Integer stock = Integer.parseInt(drinkDetails[3]);
		if (stock == 0 && stock != null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Alert Dialog");
			alert.setHeaderText("Out of stock");;
			alert.setContentText("This drink is out of stock.\n" + "Please select another drink.");
			
			// Center the alert dialog in the center of the application
			alert.initOwner(((Node)event.getSource()).getScene().getWindow());
			alert.showAndWait();
			product.selectToggle(null);
		}
	}
	
	
	// Get Id, Name, Price, Number of stocks and image file name of drink selected
	public String[] getSelectedDrinkDetails() throws FileNotFoundException {
		ArrayList<Drink> drinks = extractProducts("./txt/product.txt");
		RadioButton[] radioButtons = {rButton1, rButton2, rButton3, rButton4, rButton5, rButton6, rButton7, rButton8, rButton9, rButton10};
		String[] drinkDetails = new String[5];
		
		for (int i = 0; i < radioButtons.length; i++) {
			if (radioButtons[i].isSelected()) {
				drinkDetails[0] = drinks.get(i).id;;
				drinkDetails[1] = drinks.get(i).name;
				drinkDetails[2] = drinks.get(i).price;
				drinkDetails[3] = Integer.toString(drinks.get(i).numberOfStock);
				drinkDetails[4] = drinks.get(i).imageFileName;
				return drinkDetails;
			}
		}
		return null;
	}
	
	
	public void setSelected(MouseEvent event) {

		rButton1.setSelected(true);
	}
	
	
	// Get money input and return after validation is done
	public String getMoney(ActionEvent event) {
		String money = moneyInput.getText().trim();
		if(Validation.moneyInput(money, event)) {
			return money;
		}else {
			moneyInput.clear();
			return null;
		}
	}
	
	
	// Calculate and return the remaining money
	public Double calculateChange(Double money, Double drinkPrice) {
		return  money - drinkPrice;
	}
	

	// Purchase the drink and display the change
	public void purchase(ActionEvent event) throws IOException {
		if (Validation.drinkSelected(product, event)) {
			String money = getMoney(event);
			if (money != null) {
				String[] drinkDetails = getSelectedDrinkDetails();
				if (drinkDetails != null) {
					String productId = drinkDetails[0];
					String productName = drinkDetails[1];
					String price = drinkDetails[2];
					Integer stock = Integer.parseInt(drinkDetails[3]);
					String imageName = drinkDetails[4];
					Double change = calculateChange(Double.parseDouble(money), Double.parseDouble(price));
					if (displayResponse(change)) { // If purchase successfully
						decreaseStockBy1(productId, productName, price, stock, imageName);
					}
				}
			}
		}
	}
	
	
	// Decrease the number of stock by 1 after user purchased
	public void decreaseStockBy1(String productId, String productName, String price, Integer stock, String imageName) throws IOException {
		if (stock != 0 && stock != null && productName != null) {
			Integer updatedStock = stock - 1;
			String newProductDetail = String.join(";", productId, productName, price, Integer.toString(updatedStock), imageName);
			AdminController.updateFile(productId, newProductDetail, "./txt/product.txt");
		}
	}
	
	
	// Display response to user based on the amount of change
	public Boolean displayResponse(Double change) {
		if (Validation.moneyEnough(change)) {
			changeOutput.setText(String.format("%.2f", change));
			vendResponse.setText("Purchase Successfully. Thank You");
			vendResponse.setVisible(true);
			vendResponse.setTextFill(Color.GREEN);
			moneyInput.clear();
			product.selectToggle(null);
			return true;
		} else {
			vendResponse.setText("Purchase Failed. Not Enough Money");
			vendResponse.setVisible(true);
			vendResponse.setTextFill(Color.RED);
			changeOutput.clear();
			moneyInput.clear();
			return false;
		}
	}
	
	
	// Clear all selection and money input text area
	public void clear(ActionEvent event) {
		product.selectToggle(null);
		moneyInput.clear();
	}
	
	
	// Exit the program
	public void exit(ActionEvent event) {
		stage = (Stage) vendPage.getScene().getWindow();
		stage.close();	
	}
	
	
}
