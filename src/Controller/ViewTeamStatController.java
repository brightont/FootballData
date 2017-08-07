package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MainApplication.MainApplication;
import Model.Model;
import Model.Probability;
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
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
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
	}
	
	/**
	 * Populates the table view based on chosen statistic
	 */
	public void ChooseStat() {
		statTable.setVisible(true);
		String stat = statisticMenu.getSelectionModel().getSelectedItem();
		
		stringTeamName = TeamName.valueOf(TeamSelectorController.stringTeam).getTeam();
        stringOppName = TeamName.valueOf(TeamSelectorController.stringOpponent).getTeam();
        
        statTable.getItems().clear();
		teamView.getItems().clear();
		
		statName.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("statName"));
		homeStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("homeStat"));
		oppStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("oppStat"));
        
		if (stat.equals("General")) {
			DisplayGeneral("General");
		} else if (stat.equals("Quarterback")) {
			DisplayGeneral("Quarterback");
		}
	}
	
	/**
	 * Displays general stats
	 */
	private void DisplayGeneral(String type) {
		ObservableList<TeamStatTable> teamStatList = FXCollections.observableArrayList(PopulateTeamStatList(type));
		statTable.setItems(teamStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		if (type.equals("General")) {
			double result =  prob.CalculateTeamProb(stringTeamName, stringOppName) * 100;
			String p = "Probability: " + result + " %";
			probability.add(p);
			teamView.getItems().add(probability.get(0));
		}
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
			homeStats = model.GetTeamStats(stringTeamName);
			oppStats = model.GetTeamStats(stringOppName);
		} else {
			statNames = model.GetQBStatsName();
			homeStats = model.GetQBStats(stringTeamName);
			oppStats = model.GetQBStats(stringOppName);
		}
		
		int index = 0;
		
		for (String name: statNames) {
			returnList.add(new TeamStatTable(name, homeStats.get(index), oppStats.get(index)));
			index++;	
		}
		return returnList;
	}
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<TeamStatTable> PopulateQBStatList() {
		ArrayList<TeamStatTable> returnList = new ArrayList<TeamStatTable>();
		
		Model model = new Model();
		//ArrayList<String> statNames = model.GetQBStatsName(stringTeamName);
		//ArrayList<String> homeStats = model.GetQBStats(stringTeamName);
		//ArrayList<String> oppStats = model.GetQBStats(stringOppName);
		
		int index = 0;
		//for (String name: statNames) {
			//returnList.add(new QBStatTable(name, homeStats.get(index), oppStats.get(index)));
			//index++;	
		//}
		
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
