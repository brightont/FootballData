package Controller;

import java.util.ArrayList;

import Model.HomeRushStatTable;
import Model.Model;
import Model.OppRushStatTable;
import Model.QBStatTable;
import Model.RushStat;
import Model.TeamStat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewRushStatController {
	
	@FXML
	private TableView<HomeRushStatTable> rushStatTable;
	
	@FXML
	private TableColumn homePlayer;
	
	@FXML
	private TableColumn homeAtt;
	
	@FXML
	private TableColumn homeYds;
	
	@FXML
	private TableColumn homeYA;
	
	@FXML
	private TableColumn homeLong;
	
	@FXML
	private TableColumn homeTD;
	
	@FXML
	private TableView<OppRushStatTable> oppRushStatTable;
	
	@FXML
	private TableColumn oppPlayer;
	
	@FXML
	private TableColumn oppAtt;
	
	@FXML
	private TableColumn oppYds;
	
	@FXML
	private TableColumn oppYA;
	
	@FXML
	private TableColumn oppLong;
	
	@FXML
	private TableColumn oppTD;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        RushStat team = new RushStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        homePlayer.setCellValueFactory(new PropertyValueFactory<HomeRushStatTable, String>("homePlayer"));
        homeAtt.setCellValueFactory(new PropertyValueFactory<HomeRushStatTable, String>("homeAtt"));
        homeYds.setCellValueFactory(new PropertyValueFactory<HomeRushStatTable, String>("homeYds"));
        homeYA.setCellValueFactory(new PropertyValueFactory<HomeRushStatTable, String>("homeYA"));
        homeLong.setCellValueFactory(new PropertyValueFactory<HomeRushStatTable, String>("homeLong"));
        homeTD.setCellValueFactory(new PropertyValueFactory<HomeRushStatTable, String>("homeTD"));
        ObservableList<HomeRushStatTable> rushStatList = FXCollections.observableArrayList(populateHomeTable());
		rushStatTable.setItems(rushStatList);
		
		oppPlayer.setCellValueFactory(new PropertyValueFactory<OppRushStatTable, String>("oppPlayer"));
		oppAtt.setCellValueFactory(new PropertyValueFactory<OppRushStatTable, String>("oppAtt"));
		oppYds.setCellValueFactory(new PropertyValueFactory<OppRushStatTable, String>("oppYds"));
		oppYA.setCellValueFactory(new PropertyValueFactory<OppRushStatTable, String>("oppYA"));
		oppLong.setCellValueFactory(new PropertyValueFactory<OppRushStatTable, String>("oppLong"));
		oppTD.setCellValueFactory(new PropertyValueFactory<OppRushStatTable, String>("oppTD"));
		ObservableList<OppRushStatTable> oppRushStatList = FXCollections.observableArrayList(populateOppTable());
		oppRushStatTable.setItems(oppRushStatList);
	}
	
	private ArrayList<HomeRushStatTable> populateHomeTable() {
		ArrayList<HomeRushStatTable> returnList = new ArrayList<HomeRushStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getRushStat(stringTeamName, 2);
		ArrayList<String> att = model.getRushStat(stringTeamName, 3);
		ArrayList<String> yds = model.getRushStat(stringTeamName, 4);
		ArrayList<String> ya = model.getRushStat(stringTeamName, 5);
		ArrayList<String> lng = model.getRushStat(stringTeamName, 6);
		ArrayList<String> td = model.getRushStat(stringTeamName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new HomeRushStatTable(player, att.get(index), yds.get(index), ya.get(index), lng.get(index), td.get(index)));
			index++;
		}
	
		return returnList;
	}
	
	private ArrayList<OppRushStatTable> populateOppTable() {
		ArrayList<OppRushStatTable> returnList = new ArrayList<OppRushStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getRushStat(stringOppName, 2);
		ArrayList<String> att = model.getRushStat(stringOppName, 3);
		ArrayList<String> yds = model.getRushStat(stringOppName, 4);
		ArrayList<String> ya = model.getRushStat(stringOppName, 5);
		ArrayList<String> lng = model.getRushStat(stringOppName, 6);
		ArrayList<String> td = model.getRushStat(stringOppName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new OppRushStatTable(player, att.get(index), yds.get(index), ya.get(index), lng.get(index), td.get(index)));
			index++;
		}
	
		return returnList;
		
	}
}
