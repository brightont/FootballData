package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("Stat.class");
	private final Connection connection = database.establishConnection();
	private String team;

	public Stat() {
		
	}
	
	public Stat(String team) {
		this.team = team;
	}

	/**
	 * Check database and make sure the facts are correct
	 */
	public boolean checkDatabase(HashMap<String, String> hash, String value, String table) {
		String hashValue = hash.get(value);
		int intVal = Integer.parseInt(hashValue);
		String query = "SELECT * FROM footballstats." + table + "stats WHERE " + value + " = " + intVal + ";";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			boolean bool = result.next();
			if (bool == false) {
				return false;
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not check the database");
		}
		return true;
		
	}
	
	/**
	 * Updates the database with a double
	 * @param key
	 * @param value
	 * @param team
	 */
	public void updateQueryDouble(String key, double value, String team, String table) {
		String update = "UPDATE footballstats." + table + " SET " + key + " = " + value + " WHERE Team = '" + team + "';";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(update);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.FINE, "Cannot update query (double).");
		}
	}

	/**
	 * Updates the database with an int
	 * @param key
	 * @param value
	 * @param team
	 */
	public void updateQueryInt(String key, int value, String team, String table) {
		String update = "UPDATE footballstats." + table + " SET " + key + " = " + value + " WHERE Team = '" + team + "';";
		//System.out.println(update);
		try {
			PreparedStatement prepStatement = connection.prepareStatement(update);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.FINE, "Cannot update query (int).");
		}
	}
	
	/**
	 * Updates the database with a String
	 * @param key
	 * @param value
	 * @param team
	 */
	public void updateQueryString(String key, String value, String team, String table) {
		String update = "UPDATE footballstats." + table + " SET " + key + " = " + value + " WHERE Team = '" + team + "';";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(update);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.FINE, "Cannot update query (String).");
		}
	}
	

	
	/**
	 * Gets the team name from enum
	 * @param teamName
	 */
	public String getTeamName(String teamName) {
		String team = "";
		if (teamName.equals("ATL")) {
			team = TeamName.ATL.getTeam();
		} else if (teamName.equals("TB")) {
			team = TeamName.TB.getTeam();
		} else if (teamName.equals("BAL")) {
			team = TeamName.BAL.getTeam();
		}
		return team;
	}

}
