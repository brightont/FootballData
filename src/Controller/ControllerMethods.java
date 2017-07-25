package Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class ControllerMethods {
	private static final Logger logger = Logger.getLogger("ControllerMethods");

	/**
	 * Sets the scene
	 * @param button
	 * @param view
	 */
	public void SetScene(Button button, String view) {
		Stage stage;
        Parent root;

        stage = (Stage) button.getScene().getWindow();
        try {
			root = FXMLLoader.load(getClass().getResource(view));
			Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			logger.log(Level.FINE, "Error: Couldn't load scene (url: " + view + ").");
		}
	}
}
