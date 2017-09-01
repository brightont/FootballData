package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.InjuryStat;
import Model.Model;
import Model.PassStat;

public class InjuriesTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private ArrayList<String> list = new ArrayList<String>();
    
    @Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();
		
		InjuryStat injuryStat = new InjuryStat("Testing", "Tester", "a", "a", "a");
		
		// player 1
		list.add("Tester");
		list.add("a");
		list.add("a");
		list.add("a");
    }
    
    @Test(timeout = TIMEOUT)
    public void CheckForNewPlayerTest() throws InterruptedException {	
    	list.add("Tester2");
		list.add("b");
		list.add("b");
		list.add("b");
		
		list.add("Tester3");
		list.add("c");
		list.add("c");
		list.add("c");
		
    	InjuryStat injury = new InjuryStat();
    	ArrayList<Integer> intList = injury.checkForNewInjury(list, "injuries");
    	if (intList.size() != 0) {
    		injury.ScrapeNewPlayer(list, intList, "Testing");
    	}

    	assertEquals("Tester2", getPlayer("Tester2"));
    	assertEquals("Tester3", getPlayer("Tester3"));
    	
    }
    
    @Test(timeout = TIMEOUT)
    public void removePlayerTest() throws InterruptedException {	
    	InjuryStat injuryStat = new InjuryStat("Testing", "Tester2", "a", "a", "a");
    	
    	InjuryStat injury = new InjuryStat();
    	boolean bool = injury.removeNewPlayer(list, "Testing");
    	
    	assertEquals(1, getCount("Testing"));
    	assertFalse(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void UpdateDatabase() throws InterruptedException {
    	InjuryStat injury = new InjuryStat();
    	
    	//set for integer
    	list.set(1, "b");
    	
    	injury.UpdateDatabase(list, "Testing");
    	
    	assertEquals("b", getValueString("Position"));
    }
    /**
     * Gets the player from a database
     * @param player
     * @return
     */
    public String getPlayer(String player) {
        String queryUser = "SELECT * FROM footballstats.injuries WHERE Player = '" + player + "';";
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
	 * Removes a player
	 * @param list
	 * @param team
	 */
	public int getCount(String team) {
		String query = "SELECT COUNT(Player) FROM footballstats.injuries WHERE Team = '" + team + "';";
		int answer = 0;
		Connection connection = database.EstablishConnection();
		try {
            Statement userStatement = connection.createStatement();
            ResultSet result = userStatement.executeQuery(query);
            while (result.next()) {
                answer = result.getInt("COUNT(PLAYER)");
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
	public String getValueString(String name) {
		String newResult = "";
		Connection connection = database.EstablishConnection();
		String queryUser = "SELECT " + name + " FROM footballstats.injuries WHERE Team = 'Testing';";
		try {
			Statement userStatement = connection.createStatement();
			ResultSet result = userStatement.executeQuery(queryUser);
			while (result.next()) {
				newResult = result.getString(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newResult;
	}
	
    @After
    public void delete() {
    	database.RemoveStat("Testing", "injuries");
    }
}
