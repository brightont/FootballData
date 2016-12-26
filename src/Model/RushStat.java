package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RushStat extends Stat{
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("Rush.class");
	private final Connection connection = database.establishConnection();
	public Document document;
	
	//empty constructor
	public RushStat() {
		
	}
	
	public RushStat(String team, String player, int att, int yds, double ya, int lng, int td) {
		super(team);
		String insert = "INSERT INTO footballstats.rushstats VALUES" + " ('" + team + "','" + player + "','" + att + "','"
				+ yds + "','" + ya + "','" + lng + "','" + td + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets Rush statistics off NFL website
	 * @param team
	 * @return
	 */
	public ArrayList<String> getRushStats(String team) {
		ArrayList<String> rushStatistics = new ArrayList<String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get rush statistic information.");
		}
		Elements rushStats = document.getElementsByClass("data-table1 six-col");
		Elements rushStatsRow = rushStats.select("td");
		for (int i = 1; i < rushStatsRow.size(); i++) {
			String temp = rushStatsRow.get(i).text();
			if (temp.equals("Receiving Statistics")) {
				break;
			} else {
				rushStatistics.add(temp);
			}
		}
		return rushStatistics;
	}
	
	/**
	 * Gets the information for a new player
	 * @param i
	 * @param team
	 */	
	public void scrapeNewPlayer(ArrayList<String> list, ArrayList<Integer> intList, String team) {
		for (Integer i : intList) {
			String player = list.get(i);
			String att = list.get(i + 1);
			String yds = list.get(i + 2);
			String ya = list.get(i + 3);
			String lng = list.get(i + 4);
			String td = list.get(i + 5);
	
			// convert the appropriate values
			int attInt = Integer.parseInt(att);
			int ydsInt = Integer.parseInt(yds);
			double yaInt = Double.parseDouble(ya);
			int lngInt = Integer.parseInt(lng);
			int tdInt = Integer.parseInt(td);
	
			addNewPlayer(team, player, attInt, ydsInt, yaInt, lngInt, tdInt);
		}
	}
	
	/**
	 * Adds a new player into database
	 * @param team
	 * @param player
	 * @param att
	 * @param yds
	 * @param ya
	 * @param lng
	 * @param td
	 */
	public void addNewPlayer(String team, String player, int att, int yds, double ya, int lng, int td) {
		String insert = "INSERT INTO footballstats.rushstats VALUES ('" + team + "','" + player + "','" + att + "','"
				+ yds + "','" + ya + "','" + lng + "','" + td + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not add a new player.");
		}
	}
	
	/**
	 * Update the database
	 * @param list
	 * @param team
	 */
	public void updateDatabase(ArrayList<String> list, String team) {
		for (int i = 6; i < list.size(); i++) {
			if ((i % 6) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (result.contains("-")) {
					resultDouble = 0;
					resultInt = 0;
				}
				if (((i - 5) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushstats SET TD = " + resultInt + " WHERE Team = '" + team + "';";
				} else if (((i - 4) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushstats SET Lng = " + resultInt + " WHERE Team = '" + team
							+ "';";
				} else if (((i - 3) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.rushstats SET Yds_Att = " + resultDouble + " WHERE Team = '" + team
							+ "';";
				} else if (((i - 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushstats SET Yds = " + resultInt + " WHERE Team = '" + team
							+ "';";
				} else {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushstats SET Att = " + resultInt + " WHERE Team = '" + team
							+ "';";
				}
				try {
					PreparedStatement prepStatement = connection.prepareStatement(update);
					prepStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}
	
}
