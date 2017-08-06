package Controller;

import java.util.ArrayList;
import java.util.List;

import MainApplication.MainApplication;
import Model.Model;
import Model.Probability;
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
		
		List<String> stats = new ArrayList<String>();
		stats.add("General");
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
		if (stat.equals("General")) {
			DisplayGeneral();
		}
	}
	
	/**
	 * Displays general stats
	 */
	private void DisplayGeneral() {
		TeamStat team = new TeamStat();
        stringTeamName = team.getTeamName(TeamSelectorController.stringTeam);
        stringOppName = team.getTeamName(TeamSelectorController.stringOpponent);
		
		statName.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("statName"));
		homeStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("homeStat"));
		oppStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("oppStat"));
		
		//PopulateTeamStatList();
		ObservableList<TeamStatTable> teamStatList = FXCollections.observableArrayList(PopulateTeamStatList());
		statTable.setItems(teamStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result =  prob.calculateTeamProbability(stringTeamName, stringOppName) * 100;
		String p = "Probability: " + result + " %";
		probability.add(p);
		for (String pr : probability) {
			teamView.getItems().add(pr);
		}
	}

	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	public ArrayList<TeamStatTable> PopulateTeamStatList() {
		ArrayList<TeamStatTable> returnList = new ArrayList<TeamStatTable>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.GetStatsName();
		ArrayList<String> homeStats = model.GetTeamStats(stringTeamName);
		ArrayList<String> oppStats = model.GetTeamStats(stringOppName);
		
		int index = 0;
		
		for (String name: statNames) {
			returnList.add(new TeamStatTable(name, homeStats.get(index), oppStats.get(index)));
			index++;	
		}
		return returnList;
	}
	
	@FXML
	private void handleBack() throws Exception {
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
