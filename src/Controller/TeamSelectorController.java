package Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import MainApplication.MainApplication;
import Model.TeamName; 
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TeamSelectorController {

	private static final Logger logger = Logger.getLogger("TeamSelectorController");
	private MainApplication mainApplication;
	private ObservableList<TeamName> teamList = FXCollections.observableArrayList(TeamName.values());
	private final ObjectProperty<TeamName> teams = new SimpleObjectProperty<>();
	private static String stringTeam;
	private static String stringOpponent;

	@FXML
	private ComboBox<TeamName> teamOfChoice;
	
	@FXML
	private ComboBox<TeamName> opponent;
	
	@FXML
	private Button submitButton;
	
	
	public TeamName getTeams() {
		return teams.get();
	}
	
	public void setTeams(TeamName team) {
		teams.set(team);
	}
	
	public String getStringTeam() {
		return stringTeam;
	}
	
	public String getStringOpponent() {
		return stringOpponent; 
	}
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }
	
	/**
	 * Initializes the lists for the selector
	 */
	@FXML
    public void initialize() {
		teamOfChoice.setItems(teamList);
		opponent.setItems(teamList);
		teamOfChoice.getSelectionModel().selectFirst();
		opponent.getSelectionModel().selectFirst();
    }
	
	/**
	 *  Allows the user to pick the team by loading website and getting stats
	 */
	@FXML
	public void pickTeam() {
		stringTeam = teamOfChoice.getSelectionModel().getSelectedItem().toString();
		stringOpponent = opponent.getSelectionModel().getSelectedItem().toString();
	}

	/**
	 * Sets up the options screen when the submit button is pressed
	 */
	@FXML
	public void pressSubmit() {
        try {
        	Stage stage;
            Parent root;

            stage = (Stage) submitButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/OptionsView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Could not properly submit.");
			e.printStackTrace();
		}
	}

}
