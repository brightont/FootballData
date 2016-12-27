package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import MainApplication.MainApplication;
import Model.DefStat;
import Model.FieldGoalStat;
import Model.PassStat;
import Model.QBStat;
import Model.RushStat;
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
	private Button passStats;
	
	@FXML
	private Button fieldGoals;
	
	@FXML
	private Button defStats;
	
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
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
            
            RushStat rush = new RushStat();
            ArrayList<String> listTeam = rush.getRushStats(stringTeam);
            ArrayList<String> listOpponent = rush.getRushStats(stringOpponent);
            
            //add a new player if a new player is added
            ArrayList<Integer> intListTeam = rush.checkForNewPlayer(listTeam, "rushstats");
            if (intListTeam.size() != 0) {
        		rush.scrapeNewPlayer(listTeam, intListTeam, rush.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = rush.checkForNewPlayer(listOpponent, "rushstats");
        	if (intListOpponent.size() != 0) {
        		rush.scrapeNewPlayer(listOpponent, intListOpponent, rush.getTeamName(stringTeam));
        	}
        	
        	//update database if needed
			if (rush.checkDatabaseList(listTeam, 7, "rush", "Att") == false
					|| rush.checkDatabaseList(listTeam, 13, "rush", "Att") == false) {
				rush.updateDatabase(listTeam, rush.getTeamName(stringTeam));
			}
			
			if (rush.checkDatabaseList(listOpponent, 7, "rush", "Att") == false
					|| rush.checkDatabaseList(listOpponent, 13, "rush", "Att") == false) {
				rush.updateDatabase(listOpponent, rush.getTeamName(stringOpponent));
			}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Rush Stats couldn't be loaded.");
		}
		
	}
	
	/**
	 * View the pass stats
	 */
	@FXML
	private void selectPassStats() {
		try {
			Stage stage;
            Parent root;

            stage = (Stage) passStats.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/PassStatView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
			
            PassStat pass = new PassStat();
            ArrayList<String> listTeam = pass.getPassStats(stringTeam);
            ArrayList<String> listOpponent = pass.getPassStats(stringOpponent);
            
            //add a new player if a new player is added
            ArrayList<Integer> intListTeam = pass.checkForNewPlayer(listTeam, "pass_stats");
            if (intListTeam.size() != 0) {
        		pass.scrapeNewPlayer(listTeam, intListTeam, pass.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = pass.checkForNewPlayer(listOpponent, "pass_stats");
        	if (intListOpponent.size() != 0) {
        		pass.scrapeNewPlayer(listOpponent, intListOpponent, pass.getTeamName(stringOpponent));
        	}
        	
        	//update database if needed
			if (pass.checkDatabaseList(listTeam, 7, "pass_", "Att") == false
					|| pass.checkDatabaseList(listTeam, 13, "pass_", "Att") == false) {
				pass.updateDatabase(listTeam, pass.getTeamName(stringTeam));
			}
			
			if (pass.checkDatabaseList(listOpponent, 7, "pass_", "Att") == false
					|| pass.checkDatabaseList(listOpponent, 13, "pass_", "Att") == false) {
				pass.updateDatabase(listOpponent, pass.getTeamName(stringOpponent));
			}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Pass Stats couldn't be loaded.");
		}
		
	}
	
	/**
	 * View Field Goals
	 */
	@FXML
	private void selectFieldGoals() {
		try {
			Stage stage;
            Parent root;

            stage = (Stage) fieldGoals.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/FieldGoalView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
            
            FieldGoalStat fg = new FieldGoalStat();
            HashMap<String, String> hashFG = fg.getFGStats(stringTeam);
            HashMap<String, String> hashOppFG = fg.getFGStats(stringOpponent);
            
            String value = "30-39 M";
        	if (fg.checkDatabase(hashFG, value, "fg") == false) {
        		fg.updateDatabase(hashFG, fg.getTeamName(stringTeam));
        	}
			if (fg.checkDatabase(hashOppFG, value, "fg") == false) {
				fg.updateDatabase(hashFG, fg.getTeamName(stringOpponent));
			}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Field Goal Stats couldn't be loaded.");
		}
		
	}
	
	/**
	 * View Def Stats
	 */
	@FXML
	private void selectDefStats() {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) defStats.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/DefStatView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
			
            DefStat def = new DefStat();
            ArrayList<String> listTeam = def.getDefStats(stringTeam);
            ArrayList<String> listOpponent = def.getDefStats(stringOpponent);
            
            listTeam = def.removeLastItem(listTeam);
            listOpponent = def.removeLastItem(listOpponent);
           
            ArrayList<Integer> intListTeam = def.checkForNewPlayer(listTeam, "defstats");
            if (intListTeam.size() != 0) {
        		def.scrapeNewPlayer(listTeam, intListTeam, def.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = def.checkForNewPlayer(listOpponent, "defstats");
        	if (intListOpponent.size() != 0) {
        		def.scrapeNewPlayer(listOpponent, intListOpponent, def.getTeamName(stringOpponent));
        	}
        	
        	//update database if needed
			if (def.checkDatabaseList(listTeam, 7, "def", "Comb") == false
					|| def.checkDatabaseList(listTeam, 13, "def", "Comb") == false) {
				def.updateDatabase(listTeam, def.getTeamName(stringTeam));
			}
			
			if (def.checkDatabaseList(listOpponent, 7, "def", "Comb") == false
					|| def.checkDatabaseList(listOpponent, 13, "def", "Comb") == false) {
				def.updateDatabase(listOpponent, def.getTeamName(stringOpponent));
			}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Defense Stats couldn't be loaded.");
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
