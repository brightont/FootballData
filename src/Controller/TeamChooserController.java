package Controller;

import MainApplication.MainApplication;
import Model.Teams;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class TeamChooserController {

	private MainApplication mainApplication;
	
	@FXML
	private ComboBox<Teams> teamOfChoice;
	
	@FXML
	private ComboBox<Teams> opponent;
	
	private final ObjectProperty<Teams> teams = new SimpleObjectProperty<>();
	
	public Teams getTeams() {
		return teams.get();
	}
	
	public void setTeams(Teams team) {
		teams.set(team);
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
		
	}
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
        //teamOfChoice.setItems(teamList);
    }

}
