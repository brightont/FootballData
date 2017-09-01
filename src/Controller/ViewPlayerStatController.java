package Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.HomePlayerStatTable;
import Model.Model;
import Model.OppPlayerStatTable;
import Model.Probability;
import Model.RushStat;
import Model.TeamName;
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

public class ViewPlayerStatController {
	
	private ObservableList<String> statList;
	
	@FXML
	private ComboBox<String> statisticMenu;
	
	@FXML
	private Button goButton;
	
	@FXML
	private Label homeText;
	
	@FXML
	private Label oppText;
	
	@FXML
	private TableView<HomePlayerStatTable> homeStatTable;
	
	@FXML
	private TableColumn homePlayer;
	
	@FXML
	private TableColumn home1;
	
	@FXML
	private TableColumn home2;
	
	@FXML
	private TableColumn home3;
	
	@FXML
	private TableColumn home4;
	
	@FXML
	private TableColumn home5;
	
	@FXML
	private TableView<OppPlayerStatTable> oppStatTable;
	
	@FXML
	private TableColumn oppPlayer;
	
	@FXML
	private TableColumn opp1;
	
	@FXML
	private TableColumn opp2;
	
	@FXML
	private TableColumn opp3;
	
	@FXML
	private TableColumn opp4;
	
	@FXML
	private TableColumn opp5;
	
	@FXML
	private ListView<String> statView;
	
	@FXML
	private Button rushReturnButton;
	
	// team abbreviations
	private String teamAbbr = "";

	private String oppAbbr = "";

	// full team names (ie Falcons)
	private String teamName = "";

	private String oppName = "";
	
	private Model model;
	
	@FXML
	private void initialize() {
		homeStatTable.setVisible(false);
		oppStatTable.setVisible(false);
		homeText.setVisible(false);
		oppText.setVisible(false);
		
		model = new Model();
		
		List<String> stats = new ArrayList<String>(Arrays.asList("Rushing"));
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
		homeStatTable.setVisible(true);
		oppStatTable.setVisible(true);
		homeText.setVisible(true);
		oppText.setVisible(true);
		
		String stat = statisticMenu.getSelectionModel().getSelectedItem();
		
		teamName = TeamName.valueOf(TeamSelectorController.stringTeam).getTeam();
        oppName = TeamName.valueOf(TeamSelectorController.stringOpponent).getTeam();
        
        homeStatTable.getItems().clear();
        oppStatTable.getItems().clear();
		statView.getItems().clear();
		
		PrepareTables();
        
		if (stat.equals("Rushing")) {
			UpdateDatabaseRush();
			DisplayStat("Rushing");
		} 
	}
	
	/**
	 * Prepares the stat tables
	 */
	private void PrepareTables() {
		homePlayer.setCellValueFactory(new PropertyValueFactory<HomePlayerStatTable, String>("homePlayer"));
        home1.setCellValueFactory(new PropertyValueFactory<HomePlayerStatTable, String>("home1"));
        home2.setCellValueFactory(new PropertyValueFactory<HomePlayerStatTable, String>("home2"));
        home3.setCellValueFactory(new PropertyValueFactory<HomePlayerStatTable, String>("home3"));
        home4.setCellValueFactory(new PropertyValueFactory<HomePlayerStatTable, String>("home4"));
        home5.setCellValueFactory(new PropertyValueFactory<HomePlayerStatTable, String>("home5"));
		
		oppPlayer.setCellValueFactory(new PropertyValueFactory<OppPlayerStatTable, String>("oppPlayer"));
		opp1.setCellValueFactory(new PropertyValueFactory<OppPlayerStatTable, String>("opp1"));
		opp2.setCellValueFactory(new PropertyValueFactory<OppPlayerStatTable, String>("opp2"));
		opp3.setCellValueFactory(new PropertyValueFactory<OppPlayerStatTable, String>("opp3"));
		opp4.setCellValueFactory(new PropertyValueFactory<OppPlayerStatTable, String>("opp4"));
		opp5.setCellValueFactory(new PropertyValueFactory<OppPlayerStatTable, String>("opp5"));
	}
	
