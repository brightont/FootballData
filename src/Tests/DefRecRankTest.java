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

import Model.DefRecRankStat;
import Model.Model;

public class DefRecRankTest {
	
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
	ArrayList<String> list = new ArrayList<String>();
	
    @Before
	public void beforeTest(){
    	database = new Model();
		database.readDatabase();
		
		DefRecRankStat stat = new DefRecRankStat("Testing", 1);
		list.add("Testing");
		list.add("1");
    }
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {		
    	DefRecRankStat drrs = new DefRecRankStat();
    	
    	boolean bool = drrs.checkDatabaseList(list, 1 , "defrecrank", "Yds_G");
		assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void updateDatabase() throws InterruptedException {
    	DefRecRankStat drrs = new DefRecRankStat();
    	
    	//set for integer
    	list.set(1, "2.3");
    	
    	drrs.updateDatabase(list);
    	
    	assertEquals("2.3", getValueDouble("Yds_G"));
    }
    
    /**
   	 * Gets the value from the database
   	 * @param name
   	 * @param value
   	 * @return
   	 */
   	public String getValueDouble(String name) {
   		String newResult = "";
   		Connection connection = database.establishConnection();
   		String queryUser = "SELECT " + name + " FROM footballstats.defrecrank WHERE Team = 'Testing';";
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
    	database.removeStat("Testing", "defrecrank");
    }
}
