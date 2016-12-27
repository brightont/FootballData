package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TeamStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("TeamStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	//empty constructor
	public TeamStat() {
		
	}
	
	//constructor used for testing purposes
	public TeamStat(String team, int tfd, int firstDowns, double tdc, double fdc, int toy, int offense, int tryd, int rushing,
			int tpyd, int passing, int sacks, double fg, int td, int rprd, double time, int turnover) {
		super(team);
		String insert = "INSERT INTO footballstats.teamstats VALUES" + " ('" + team + "','" + tfd + "','" + firstDowns
				+ "','" + tdc + "','" + fdc + "','" + toy + "','" + offense + "','" + tryd + "','" + rushing + "','" + tpyd + "','"
				+ passing + "','" + sacks + "','" + fg + "','" + td + "','" + rprd + "','" + time + "','" + turnover
				+ "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Gets the statistics off NFL website
	 * @param team
	 */
	public HashMap<String, String> getTeamStats(String team) {
		HashMap<String, String> homeStatistics = new HashMap<String, String>();

		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team).get();
		} catch (IOException e) {
			logger.log(Level.FINE, "Failed to get team statistics.");
		}
		Elements teamStats = document.getElementsByClass("data-table1 team-stats");
		Elements teamStatsRow = teamStats.select("td");
		for (int i = 4; i < teamStatsRow.size(); i++) {
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
	 * Update the database if the website info has changed
	 * @param hash
	 */
	public void updateDatabase(HashMap<String, String> hash, String team) {
		String databaseKey = "";
		String databaseValue = "";
		for (HashMap.Entry<String, String> entry : hash.entrySet()) {
			if (entry.getKey().contains(" (")) {
				databaseKey = entry.getKey().split(" \\(")[0];
				databaseValue = entry.getValue();
				if (databaseKey.contains(" ")) {
					databaseKey = databaseKey.replace(" ", "_");
				}
				if (databaseValue.contains(" -")) {
					databaseValue = databaseValue.split(" -")[0];
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				} else {
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				}
				
			} else if (entry.getKey().contains("(")) {
				databaseKey = entry.getKey().replace("-", "_");
				databaseKey = databaseKey.substring(1, databaseKey.length()-1);
				databaseValue = entry.getValue().split(" -")[0];
				int ivi = Integer.parseInt(databaseValue);
				updateQueryInt(databaseKey, ivi, team, "teamstats");
				
			} else if (entry.getKey().contains(" ")) {
				databaseKey = entry.getKey().replace(" ", "_");
				databaseValue = entry.getValue();
				if (databaseValue.contains(" -")) {
					databaseValue = databaseValue.split(" -")[0];
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				} else if (databaseValue.contains("/")) {
					String[] arr = databaseValue.split("/");
					double dvi = Double.parseDouble(arr[0]) / Double.parseDouble(arr[1]);
					updateQueryDouble(databaseKey, dvi, team, "teamstats");
				} else if (databaseValue.contains(":")) {
					databaseValue = databaseValue.replace(":", ".");
					double dvi = Double.parseDouble(databaseValue);
					updateQueryDouble(databaseKey, dvi, team, "teamstats");
				} else if (databaseValue.contains("+")){
					databaseValue = databaseValue.substring(1);
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				}else {
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				}
				
			} else {
				databaseKey = entry.getKey();
				databaseValue = entry.getValue();
				if (databaseValue.contains(" -")) {
					databaseValue = databaseValue.split(" -")[0];
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				} else {
					int ivi = Integer.parseInt(databaseValue);
					updateQueryInt(databaseKey, ivi, team, "teamstats");
				}
			}
		}
	}
	
}
