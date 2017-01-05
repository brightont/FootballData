package Controller;

import java.util.ArrayList;

import Model.DefStat;
import Model.HomeDefStatTable;
import Model.OppDefStatTable;
import Model.Probability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProbabilityController {
	@FXML
	private ListView<String> probView;
	
	@FXML
	private Button probReturnButton;
	
private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        DefStat team = new DefStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double teamChance = prob.calculateProbability(stringTeamName, stringOppName);
		prob.reset();
		double oppChance = prob.calculateProbability(stringOppName, stringTeamName);
		probability.add("These scores are relative.");
		probability.add(stringTeamName + "'s Score : " + teamChance);
		probability.add(stringOppName + "'s Score : " + oppChance);
		if (teamChance > oppChance) {
			probability.add("PREDICTION: " + stringTeamName + " will win.");
		} else {
			probability.add("PREDICTION: " + stringOppName + " will win.");
		}
		for (String pr : probability) {
			probView.getItems().add(pr);
		}
	}
	
	/**
	 * Returns to the main screen
	 * @throws Exception
	 */
	@FXML
	private void handleBack() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) probReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
}
