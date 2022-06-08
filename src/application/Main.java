package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Vend.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Set Icon for the application
			Image image = new Image("file:./image/vend.png");
			primaryStage.getIcons().add(image);
			
			primaryStage.setTitle("GoVend");
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false); // Disable window resize
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// Launch the program
	public static void main(String[] args) {
		launch(args);
	}
}
