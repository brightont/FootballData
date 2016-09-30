package Controller;

import java.io.IOException;
import java.util.logging.Logger;

import MainApplication.MainApplication;
import Model.Teams;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TeamChooserController {

	private static final Logger logger = Logger.getLogger("TeamChooserController");
	
	private MainApplication mainApplication;
	
	@FXML
	private ComboBox<Teams> teamOfChoice;
	
	@FXML
	private ComboBox<Teams> opponent;
	
	@FXML
	private Button submit;
	
	private final ObjectProperty<Teams> teams = new SimpleObjectProperty<>();
	
	private String stringTeam;
	
	private String stringOpponent;
	
	public Teams getTeams() {
		return teams.get();
	}
	
	public void setTeams(Teams team) {
		teams.set(team);
	}
	
	public String getStringTeam() {
		return stringTeam;
	}
	
	public String getStringOpponent() {
		return stringOpponent; 
	}
	
	private ObservableList<Teams> teamList = FXCollections.observableArrayList(Teams.values());
	
	@FXML
    public void initialize() {
		teamOfChoice.setItems(teamList);
		opponent.setItems(teamList);
		
		teamOfChoice.getSelectionModel().selectFirst();
		opponent.getSelectionModel().selectFirst();
    }
	
	@FXML
	public void pickTeam() {
		stringTeam = teamOfChoice.getSelectionModel().getSelectedItem().toString();
		stringOpponent = opponent.getSelectionModel().getSelectedItem().toString();
	}

	@FXML
	public void pressSubmit() {
		Stage stage;
		Parent root;
		
		stage = (Stage) submit.getScene().getWindow();
		try {
			root = FXMLLoader.load(getClass().getResource("../view/StatView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }

}
