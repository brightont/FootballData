package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import MainApplication.MainApplication;
import Model.DefStat;
import Model.InjuryStat;
import Model.IntStat;
import Model.PassStat;
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
	private Button playerStats;
	@FXML
	private Button passStats;
	@FXML
	private Button defStats;
	@FXML 
	private Button interceptions;
	@FXML
	private Button injuries;
	@FXML
	private Button scores;
	
	@FXML
	private Button probability;
	
	@FXML
	private Button mainScreenButton;
	
	ControllerMethods cm = new ControllerMethods();
	
	/**
	 * Sets the main application
	 * @param main
	 */
	public void SetMainApp(MainApplication main) {
        mainApplication = main;
    }
	
	/**
	 * Select team statistics
	 */
	@FXML
	public void SelectTeamStats() {
        cm.SetScene(teamStats, "../view/StatView.fxml");
	}
	
	/**
	 * View the rush stats
	 */
	@FXML
	private void SelectPlayerStats() {
		cm.SetScene(playerStats, "../view/PlayerStatView.fxml");
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
            ArrayList<Integer> intListTeam = pass.CheckForNewPlayer(listTeam, "pass_stats");
            if (intListTeam.size() != 0) {
        		pass.ScrapeNewPlayer(listTeam, intListTeam, pass.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = pass.CheckForNewPlayer(listOpponent, "pass_stats");
        	if (intListOpponent.size() != 0) {
        		pass.ScrapeNewPlayer(listOpponent, intListOpponent, pass.getTeamName(stringOpponent));
        	}
        	
        	//update database if needed
			if (pass.CheckDatabaseList(listTeam, 7, "pass_", "Rec") == false
					|| pass.CheckDatabaseList(listTeam, 13, "pass_", "Rec") == false) {
				pass.UpdateDatabase(listTeam, pass.getTeamName(stringTeam));
			}
			
			if (pass.CheckDatabaseList(listOpponent, 7, "pass_", "Rec") == false
					|| pass.CheckDatabaseList(listOpponent, 13, "pass_", "Rec") == false) {
				pass.UpdateDatabase(listOpponent, pass.getTeamName(stringOpponent));
			}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Pass Stats couldn't be loaded.");
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
           
            ArrayList<Integer> intListTeam = def.CheckForNewPlayer(listTeam, "defstats");
            if (intListTeam.size() != 0) {
        		def.ScrapeNewPlayer(listTeam, intListTeam, def.getTeamName(stringTeam));
        	}
        	ArrayList<Integer> intListOpponent = def.CheckForNewPlayer(listOpponent, "defstats");
        	if (intListOpponent.size() != 0) {
        		def.ScrapeNewPlayer(listOpponent, intListOpponent, def.getTeamName(stringOpponent));
        	}
     
        	//update database if needed
			if (def.CheckDatabaseList(listTeam, 7, "def", "Comb") == false
					|| def.CheckDatabaseList(listTeam, 13, "def", "Comb") == false) {
				def.UpdateDatabase(listTeam, def.getTeamName(stringTeam));
			}
			
			if (def.CheckDatabaseList(listOpponent, 7, "def", "Comb") == false
					|| def.CheckDatabaseList(listOpponent, 13, "def", "Comb") == false) {
				def.UpdateDatabase(listOpponent, def.getTeamName(stringOpponent));
			}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Defense Stats couldn't be loaded.");
		}
	}
	
	/**
	 * View Interceptions Stats
	 */
	@FXML
	private void selectInterceptions() {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) interceptions.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/InterceptionsView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
			
            IntStat intStat = new IntStat();
            ArrayList<String> listTeam = intStat.getIntStats(stringTeam);
            ArrayList<String> listOpponent = intStat.getIntStats(stringOpponent);
            
            ArrayList<Integer> intListTeam = intStat.CheckForNewPlayer(listTeam, "intstats");
            if (intListTeam.size() != 0) {
        		intStat.ScrapeNewPlayer(listTeam, intListTeam, intStat.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = intStat.CheckForNewPlayer(listOpponent, "intstats");
        	if (intListOpponent.size() != 0) {
        		intStat.ScrapeNewPlayer(listOpponent, intListOpponent, intStat.getTeamName(stringOpponent));
        	}
            
        	//update database if needed
			if (intStat.CheckDatabaseList(listTeam, 7, "int", "It") == false
					|| intStat.CheckDatabaseList(listTeam, 13, "int", "It") == false) {
				intStat.UpdateDatabase(listTeam, intStat.getTeamName(stringTeam));
			}
			
			if (intStat.CheckDatabaseList(listOpponent, 7, "int", "It") == false
					|| intStat.CheckDatabaseList(listOpponent, 13, "int", "It") == false) {
				intStat.UpdateDatabase(listOpponent, intStat.getTeamName(stringOpponent));
			}
		} catch (IOException e) {
			logger.log(Level.FINE, "Interception Stats couldn't be loaded.");
		}
	}
	
	/**
	 * Select Injuries
	 */
	@FXML
	private void selectInjuries() {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) injuries.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/InjuriesView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
            
            InjuryStat injuryStat = new InjuryStat();
            ArrayList<String> listTeam = injuryStat.getAllInjuries(stringTeam);
            ArrayList<String> listOpponent = injuryStat.getAllInjuries(stringOpponent);
            
            ArrayList<Integer> intListTeam = injuryStat.checkForNewInjury(listTeam, "injuries");
            
         
            if (intListTeam.size() != 0) {
        		injuryStat.ScrapeNewPlayer(listTeam, intListTeam, injuryStat.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = injuryStat.checkForNewInjury(listOpponent, "injuries");
        	if (intListOpponent.size() != 0) {
        		injuryStat.ScrapeNewPlayer(listOpponent, intListOpponent, injuryStat.getTeamName(stringOpponent));
        	}
        	
        	if (injuryStat.removeNewPlayer(listTeam, stringTeam) == false) {
        		injuryStat.UpdateDatabase(listTeam, injuryStat.getTeamName(stringTeam));
        	}
        	if (injuryStat.removeNewPlayer(listOpponent, stringOpponent) == false) {
        		injuryStat.UpdateDatabase(listOpponent, injuryStat.getTeamName(stringOpponent));
        	}
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Injuries couldn't be loaded.");
		}
	}
	
	/**
	 * Selects scores
	 */
	@FXML
	private void selectScores() {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) scores.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/ScoresView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            
            //ScoreStat scoreStat = new ScoreStat();
           //ArrayList<String> listTeam = scoreStat.getScoreStats(stringTeam);
            
            //TODO: WIll Fix this function later
            /*if (scoreStat.checkNewGame(listTeam, scoreStat.getTeamName(stringTeam)) == false) {
            	scoreStat.addGame(listTeam, stringTeam, scoreStat.getTeamName(stringTeam));
            }*/
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Scores couldn't be loaded.");
		}
	}
	
	@FXML
	private void selectProb() throws Exception {
		Stage stage;
		Parent root;
		
		stage = (Stage) probability.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProbabilityView.fxml"));
		root = loader.load();
		
		Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        
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
