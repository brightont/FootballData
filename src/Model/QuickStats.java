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

public class QuickStats extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("QuickStats.class");
	private final Connection connection = database.establishConnection();
	public Document document;
	
	//empty constructor
	public QuickStats() {
		
	}
	
	public QuickStats(String team, double pts, int pp, double yds, int yp, double pass, int passp, double rush,
			int rp) {
		super(team);
		String insert = "INSERT INTO footballstats.quickstats VALUES" + " ('" + team + "','" + pts + "','" + pp + "','"
				+ yds + "','" + yp + "','" + pass + "','" + passp + "','" + rush + "','" + rp + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getQuickStats(String team) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(getTeamAbb(team));
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/teams/atlantafalcons/statistics?team=" + team)
					.get();
		} catch (IOException e) {
			logger.log(Level.FINE, "Couldn't get quick stats.");
		}
		Elements elements = document.getElementsByClass("team-quick-stat-body");
		for (int i = 0; i < 4; i++) {
			String temp = elements.get(i).text();
			String[] tempArr1 = temp.split("\\(");
			String value = tempArr1[0];
			arr.add(value);
			String[] tempArr2 = tempArr1[1].split("\\)");
			String place = tempArr2[0];
			char placeNumber = place.charAt(0);
			String pnString = Character.toString(placeNumber);
			arr.add(pnString);
		}
		return arr;
	}
	
	/**
	 * Update the database
	 * @param list
	 * @param team
	 */
	public void updateDatabase(ArrayList<String> list, String team) {
		int j = 0;
		for (int i = 0; i < list.size(); i++) {
			if ((i % 9) == 0) {
				j++;
			} else if ((i % 9) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (((j - 8) % 9) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.quickstats SET Rush_Yds_Place = " + resultInt + " WHERE Team = '"
							+ team + "';";
					j = 0;
				} else if (((j - 7) % 9) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.quickstats SET Rush_Yds = " + resultDouble + " WHERE Team = '"
							+ team + "';";
				} else if (((j - 6) % 9) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.quickstats SET Pass_Yds_Place = " + resultInt + " WHERE Team = '"
							+ team + "';";
				} else if (((j - 5) % 9) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.quickstats SET Pass_Yds = " + resultDouble + " WHERE Team = '" + team
							+ "';";
				} else if (((j - 4) % 9) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.quickstats SET Yds_Place = " + resultInt + " WHERE Team = '" + team
							+ "';";
				} else if (((j - 3) % 9) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.quickstats SET Yds = " + resultDouble + " WHERE Team = '" + team
							+ "';";
				} else if (((j - 2) % 9) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.quickstats SET Pts_Place = " + resultInt + " WHERE Team = '" + team
							+ "';";
				} else {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.quickstats SET Pts = " + resultDouble + " WHERE Team = '" + team
							+ "';";
				}
				if (j != 0) {
					j++;
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
