package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.QuarterBack;
import Model.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OptionsController {
	
	@FXML
	private Button teamStats;
	
	@FXML
	private Button qbStats;
	
	private static final Logger logger = Logger.getLogger("OptionsController");
	
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
  
            TeamChooserController tcc = new TeamChooserController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
                        
            Team team = new Team();
            HashMap<String, String> hashTeam = team.getTeamStats(stringTeam);
            HashMap<String, String> hashOpponent = team.getTeamStats(stringOpponent);
            
            if (team.checkDatabase(hashTeam) == false) {
            	team.updateDatabase(hashTeam, team.getTeamName(stringTeam));
            }  
            if (team.checkDatabase(hashOpponent) == false) {
            	team.updateDatabase(hashOpponent, team.getTeamName(stringTeam));
            }
		} catch (IOException e) {
			logger.log(Level.FINE, "Team Stats button couldn't be clicked.");
		}
	}
	
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
            
            TeamChooserController tcc = new TeamChooserController();
            String stringTeam = tcc.getStringTeam();
            String stringOpponent = tcc.getStringOpponent();
                        
            QuarterBack qb = new QuarterBack();
            HashMap<String, String> hashQB = qb.getQBStats(stringTeam);
            HashMap<String, String> hashOppQB = qb.getQBStats(stringOpponent);
            
            if (qb.checkDatabase(hashQB) == false) {
            	qb.updateDatabase(hashQB, qb.getTeamName(stringTeam));
            }
            if (qb.checkDatabase(hashOppQB) == false) {
            	qb.updateDatabase(hashQB, qb.getTeamName(stringOpponent));
            }
		} catch (IOException e) {
			logger.log(Level.FINE, "QB Stats button couldn't be clicked.");
		}
		
	}
}
