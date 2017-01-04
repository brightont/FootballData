package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.Probability;
import Model.QBStat;
import Model.QBStatTable;
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

public class ViewQBStatController {
	
	@FXML
	private TableView<QBStatTable> qbStatTable;

	@FXML
	private TableColumn qbStatName;
	
	@FXML
	private TableColumn qbHomeStat;
	
	@FXML
	private TableColumn qbOppStat;
	
	@FXML
	private ListView<String> qbView;
	
	@FXML
	private Button qbReturnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        QBStat team = new QBStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        qbStatName.setCellValueFactory(new PropertyValueFactory<QBStatTable, String>("qbStatName"));
		qbHomeStat.setCellValueFactory(new PropertyValueFactory<QBStatTable, String>("qbHomeStat"));
		qbOppStat.setCellValueFactory(new PropertyValueFactory<QBStatTable, String>("qbOppStat"));
		ObservableList<QBStatTable> qbStatList = FXCollections.observableArrayList(populateQBStatList());
		qbStatTable.setItems(qbStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result =  prob.calculateQBProbability(stringTeamName, stringOppName) * 100;
		String p = "Probability: " + result + " %";
		probability.add(p);
		for (String pr : probability) {
			qbView.getItems().add(pr);
		}
	} 
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<QBStatTable> populateQBStatList() {
		ArrayList<QBStatTable> returnList = new ArrayList<QBStatTable>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getQBStatsName(stringTeamName);
		ArrayList<String> homeStats = model.getQBStats(stringTeamName);
		ArrayList<String> oppStats = model.getQBStats(stringOppName);
		
		int index = 0;
		for (String name: statNames) {
			returnList.add(new QBStatTable(name, homeStats.get(index), oppStats.get(index)));
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
		
		stage = (Stage) qbReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}

}
