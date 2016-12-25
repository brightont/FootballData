package Controller;

import Model.Model;
import Model.QBStatTable;
import Model.TeamStat;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewRushStatController {
	
	@FXML
	private TableView<QBStatTable> rushStatTable;
	
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
	private TableView<QBStatTable> oppRushStatTable;
	
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
        
        //TeamStat team = new TeamStat();
        //stringTeamName = team.getTeamName(stringTeam);
        //stringOppName = team.getTeamName(stringOpponent);
        
	}
	
	private void populateHomeTable() {
		Model model = new Model();
		
	}
	
	
}
