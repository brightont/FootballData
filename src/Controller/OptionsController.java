package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Model;
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
			logger.log(Level.FINE, "Team Stats button failed to click");
			e.printStackTrace();
		}
	}
}
