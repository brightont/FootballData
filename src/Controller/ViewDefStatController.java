package Controller;

import Model.HomeDefStatTable;
import Model.HomePassStatTable;
import Model.Model;
import Model.OppDefStatTable;
import Model.Probability;

import java.util.ArrayList;

import Model.DefStat;
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

public class ViewDefStatController {
	@FXML
	private TableView<HomeDefStatTable> defStatTable;
	
	@FXML
	private TableColumn dhomePlayer;
	
	@FXML
	private TableColumn dhomeComb;
	
	@FXML
	private TableColumn dhomeTotal;
	
	@FXML
	private TableColumn dhomeAssist;
	
	@FXML
	private TableColumn dhomeSck;
	
	@FXML
	private TableColumn dhomeFumb;
	
	@FXML
	private TableView<OppDefStatTable> oppDefStatTable;
	
	@FXML
	private TableColumn doppPlayer;
	
	@FXML
	private TableColumn doppComb;
	
	@FXML
	private TableColumn doppTotal;
	
	@FXML
	private TableColumn doppAssist;
	
	@FXML
	private TableColumn doppSck;
	
	@FXML
	private TableColumn doppFumb;
	
	@FXML
	private ListView<String> defView;
	
	@FXML
	private Button defReturnButton;
	
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
        
        dhomePlayer.setCellValueFactory(new PropertyValueFactory<HomeDefStatTable, String>("dhomePlayer"));
        dhomeComb.setCellValueFactory(new PropertyValueFactory<HomeDefStatTable, String>("dhomeComb"));
        dhomeTotal.setCellValueFactory(new PropertyValueFactory<HomeDefStatTable, String>("dhomeTotal"));
        dhomeAssist.setCellValueFactory(new PropertyValueFactory<HomeDefStatTable, String>("dhomeAssist"));
        dhomeSck.setCellValueFactory(new PropertyValueFactory<HomeDefStatTable, String>("dhomeSck"));
        dhomeFumb.setCellValueFactory(new PropertyValueFactory<HomeDefStatTable, String>("dhomeFumb"));
        ObservableList<HomeDefStatTable> defStatList = FXCollections.observableArrayList(populateHomeTable());
		defStatTable.setItems(defStatList);
		
		doppPlayer.setCellValueFactory(new PropertyValueFactory<OppDefStatTable, String>("doppPlayer"));
		doppComb.setCellValueFactory(new PropertyValueFactory<OppDefStatTable, String>("doppComb"));
		doppTotal.setCellValueFactory(new PropertyValueFactory<OppDefStatTable, String>("doppTotal"));
		doppAssist.setCellValueFactory(new PropertyValueFactory<OppDefStatTable, String>("doppAssist"));
		doppSck.setCellValueFactory(new PropertyValueFactory<OppDefStatTable, String>("doppSck"));
		doppFumb.setCellValueFactory(new PropertyValueFactory<OppDefStatTable, String>("doppFumb"));
		ObservableList<OppDefStatTable> oppDefStatList = FXCollections.observableArrayList(populateOppTable());
		oppDefStatTable.setItems(oppDefStatList);
		
		ArrayList<String> probability = new ArrayList<String>();
		Probability prob = new Probability();
		double result =  prob.calculateDefProbability(stringTeamName, stringOppName) * 100;
		String p = "Probability: " + result + " %";
		probability.add(p);
		for (String pr : probability) {
			defView.getItems().add(pr);
		}
	}
	
	private ArrayList<HomeDefStatTable> populateHomeTable() {
		ArrayList<HomeDefStatTable> returnList = new ArrayList<HomeDefStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getDefStat(stringTeamName, 2);
		ArrayList<String> comb = model.getDefStat(stringTeamName, 3);
		ArrayList<String> total = model.getDefStat(stringTeamName, 4);
		ArrayList<String> assist = model.getDefStat(stringTeamName, 5);
		ArrayList<String> sck = model.getDefStat(stringTeamName, 6);
		ArrayList<String> fumb = model.getDefStat(stringTeamName, 7);
	
		int index = 0;
		for (String player : players) {
			returnList.add(new HomeDefStatTable(player, comb.get(index), total.get(index), assist.get(index), sck.get(index), fumb.get(index)));
			index++;
		}
	
		return returnList;
	}
	
	private ArrayList<OppDefStatTable> populateOppTable() {
		ArrayList<OppDefStatTable> returnList = new ArrayList<OppDefStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getDefStat(stringOppName, 2);
		ArrayList<String> comb = model.getDefStat(stringOppName, 3);
		ArrayList<String> total = model.getDefStat(stringOppName, 4);
		ArrayList<String> assist = model.getDefStat(stringOppName, 5);
		ArrayList<String> sck = model.getDefStat(stringOppName, 6);
		ArrayList<String> fumb = model.getDefStat(stringOppName, 7);
	
		int index = 0;
		for (String player : players) {
			returnList.add(new OppDefStatTable(player, comb.get(index), total.get(index), assist.get(index), sck.get(index), fumb.get(index)));
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
		
		stage = (Stage) defReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
}
