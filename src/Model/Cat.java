package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Cat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("DefStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;
	
	/**
	 * Gets Defensive statistics off NFL website
	 * @param team
	 * @return
	 */
	public ArrayList<String> getList(String team) {
		int start = 0;
		ArrayList<String> scores = new ArrayList<String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/schedule?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get score statistics.");
		}
		Elements scoreStats = document.getElementsByClass("data-table1");
		Elements scoreStatsRow = scoreStats.select("td");
		for (int i = 9; i < scoreStatsRow.size(); i++) {
			String temp = scoreStatsRow.get(i).text();
			if ((i - 1) % 8 == 0 && start == 0) {
				scores.add(scoreStatsRow.get(i).text());
				scores.add(scoreStatsRow.get(i+2).text().replace(" ", "_"));
			}
			if (temp.equals("Bye")) {
				start = 1;
			}
			if (start != 0 && (i-4) % 8 == 0) {
				scores.add(scoreStatsRow.get(i).text());
				scores.add(scoreStatsRow.get(i+2).text().replace(" ", "_"));
			}
			if (temp.equals("Wk")) {
				break;
			} 
		}
		return scores;
	}
	
	/**
	 * Update the database
	 * @param list
	 * @param team
	 */
	public void addGame(ArrayList<String> list, String team, String fullName) {
		//int index = 0;
		for (int index = 18; index < list.size(); index++) {
			if ((index % 2) == 0) {
				String week = list.get(index);
				int weekInt = Integer.parseInt(week);
				String score = list.get(index + 1);
				score = score.replace(" ", "_");
				String[] parts = score.split("_");
				String score1 = parts[1];
				int s1 = Integer.parseInt(score1);
				String score2 = parts[4];
				int s2 = Integer.parseInt(score2);
				String[] outcomes = score.split("@");
				String outcome = "";
				if (outcomes[0].contains(team)) {
					if (s1 > s2) {
						outcome = "W";
					} else if (s1 < s2) {
						outcome = "L";
					} else {
						outcome = "T";
					}
				} else {
					if (s1 > s2) {
						outcome = "L";
					} else if (s1 < s2) {
						outcome = "W";
					} else {
						outcome = "T";
					}
				}
				String label = fullName + weekInt;
				
				String insert = "INSERT INTO footballstats.scores VALUES" + " ('" + fullName + "','" + weekInt + "','"
						+ score + "','" + outcome + "','" + label + "');";
				try {
					PreparedStatement prepStatement = connection.prepareStatement(insert);
					prepStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			index++;
		}
	}
	
	
}
