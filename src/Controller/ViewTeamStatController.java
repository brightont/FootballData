package Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import MainApplication.MainApplication;
import Model.Model;
import Model.Probability;
import Model.QBStat;
import Model.TeamName;
import Model.TeamStat;
import Model.TeamStatTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewTeamStatController {
	
	private MainApplication mainApplication;
	
	private ObservableList<String> statList;
	
	@FXML
	private ComboBox<String> statisticMenu;
	
	@FXML
	private Button goButton;
	
	@FXML
	private TableView<TeamStatTable> statTable;

	@FXML
	private TableColumn statName;
	
	@FXML
	private TableColumn homeStat;
	
	@FXML
	private TableColumn oppStat;
	
	@FXML
	private ListView<String> teamView;
	
	@FXML
	private Button returnButton;
	
	//team abbreviations
	private String teamAbbr = "";
	
	private String oppAbbr = "";
	
	//full team names (ie Falcons)
	private String teamName = "";
	
	private String oppName = "";
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }
	
	@FXML
	private void initialize() {
		statTable.setVisible(false);
		
		List<String> stats = new ArrayList<String>(Arrays.asList("General", "Quarterback"));
		
		statList = FXCollections.observableArrayList(stats);
		
		statisticMenu.setItems(statList);
		statisticMenu.getSelectionModel().selectFirst();		
		
		teamAbbr = TeamSelectorController.stringTeam;
		oppAbbr = TeamSelectorController.stringOpponent;
		
		teamName = TeamName.valueOf(teamAbbr).getTeam();
		oppName = TeamName.valueOf(oppAbbr).getTeam();
	}
	
	/**
	 * Populates the table view based on chosen statistic
	 */
	public void ChooseStat() {
		statTable.setVisible(true);
		String stat = statisticMenu.getSelectionModel().getSelectedItem();
		
		teamName = TeamName.valueOf(TeamSelectorController.stringTeam).getTeam();
        oppName = TeamName.valueOf(TeamSelectorController.stringOpponent).getTeam();
        
        statTable.getItems().clear();
		teamView.getItems().clear();
		
		statName.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("statName"));
		homeStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("homeStat"));
		oppStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("oppStat"));
        
		if (stat.equals("General")) {
			UpdateDatabaseTeam();
			DisplayStat("General");
		} else if (stat.equals("Quarterback")) {
			UpdateDatabaseQB();
			DisplayStat("Quarterback");
		}
	}
	
	private void UpdateDatabaseTeam() {
		// Populate the two hash maps
		TeamStat team = new TeamStat();
		HashMap<String, String> hashTeam = team.GetTeamStats(teamAbbr);
		HashMap<String, String> hashOpp = team.GetTeamStats(oppAbbr);

		String value1 = "Touchdowns";
		String value2 = "Total First Downs";

		hashTeam.values().remove(teamAbbr);
		hashOpp.values().remove(oppAbbr);

		if (!team.CheckDatabase(hashTeam, value1, "team", teamName)
				|| !team.CheckDatabase(hashTeam, value2, "team", teamName)) {
			team.UpdateDatabase(hashTeam, team.getTeamName(teamAbbr));
		} 

		if (!team.CheckDatabase(hashOpp, value1, "team", oppName)
				|| !team.CheckDatabase(hashOpp, value2, "team", oppName)) {
			team.UpdateDatabase(hashOpp, team.getTeamName(oppAbbr));
		}
	}
	
	/**
	 * Updates the database quarterback
	 */
	private void UpdateDatabaseQB() {
		QBStat qb = new QBStat();
		HashMap<String, String> hashQB = qb.GetQBStats(teamAbbr);
		HashMap<String, String> hashOppQB = qb.GetQBStats(oppAbbr);

		String value1 = "Att";
		String value2 = "Comp";

		
		if (!qb.CheckDatabase(hashQB, value1, "qb", teamName)
				|| !qb.CheckDatabase(hashQB, value2, "qb", teamName)) {
			qb.UpdateDatabase(hashQB, qb.getTeamName(teamAbbr));
		} 
		
		if (!qb.CheckDatabase(hashOppQB, value1, "qb", oppName)
				|| !qb.CheckDatabase(hashOppQB, value2, "qb", oppName)) {
			qb.UpdateDatabase(hashOppQB, qb.getTeamName(oppAbbr));
		}

	}
	/**
	 * Displays general stats
	 */
	private void DisplayStat(String type) {
		ObservableList<TeamStatTable> teamStatList = FXCollections.observableArrayList(PopulateTeamStatList(type));
		statTable.setItems(teamStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result;
		if (type.equals("General")) {
			result =  prob.CalculateTeamProb(teamName, oppName) * 100;
		} else {
			result = prob.CalculateQBProb(teamName, oppName) * 100;
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		String p = "Probability: " + df.format(result) + " %";
		probability.add(p);
		teamView.getItems().add(probability.get(0));
	}

	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	public ArrayList<TeamStatTable> PopulateTeamStatList(String type) {
		ArrayList<TeamStatTable> returnList = new ArrayList<TeamStatTable>();
		
		Model model = new Model();
		
		ArrayList<String> statNames;
		ArrayList<String> homeStats;
		ArrayList<String> oppStats;
		if (type.equals("General")) {
			statNames = model.GetStatsName();
			homeStats = model.GetTeamStats(teamName);
			oppStats = model.GetTeamStats(oppName);
		} else {
			statNames = model.GetQBStatsName();
			homeStats = model.GetQBStats(teamName);
			oppStats = model.GetQBStats(oppName);
		}
		
		int index = 0;
		
		for (String name: statNames) {
			returnList.add(new TeamStatTable(name, homeStats.get(index), oppStats.get(index)));
			index++;	
		}
		return returnList;
	}
	
	
	@FXML
	private void HandleBack() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) returnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
	
}
