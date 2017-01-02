package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

	private static final Model instance = new Model();
	private static final Logger logger = Logger.getLogger("Model.class");
	
	private Connection connection;

	/**
	 * Gets a singleton of the model
	 * @return the model singleton
	 */
	public static Model getInstance() {
		return instance;
	}

	/**
	 * Default model constructor
	 */
	public Model() {
		// default
	}

	/**
	 * Database methods to establish connection and read database
	 * @return the connection
	 */
	public Connection establishConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballstats", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
     * Tries to connect to the database, let's the user
     * know if the connection has been established by
     * printing to the console
     */
    public void readDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            establishConnection();
            if (connection != null) {
                System.out.println("Database accessed.");
            } else {
                System.out.println("Database cannot be accessed.");
            }
        } catch (ClassNotFoundException e) {
        	logger.log(Level.FINE, "Read database didn't work");
        }
    }
    
    /**
     * Gets the name of the stats
     * @param team
     * @return
     */
    public ArrayList<String> getStatsName() {
    	ArrayList<String> statsName = new ArrayList<>();
    	String newName = "";
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.teamstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				if (name.contains("_")) {
					newName = name.replace("_", " ");
					statsName.add(newName);
				} else {
					statsName.add(name);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get the stats name.");
		}
    	return statsName;
    }
    
    /**
     * Gets the team stats and displays them
     * @param team
     * @return 
     */
    public ArrayList<String> getTeamStats(String team) {
    	ArrayList<String> stats = new ArrayList<>();
    	try {
    		stats.add(team);
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.teamstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 2; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query2 = "SELECT * FROM footballstats.teamstats WHERE Team = '" + team + "';";
				ResultSet result = statement.executeQuery(query2);
				while (result.next()) {
					double data = result.getDouble(name);
					String dataString = Double.toString(data);
					stats.add(dataString);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get team stat values.");
		}
    	return stats;
    }

    /**
     * Gets the name of the stats
     * @param team
     * @return
     */
    public ArrayList<String> getQBStatsName(String team) {
    	ArrayList<String> qbStatsName = new ArrayList<String>();
    	String newName = "";
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.qbstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				if (name.contains("_")) {
					newName = name.replace("_", " ");
					qbStatsName.add(newName);
				} else {
					qbStatsName.add(name);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get the qb stats name.");
		}
    	return qbStatsName;
    }
    
    /**
     * Gets the qb stats and displays them
     * @param team
     * @return 
     */
    public ArrayList<String> getQBStats(String team) {
    	ArrayList<String> qbStats = new ArrayList<>();
    	try {
    		qbStats.add(team);
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.qbstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 2; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query2 = "SELECT * FROM footballstats.qbstats WHERE team = '" + team + "';";
				ResultSet result = statement.executeQuery(query2);
				if (i == 2) {
					while (result.next()) {
						String player = result.getString(name);
						qbStats.add(player);
					}
				} else {
					while (result.next()) {
						double data = result.getDouble(name);
						String dataString = Double.toString(data);
						qbStats.add(dataString);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get qb stat values.");
		}
    	return qbStats;
    }
    
    public void removeStat(String team, String table) {
    	String query = "DELETE FROM footballstats." + table + " WHERE Team = '" + team + "';";
    	try {
            Statement userStatement = connection.createStatement();
            userStatement.execute(query);
        } catch (SQLException e) {
            logger.log(Level.FINE, "Could not delete team stat.");
        }
    }
    
    /**
     * Gets the rush stat
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getRushStat(String team, int columnIndex) {
    	ArrayList<String> rushStats =  new ArrayList<String>();
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.rushstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(columnIndex);
			String query = "SELECT " + name + " FROM footballstats.rushstats WHERE team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			if (columnIndex == 2) {
				while (result.next()) {
					String player = result.getString(name);
					rushStats.add(player);
				}
			} else {
				while (result.next()) {
					double data = result.getDouble(name);
					String dataString = Double.toString(data);
					rushStats.add(dataString);
				}
			}
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get RushStats.");
    	}
    	return rushStats;
    }
    
    /**
     * Gets the pass stat
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getPassStat(String team, int columnIndex) {
    	ArrayList<String> passStats =  new ArrayList<String>();
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.pass_stats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(columnIndex);
			String query = "SELECT " + name + " FROM footballstats.pass_stats WHERE team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			if (columnIndex == 2) {
				while (result.next()) {
					String player = result.getString(name);
					passStats.add(player);
				}
			} else {
				while (result.next()) {
					double data = result.getDouble(name);
					String dataString = Double.toString(data);
					passStats.add(dataString);
				}
			}
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get PassStats.");
    	}
    	return passStats;
    }
    
    /**
     * Gets the name of the stats
     * @param team
     * @return
     */
    public ArrayList<String> getFGStatsName(String team) {
    	ArrayList<String> fgStatsName = new ArrayList<String>();
    	String newName = "";
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.fgstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				if (name.contains("to")) {
					newName = name.replace("to", "-");
					newName = newName.replace("_", " ");
					fgStatsName.add(newName);
				} else if (name.contains("plus")) {
					newName = name.replace("plus", "+");
					newName = newName.replace("_", " ");
					fgStatsName.add(newName);
				} else {
					fgStatsName.add(name);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get the field goal stats name.");
		}
    	return fgStatsName;
    }
    
    /**
     * Gets the fg stats and displays them
     * @param team
     * @return 
     */
    public ArrayList<String> getFGStats(String team) {
    	ArrayList<String> fgStats = new ArrayList<>();
    	try {
    		fgStats.add(team);
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.fgstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 2; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query2 = "SELECT * FROM footballstats.fgstats WHERE team = '" + team + "';";
				ResultSet result = statement.executeQuery(query2);
				if (i == 2) {
					while (result.next()) {
						String player = result.getString(name);
						fgStats.add(player);
					}
				} else {
					while (result.next()) {
						int data = result.getInt(name);
						String dataString = Integer.toString(data);
						fgStats.add(dataString);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get field goal stat values.");
		}
    	return fgStats;
    }
    
    /**
     * Gets the def stat
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getDefStat(String team, int columnIndex) {
    	ArrayList<String> defStats =  new ArrayList<String>();
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.defstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(columnIndex);
			String query = "SELECT " + name + " FROM footballstats.defstats WHERE team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			if (columnIndex == 2) {
				while (result.next()) {
					String player = result.getString(name);
					defStats.add(player);
				}
			} else {
				while (result.next()) {
					double data = result.getDouble(name);
					String dataString = Double.toString(data);
					defStats.add(dataString);
				}
			}
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get Defensive Stats.");
    	}
    	return defStats;
    }
    
    /**
     * Gets the pass stat
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getIntStat(String team, int columnIndex) {
    	ArrayList<String> intStats =  new ArrayList<String>();
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.intstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(columnIndex);
			String query = "SELECT " + name + " FROM footballstats.intstats WHERE team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			if (columnIndex == 2) {
				while (result.next()) {
					String player = result.getString(name);
					intStats.add(player);
				}
			} else {
				while (result.next()) {
					double data = result.getDouble(name);
					String dataString = Double.toString(data);
					intStats.add(dataString);
				}
			}
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get Interception Stats.");
    	}
    	return intStats;
    }
    
    /**
     * Gets the injury stat
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getInjury(String team, int columnIndex) {
    	ArrayList<String> injuries =  new ArrayList<String>();
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.injuries;");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(columnIndex);
			String query = "SELECT " + name + " FROM footballstats.injuries WHERE team = '" + team + "';";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String item = result.getString(name);
				if (item.equals("NULL")) {
					item = item.replace("NULL", "--");
				}
				injuries.add(item);
			}
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get injuries.");
    	}
    	return injuries;
    }
    
    /**
     * Gets the scores stat
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getScores(String team, int columnIndex) {
    	ArrayList<String> scores =  new ArrayList<String>();
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.scores;");
			ResultSetMetaData rsmd = rs.getMetaData();
			String name = rsmd.getColumnName(columnIndex);
			String query = "SELECT " + name + " FROM footballstats.scores WHERE team = '" + team + " ' ORDER BY Week;";
			ResultSet result = statement.executeQuery(query);
			if (columnIndex == 2) {
				while (result.next()) {
					String item = result.getString(name);
					scores.add(item);
				}
			} else if (columnIndex == 3) {
				while (result.next()) {
					String item = result.getString(name);
					item = item.replace("_", " ");
					scores.add(item);
				}
			}
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get scores.");
    	}
    	return scores;
    }
    
    /**
     * Gets the scores stat (opponent)
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<String> getScoresOpp(String team) {
    	ArrayList<String> opp =  new ArrayList<String>();
    	ScoreStat ss = new ScoreStat();
    	String newOpp = "";
    	try {
    		establishConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT Score FROM footballstats.scores WHERE team = '" + team + "' ORDER BY Week;";
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				String item = result.getString("Score");
				String[] items = item.split("@");
				if (!items[0].contains(ss.getTeamAbb(team))) {
					String[] chunks = items[0].split("_");
					String chunk = chunks[0];
					newOpp = ss.getTeamName(chunk);
					opp.add(newOpp);
				} else {
					String[] chunks = items[1].split("_");
					String chunk = chunks[1];
					newOpp = ss.getTeamName(chunk);
					opp.add(newOpp);
				}
			}
			
    	} catch (SQLException e) {
    		logger.log(Level.FINE, "Could not get scores' opponent.");
    	}
    	return opp;
    }
    
    /**
     * Gets the name of the stats
     * @param team
     * @return
     */
    public ArrayList<String> getRankStatsName() {
    	ArrayList<String> statsName = new ArrayList<>();
    	statsName.add("Offensive Yards");
    	statsName.add("Offensive Rushing");
    	statsName.add("Offensive Receiving");
    	statsName.add("Defensive Yards");
    	statsName.add("Defensive Rushing");
    	statsName.add("Defensive Receiving");
    	statsName.add("Sacks");
    	statsName.add("Interceptions");
    	statsName.add("Points");
    	statsName.add("Passing");
    	return statsName;
    }
    
    /**
     * Gets the ranking
     * @param team
     * @param columnIndex
     * @return
     */
    public ArrayList<Integer> getRanking(String team) {
		ArrayList<Integer> ranking = new ArrayList<>();
		String query = "";
		String name = "";
		establishConnection();
		Statement statement;
		try {
			statement = connection.createStatement();
			for (int i = 0; i < 10; i++) {
				if (i == 0) {
					query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats.yardsrank a JOIN footballstats.yardsrank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 1) {
					query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats.rushrank a JOIN footballstats.rushrank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 2) {
					query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats.recrank a JOIN footballstats.recrank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 3) {
					query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats.defyardsrank a JOIN footballstats.defyardsrank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 4) {
					query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats.defrushrank a JOIN footballstats.defrushrank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 5) {
					query = "SELECT a.Yds_G, a.Team, count(*) as rank FROM footballstats.defrecrank a JOIN footballstats.defrecrank b ON a.Yds_G <= b.Yds_G WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 6) {
					query = "SELECT a.Sacks, a.Team, count(*) as rank FROM footballstats.sackrank a JOIN footballstats.sackrank b ON a.Sacks <= b.Sacks WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 7) {
					query = "SELECT a.Interceptions, a.Team, count(*) as rank FROM footballstats.intrank a JOIN footballstats.intrank b ON a.Interceptions <= b.Interceptions WHERE a.Team = '"
							+ team + "' GROUP BY a.Team;";
				} else if (i == 8) {
					query = "SELECT Pts_Place FROM footballstats.quickstats WHERE Team = '" + team + "';";
					name = "Pts_Place";
				} else {
					query = "SELECT Pass_Yds_Place FROM footballstats.quickstats WHERE Team = '" + team + "';";
					name = "Pass_Yds_Place";
				}

				if (i < 8) {
					ResultSet result = statement.executeQuery(query);
					while (result.next()) {
						int item = result.getInt("rank");
						ranking.add(item);
					}
				} else {
					ResultSet result = statement.executeQuery(query);
					while (result.next()) {
						int item = result.getInt(name);
						ranking.add(item);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Connection couldn't be established.");
		}
		return ranking;
    }
}
