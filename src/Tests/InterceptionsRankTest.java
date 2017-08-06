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

import Model.IntRankStat;
import Model.Model;
import Model.SackRankStat;

public class InterceptionsRankTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
	ArrayList<String> list = new ArrayList<String>();
	
    @Before
	public void beforeTest(){
    	database = new Model();
		database.readDatabase();
		
		IntRankStat stat = new IntRankStat("Testing", 1);
		list.add("Testing");
		list.add("1");
    }
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {		
    	IntRankStat irs = new IntRankStat();
    	
    	boolean bool = irs.checkDatabaseList(list, 1 , "intrank", "Interceptions");
		assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void UpdateDatabase() throws InterruptedException {
    	IntRankStat irs = new IntRankStat();
    	
    	//set for integer
    	list.set(1, "2");
    	
    	irs.UpdateDatabase(list);
    	
    	assertEquals("2", getValueInt("Interceptions"));
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
   		String queryUser = "SELECT " + name + " FROM footballstats.intrank WHERE Team = 'Testing';";
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
    	database.removeStat("Testing", "intrank");
    }
}
