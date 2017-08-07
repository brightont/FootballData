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

public class QBStat extends Stat{
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("QBStat.class");
	private final Connection connection = database.EstablishConnection();
	public Document document;
	
	//empty constructor
	public QBStat() {
		
	}
	
	// constructor using for testing purposes
	public QBStat(String team, String player, int att, int comp, int yds, double cp, double ya, int td,
			double tdp, int i, double ip, int lg, int sack, int sl, double rating) {
		super(team);
		String insert = "INSERT INTO footballstats.qbstats VALUES" + " ('" + team + "','" + player + "','" + att + "','"
				+ comp + "','" + yds + "','" + cp + "','" + ya + "','" + td + "','" + tdp + "','" + i + "','" + ip
				+ "','" + lg + "','" + sack + "','" + sl + "','" + rating + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets QB statistics off NFL website
	 * @param team
	 * @return
	 */
	public HashMap<String, String> GetQBStats(String team) {
		HashMap<String, String> qbStatistics = new HashMap<String, String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team + "&seasonType=REG").get();
		} catch (IOException e) {
			logger.info("Failed to get quarter back statistics.");
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
	 * Update the database if the website info has changed
	 * @param hash
	 */
	public void UpdateDatabase(HashMap<String, String> hash, String team) {
		String databaseKey = "";
		String databaseValue = "";
		for (HashMap.Entry<String, String> entry : hash.entrySet()) {
			if (entry.getKey().contains(" %")) {
				databaseKey = entry.getKey().replace(" %", "_P");
				databaseValue = entry.getValue();
				double dvi = Double.parseDouble(databaseValue);
				UpdateQueryDouble(databaseKey, dvi, team, "qbstats");
			} else if (entry.getKey().contains("/")) {
				databaseKey = entry.getKey().replace("/", "_");
				databaseValue = entry.getValue();
				double dvi = Double.parseDouble(databaseValue);
				if ((dvi % 1) != 0) { 
					UpdateQueryDouble(databaseKey, dvi, team, "qbstats");
				} else {
					int ivi = Integer.parseInt(databaseValue);
					UpdateQueryInt(databaseKey, ivi, team, "qbstats");
				}
			}else {
				databaseKey = entry.getKey();
				if (databaseKey.equals("Player")) {
					databaseValue = entry.getValue();
					updateQueryString(databaseKey, databaseValue, team, "qbstats");
				} else if (databaseKey.equals("Rating")) {
					databaseValue = entry.getValue();
					double dvi = Double.parseDouble(databaseValue);
					UpdateQueryDouble(databaseKey, dvi, team, "qbstats");
				} else {
					databaseValue = entry.getValue();
					int ivi = Integer.parseInt(databaseValue);
					UpdateQueryInt(databaseKey, ivi, team, "qbstats");
				}
			}
		}
	}
	
}
