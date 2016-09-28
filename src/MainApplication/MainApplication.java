package MainApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controller.MainScreenController;
import Controller.TeamChooserController;


public class MainApplication extends Application{
    private static final Logger logger = Logger.getLogger("MainApplication");

    private Stage mainScreen;

    private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		 mainScreen = primaryStage;
	     initRootLayout(mainScreen);
	     showCourseOverview(mainScreen);
	}
	
	public Stage getMainScreen() { 
		 return mainScreen;
	}
	
	private void initRootLayout(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("../view/FootballMainScreen.fxml"));
            rootLayout = loader.load();

            MainScreenController controller = loader.getController();
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
	
	private void showCourseOverview(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("../view/FootballTeamChooser.fxml"));
            AnchorPane footballMainScreen = loader.load();

            rootLayout.setCenter(footballMainScreen);

            TeamChooserController controller = loader.getController();
            //controller.setMainApp(this);

        } catch (IOException e) {
            //error on load, so log it
            logger.log(Level.SEVERE, "Failed to find the fxml file for CourseOverview!!");
            e.printStackTrace();
        }

    }

	public static void main(String[] args) {
		launch(args);
    }
}
