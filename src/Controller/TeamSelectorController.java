package Controller;

import MainApplication.MainApplication;
import Model.TeamName; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class TeamSelectorController {

	private MainApplication mainApplication;
	
	private ObservableList<TeamName> teamList = FXCollections.observableArrayList(TeamName.values());
	private static String stringTeam;
	private static String stringOpponent;

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
		ControllerMethods cm = new ControllerMethods();
		cm.SetScene(submitButton, "../view/OptionsView.fxml");
	}

}
