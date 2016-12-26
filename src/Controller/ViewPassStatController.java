package Controller;

import java.util.ArrayList;

import Model.HomePassStatTable;
import Model.Model;
import Model.OppPassStatTable;
import Model.PassStat;
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

public class ViewPassStatController {
	@FXML
	private TableView<HomePassStatTable> passStatTable;
	
	@FXML
	private TableColumn phomePlayer;
	
	@FXML
	private TableColumn phomeRec;
	
	@FXML
	private TableColumn phomeYds;
	
	@FXML
	private TableColumn phomeYR;
	
	@FXML
	private TableColumn phomeLong;
	
	@FXML
	private TableColumn phomeTD;
	
	@FXML
	private TableView<OppPassStatTable> oppPassStatTable;
	
	@FXML
	private TableColumn poppPlayer;
	
	@FXML
	private TableColumn poppRec;
	
	@FXML
	private TableColumn poppYds;
	
	@FXML
	private TableColumn poppYR;
	
	@FXML
	private TableColumn poppLong;
	
	@FXML
	private TableColumn poppTD;
	
	@FXML
	private Button passReturnButton;
	
	private String stringTeamName = "";
	
	private String stringOppName = "";
	
	@FXML
	private void initialize() {
		TeamSelectorController tcc = new TeamSelectorController();
        String stringTeam = tcc.getStringTeam();
        String stringOpponent = tcc.getStringOpponent();
        
        PassStat team = new PassStat();
        stringTeamName = team.getTeamName(stringTeam);
        stringOppName = team.getTeamName(stringOpponent);
        
        phomePlayer.setCellValueFactory(new PropertyValueFactory<HomePassStatTable, String>("phomePlayer"));
        phomeRec.setCellValueFactory(new PropertyValueFactory<HomePassStatTable, String>("phomeRec"));
        phomeYds.setCellValueFactory(new PropertyValueFactory<HomePassStatTable, String>("phomeYds"));
        phomeYR.setCellValueFactory(new PropertyValueFactory<HomePassStatTable, String>("phomeYR"));
        phomeLong.setCellValueFactory(new PropertyValueFactory<HomePassStatTable, String>("phomeLong"));
        phomeTD.setCellValueFactory(new PropertyValueFactory<HomePassStatTable, String>("phomeTD"));
        ObservableList<HomePassStatTable> passStatList = FXCollections.observableArrayList(populateHomeTable());
		passStatTable.setItems(passStatList);
		
		poppPlayer.setCellValueFactory(new PropertyValueFactory<OppPassStatTable, String>("poppPlayer"));
        poppRec.setCellValueFactory(new PropertyValueFactory<OppPassStatTable, String>("poppRec"));
        poppYds.setCellValueFactory(new PropertyValueFactory<OppPassStatTable, String>("poppYds"));
        poppYR.setCellValueFactory(new PropertyValueFactory<OppPassStatTable, String>("poppYR"));
        poppLong.setCellValueFactory(new PropertyValueFactory<OppPassStatTable, String>("poppLong"));
        poppTD.setCellValueFactory(new PropertyValueFactory<OppPassStatTable, String>("poppTD"));
        ObservableList<OppPassStatTable> oppPassStatList = FXCollections.observableArrayList(populateOppTable());
		oppPassStatTable.setItems(oppPassStatList);
	}
	
	private ArrayList<HomePassStatTable> populateHomeTable() {
		ArrayList<HomePassStatTable> returnList = new ArrayList<HomePassStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getPassStat(stringTeamName, 2);
		ArrayList<String> rec = model.getPassStat(stringTeamName, 3);
		ArrayList<String> yds = model.getPassStat(stringTeamName, 4);
		ArrayList<String> yr = model.getPassStat(stringTeamName, 5);
		ArrayList<String> lng = model.getPassStat(stringTeamName, 6);
		ArrayList<String> td = model.getPassStat(stringTeamName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new HomePassStatTable(player, rec.get(index), yds.get(index), yr.get(index), lng.get(index), td.get(index)));
			index++;
		}
	
		return returnList;
	}
	
	private ArrayList<OppPassStatTable> populateOppTable() {
		ArrayList<OppPassStatTable> returnList = new ArrayList<OppPassStatTable>();
		
		Model model = new Model();
		ArrayList<String> players = model.getPassStat(stringTeamName, 2);
		ArrayList<String> rec = model.getPassStat(stringTeamName, 3);
		ArrayList<String> yds = model.getPassStat(stringTeamName, 4);
		ArrayList<String> yr = model.getPassStat(stringTeamName, 5);
		ArrayList<String> lng = model.getPassStat(stringTeamName, 6);
		ArrayList<String> td = model.getPassStat(stringTeamName, 7);
		
		int index = 0;
		for (String player : players) {
			returnList.add(new OppPassStatTable(player, rec.get(index), yds.get(index), yr.get(index), lng.get(index), td.get(index)));
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
		
		stage = (Stage) passReturnButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/OptionsView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}

}
