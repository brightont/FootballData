package Controller;

import java.util.ArrayList;

import MainApplication.MainApplication;
import Model.Model;
import Model.TeamStat;
import Model.TeamStatTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewTeamStatController {
	
	private MainApplication mainApplication;
	
	@FXML
	private TableView<TeamStatTable> statTable;

	@FXML
	private TableColumn statName;
	
	@FXML
	private TableColumn homeStat;
	
	@FXML
	private TableColumn oppStat;
	
	@FXML
	private Button returnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        TeamStat team = new TeamStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
		
		statName.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("statName"));
		homeStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("homeStat"));
		oppStat.setCellValueFactory(new PropertyValueFactory<TeamStatTable, String>("oppStat"));
		ObservableList<TeamStatTable> teamStatList = FXCollections.observableArrayList(populateTeamStatList());
		statTable.setItems(teamStatList);
		
	}

	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<TeamStatTable> populateTeamStatList() {
		ArrayList<TeamStatTable> returnList = new ArrayList<TeamStatTable>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getStatsName();
		ArrayList<String> homeStats = model.getTeamStats(stringTeamName);
		ArrayList<String> oppStats = model.getTeamStats(stringOppName);
		
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TeamSelector.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
	
}
