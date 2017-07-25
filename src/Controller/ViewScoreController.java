package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.Probability;
import Model.QBStatTable;
import Model.ScoreStat;
import Model.ScoreStatTable;
import Model.TeamName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
	private ListView<String> scoreView;
	
	@FXML
	private ListView<String> record;
	
	@FXML
	private Button sReturnButton;
	
	private String stringTeamName = "";
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        ScoreStat team = new ScoreStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        week.setCellValueFactory(new PropertyValueFactory<ScoreStatTable, String>("week"));
		opp.setCellValueFactory(new PropertyValueFactory<ScoreStatTable, String>("opp"));
		score.setCellValueFactory(new PropertyValueFactory<ScoreStatTable, String>("score"));
		ObservableList<ScoreStatTable> scoreStatList = FXCollections.observableArrayList(populateScoreStatList());
		scoreTable.setItems(scoreStatList);
		
		ArrayList<String> records = new ArrayList<String>();
		records.add(team.getRecord(stringTeamName));
		for (String r : records) {
			record.getItems().add(r);
		}
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result =  prob.calculateFGProbability(stringTeamName, stringOppName) * 100;
		String p = "Probability: " + result + " %";
		probability.add(p);
		for (String pr : probability) {
			scoreView.getItems().add(pr);
		}
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
	/**
	 * Returns to the main screen
	 * @throws Exception
	 */
	@FXML
	private void handleBack() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) sReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}

}
