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
	 * Check database and make sure the facts are correct
	 * @param list
	 * @return
	 */
	public boolean checkDatabaseRank(ArrayList<String> list, int i, String table, String val) {
		String temp = list.get(i);
		String query = "SELECT * FROM footballstats." + table + "rank WHERE " + val + " = " + temp + ";";
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
		if (teamName.equals("ARI")) {
			team = TeamName.ARI.getTeam();
		} else if (teamName.equals("ATL")) {
			team = TeamName.ATL.getTeam();
		} else if (teamName.equals("BAL")) {
			team = TeamName.BAL.getTeam();
		} else if (teamName.equals("BUF")) {
			team = TeamName.BUF.getTeam();
		} else if (teamName.equals("CAR")) {
			team = TeamName.CAR.getTeam();
		} else if (teamName.equals("CHI")) {
			team = TeamName.CHI.getTeam();
		} else if (teamName.equals("CIN")) {
			team = TeamName.CIN.getTeam();
		} else if (teamName.equals("CLE")) {
			team = TeamName.CLE.getTeam();
		} else if (teamName.equals("DAL")) {
			team = TeamName.DAL.getTeam();
		} else if (teamName.equals("DEN")) {
			team = TeamName.DEN.getTeam();
		} else if (teamName.equals("DET")) {
			team = TeamName.DET.getTeam();
		} else if (teamName.equals("GB")) {
			team = TeamName.GB.getTeam();
		} else if (teamName.equals("HOU")) {
			team = TeamName.HOU.getTeam();
		} else if (teamName.equals("IND")) {
			team = TeamName.IND.getTeam();
		} else if (teamName.equals("JAX")) {
			team = TeamName.JAX.getTeam();
		} else if (teamName.equals("KC")) {
			team = TeamName.KC.getTeam();
		} else if (teamName.equals("LA")) {
			team = TeamName.LA.getTeam();
		} else if (teamName.equals("MIA")) {
			team = TeamName.MIA.getTeam();
		} else if (teamName.equals("MIN")) {
			team = TeamName.MIN.getTeam();
		} else if (teamName.equals("NO")) {
			team = TeamName.NO.getTeam();
		} else if (teamName.equals("NYG")) {
			team = TeamName.NYG.getTeam();
		} else if (teamName.equals("NYJ")) {
			team = TeamName.NYJ.getTeam();
		} else if (teamName.equals("NWE")) {
			team = TeamName.NWE.getTeam();
		} else if (teamName.equals("OAK")) {
			team = TeamName.OAK.getTeam();
		} else if (teamName.equals("PHI")) {
			team = TeamName.PHI.getTeam();
		} else if (teamName.equals("PIT")) {
			team = TeamName.PIT.getTeam();
		} else if (teamName.equals("SD")) {
			team = TeamName.SD.getTeam();
		} else if (teamName.equals("SEA")) {
			team = TeamName.SEA.getTeam();
		} else if (teamName.equals("SF")) {
			team = TeamName.SF.getTeam();
		} else if (teamName.equals("TB")) {
			team = TeamName.TB.getTeam();
		} else if (teamName.equals("TEN")) {
			team = TeamName.TEN.getTeam();
		} else if (teamName.equals("WAS")) {
			team = TeamName.WAS.getTeam();
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
		} else if (teamName.equals("Ravens")) {
			team = "BAL";
		} else if (teamName.equals("Saints")) {
			team = "NO";
		} else if (teamName.equals("Buccaneers")) {
			team = "TB";
		} 
		return team;
	}
	


}
