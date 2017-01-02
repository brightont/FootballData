package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		if (value.contains("-")) {
			value = value.replace("-", "to");
			value = value.replace(" ", "_");
		} 
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
	 * Check database and make sure the facts are correct
	 * @param list
	 * @return
	 */
	public boolean checkDatabaseList(ArrayList<String> list, int i, String table, String val) {
		String temp = list.get(i);
		System.out.println("This is the temp" + temp);
		String query = "SELECT * FROM footballstats." + table + "stats WHERE " + val + " = " + temp + ";";
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
	 * Checks to see if there's a player and returns the index
	 * @param list
	 * @return
	 */
	public ArrayList<Integer> checkForNewPlayer(ArrayList<String> list, String table) {
		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		int i;
		for (i = 6; i < list.size(); i++) {
			if ((i % 6) == 0) {
				if (list.get(i) != "TEAM TOTAL") {
					if ((getPlayer(list.get(i), table)) == "") {
						returnArray.add(i);
					} 
				}
			}
		}
		return returnArray;
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
	 * Gets the player
	 * @param player
	 * @return
	 */
    public String getPlayer(String player, String table) {
    	if (player.contains("'")) {
    		player = player.replace("'", "");
    	} else if (player.contains(".")) {
    		player = player.replace(".", "");
    	} else if (player.contains("-")) {
    		player = player.split("-")[0];
    	}
        String queryUser = "SELECT * FROM footballstats." + table + " WHERE Player = '" + player + "';";
        String answer = "";
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
	 * Removes the last item from list
	 * @param arr
	 */
	public ArrayList<String> removeLastItem(ArrayList<String> arr) {
		arr.remove(arr.size()-1);
		return arr;
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
	
	/**
	 * Gets the team name from enum
	 * @param teamName
	 */
	public String getTeamAbb(String teamName) {
		String team = "";
		if (teamName.equals("Falcons")) {
			team = "ATL";
		} else if (teamName.equals("Buccaneers")) {
			team = "TB";
		} else if (teamName.equals("Ravens")) {
			team = "BAL";
		}
		return team;
	}
	


}
