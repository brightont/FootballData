package Controller;

import java.util.ArrayList;

import Model.FieldGoalStat;
import Model.FieldGoalStatTable;
import Model.Model;
import Model.Probability;
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

public class ViewFieldGoalsController {
	@FXML
	private TableView<FieldGoalStatTable> fgStatTable;

	@FXML
	private TableColumn fgStatName;
	
	@FXML
	private TableColumn fgHomeStat;
	
	@FXML
	private TableColumn fgOppStat;
	
	@FXML
	private ListView<String> fgView;
	
	@FXML
	private Button fgReturnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        FieldGoalStat team = new FieldGoalStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        fgStatName.setCellValueFactory(new PropertyValueFactory<FieldGoalStatTable, String>("fgStatName"));
		fgHomeStat.setCellValueFactory(new PropertyValueFactory<FieldGoalStatTable, String>("fgHomeStat"));
		fgOppStat.setCellValueFactory(new PropertyValueFactory<FieldGoalStatTable, String>("fgOppStat"));
		ObservableList<FieldGoalStatTable> fgStatList = FXCollections.observableArrayList(populateFGStatList());
		fgStatTable.setItems(fgStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result =  prob.calculateFGProbability(stringTeamName, stringOppName) * 100;
		String p = "Probability: " + result + " %";
		probability.add(p);
		for (String pr : probability) {
			fgView.getItems().add(pr);
		}
	} 
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<FieldGoalStatTable> populateFGStatList() {
		ArrayList<FieldGoalStatTable> returnList = new ArrayList<FieldGoalStatTable>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getFGStatsName(stringTeamName);
		ArrayList<String> homeStats = model.getFGStats(stringTeamName);
		ArrayList<String> oppStats = model.getFGStats(stringOppName);
		
		int index = 0;
		for (String name: statNames) {
			returnList.add(new FieldGoalStatTable(name, homeStats.get(index), oppStats.get(index)));
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
		
		stage = (Stage) fgReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
}
