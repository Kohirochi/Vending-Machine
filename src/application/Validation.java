package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Validation {
	
	// Pop up an error message to user
	public static void showErrorMessage(String headerText,String contentText, ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Alert Dialog");
		alert.setHeaderText(headerText);;
		alert.setContentText(contentText);
		
		// Center the alert dialog in the center of the application
		alert.initOwner(((Node)event.getSource()).getScene().getWindow());
		alert.showAndWait();
	}
	
	
	// Check if a drink is selected before purchasing
	public static Boolean drinkSelected(ToggleGroup group, ActionEvent event) {
		RadioButton drinkSelection = ((RadioButton) group.getSelectedToggle());
		if (drinkSelection != null) {
			return true;
		} else {
			showErrorMessage("Product undetected", "Please select one product before purchasing", event);
			return false;
		}
	}
	
	// Validate money entered by user
	public static Boolean moneyInput(String moneyInput, ActionEvent event) {
		final String numberOrDecimal = "\\d+(?:\\.\\d[0,5]?)?";
		final String lastDecimalError = "\\d+(?:\\.\\d[1,2,3,4,6,7,8,9]?)?";
		final String letterError = "[a-zA-Z]+";
		if (moneyInput.matches(numberOrDecimal)) {
			return true;
		}else if (moneyInput.equals("")){
			showErrorMessage("Input Error","Money textfield cannot be left blank.\n" + "Please enter an amount", event);
			return false;
		}else if (moneyInput.matches(letterError)){
			showErrorMessage("Input Error","Letters are not allowed.\n" + "Please enter numbers with or without decimal only", event);
			return false;
		}else if (moneyInput.matches(lastDecimalError)){
			showErrorMessage("Input Error","Please enter only 0 or 5 as your last decimal number if you entered two number after the decimal point", event);
			return false;
		}
		else {
			showErrorMessage("Input Error","Please make sure your input follows the criteria below:\n"
					+ "1) Numbers with or without decimal.\n" 
					+ "2) 0 or 5 as the last decimal number if you entered two number after the decimal point", event);
			return false;
		}
	}
	
	
	// Check if the user entered enough money to buy the product
	public static Boolean moneyEnough(Double change) {
		return (change >= 0) ? true : false;
	}
	
	
	// Check username and password in admin login page
	public static Boolean login(String username, String password, Label usernameError, Label passwordError) throws FileNotFoundException {
		// Username left blank
		if (username.equals("")) {
			displayError(usernameError, "Please enter your username");
			return false;
		} else {
			usernameError.setVisible(false);
		}
		// Password left blank
		if (password.equals("")) {
			displayError(passwordError, "Please enter your password");
			return false;
		} else {
			passwordError.setVisible(false);
		}
		
		// When admin entered username and password, extract data from file and validate
		ArrayList<Admin> admins = LoginController.extractAdmin("./txt/admin.txt");
		
		for(int i = 0; i < admins.size(); i++) {
			if (username.equals(admins.get(i).username)) {
				if (password.equals(admins.get(i).password)) {
					return true;
					
				}else {
					displayError(passwordError, "Password incorrect. Please try again");
					return false;
				}
			}
		}
		
		displayError(usernameError, "Username not found");
		return false;
	}
	
	
	// Display error message on label
	public static void displayError(Label errorField, String errorMessage) {
		errorField.setText(errorMessage);
		errorField.setVisible(true);
	}
	
	
	// Validate amount input from user
	public static Boolean amountInput(String amountInput, ActionEvent event) {
		final String numberOnly = "^\\d*[1-9]\\d*$";
		final String letterError = "[a-zA-Z]+";
		final String decimalError = "\\d+\\.\\d+";
		
		if  (amountInput.matches(numberOnly)) {
			return true;
		} else if (amountInput.equals("")) {
			showErrorMessage("Input Error","Amount textfield cannot be left blank.\n" + "Please enter an amount.", event);
			return false;
		} else if (amountInput.matches(letterError)){
			showErrorMessage("Input Error","Letters are not allowed.\n" + "Please enter numbers only.", event);
			return false;
		}  else if (amountInput.matches(decimalError)) {
			showErrorMessage("Input Error","Decimal numbers are not allowed\n" + "Please enter whole numbers only.", event);
			return false;
		} else {
			showErrorMessage("Input Error","Invalid Input\n" + "Only positive numbers excluding 0 are allowed.", event);
			return false;
		}
	}
	
	
	// Validate stock to prevent user removing too many stock
	public static Boolean stock(String updatedStock, ActionEvent event) {
		if (Integer.parseInt(updatedStock) < 0) {
			showErrorMessage("Input Error","You removed too many stock.\n" + "Stock cannot be negative number.", event);
			return false;
		} else {
			return true;
		}
	}
	
	
	// validate product name entered by admin
	public static Boolean productName(String productID, String productName, Label productNameError) throws IOException {
		final Set<String> productNames = new HashSet<String>(Arrays.asList());
		
		for (String line : Files.readAllLines(Paths.get("./txt/product.txt"), StandardCharsets.UTF_8)) {
		    if (!(line.contains(productID))) {
		    	String[] details = line.split(";");
		    	productNames.add(details[1]);
		    }
		}
		
		if  (productName.equals("")) {
			displayError(productNameError, "Please enter a product name");
			return false;
		} else if (productNames.contains(productName)) {
			displayError(productNameError, "Product name existed. Please enter another one.");
			return false;
		}else {
			productNameError.setVisible(false);
			return true;
		}
	}
	
	
	// validate price entered by admin
	public static Boolean price(String price, Label priceError) {
		final String numberOrDecimal = "\\d+(?:\\.\\d[0,5]?)?";
		final String lastDecimalError = "\\d+(?:\\.\\d[1,2,3,4,6,7,8,9]?)?";
		final String letterError = "[a-zA-Z]+";
		if (price.matches(numberOrDecimal)) {
			priceError.setVisible(false);
			return true;
		}else if (price.equals("")){
			displayError(priceError, "Please enter a price");
			return false;
		}else if (price.matches(letterError)){
			displayError(priceError, "Letters are not allowed. Please enter numbers only");
			return false;
		}else if (price.matches(lastDecimalError)){
			displayError(priceError, "Please enter only 0 or 5 as the last decimal number");
			return false;
		}
		else {
			displayError(priceError, "Invalid input. Only numbers and dot are allowed");
			return false;
		}
	}
	
	
	// Validate stock entered by admin
	public static Boolean stock(String stock, Label stockError) {
		final String numberOnly = "^\\d*[0-9]\\d*$";
		final String letterError = "[a-zA-Z]+";
		final String decimalError = "\\d+\\.\\d+";
		
		if (stock.matches(numberOnly)) {
			stockError.setVisible(false);
			return true;
		} else if (stock.equals("")) {
			displayError(stockError, "Please enter an amount");
			return false;
		} else if (stock.matches(letterError)){
			displayError(stockError, "Please enter numbers only.");
			return false;
		}  else if (stock.matches(decimalError)) {
			displayError(stockError, "Please enter whole numbers only.");
			return false;
		} else {
			displayError(stockError, "Invalid Input. Only numbers are allowed");
			return false;
		}
	}
	
	
	// Return true if ImageView is empty
	public static Boolean isImageEmpty(ImageView imageView) {
	    Image image = imageView.getImage();
	    return image == null || image.isError();
	}
	
	
	// Validate username entered by admin
	public static Boolean username(String adminID, String username, Label usernameError) throws IOException {
		final String letterOnly = "[a-zA-Z]+";
		final String numberError = "\\d+";
		
		final Set<String> usernames = new HashSet<String>(Arrays.asList());
		
		for (String line : Files.readAllLines(Paths.get("./txt/admin.txt"), StandardCharsets.UTF_8)) {
		    if (!(line.contains(adminID))) {
		    	String[] details = line.split(";");
		    	usernames.add(details[1]);
		    }
		}
		
		if (usernames.contains(username)) {
			displayError(usernameError, "Username existed. Please create another username.");
			return false;
		} else if (username.matches(letterOnly)) {
			usernameError.setVisible(false);
			return true;
		} else if (username.equals("")){
			displayError(usernameError, "Please enter a username.");
			return false;
		} else if (username.matches(numberError)) {
			displayError(usernameError, "Please enter letters only.");
			return false;
		} else {
			displayError(usernameError, "Invalid username. Only letters are allowed.");
			return false;
		}
		
	}
	
	// Validate password entered by admin
	public static Boolean password(String password, Label passwordError) {
		if (password.equals("")){
			displayError(passwordError, "Please enter a password.");
			return false;
		} else if (password.length() < 8 || password.length() > 20 ) {
			displayError(passwordError, "Password length should be between 8 and 20.");
			return false;
		} else {
			passwordError.setVisible(false);
			return true;
		}
		
	}
}
