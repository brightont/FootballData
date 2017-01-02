package Controller;

import java.util.ArrayList;

import Model.HomeRushStatTable;
import Model.Model;
import Model.QuickStats;
import Model.RankStatTable;
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

public class ViewRankController {
	@FXML
	private TableView<RankStatTable> scoreTable;
	
	@FXML
	private TableColumn rankStat;
	
	@FXML
	private TableColumn homeRank;
	
	@FXML
	private TableColumn oppRank;
	
	@FXML
	private Button rankReturnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";

	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        QuickStats team = new QuickStats();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        rankStat.setCellValueFactory(new PropertyValueFactory<RankStatTable, String>("rankStat"));
		homeRank.setCellValueFactory(new PropertyValueFactory<RankStatTable, String>("homeRank"));
		oppRank.setCellValueFactory(new PropertyValueFactory<RankStatTable, String>("oppRank"));
	
		ObservableList<RankStatTable> rankStatList = FXCollections.observableArrayList(populateRankStatList());
		scoreTable.setItems(rankStatList);
	} 
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<RankStatTable> populateRankStatList() {
		ArrayList<RankStatTable> returnList = new ArrayList<RankStatTable>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getRankStatsName();
		ArrayList<Integer> homeRank = model.getRanking(stringTeamName);
		ArrayList<Integer> oppRank = model.getRanking(stringOppName);
		
		int index = 0;
		for (String name: statNames) {
			String hrs = homeRank.get(index).toString();
			String ors = oppRank.get(index).toString();
			returnList.add(new RankStatTable(name, hrs, ors));
			index++;	
		}
		
		return returnList;
	}
	
	/**
	 * Returns to the main screen
	 * @throws Exception
	 */
	@FXML
	private void handleBack() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) rankReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
	
}
