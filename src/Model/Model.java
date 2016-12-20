package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model {

	private static final Model instance = new Model();
	private Connection connection;

	/**
	 * Gets a singleton of the model
	 * 
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
            e.printStackTrace();
        }
    }

}