	/**
	 * Updates database stats for players
	 */
	private void UpdateDatabaseRush() {
		RushStat rush = new RushStat();
	    ArrayList<String> listTeam = rush.GetRushStats(teamAbbr);
	    ArrayList<String> listOpponent = rush.GetRushStats(oppAbbr);
	    
	    //add a new player if a new player is added
	    ArrayList<Integer> intListTeam = rush.CheckForNewPlayer(listTeam, "rushstats");
	    if (intListTeam.size() != 0) {
			rush.ScrapeNewPlayer(listTeam, intListTeam, teamName);
		}
		
		ArrayList<Integer> intListOpponent = rush.CheckForNewPlayer(listOpponent, "rushstats");
		if (intListOpponent.size() != 0) {
			rush.ScrapeNewPlayer(listOpponent, intListOpponent, oppName);
		}
		
		//update database if needed by checking random players' attempted yards
		if (!rush.CheckDatabaseList(listTeam, 1, "rush", "Att")
				|| !rush.CheckDatabaseList(listTeam, 3, "rush", "Att")) {
			rush.UpdateDatabase(listTeam, teamName);
		}
		
		if (!rush.CheckDatabaseList(listOpponent, 1, "rush", "Att")
				|| !rush.CheckDatabaseList(listOpponent, 3, "rush", "Att")) {
			rush.UpdateDatabase(listOpponent, oppName);
		}
	}

	/**
	 * Display player stats 
	 * @param type
	 */
	private void DisplayStat(String type) {
		ObservableList<HomePlayerStatTable> statList = FXCollections.observableArrayList(PopulateHomeStatList(type));
		homeStatTable.setItems(statList);
		
		ObservableList<OppPlayerStatTable> oppStatList = FXCollections.observableArrayList(PopulateOppStatList(type));
		oppStatTable.setItems(oppStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result;
		//if (type.equals("Rushing")) {
			result =  prob.CalculateRushProb(teamName, oppName) * 100;
		//} 
		DecimalFormat df = new DecimalFormat("#.##");
		String p = "Probability: " + df.format(result) + " %";
		probability.add(p);
		statView.getItems().add(probability.get(0));
	}
	
	/**
	 * Populates an arraylist for the home player stat table
	 * @return
	 */
	public ArrayList<HomePlayerStatTable> PopulateHomeStatList(String type) {
		ArrayList<HomePlayerStatTable> returnList = new ArrayList<HomePlayerStatTable>();
		
		if (type.equals("Rushing")) {
			ArrayList<String> players = model.GetRushStat(teamName, 2);
			ArrayList<String> att = model.GetRushStat(teamName, 3);
			ArrayList<String> yds = model.GetRushStat(teamName, 4);
			ArrayList<String> ya = model.GetRushStat(teamName, 5);
			ArrayList<String> lng = model.GetRushStat(teamName, 6);
			ArrayList<String> td = model.GetRushStat(teamName, 7);
			
			int index = 0;
			for (String player : players) {
				returnList.add(new HomePlayerStatTable(player, att.get(index), yds.get(index), ya.get(index), lng.get(index), td.get(index)));
				index++;
			}
		}
		
		return returnList;
	}
	
	/**
	 * Populates an arraylist for the opp player stat table
	 * @return
	 */
	public ArrayList<OppPlayerStatTable> PopulateOppStatList(String type) {
		ArrayList<OppPlayerStatTable> returnList = new ArrayList<OppPlayerStatTable>();
		
		ArrayList<String> players = model.GetRushStat(oppName, 2);
		ArrayList<String> att = model.GetRushStat(oppName, 3);
		ArrayList<String> yds = model.GetRushStat(oppName, 4);
		ArrayList<String> ya = model.GetRushStat(oppName, 5);
		ArrayList<String> lng = model.GetRushStat(oppName, 6);
		ArrayList<String> td = model.GetRushStat(oppName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new OppPlayerStatTable(player, att.get(index), yds.get(index), ya.get(index), lng.get(index), td.get(index)));
			index++;
		}
		
		return returnList;
	}
	
	/**
	 * Returns to the main screen
	 * @throws Exception
	 */
	@FXML
	private void HandleBack() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) rushReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}

}
