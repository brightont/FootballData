package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Model;
import Model.QBStat;
import Model.RushStat;

public class RushStatsTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private ArrayList<String> list = new ArrayList<String>();
    
    @Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();
		
		RushStat rushStat = new RushStat("Testing", "Tester", 1, 1, 1, 1, 1);
		//list of stat names
		list.add("Stat1");
		list.add("Stat2");
		list.add("Stat3");
		list.add("Stat4");
		list.add("Stat5");
		list.add("Stat6");
		
		//player 1
		list.add("Tester");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		
		//player 3
		list.add("Tester2");
		list.add("2");
		list.add("2");
		list.add("2");
		list.add("2");
		list.add("2");
    }
    
    @Test(timeout = TIMEOUT)
    public void checkForNewPlayer() throws InterruptedException {		
    	RushStat rush = new RushStat();
		
    	int result = rush.checkForNewPlayer(list);
    	System.out.println(result);
    	if (result != 0) {
    		rush.scrapeNewPlayer(list, result, "Testing");
    	}
    }
    
    public String getPlayer(String player) {
        String queryUser = "SELECT * FROM footballstats.rushstats WHERE Player = '" + player + "';";
        String answer = "";
        Connection connection = database.establishConnection();
        try {
            Statement userStatement = connection.createStatement();
            ResultSet result = userStatement.executeQuery(queryUser);
            while (result.next()) {
                answer = result.getString("Player");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }
    
    @After
    public void delete() {
    	database.removeStat("Testing", "rushstats");
    }

}
