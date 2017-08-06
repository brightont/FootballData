package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import MainApplication.MainApplication;
import Model.DefRecRankStat;
import Model.DefRushRankStat;
import Model.DefStat;
import Model.DefYardsRankStat;
import Model.FieldGoalStat;
import Model.InjuryStat;
import Model.IntRankStat;
import Model.IntStat;
import Model.PassStat;
import Model.QBStat;
import Model.QuickStats;
import Model.RecRankStat;
import Model.RushRankStat;
import Model.RushStat;
import Model.SackRankStat;
import Model.TeamName;
import Model.TeamStat;
import Model.YardsRankStat;
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
	private Button interceptions;
	@FXML
	private Button injuries;
	@FXML
	private Button scores;
	@FXML
	private Button ranking;
	
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
		
        //Gets the team names 
		String stringTeam = TeamSelectorController.stringTeam;
		String stringOpp = TeamSelectorController.stringOpponent;
		
		//Populate the two hash maps
        TeamStat team = new TeamStat();
        HashMap<String, String> hashTeam = team.GetTeamStats(stringTeam);
        HashMap<String, String> hashOpp = team.GetTeamStats(stringOpp); 
        
        String value1 = "Touchdowns";
        String value2 = "Total First Downs";
        
        hashTeam.values().remove(stringTeam);
        hashOpp.values().remove(stringOpp);   
        
        String teamName = TeamName.valueOf(stringTeam).getTeam();
        String teamOpp = TeamName.valueOf(stringOpp).getTeam();
        
        if (!team.CheckDatabase(hashTeam, value1, "team", teamName) && 
    		   !team.CheckDatabase(hashTeam, value2, "team", teamName)) {
        	team.UpdateDatabase(hashTeam, team.getTeamName(stringTeam));
        }  
            
        if (!team.CheckDatabase(hashOpp, value1, "team", teamOpp) && 
    		   !team.CheckDatabase(hashOpp, value2, "team", teamOpp)) {
        	team.UpdateDatabase(hashTeam, team.getTeamName(stringTeam));
        }     
        
        //set scene
        cm.SetScene(teamStats, "../view/StatView.fxml");
            
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
            
            //if (qb.CheckDatabase(hashQB, value, "qb") == false) {
            	qb.UpdateDatabase(hashQB, qb.getTeamName(stringTeam));
            //}
            //if (qb.CheckDatabase(hashOppQB, value, "qb") == false) {
            	qb.UpdateDatabase(hashOppQB, qb.getTeamName(stringOpponent));
            //}
            
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
        		rush.scrapeNewPlayer(listOpponent, intListOpponent, rush.getTeamName(stringOpponent));
        	}
        	
        	//update database if needed
			if (rush.checkDatabaseList(listTeam, 7, "rush", "Att") == false
					|| rush.checkDatabaseList(listTeam, 13, "rush", "Att") == false) {
				rush.UpdateDatabase(listTeam, rush.getTeamName(stringTeam));
			}
			
			if (rush.checkDatabaseList(listOpponent, 7, "rush", "Att") == false
					|| rush.checkDatabaseList(listOpponent, 13, "rush", "Att") == false) {
				rush.UpdateDatabase(listOpponent, rush.getTeamName(stringOpponent));
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
			if (pass.checkDatabaseList(listTeam, 7, "pass_", "Rec") == false
					|| pass.checkDatabaseList(listTeam, 13, "pass_", "Rec") == false) {
				pass.UpdateDatabase(listTeam, pass.getTeamName(stringTeam));
			}
			
			if (pass.checkDatabaseList(listOpponent, 7, "pass_", "Rec") == false
					|| pass.checkDatabaseList(listOpponent, 13, "pass_", "Rec") == false) {
				pass.UpdateDatabase(listOpponent, pass.getTeamName(stringOpponent));
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
            String value1 = "20-29 A";
			//if (fg.CheckDatabase(hashFG, value, "fg") == false || fg.CheckDatabase(hashFG, value1, "fg")) {
				fg.UpdateDatabase(hashFG, fg.getTeamName(stringTeam));
			//}
			//if (fg.CheckDatabase(hashOppFG, value, "fg") == false || fg.CheckDatabase(hashFG, value1, "fg")) {
				fg.UpdateDatabase(hashOppFG, fg.getTeamName(stringOpponent));
			//}
            
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
				def.UpdateDatabase(listTeam, def.getTeamName(stringTeam));
			}
			
			if (def.checkDatabaseList(listOpponent, 7, "def", "Comb") == false
					|| def.checkDatabaseList(listOpponent, 13, "def", "Comb") == false) {
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
            
            ArrayList<Integer> intListTeam = intStat.checkForNewPlayer(listTeam, "intstats");
            if (intListTeam.size() != 0) {
        		intStat.scrapeNewPlayer(listTeam, intListTeam, intStat.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = intStat.checkForNewPlayer(listOpponent, "intstats");
        	if (intListOpponent.size() != 0) {
        		intStat.scrapeNewPlayer(listOpponent, intListOpponent, intStat.getTeamName(stringOpponent));
        	}
            
        	//update database if needed
			if (intStat.checkDatabaseList(listTeam, 7, "int", "It") == false
					|| intStat.checkDatabaseList(listTeam, 13, "int", "It") == false) {
				intStat.UpdateDatabase(listTeam, intStat.getTeamName(stringTeam));
			}
			
			if (intStat.checkDatabaseList(listOpponent, 7, "int", "It") == false
					|| intStat.checkDatabaseList(listOpponent, 13, "int", "It") == false) {
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
        		injuryStat.scrapeNewPlayer(listTeam, intListTeam, injuryStat.getTeamName(stringTeam));
        	}
        	
        	ArrayList<Integer> intListOpponent = injuryStat.checkForNewInjury(listOpponent, "injuries");
        	if (intListOpponent.size() != 0) {
        		injuryStat.scrapeNewPlayer(listOpponent, intListOpponent, injuryStat.getTeamName(stringOpponent));
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
	private void selectRanking() throws Exception {
		try {
        	Stage stage;
            Parent root;

            stage = (Stage) ranking.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/RankView.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            TeamSelectorController tcc = new TeamSelectorController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
            
            //get the first team
            YardsRankStat yrStat = new YardsRankStat();
            ArrayList<String> listTeam = yrStat.getYardsStats();
            
            if (yrStat.checkDatabaseRank(listTeam, 1, "yards", "Pts_G") == false) {
            	yrStat.UpdateDatabase(listTeam);
            	
            	RushRankStat rrStat = new RushRankStat();
                ArrayList<String> listTeam1 = rrStat.getRushRankStat();
                
                rrStat.UpdateDatabase(listTeam1);
                
            	RecRankStat rcStat = new RecRankStat();
                ArrayList<String> listTeam2 = rcStat.getRecRankStat();
                
            	rcStat.UpdateDatabase(listTeam2);
            } 
            
            DefYardsRankStat dyrStat = new DefYardsRankStat();
            ArrayList<String> listTeam3 = dyrStat.getDefYardsStats();
            
            if (dyrStat.checkDatabaseRank(listTeam3, 1, "defyards", "Pts_G") == false) {
            	dyrStat.UpdateDatabase(listTeam3);
            	
            	DefRushRankStat drrStat = new DefRushRankStat();
                ArrayList<String> listTeam4 = drrStat.getDefRushStats();
                
                drrStat.UpdateDatabase(listTeam4);
                
                DefRecRankStat drcStat = new DefRecRankStat();
                ArrayList<String> listTeam5 = drcStat.getDefRecStats();
                
                drcStat.UpdateDatabase(listTeam5);
            }
           
			SackRankStat srStat = new SackRankStat();
			ArrayList<String> listTeam6 = srStat.getSackStats();

			if (srStat.checkDatabaseRank(listTeam6, 1, "sack", "Sacks") == false) {
				srStat.UpdateDatabase(listTeam6);
				
				IntRankStat irStat = new IntRankStat();
	            ArrayList<String> listTeam7 = irStat.getIntRankStats();
	            
	            irStat.UpdateDatabase(listTeam7);
			}
           
            QuickStats qStat = new QuickStats();
            ArrayList<String> listTeam8 = qStat.getQuickStats(stringTeam);
            ArrayList<String> listOpponent = qStat.getQuickStats(stringOpponent);
         
            if (qStat.checkDatabaseList(listTeam8, 1, "quick", "Pts") == false) {
            	qStat.UpdateDatabase(listTeam8, qStat.getTeamName(stringTeam));
            } 
            
            if (qStat.checkDatabaseList(listOpponent, 1, "quick", "Pts") == false) {
            	qStat.UpdateDatabase(listOpponent, qStat.getTeamName(stringOpponent));
            } 
            
		} catch (IOException e) {
			logger.log(Level.FINE, "Ranks couldn't be loaded.");
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
