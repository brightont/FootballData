package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import MainApplication.MainApplication;
import Model.QBStat;
import Model.TeamStat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OptionsController {

	private static final Logger logger = Logger.getLogger("OptionsController");
	private MainApplication mainApplication;
	
	@FXML
	private Button teamStats;
	
	@FXML
	private Button qbStats;
	
	@FXML
	private Button rushStats;
	
	@FXML
	private Button mainScreenButton;
	
	public void setMainApp(MainApplication main) {
        mainApplication = main;
    }
	
	/**
	 * View the team stats
	 */
	@FXML
	public void selectTeamStats() {
        try {
        	Stage stage;
            Parent root;

            stage = (Stage) teamStats.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/StatView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
  
            //Gets the team names 
            TeamSelectorController tsc = new TeamSelectorController();
            String stringTeam = tsc.getStringTeam();
            String stringOpponent = tsc.getStringOpponent();
            
            //gets the two hash maps
            TeamStat team = new TeamStat();
            HashMap<String, String> hashTeam = team.getTeamStats(stringTeam);
            HashMap<String, String> hashOpponent = team.getTeamStats(stringOpponent); 
            
            String value = "Sacks";
            
            hashTeam.values().remove(stringTeam);
            hashOpponent.values().remove(stringOpponent);
            
            if (team.checkDatabase(hashTeam, value, "team") == false) {
            	System.out.println("Dog");
            	team.updateDatabase(hashTeam, team.getTeamName(stringTeam));
            }  
            if (team.checkDatabase(hashOpponent, value, "team") == false) {
            	team.updateDatabase(hashOpponent, team.getTeamName(stringOpponent));
            }
		} catch (IOException e) {
			logger.log(Level.FINE, "Team Stats could not be loaded.");
		}
	}
	
	/**
	 * View the QB stats
	 */
	@FXML
	private void selectQBStats() {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) qbStats.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/QBStatView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
                        
            QBStat qb = new QBStat();
            HashMap<String, String> hashQB = qb.getQBStats(stringTeam);
            HashMap<String, String> hashOppQB = qb.getQBStats(stringOpponent);
            
            String value = "Att";
            
            if (qb.checkDatabase(hashQB, value, "qb") == false) {
            	qb.updateDatabase(hashQB, qb.getTeamName(stringTeam));
            }
            if (qb.checkDatabase(hashOppQB, value, "qb") == false) {
            	qb.updateDatabase(hashOppQB, qb.getTeamName(stringOpponent));
            }
            
		} catch (IOException e) {
			logger.log(Level.FINE, "QB Stats could not be loaded.");
		}
		
	}
	
	/**
	 * View the rush stats
	 */
	@FXML
	private void selectRushStats() {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) rushStats.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/RushStatView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            /*TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
            
            Rush rush = new Rush();
            ArrayList<String> listTeam = rush.getRushStats(stringTeam);
            ArrayList<String> listOpp = rush.getRushStats(stringOpponent);
            
            rush.checkForNewPlayer(listTeam, stringTeam);
            rush.checkForNewPlayer(listOpp, stringOpponent);
            
            if (rush.checkDatabase(listTeam) == false) {
            	rush.updateDatabase(listTeam, rush.getTeamName(stringTeam));
            } 
            if (rush.checkDatabase(listOpp) == false) {
            	rush.updateDatabase(listOpp, rush.getTeamName(stringOpponent));
            }*/
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Rush Stats button couldn't be clicked.");
		}
		
	}
	
	@FXML
	private void handleBack() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) mainScreenButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TeamSelector.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
	}
}
