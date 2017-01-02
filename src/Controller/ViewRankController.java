package Controller;

import java.util.ArrayList;

import Model.HomeRushStatTable;
import Model.Model;
import Model.QBStat;
import Model.QBStatTable;
import Model.QuickStats;
import Model.RankStatTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewRankController {
	@FXML
	private TableView<HomeRushStatTable> scoreTable;
	
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
		/*ObservableList<RankStatTable> rankStatList = FXCollections.observableArrayList(populateQBStatList());
		qbStatTable.setItems(qbStatList);*/
	} 
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<RankStatTable> populateRankStatList() {
		ArrayList<RankStatTable> returnList = new ArrayList<RankStatTable>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getRankStatsName();
		ArrayList<String> homeStats = model.getQBStats(stringTeamName);
		ArrayList<String> oppStats = model.getQBStats(stringOppName);
		
		int index = 0;
		/*for (String name: statNames) {
			returnList.add(new QBStatTable(name, homeStats.get(index), oppStats.get(index)));
			index++;	
		}*/
		
		return returnList;
	}
	
}
