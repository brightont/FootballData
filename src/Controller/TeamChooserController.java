package Controller;

import MainApplication.MainApplication;
import Model.Teams;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeamChooserController {

	private Stage dialogStage;
	
	private MainApplication mainApplication;
	
	@FXML
	private ComboBox<Teams> teamOfChoice;
	
	@FXML
	private ComboBox opponent;
	
	private AnchorPane rootLayout;
	
	private final ObjectProperty<Teams> teams = new SimpleObjectProperty<>();
	
	public Teams getTeams(Teams team) {
		return teams.get();
	}
	
	public void setTeams(Teams team) {
		teams.set(team);
	}
	
	private final ObservableList<Teams> teamList = FXCollections.observableArrayList();
	
	
	/*@FXML
    public void initialize() {
		teamOfChoice.setItems(teamList);
    }
	*/
	public void setMainApp(MainApplication main) {

        mainApplication = main;
        //teamOfChoice.setItems(teamList);
        //set the table view to contain the list of courses from the model

        //automatically select the first table item and display the students
    }

}
