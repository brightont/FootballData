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

public class IntStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("IntStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;
	
	//empty constructor
	public IntStat() {
		
	}
	
	public IntStat(String team, String player, int it, int yds, double yi, int lng, int td) {
		super(team);
		String insert = "INSERT INTO footballstats.intstats VALUES" + " ('" + team + "','" + player + "','" + it
				+ "','" + yds + "','" + yi + "','" + lng + "','" + td + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Gets Interception statistics off NFL website
	 * @param team
	 * @return
	 */
	public ArrayList<String> getIntStats(String team) {
		int start = 0;
		ArrayList<String> intStatistics = new ArrayList<String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get interception statistics.");
		}
		Elements passStats = document.getElementsByClass("data-table1 six-col");
		Elements passStatsRow = passStats.select("td");
		for (int i = 1; i < passStatsRow.size(); i++) {
			String temp = passStatsRow.get(i).text();
			if (temp.equals("TOTAL") && start != 0) {
				break;
			}
			if (start != 0) {
				intStatistics.add(temp);
			}
			if (temp.equals("Interception Statistics")) {
				start = i;
			} 
		}
		return intStatistics;
	}
	
	/**
	 * Gets the information for a new player
	 * @param i
	 * @param team
	 */	
	public void scrapeNewPlayer(ArrayList<String> list, ArrayList<Integer> intList, String team) {
		for (Integer i : intList) {
			String player = list.get(i);
			String it = list.get(i + 1);
			String yds = list.get(i + 2);
			String yi = list.get(i + 3);
			String lng = list.get(i + 4);
			String td = list.get(i + 5);
	
			// convert the appropriate values
			int intInt = Integer.parseInt(it);
			int ydsInt = Integer.parseInt(yds);
			double yiInt = Double.parseDouble(yi);
			int lngInt = Integer.parseInt(lng);
			int tdInt = Integer.parseInt(td);
	
			addNewPlayer(team, player, intInt, ydsInt, yiInt, lngInt, tdInt);
		}
	}
	
	/**
	 * Adds a new player
	 * @param team
	 * @param player
	 * @param it
	 * @param yds
	 * @param yi
	 * @param lng
	 * @param td
	 */
	public void addNewPlayer(String team, String player, int it, int yds, double yi, int lng, int td) {
		String insert = "INSERT INTO footballstats.intstats VALUES ('" + team + "','" + player + "','" + it + "','"
				+ yds + "','" + yi + "','" + lng + "','" + td + "');";
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
		String player = "";
		for (int i = 6; i < list.size(); i++) {
			if ((i % 6) == 0) {
				player = list.get(i);
			} else if ((i % 6) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (result.contains("/")) {
					resultDouble = 0;
					resultInt = 0;
				}
				if (((i - 5) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.intstats SET TD = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 4) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.intstats SET Lng = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 3) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.intstats SET Yds_Int = " + resultDouble + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.intstats SET Yds = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.intstats SET It = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
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
