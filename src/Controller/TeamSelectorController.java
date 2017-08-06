package Controller;

import MainApplication.MainApplication;
import Model.TeamName; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TeamSelectorController {

	private MainApplication mainApplication;
	private Stage errorStage;
	
	private ObservableList<TeamName> teamList = FXCollections.observableArrayList(TeamName.values());
	public static String stringTeam;
	public static String stringOpponent;

	@FXML
	private ComboBox<TeamName> teamOfChoice;
	@FXML
	private ComboBox<TeamName> opponent;
	@FXML
	private Button submitButton;
	
	
	public String getStringTeam() {
		return stringTeam;
	}
	
	public String getStringOpponent() {
		return stringOpponent; 
	}
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }
	
	@FXML
    public void initialize() {
		//set team lists
		teamOfChoice.setItems(teamList);
		opponent.setItems(teamList);
		teamOfChoice.getSelectionModel().selectFirst();
		opponent.getSelectionModel().selectFirst();
		stringTeam = "ARI";
		stringOpponent = "ARI";
    }
	
	/**
	 * Selects team from combo boxes
	 */
	@FXML
	public void PickTeam() {
		stringTeam = teamOfChoice.getSelectionModel().getSelectedItem().toString();
		stringOpponent = opponent.getSelectionModel().getSelectedItem().toString();
	}

	/**
	 * Submit teams
	 */
	@FXML
	public void Submit() {
		//checks to see if the teams are equal
		if (stringTeam.equals(stringOpponent)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(errorStage);
            alert.setTitle("Team Selection Error");
            alert.setHeaderText("Try again.");
            alert.setContentText("Cannot pick the same team twice.");
            alert.showAndWait();
		} else {
			ControllerMethods cm = new ControllerMethods();
			cm.SetScene(submitButton, "../view/OptionsView.fxml");
		}
	}

}
