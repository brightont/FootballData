package Controller;

import java.util.ArrayList;

import Model.HomeIntStatTable;
import Model.IntStat;
import Model.Model;
import Model.OppIntStatTable;
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

public class ViewInterceptionsController {
	@FXML
	private TableView<HomeIntStatTable> intStatTable;
	
	@FXML
	private TableColumn ihomePlayer;
	
	@FXML
	private TableColumn ihomeInt;
	
	@FXML
	private TableColumn ihomeYds;
	
	@FXML
	private TableColumn ihomeYI;
	
	@FXML
	private TableColumn ihomeLong;
	
	@FXML
	private TableColumn ihomeTD;
	
	@FXML
	private TableView<OppIntStatTable> oppIntStatTable;
	
	@FXML
	private TableColumn ioppPlayer;
	
	@FXML
	private TableColumn ioppInt;
	
	@FXML
	private TableColumn ioppYds;
	
	@FXML
	private TableColumn ioppYI;
	
	@FXML
	private TableColumn ioppLong;
	
	@FXML
	private TableColumn ioppTD;
	
	@FXML
	private Button ireturnButton;

	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        IntStat team = new IntStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        ihomePlayer.setCellValueFactory(new PropertyValueFactory<HomeIntStatTable, String>("ihomePlayer"));
        ihomeInt.setCellValueFactory(new PropertyValueFactory<HomeIntStatTable, String>("ihomeInt"));
        ihomeYds.setCellValueFactory(new PropertyValueFactory<HomeIntStatTable, String>("ihomeYds"));
        ihomeYI.setCellValueFactory(new PropertyValueFactory<HomeIntStatTable, String>("ihomeYI"));
        ihomeLong.setCellValueFactory(new PropertyValueFactory<HomeIntStatTable, String>("ihomeLong"));
        ihomeTD.setCellValueFactory(new PropertyValueFactory<HomeIntStatTable, String>("ihomeTD"));
        ObservableList<HomeIntStatTable> intStatList = FXCollections.observableArrayList(populateHomeTable());
		intStatTable.setItems(intStatList);
		
		ioppPlayer.setCellValueFactory(new PropertyValueFactory<OppIntStatTable, String>("ioppPlayer"));
        ioppInt.setCellValueFactory(new PropertyValueFactory<OppIntStatTable, String>("ioppInt"));
        ioppYds.setCellValueFactory(new PropertyValueFactory<OppIntStatTable, String>("ioppYds"));
        ioppYI.setCellValueFactory(new PropertyValueFactory<OppIntStatTable, String>("ioppYI"));
        ioppLong.setCellValueFactory(new PropertyValueFactory<OppIntStatTable, String>("ioppLong"));
        ioppTD.setCellValueFactory(new PropertyValueFactory<OppIntStatTable, String>("ioppTD"));
        ObservableList<OppIntStatTable> oppIntStatList = FXCollections.observableArrayList(populateOppTable());
		oppIntStatTable.setItems(oppIntStatList);
	}
	
	private ArrayList<HomeIntStatTable> populateHomeTable() {
		ArrayList<HomeIntStatTable> returnList = new ArrayList<HomeIntStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getIntStat(stringTeamName, 2);
		ArrayList<String> it = model.getIntStat(stringTeamName, 3);
		ArrayList<String> yds = model.getIntStat(stringTeamName, 4);
		ArrayList<String> yi = model.getIntStat(stringTeamName, 5);
		ArrayList<String> lng = model.getIntStat(stringTeamName, 6);
		ArrayList<String> td = model.getIntStat(stringTeamName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new HomeIntStatTable(player, it.get(index), yds.get(index), yi.get(index), lng.get(index), td.get(index)));
			index++;
		}
	
		return returnList;
	}
	
	private ArrayList<OppIntStatTable> populateOppTable() {
		ArrayList<OppIntStatTable> returnList = new ArrayList<OppIntStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getIntStat(stringOppName, 2);
		ArrayList<String> it = model.getIntStat(stringOppName, 3);
		ArrayList<String> yds = model.getIntStat(stringOppName, 4);
		ArrayList<String> yi = model.getIntStat(stringOppName, 5);
		ArrayList<String> lng = model.getIntStat(stringOppName, 6);
		ArrayList<String> td = model.getIntStat(stringOppName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new OppIntStatTable(player, it.get(index), yds.get(index), yi.get(index), lng.get(index), td.get(index)));
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
		
		stage = (Stage) ireturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
	
}
