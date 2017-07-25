package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FieldGoalStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("FieldGoalStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;
	
	//empty constructor
	public FieldGoalStat() {
		
	}
	
	public FieldGoalStat(String team, String player, int a1, int m1, int a2, int m2, int a3, int m3, int a4, int m4,
			int a5, int m5) {
		super(team);
		String insert = "INSERT INTO footballstats.fgstats VALUES" + " ('" + team + "','" + player + "','" + a1 + "','"
				+ m1 + "','" + a2 + "','" + m2 + "','" + a3 + "','" + m3 + "','" + a4 + "','" + m4 + "','" + a5 + "','"
				+ m5 + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets FG statistics off NFL website
	 * @param team
	 * @return
	 */
	public HashMap<String, String> getFGStats(String team) {
		int start = 0;
		int end = 0;
		HashMap<String, String> fgStatistics = new HashMap<String, String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team + "&seasonType=REG").get();
		} catch (IOException e) {
			logger.info("Failed to get field goal statistics.");
		}
		Elements fgStats = document.getElementsByClass("data-table1 ");
		Elements fgStatsRow = fgStats.select("td");
		for (int i = 1; i < fgStatsRow.size(); i++) {
			String temp = fgStatsRow.get(i).text();
			if (end == i) {
				break;
			}
			if (start != 0) {
				String keyHash = "";
				String valueHash = "";
				keyHash = fgStatsRow.get(i).text();
				valueHash = fgStatsRow.get(i+11).text();
				fgStatistics.put(keyHash, valueHash);
				
			}
			if (temp.equals("Field Goal Statistics")) {
				start = i;
				end = i + 12;
			}
		}
		return fgStatistics;
	}
	
	/**
	 * Update the database if the website info has changed
	 * @param hash
	 */
	public void updateDatabase(HashMap<String, String> hash, String team) {
		String databaseKey = "";
		String databaseValue = "";
		for (HashMap.Entry<String, String> entry : hash.entrySet()) {
			if (entry.getKey().contains("-")) {
				databaseKey = entry.getKey().replace("-", "to");
				databaseKey = databaseKey.replace(" ", "_");
				databaseValue = entry.getValue();
				int ivi = Integer.parseInt(databaseValue);
				updateQueryInt(databaseKey, ivi, team, "fgstats");
			} else if (entry.getKey().contains("+")){
				databaseKey = entry.getKey().replace("+", "plus");
				databaseKey = databaseKey.replace(" ", "_");
				databaseValue = entry.getValue();
				int ivi = Integer.parseInt(databaseValue);
				updateQueryInt(databaseKey, ivi, team, "fgstats");
			} else {
				databaseKey = entry.getKey();
				databaseValue = entry.getValue();
				updateQueryString(databaseKey, databaseValue, team, "fgstats");
			}
		}
	}
}
