package MainApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controller.TeamChooserController;


public class MainApplication extends Application{
	
	private static final Logger logger = Logger.getLogger("MainApplication");
	
	private Stage mainScreen;
	
	private AnchorPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainScreen = primaryStage;
		initRootLayout(mainScreen);

	}
	 
	public Stage getMainScreen() {
		return mainScreen;
	}
	
	private void initRootLayout(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("../view/FootballTeamChooser.fxml"));
            rootLayout = loader.load();

            TeamChooserController controller = loader.getController();
            controller.setMainApp(this);

            mainScreen.setTitle("Football Data Analyzer");

            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to find the fxml file for MainScreen!");
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
        launch(args);
    }
}
