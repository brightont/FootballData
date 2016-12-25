package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class QuarterBack {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("QuarterBack.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	/**
	 * Gets QB statistics off NFL website
	 * @param team
	 * @return
	 */
	public HashMap<String, String> getQBStats(String team) {
		HashMap<String, String> qbStatistics = new HashMap<String, String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get quarter back information.");
		}
		Elements qbStats = document.getElementsByClass("data-table1 ");
		Elements qbStatsRow = qbStats.select("td");
		for (int i = 1; i < qbStatsRow.size() - 14; i++) {
			String keyHash = "";
			String valueHash = "";
			if (i < 15) {
				keyHash = qbStatsRow.get(i).text();
				valueHash = qbStatsRow.get(i+14).text();
				qbStatistics.put(keyHash, valueHash);
			} 
		}
		return qbStatistics;
	}

	/**
	 * Check database and make sure the facts are correct
	 */
	public boolean checkDatabase(HashMap<String, String> hash) {
		String value = hash.get("Att");
		int att = Integer.parseInt(value);
		String query = "SELECT * FROM footballstats.qbstats WHERE Att = " + att + ";";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			boolean bool = result.next();
			if (bool == false) {
				return false;
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, "Database failed to load");
		}
		return true;
	}


	/**
	 * Update the database if the website info has changed
	 * @param hash
	 */
	public void updateDatabase(HashMap<String, String> hash, String team) {
		String databaseKey = "";
		String databaseValue = "";
		for (HashMap.Entry<String, String> entry : hash.entrySet()) {
			if (entry.getKey().contains(" ")) {
				databaseKey = entry.getKey().replace(" ", "_");
				databaseValue = entry.getValue();
				double dvi = Double.parseDouble(databaseValue);
				updateQuery(databaseKey, dvi, team);
			} else {
				databaseValue = entry.getValue();
				double dvi = Double.parseDouble(databaseValue);
				updateQuery(databaseKey, dvi, team);
			}
		}
	}
	
	/**
	 * Updates the database
	 * @param key
	 * @param value
	 * @param team
	 */
	private void updateQuery(String key, double value, String team) {
		String update = "UPDATE footballstats.qbstats SET " + key + " = " + value + " WHERE team = '" + team + "';";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(update);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
		} else if (teamName.equals("BAL")) {
			team = TeamName.BAL.getTeam();
		} else if (teamName.equals("TB")) {
			team = TeamName.TB.getTeam();
		}
		return team;
	}
	
}
