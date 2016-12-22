package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.Team;
import Model.TeamStat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeamStatViewController {
	
	@FXML
	private TableView<TeamStat> statTable;

	@FXML
	private TableColumn statName;
	
	@FXML
	private TableColumn homeStat;
	
	@FXML
	private TableColumn oppStat;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamChooserController tcc = new TeamChooserController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        Team team = new Team();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
		
        populateTeamStatList();
        //teamStatList.add(new TeamStat("Dog", "cat", "bat"));
		statName.setCellValueFactory(new PropertyValueFactory<TeamStat, String>("statName"));
		homeStat.setCellValueFactory(new PropertyValueFactory<TeamStat, String>("homeStat"));
		oppStat.setCellValueFactory(new PropertyValueFactory<TeamStat, String>("oppStat"));
		ObservableList<TeamStat> teamStatList = FXCollections.observableArrayList(populateTeamStatList());
		statTable.setItems(teamStatList);
		
	}

	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<TeamStat> populateTeamStatList() {
		ArrayList<TeamStat> returnList = new ArrayList<TeamStat>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getStatsName(stringTeamName);
		ArrayList<String> homeStats = model.getTeamStats(stringTeamName);
		
		//prevents pointing to the same thing
		Model model2 = new Model();
		ArrayList<String> oppStats = model2.getTeamStats(stringOppName);
		
		int index = 0;
		for (String name: statNames) {
			returnList.add(new TeamStat(name, homeStats.get(index), oppStats.get(index)));
			index++;	
		}
		
		return returnList;
	}
	
}
