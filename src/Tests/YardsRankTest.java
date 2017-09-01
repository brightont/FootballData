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
import Model.YardsRankStat;

public class YardsRankTest {

	private static final int TIMEOUT = 10000;
	private Model database = new Model();
	ArrayList<String> list = new ArrayList<String>();
	
    @Before
	public void beforeTest(){
    	database = new Model();
		database.readDatabase();
		
		YardsRankStat stat = new YardsRankStat("Testing", 1, 1, 1, 1, 1);
		list.add("Testing");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		
    }
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {		
    	YardsRankStat yrs = new YardsRankStat();
    	
    	boolean bool = yrs.CheckDatabaseList(list, 1 , "yardsrank", "Pts_G");
		assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void UpdateDatabase() throws InterruptedException {
    	YardsRankStat yrs = new YardsRankStat();
    	
    	//set for integer
    	list.set(1, "2.3");
    
    	//set for double
    	list.set(2, "2");
    	
    	yrs.UpdateDatabase(list);
    	
    	assertEquals("2.3", getValueDouble("Pts_G"));
    	assertEquals("2", getValueInt("TotPts"));
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
		String queryUser = "SELECT " + name + " FROM footballstats.yardsrank WHERE Team = 'Testing';";
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
		String queryUser = "SELECT " + name + " FROM footballstats.yardsrank WHERE Team = 'Testing';";
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
    	database.RemoveStat("Testing", "yardsrank");
    }
}
