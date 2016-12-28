package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.QBStatTable;
import Model.ScoreStat;
import Model.ScoreStatTable;
import Model.TeamName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewScoreController {
	@FXML
	private TableView<ScoreStatTable> scoreTable;

	@FXML
	private TableColumn week;
	
	@FXML
	private TableColumn opp;
	
	@FXML
	private TableColumn score;
	
	@FXML
	private Button scoreReturnButton;
	
	private String stringTeamName = "";
	
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        ScoreStat team = new ScoreStat();
        stringTeamName = team.getTeamName(stringTeam);
        
        week.setCellValueFactory(new PropertyValueFactory<ScoreStatTable, String>("week"));
		opp.setCellValueFactory(new PropertyValueFactory<ScoreStatTable, String>("opp"));
		score.setCellValueFactory(new PropertyValueFactory<ScoreStatTable, String>("score"));
		ObservableList<ScoreStatTable> scoreStatList = FXCollections.observableArrayList(populateScoreStatList());
		scoreTable.setItems(scoreStatList);
	} 
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<ScoreStatTable> populateScoreStatList() {
		ArrayList<ScoreStatTable> returnList = new ArrayList<ScoreStatTable>();
        
		Model model = new Model();
		ArrayList<String> weeks = model.getScores(stringTeamName, 2);
		ArrayList<String> opp = model.getScoresOpp(stringTeamName);
		ArrayList<String> scores = model.getScores(stringTeamName, 3);
		
		int index = 0;
		for (String week: weeks) {
			returnList.add(new ScoreStatTable(week, opp.get(index), scores.get(index)));
			index++;	
		}
		
		return returnList;
	}

}
