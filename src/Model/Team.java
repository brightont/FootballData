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

public class Team {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("Team.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	/**
	 * Gets the statistics off NFL website
	 * @param team
	 */
	public HashMap<String, String> getTeamStats(String team) {
		HashMap<String, String> homeStatistics = new HashMap<String, String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get information.");
		}
		Elements teamStats = document.getElementsByClass("data-table1 team-stats");
		Elements teamStatsRow = teamStats.select("td");
		for (int i = 0; i < teamStatsRow.size(); i++) {
			if (((i - 1) % 3) == 0) {
				String keyHash = "";
				String valueHash = "";

				keyHash = teamStatsRow.get(i).text();
				valueHash = teamStatsRow.get(i + 1).text();
				homeStatistics.put(keyHash, valueHash);
			}
		}
		return homeStatistics;
	}

	/**
	 * Check database and make sure the facts are correct
	 */
	public boolean checkDatabase(HashMap<String, String> hash) {
		String value = hash.get("Total First Downs");
		int tfd = Integer.parseInt(value);
		String query = "SELECT * FROM footballstats.teamstats WHERE Total_First_Downs = " + tfd + ";";
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
				if (databaseValue.contains("-")) {
					databaseValue = databaseValue.split("-")[0];
					double dvi = Double.parseDouble(databaseValue);
					updateQuery(databaseKey, dvi, team);
				}
			} else if (entry.getKey().contains(" (")) {
				databaseKey = entry.getKey().split(" (")[0];
				databaseValue = entry.getValue();
				if (databaseValue.contains("-")) {
					databaseValue = databaseValue.split("-")[0];
					double dvi = Double.parseDouble(databaseValue);
					updateQuery(databaseKey, dvi, team);
				}
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
		String update = "UPDATE footballstats.teamstats SET " + key + " = " + value + " WHERE team = '" + team + "';";
		try {
            PreparedStatement prepStatement =
                    connection.prepareStatement(update);
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
		} else if (teamName.equals("TB")) {
			team = TeamName.TB.getTeam();
		}
		return team;
	}
}
