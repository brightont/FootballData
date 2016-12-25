package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.Team;
import Model.Stat;
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

public class TeamStatViewController {
	
	@FXML
	private TableView<Stat> statTable;

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
	
	@FXML
	private void initialize() {
		TeamChooserController tcc = new TeamChooserController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        Team team = new Team();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
		
		statName.setCellValueFactory(new PropertyValueFactory<Stat, String>("statName"));
		homeStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("homeStat"));
		oppStat.setCellValueFactory(new PropertyValueFactory<Stat, String>("oppStat"));
		ObservableList<Stat> teamStatList = FXCollections.observableArrayList(populateTeamStatList());
		statTable.setItems(teamStatList);
		
	}

	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<Stat> populateTeamStatList() {
		ArrayList<Stat> returnList = new ArrayList<Stat>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getStatsName(stringTeamName);
		ArrayList<String> homeStats = model.getTeamStats(stringTeamName);
		
		//prevents pointing to the same thing
		Model model2 = new Model();
		ArrayList<String> oppStats = model2.getTeamStats(stringOppName);
		
		int index = 0;
		for (String name: statNames) {
			returnList.add(new Stat(name, homeStats.get(index), oppStats.get(index)));
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
