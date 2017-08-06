package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbabilityQueries {
	private Model database = new Model();
	private final Connection connection = database.EstablishConnection();
	private static final Logger logger = Logger.getLogger("ProbabilityQueries.class");

	/**
	 * Gets the ranking of the offense/defense
	 * @param team
	 * @return
	 */
	public int getRank(String team, String table) {
		int rank = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats." + table
					+ "rank a JOIN footballstats." + table + "rank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '" + team
					+ "' GROUP BY a.Team;";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				rank = result.getInt("rank");
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get ranking.");
		}
		return rank;
	}
	
	/**
	 * Gets the ranking of the interceptions or stats
	 * @param team
	 * @return
	 */
	public int getRankSpecial(String team, String table, String indicator) {
		int rank = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT a." + indicator + ", a.Team, count(*) as rank FROM footballstats." + table
					+ "rank a JOIN footballstats." + table + "rank b ON a." + indicator + " <= b." + indicator
					+ " WHERE a.Team = '" + team + "' GROUP BY a.Team;";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				rank = result.getInt("rank");
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get ranking.");
		}
		return rank;
	}
	
	/**
	 * Gets the rank based on quick stat
	 * @param team
	 * @param stat
	 * @return
	 */
	public int getQuickStatRank(String team, String stat) {
		int rank = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT " + stat + " FROM footballstats.quickstats WHERE Team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				rank = result.getInt(stat);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get ranking.");
		}
		return rank;
	}
	/**
	 * Gets team stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> GetTeamStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.teamstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 2; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query = "SELECT * FROM footballstats.teamstats WHERE Team = '" + team + "';";
				ResultSet result = statement.executeQuery(query);
				if (i < 4 || (i > 5 && i < 13) || (i > 13 && i < 16) || (i > 16)) {
					while (result.next()) {
						int answer = result.getInt(name);
						double data = (double)answer;
						returnList.add(data);
					}
				} else {
					while (result.next()) {
						double data = result.getDouble(name);
						returnList.add(data);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get team stats.");
		}
		return returnList;
	}
	
	/**
	 * Gets qb stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> getQBStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.qbstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 4; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query = "SELECT * FROM footballstats.qbstats WHERE Team = '" + team + "';";
				ResultSet result = statement.executeQuery(query);
				if (i < 6 || (i > 7 && i < 9) || (i == 10) || (i > 11 && i < 15)) {
					while (result.next()) {
						int answer = result.getInt(name);
						double data = (double) answer;
						returnList.add(data);
					}
				} else {
					while (result.next()) {
						double data = result.getDouble(name);
						returnList.add(data);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get qb stats.");
		}
		return returnList;
	}
	
	/**
	 * Gets rush stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> getRushStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT Att, Yds, Yds_Att, Lng, TD FROM footballstats.rushstats;");
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int j = 1; j <= columnCount; j++) {
					String name = rsmd.getColumnName(j);
					String query = "SELECT Att, Yds, Yds_Att, Lng, TD FROM footballstats.rushstats WHERE Team = '" + team + "' ORDER BY Att DESC;";
					ResultSet result = statement.executeQuery(query);
					if (j != 3) {
						while (result.next()) {
							int answer = result.getInt(name);
							double data = (double)answer;
							returnList.add(data);
						}
					} else {
						while (result.next()) {
							double data = result.getDouble(name);
							returnList.add(data);
						}
					}
				}
				
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get rush stats.");
		}
		return returnList;
	}
	
	/**
	 * Gets receiving stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> getRecStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT Rec, Yds, Yds_Rec, Lng, TD FROM footballstats.pass_stats;");
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int j = 1; j <= columnCount; j++) {
					String name = rsmd.getColumnName(j);
					String query = "SELECT Rec, Yds, Yds_Rec, Lng, TD FROM footballstats.pass_stats WHERE Team = '" + team + "' ORDER BY Rec DESC;";
					ResultSet result = statement.executeQuery(query);
					if (j != 3) {
						while (result.next()) {
							int answer = result.getInt(name);
							double data = (double)answer;
							returnList.add(data);
						}
					} else {
						while (result.next()) {
							double data = result.getDouble(name);
							returnList.add(data);
						}
					}
				}
				
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get pass stats.");
		}
		return returnList;
	}
	
	/**
	 * Gets fg stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> getFGStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.fgstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 6; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query = "SELECT * FROM footballstats.fgstats WHERE Team = '" + team + "';";
				ResultSet result = statement.executeQuery(query);
				while (result.next()) {
					int answer = result.getInt(name);
					double data = (double) answer;
					returnList.add(data);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get fg stats.");
		}
		return returnList;
	}
	
	/**
	 * Gets def stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> getDefStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT Comb, Total, Assist, Sck, Fumb FROM footballstats.defstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int j = 1; j <= columnCount; j++) {
				String name = rsmd.getColumnName(j);
				String query = "SELECT Comb, Total, Assist, Sck, Fumb FROM footballstats.defstats WHERE Team = '" + team
						+ "' ORDER BY Comb DESC;";
				ResultSet result = statement.executeQuery(query);
				if (j != 4) {
					while (result.next()) {
						int answer = result.getInt(name);
						double data = (double) answer;
						returnList.add(data);
					}
				} else {
					while (result.next()) {
						double data = result.getDouble(name);
						returnList.add(data);
					}
				}
			}
				
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get def stats.");
		}
		return returnList;
	}
	
	/**
	 * Gets receiving stats
	 * @param team
	 * @return
	 */
	public ArrayList<Double> getIntStats(String team) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		try {
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT It, Yds, Yds_Int, Lng, TD FROM footballstats.intstats;");
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				for (int j = 1; j <= columnCount; j++) {
					String name = rsmd.getColumnName(j);
					String query = "SELECT It, Yds, Yds_Int, Lng, TD FROM footballstats.intstats WHERE Team = '" + team + "' ORDER BY It DESC;";
					ResultSet result = statement.executeQuery(query);
					if (j != 3) {
						while (result.next()) {
							int answer = result.getInt(name);
							double data = (double)answer;
							returnList.add(data);
						}
					} else {
						while (result.next()) {
							double data = result.getDouble(name);
							returnList.add(data);
						}
					}
				}
				
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get def stats.");
		}
		return returnList;
	}
	
	public boolean isQBInjured(String team) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT Player FROM footballstats.injuries WHERE Position = 'QB' AND Team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			if (!result.next()) {
				return false;
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get count.");
		}
		return true;
	}
	
	/**
	 * Finds how many important injuries there are
	 * @param team
	 * @return
	 */
	public int getInjuryCount(String team) {
		int count = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT Count(*) FROM footballstats.injuries WHERE Team = '" + team
					+ "' AND (GameStatus = 'RES' OR GameStatus = 'PUP' OR GameStatus = 'SUS' OR GameStatus = 'OUT');";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get count.");
		}
		return count;
	}
	
	public ArrayList<String> getImportantPlayers(ArrayList<String> arr, String team, String table, String indicator) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM footballstats." + table + " WHERE Team = '" + team + "' ORDER BY " + indicator
					+ " DESC LIMIT 2;";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String answer = result.getString("Player");
				arr.add(answer);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get important players.");
		}
		return arr;
	}
	
	/**
	 * Get top 2 defensive players
	 * @param arr
	 * @param team
	 * @return
	 */
	public ArrayList<String> getImportantDef(ArrayList<String> arr, String team) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM footballstats.defstats WHERE Team = '" + team + "' AND not (Player = '" + team
					+ " Total') ORDER BY Comb DESC LIMIT 2;";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String answer = result.getString("Player");
				arr.add(answer);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get important players.");
		}
		return arr;
	}
	
	/**
	 * Gets a very injured player
	 * @param player
	 * @return
	 */
	public boolean isPlayerInjured(String player) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM footballstats.injuries WHERE Player = '" + player
					+ "' AND (GameStatus = 'RES' OR GameStatus = 'PUP' OR GameStatus = 'SUS' OR GameStatus = 'OUT');";
			ResultSet result = statement.executeQuery(query);
			if (!result.next()) {
				return false;
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get injured.");
		}
		return true;
	}
	
	/**
	 * Gets a slightly injured player
	 * @param player
	 * @return
	 */
	public boolean isPlayerSlightly(String player) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM footballstats.injuries WHERE Player = '" + player
					+ "';";
			ResultSet result = statement.executeQuery(query);
			if (!result.next()) {
				return false;
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get injured.");
		}
		return true;
	}
	
	/**
	 * Counts the injuries for positions
	 * @param team
	 * @param position
	 * @return
	 */
	public int countPositionInjuries(String team, String position) {
		int count = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT Count(*) FROM footballstats.injuries WHERE Team = '" + team
					+ "' AND (" + position + ");";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get count.");
		}
		return count;
	}
	
	/**
	 * Gets the count
	 * @param team
	 * @param table
	 * @return
	 */
	public int getCount(String team, String table) {
		int count = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT Count(*) FROM footballstats." + table + " WHERE Team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get count.");
		}
		return count;
	}
	
	/**
	 * Gets the count
	 * @param team
	 * @param table
	 * @return
	 */
	public double getRecord(String team) {
		double record = 0;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT Record FROM footballstats.records WHERE Team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				record = result.getDouble(1);
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get record.");
		}
		return record;
	}
	
	/**
	 * Gets the outcomes for each week
	 * @param team
	 * @return
	 */
	public ArrayList<String> getOutcomes(String team) {
		ArrayList<String> arr = new ArrayList<String>();
		int count = getCount(team, "scores");
		try {
			Statement statement = connection.createStatement();
			for (int i = 1; i <= count + 1; i++) {
				String query = "SELECT Outcome FROM footballstats.scores WHERE Team = '" + team + "' AND Week = " + i +";";
				ResultSet result = statement.executeQuery(query);
				while (result.next()) {
					arr.add(result.getString(1));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get outcomes.");
		}
		return arr;
	}
	
	/**
	 * Gets the location of the game
	 * @param team
	 * @return
	 */
	public ArrayList<String> getScoreLocation(String team) {
		ArrayList<String> arr = new ArrayList<String>();
		int count = getCount(team, "scores");
		try {
			Statement statement = connection.createStatement();
			for (int i = 1; i <= count + 1; i++) {
				String query = "SELECT Score FROM footballstats.scores WHERE Team = '" + team + "' AND Week =" + i + ";";
				ResultSet result = statement.executeQuery(query);
				while (result.next()) {
					arr.add(result.getString(1));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get scores.");
		}
		return arr;
	}
	
	
}
