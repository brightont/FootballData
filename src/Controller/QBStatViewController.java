package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.QBStat;
import Model.Team;
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

public class QBStatViewController {
	
	@FXML
	private TableView<QBStat> qbStatTable;

	@FXML
	private TableColumn qbStatName;
	
	@FXML
	private TableColumn qbHomeStat;
	
	@FXML
	private TableColumn qbOppStat;
	
	@FXML
	private Button qbReturnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamChooserController tcc = new TeamChooserController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        Team team = new Team();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        qbStatName.setCellValueFactory(new PropertyValueFactory<QBStat, String>("qbStatName"));
		qbHomeStat.setCellValueFactory(new PropertyValueFactory<QBStat, String>("qbHomeStat"));
		qbOppStat.setCellValueFactory(new PropertyValueFactory<QBStat, String>("qbOppStat"));
		ObservableList<QBStat> qbStatList = FXCollections.observableArrayList(populateQBStatList());
		qbStatTable.setItems(qbStatList); 
	} 
	
	/**
	 * Populates an arrayList for the table view
	 * @return
	 */
	private ArrayList<QBStat> populateQBStatList() {
		ArrayList<QBStat> returnList = new ArrayList<QBStat>();
		
		Model model = new Model();
		ArrayList<String> statNames = model.getQBStatsName(stringTeamName);
		ArrayList<String> homeStats = model.getQBStats(stringTeamName);
		
		//prevents pointing to the same thing
		Model model2 = new Model();
		ArrayList<String> oppStats = model2.getQBStats(stringOppName);
		
		int index = 0;
		for (String name: statNames) {
			returnList.add(new QBStat(name, homeStats.get(index), oppStats.get(index)));
			index++;	
		}
		
		return returnList;
	}
	
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