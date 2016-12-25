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
import Model.TeamStat;

public class TeamStatsTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
    
    private HashMap<String, String> hash = new HashMap<String, String>();
    
    @Before
	public void beforeTest(){
    	database = new Model();
		database.readDatabase();
    	
		TeamStat teamStat = new TeamStat("Testing", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		hash.put("3rd Down Conversions", "1");
		hash.put("Total Passing Yds", "1");
		hash.put("Passing", "1");
		hash.put("Sacks", "1");
		hash.put("Total Rushing Yds", "1");
		hash.put("Offense (Plays-Avg Yds)", "1");
		hash.put("Turnover Ratio", "1");
		hash.put("Time of Possession", "1");
		hash.put("4th Down Conversions", "1");
		hash.put("Total Offensive Yds", "1");
		hash.put("Total First Downs", "1");
		hash.put("1st Downs (Rush-Pass-By Penalty)", "1");
		hash.put("Field Goals", "1");
		hash.put("Touchdowns", "1");
		hash.put("(Rush-Pass-Ret-Def)", "1");
		hash.put("Rushing", "1");
	}
    
    @Test(timeout = TIMEOUT)
    public void checkDatabaseTest() throws InterruptedException {		
    	TeamStat team = new TeamStat();
    	
		boolean bool = team.checkDatabase(hash, "Sacks", "team");
		assertTrue(bool);
    }
    
    @Test(timeout = TIMEOUT)
    public void updateDatabaseTestNorm() throws InterruptedException {		
    	TeamStat team = new TeamStat();
    	
    	//regular hash map value
    	hash.put("Sacks", "2");
    	
    	//hash map value with parentheses 
    	hash.put("Offense (Plays-Avg Yds)", "2");
 
    	//hash map value with a space
    	hash.put("3rd Down Conversions", "3");
    	
    	//hash map value with a /
    	hash.put("4th Down Conversions", "2/4");
    	
    	//hash map value with a -
    	hash.put("Touchdowns", "3 - 2");
    	
    	//hash map value with a +
    	hash.put("Turnover Ratio", "+3");
    	
    	//hash map value with a :
    	hash.put("Time of Possession", "2:03");
    	
    	//hash map key with a " (" and a -
    	hash.put("1st Downs (Rush-Pass-By Penalty)", "2 - 10");
    	
    	hash.put("(Rush-Pass-Ret-Def)", "5 - 10");
    	
    	
    	team.updateDatabase(hash, "Testing");
    	
    	assertEquals("2", getValueInt("Sacks"));
    	assertEquals("2", getValueInt("Offense"));
    	assertEquals("3.0", getValueDouble("3rd_Down_Conversions"));
    	assertEquals("0.5", getValueDouble("4th_Down_Conversions"));
    	assertEquals("3", getValueInt("Touchdowns"));
    	assertEquals("3", getValueInt("Turnover_Ratio"));
    	assertEquals("2.03", getValueDouble("Time_of_Possession"));
    	assertEquals("2", getValueInt("1st_Downs"));
    	assertEquals("5", getValueInt("Rush_Pass_Ret_Def"));
    }
    
	/**
	 * Gets the value from the database
	 * @param name
	 * @param value
	 * @return
	 */
	public String getValueInt(String name) {
		String newResult = "";
		Connection connection = database.establishConnection();
		String queryUser = "SELECT " + name + " FROM footballstats.teamstats WHERE Team = 'Testing';";
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
		Connection connection = database.establishConnection();
		String queryUser = "SELECT " + name + " FROM footballstats.teamstats WHERE Team = 'Testing';";
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
    	database.removeTeamStat("Testing");
    }

}
