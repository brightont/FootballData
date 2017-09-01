package Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import MainApplication.MainApplication;
import Model.DefRecRankStat;
import Model.DefRushRankStat;
import Model.DefYardsRankStat;
import Model.FieldGoalStat;
import Model.IntRankStat;
import Model.Model;
import Model.Probability;
import Model.QBStat;
import Model.QuickStats;
import Model.RecRankStat;
import Model.RushRankStat;
import Model.SackRankStat;
import Model.TeamName;
import Model.TeamStat;
import Model.TeamStatTable;
import Model.YardsRankStat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
		
		List<String> stats = new ArrayList<String>(Arrays.asList("General", "Quarterback", "Field Goals", "Ranking"));
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
		} else if (stat.equals("Field Goals")) {
			UpdateDatabaseFG();
			DisplayStat("Field Goals");
		} else if (stat.equals("Ranking")) {
			UpdateDatabaseRank();
			DisplayStat("Ranking");
		}
	}
	
	/**
	 * Updates the database for team stats
	 */
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
	 * Updates the database for quarterback stats
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
	 * Updates the database for field goal stats
	 */
	private void UpdateDatabaseFG(){
		FieldGoalStat fg = new FieldGoalStat();
		HashMap<String, String> hashFG = fg.GetFGStats(teamAbbr);
		HashMap<String, String> hashOppFG = fg.GetFGStats(oppAbbr);

		String value1 = "30-39 M";
		String value2 = "20-29 A";
		
		if (!fg.CheckDatabase(hashFG, value1, "fg", teamName) || 
				!fg.CheckDatabase(hashFG, value2, "fg", teamName)) {
			fg.UpdateDatabase(hashFG, fg.getTeamName(teamAbbr));
		}
		
		if (!fg.CheckDatabase(hashOppFG, value1, "fg", oppName) || 
				!fg.CheckDatabase(hashOppFG, value2, "fg", oppName)) {
			fg.UpdateDatabase(hashOppFG, fg.getTeamName(oppAbbr));
		}
	}
	
	/**
	 * Update database rank
	 */
	private void UpdateDatabaseRank() {
		//get offensive ranking
		YardsRankStat yrStat = new YardsRankStat();
		ArrayList<String> listTeam = yrStat.GetYardsStats();

		if (!yrStat.CheckDatabaseRank(listTeam, 1, "yards", "Pts_G")) {
			yrStat.UpdateDatabase(listTeam);

			//get rushing ranking
			RushRankStat rrStat = new RushRankStat();
			ArrayList<String> listTeam1 = rrStat.GetRushRankStat();

			rrStat.UpdateDatabase(listTeam1);
			
			//get receiving ranking
			RecRankStat rcStat = new RecRankStat();
			ArrayList<String> listTeam2 = rcStat.GetRecRankStat();

			rcStat.UpdateDatabase(listTeam2);
		}

		//get defensive ranking
		DefYardsRankStat dyrStat = new DefYardsRankStat();
		ArrayList<String> listTeam3 = dyrStat.GetDefYardsStats();

		if (!dyrStat.CheckDatabaseRank(listTeam3, 1, "defyards", "Pts_G")) {
			dyrStat.UpdateDatabase(listTeam3);

			DefRushRankStat drrStat = new DefRushRankStat();
			ArrayList<String> listTeam4 = drrStat.GetDefRushStats();

			drrStat.UpdateDatabase(listTeam4);

			DefRecRankStat drcStat = new DefRecRankStat();
			ArrayList<String> listTeam5 = drcStat.GetDefRecStats();

			drcStat.UpdateDatabase(listTeam5);
		}
        
		//get sack ranking
		SackRankStat srStat = new SackRankStat();
		ArrayList<String> listTeam6 = srStat.GetSackStats();

		if (!srStat.CheckDatabaseRank(listTeam6, 1, "sack", "Sacks")) {
			srStat.UpdateDatabase(listTeam6);

			//get interception ranking
			IntRankStat irStat = new IntRankStat();
			ArrayList<String> listTeam7 = irStat.GetIntRankStats();

			irStat.UpdateDatabase(listTeam7);
		}

		//get quick rankings
		QuickStats qStat = new QuickStats();
		ArrayList<String> listTeam8 = qStat.GetQuickStats(teamAbbr);
		ArrayList<String> listOpponent = qStat.GetQuickStats(oppAbbr);

		if (!qStat.CheckDatabaseList(listTeam8, 1, "quick", "Pts")) {
			qStat.UpdateDatabase(listTeam8, qStat.getTeamName(teamAbbr));
		}

		if (!qStat.CheckDatabaseList(listOpponent, 1, "quick", "Pts")) {
			qStat.UpdateDatabase(listOpponent, qStat.getTeamName(oppAbbr));
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
		} else if (type.equals("Quarterback")){
			result = prob.CalculateQBProb(teamName, oppName) * 100;
		} else if (type.equals("Field Goals")){
			result = prob.CalculateFGProb(teamName, oppName) * 100;
		} else {
			result = prob.CalculateRankProb(teamName, oppName) * 100;
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
		} else if (type.equals("Quarterback")){
			statNames = model.GetQBStatsName();
			homeStats = model.GetQBStats(teamName);
			oppStats = model.GetQBStats(oppName);
		} else if (type.equals("Field Goals")){
			statNames = model.GetFGStatsName();
			homeStats = model.GetFGStats(teamName);
			oppStats = model.GetFGStats(oppName);
		} else {
			statNames = model.GetRankStatsName();
			homeStats = model.GetRanking(teamName);
			oppStats = model.GetRanking(oppName);
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
