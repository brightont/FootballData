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
	public ArrayList<String> statsName = new ArrayList<>();
	public ArrayList<String> stats = new ArrayList<>();
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
    public ArrayList<String> getStatsName(String team) {
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
    	//System.out.println("Here is the team" + team);
    	stats.clear();
    	try {
    		stats.add(team);
    		establishConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM footballstats.teamstats;");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 2; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				String query2 = "SELECT * FROM footballstats.teamstats WHERE team = '" + team + "';";
				ResultSet result = statement.executeQuery(query2);
				while (result.next()) {
					double data = result.getDouble(name);
					String dataString = Double.toString(data);
			//		System.out.println("Here is the data requested " + dataString);
					stats.add(dataString);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not get stat values.");
		}
    	return stats;
    }
    
}
