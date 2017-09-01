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
import Model.PassStat;

public class PassStatsTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private ArrayList<String> list = new ArrayList<String>();
    
    @Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();
		
		PassStat passStat = new PassStat("Testing", "Tester", 1, 1, 1, 1, 1);
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
	
    }
    
    @Test(timeout = TIMEOUT)
    public void CheckForNewPlayer() throws InterruptedException {
		// player 2
		list.add("Tester2");
		list.add("2");
		list.add("2");
		list.add("2");
		list.add("2");
		list.add("2");

		// player 3
		list.add("Tester3");
		list.add("3");
		list.add("3");
		list.add("3");
		list.add("3");
		list.add("3");
		
		PassStat pass = new PassStat();
		
    	ArrayList<Integer> intList = pass.CheckForNewPlayer(list, "pass_stats");
    	if (intList.size() != 0) {
    		pass.ScrapeNewPlayer(list, intList, "Testing");
    	}
    	assertEquals("Tester2", getPlayer("Tester2"));
    	assertEquals("Tester3", getPlayer("Tester3"));
    }
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {		
    	PassStat pass = new PassStat();
    	boolean bool = pass.CheckDatabaseList(list, 7 , "pass_", "Rec");
    	
		assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void UpdateDatabase() throws InterruptedException {
    	PassStat pass = new PassStat();
    	
    	//set for integer
    	list.set(7, "2");
    
    	//set for double
    	list.set(9, "2.3");
    	
    	pass.UpdateDatabase(list, "Testing");
    	
    	assertEquals("2", getValueInt("Rec"));
    	assertEquals("2.3", getValueDouble("Yds_Rec"));
    }
    
    /**
     * Gets the player from a database
     * @param player
     * @return
     */
    public String getPlayer(String player) {
        String queryUser = "SELECT * FROM footballstats.pass_stats WHERE Player = '" + player + "';";
        String answer = "";
        Connection connection = database.EstablishConnection();
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
    
    /**
	 * Gets the value from the database
	 * @param name
	 * @param value
	 * @return
	 */
	public String getValueInt(String name) {
		String newResult = "";
		Connection connection = database.EstablishConnection();
		String queryUser = "SELECT " + name + " FROM footballstats.pass_stats WHERE Team = 'Testing';";
		try {
			Statement userStatement = connection.createStatement();
			ResultSet result = userStatement.executeQuery(queryUser);
			while (result.next()) {
				int newResultInt = result.getInt(name);
				newResult = ((Integer)newResultInt).toString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newResult;
	}
	
	/**
	 * Gets the value from the database
	 * @param name
	 * @param value
	 * @return
	 */
	public String getValueDouble(String name) {
		String newResult = "";
		Connection connection = database.EstablishConnection();
		String queryUser = "SELECT " + name + " FROM footballstats.pass_stats WHERE Team = 'Testing';";
		try {
			Statement userStatement = connection.createStatement();
			ResultSet result = userStatement.executeQuery(queryUser);
			while (result.next()) {
				double newResultDouble = result.getDouble(name);
				newResult = String.valueOf(newResultDouble);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newResult;
	}
    
    @After
    public void delete() {
    	database.RemoveStat("Testing", "pass_stats");
    }

}
