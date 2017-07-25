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

public class DefStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("DefStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	// empty constructor
	public DefStat() {

	}
	
	public DefStat(String team, String player, int comb, int total, int assist, double sack, int fumb) {
		super(team);
		String insert = "INSERT INTO footballstats.defstats VALUES" + " ('" + team + "','" + player + "','" + comb
				+ "','" + total + "','" + assist + "','" + sack + "','" + fumb + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Gets Defensive statistics off NFL website
	 * @param team
	 * @return
	 */
	public ArrayList<String> getDefStats(String team) {
		int start = 0;
		int count = 0;
		ArrayList<String> defStatistics = new ArrayList<String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team + "&seasonType=REG").get();
		} catch (IOException e) {
			logger.info("Failed to get defense statistics.");
		}
		Elements defStats = document.getElementsByClass("data-table1 six-col");
		Elements defStatsRow = defStats.select("td");
		for (int i = 1; i < defStatsRow.size(); i++) {
			String temp = defStatsRow.get(i).text();
			if (count == 66) {
				start = 0;
			}
			if (start != 0) {
				defStatistics.add(temp);
				count++;
			}
			if (temp.equals("Defense Statistics")) {
				start = i;
			}
			if (temp.equals("TEAM TOTAL")) {
				String tempTeam = getTeamName(team);
				defStatistics.add(tempTeam + " Total");
				start = i;
				count++;
			}
			if (temp.equals("OPPONENTS TOTAL")) {
				start = 0;
			}
		}
		return defStatistics;
	}
	
	/**
	 * Gets the information for a new player
	 * @param i
	 * @param team
	 */	
	public void scrapeNewPlayer(ArrayList<String> list, ArrayList<Integer> intList, String team) {
		for (Integer i : intList) {
			String player = list.get(i);
			String comb = list.get(i + 1);
			String total = list.get(i + 2);
			String assist = list.get(i + 3);
			String sck = list.get(i + 4);
			String fumb = list.get(i + 5);
	
			// convert the appropriate values
			int combInt = Integer.parseInt(comb);
			int totalInt = Integer.parseInt(total);
			int assistInt = Integer.parseInt(assist);
			double sackDouble = Double.parseDouble(sck);
			int fumbInt = Integer.parseInt(fumb);
	
			addNewPlayer(team, player, combInt, totalInt, assistInt, sackDouble, fumbInt);
		}
	}
	
	/**
	 * Adds a new player
	 * @param team
	 * @param player
	 * @param comb
	 * @param total
	 * @param assist
	 * @param sck
	 * @param fumb
	 */
	public void addNewPlayer(String team, String player, int comb, int total, int assist, double sck, int fumb) {
		String insert = "INSERT INTO footballstats.defstats VALUES ('" + team + "','" + player + "','" + comb + "','"
				+ total + "','" + assist + "','" + sck + "','" + fumb + "');";
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
				if (player.contains("'")) {
		    		player = player.replace("'", "");
		    	} else if (player.contains(".")) {
		    		player = player.replace(".", "");
		    	} else if (player.contains("-")) {
		    		player = player.split("-")[0];
		    	}
			} else if ((i % 6) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble = 0;
				int resultInt = 0;
				if (((i - 5) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.defstats SET Fumb = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 4) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.defstats SET Sck = " + resultDouble + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 3) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.defstats SET Assist = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.defstats SET Total = " + resultInt + " WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.defstats SET Comb = " + resultInt + " WHERE Team = '" + team
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
