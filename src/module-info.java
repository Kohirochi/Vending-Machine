module vendings {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires javafx.swing;
	
	opens application to javafx.graphics, javafx.fxml;
}
