package Controller;

import java.util.ArrayList;

import Model.InjuryStatTable;
import Model.InjuryStat;
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

public class ViewInjuriesController {
	
	@FXML
	private TableView<InjuryStatTable> injuryTable;

	@FXML
	private TableColumn ihome;
	
	@FXML
	private TableColumn homeStatus;
	
	@FXML
	private TableColumn iopp;
	
	@FXML
	private TableColumn oppStatus;
	
	@FXML
	private ListView<String> injuryView;
	
	@FXML
	private Button injuriesReturnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        InjuryStat team = new InjuryStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        ihome.setCellValueFactory(new PropertyValueFactory<InjuryStatTable, String>("ihome"));
		homeStatus.setCellValueFactory(new PropertyValueFactory<InjuryStatTable, String>("homeStatus"));
		iopp.setCellValueFactory(new PropertyValueFactory<InjuryStatTable, String>("iopp"));
		oppStatus.setCellValueFactory(new PropertyValueFactory<InjuryStatTable, String>("oppStatus"));
		ObservableList<InjuryStatTable> injuryStatList = FXCollections.observableArrayList(populateTable());
		injuryTable.setItems(injuryStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result =  prob.calculateInjuryProbability(stringTeamName, stringOppName) * 100;
		String p = "Probability: " + result + " %";
		probability.add(p);
		for (String pr : probability) {
			injuryView.getItems().add(pr);
		}
	} 
	
	/**
	 * Populates the table
	 * @return
	 */
	private ArrayList<InjuryStatTable> populateTable() {
		ArrayList<InjuryStatTable> returnList = new ArrayList<InjuryStatTable>();
		
		Model model = new Model();
		ArrayList<String> homePlayer = model.getInjury(stringTeamName, 2);
		ArrayList<String> homeStatus = model.getInjury(stringTeamName, 5);
		ArrayList<String> oppPlayer = model.getInjury(stringOppName, 2);
		ArrayList<String> oppStatus = model.getInjury(stringOppName, 5);
		
		int index = 0;
		int stop = 0;
		if (homePlayer.size() < oppPlayer.size()) {
			stop = homePlayer.size();
		} else {
			stop = oppPlayer.size();
		}
		while (index < stop) {
			returnList.add(new InjuryStatTable(homePlayer.get(index), homeStatus.get(index), oppPlayer.get(index), oppStatus.get(index)));
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
		
		stage = (Stage) injuriesReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}

	
}
