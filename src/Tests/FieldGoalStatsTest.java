package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.FieldGoalStat;
import Model.Model;
import Model.QBStat;

public class FieldGoalStatsTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private HashMap<String, String> hash = new HashMap<String, String>();
    
    
    @Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();

    	FieldGoalStat fgs = new FieldGoalStat("Testing", "Tester", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    	hash.put("Player", "Tester");
    	hash.put("1-19 A", "1");
    	hash.put("1-19 M", "1");
    	hash.put("20-29 A", "1");
    	hash.put("20-29 M", "1");
    	hash.put("30-39 A", "1");
    	hash.put("30-39 M", "1");
    	hash.put("40-49 A", "1");
    	hash.put("40-49 M", "1");
    	hash.put("50+ A", "1");
    	hash.put("50+ M", "1");
    }
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {	
    	FieldGoalStat fieldGoal = new FieldGoalStat();
    	
    	//boolean bool = fieldGoal.CheckDatabase(hash, "1-19 A", "fg");
		//assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void updateDatabaseTest() throws InterruptedException {	
    	FieldGoalStat fieldGoal = new FieldGoalStat();
    	
    	//normal hash
    	hash.put("1-19 A", "2");
    	
    	//hash with a plus
    	hash.put("50+ A", "2");
    	
    	fieldGoal.UpdateDatabase(hash, "Testing");
    	
    	assertEquals("2", getValueInt("1to19_A"));
    	assertEquals("2", getValueInt("50plus_A"));
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
   		String queryUser = "SELECT " + name + " FROM footballstats.fgstats WHERE Team = 'Testing';";
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
   	
    
    @After
    public void delete() {
    	database.RemoveStat("Testing", "fgstats");
    }

}
