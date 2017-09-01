package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Model;
import Model.ScoreStat;

public class ScoresTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private ArrayList<String> list = new ArrayList<String>();
    
    @Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();
		
		ScoreStat scoreStat = new ScoreStat("Testing", 1, "1", "1", "1");
		
		// game 1
		list.add("1");
		list.add("a_5_@_testing_16");
    }
    
    @Test(timeout = TIMEOUT)
    public void checkForNewScore() throws InterruptedException {
    	ScoreStat score = new ScoreStat();
    	
		// player 1
		list.add("2");
		list.add("2");
		
    	boolean bool = score.checkNewGame(list, "Testing");
    	assertFalse(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void removeScore() throws InterruptedException {
    	ScoreStat score = new ScoreStat();
    	
    	String dog = "a_b_@_c_d";
    	
		// player 1
		list.add("2");
		list.add("testing_13_@_c_1");
		
		score.addGame(list, "testing", "testing");
    	assertEquals(2,getTotalGames(list, "testing"));
    
    }
    
    public int getTotalGames(ArrayList<String> list, String team) {
    	Connection connection = database.EstablishConnection();
    	
		String query = "SELECT COUNT(Week) FROM footballstats.scores WHERE Team = '" + team + "';";
		int answer = 0;
		try {
            Statement userStatement = connection.createStatement();
            ResultSet result = userStatement.executeQuery(query);
            while (result.next()) {
                answer = result.getInt("COUNT(Week)");
            }
        } catch (SQLException e) {
            
        }
		return answer;
	}
    
    
    @After
    public void delete() {
    	database.RemoveStat("Testing", "scores");
    }
}
