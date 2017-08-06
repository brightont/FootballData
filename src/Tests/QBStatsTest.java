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

import Model.Model;
import Model.QBStat;

public class QBStatsTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private HashMap<String, String> hash = new HashMap<String, String>();
    
    @Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();

    	QBStat qbs = new QBStat("Testing", "Tester", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    	hash.put("Player", "Tester");
    	hash.put("Att", "1");
    	hash.put("Comp", "1");
    	hash.put("Yds", "1");
    	hash.put("Comp %", "1");
    	hash.put("Yds/Att", "1");
    	hash.put("TD", "1");
    	hash.put("TD %", "1");
    	hash.put("INT", "1");
    	hash.put("INT %", "1");
    	hash.put("Long", "1");
    	hash.put("Sack", "1");
    	hash.put("Sack/Lost", "1");
    	hash.put("Rating", "1");
    }
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {		
    	QBStat qb = new QBStat();
    	
		//boolean bool = qb.CheckDatabase(hash, "Att", "qb");
		//assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void updateDatabaseTest() throws InterruptedException {		
    	QBStat qb = new QBStat();
    	
    	//regular hash map value
    	hash.put("Att", "2");
    	
    	//if value contains a " %"
    	hash.put("Comp %", "22.3");
    	
    	//if a value contains a /
    	hash.put("Yds/Att", "2.3");
    	
    	//if a value contains a / (integer)
    	hash.put("Sack/Lost", "3");
    	
    	//if a value is a rating
    	hash.put("Rating", "3.1");
    	
    	qb.UpdateDatabase(hash, "Testing");
    	
    	assertEquals("2", getValueInt("Att"));
    	assertEquals("22.3", getValueDouble("Comp_P"));
    	assertEquals("2.3", getValueDouble("Yds_Att"));
    	assertEquals("3", getValueInt("Sack_Lost"));
    	assertEquals("3.1", getValueDouble("Rating"));
    	
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
		String queryUser = "SELECT " + name + " FROM footballstats.qbstats WHERE Team = 'Testing';";
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
		String queryUser = "SELECT " + name + " FROM footballstats.qbstats WHERE Team = 'Testing';";
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
    	database.removeStat("Testing", "qbstats");
    }
}
