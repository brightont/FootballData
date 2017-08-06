package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ScoreStat extends Stat{
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("ScoreStat.class");
	private final Connection connection = database.EstablishConnection();
	public Document document;
	
	//empty constructor
	public ScoreStat() {
		
	}
	
	public ScoreStat(String team, int week, String score, String outcome, String label) {
		super(team);
		String insert = "INSERT INTO footballstats.scores VALUES" + " ('" + team + "','" + week + "','" + score
				+ "','" + outcome + "','" + label + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets score statistics off NFL website
	 * @param team
	 * @return
	 */
	public ArrayList<String> getScoreStats(String team) {
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
				scores.add(scoreStatsRow.get(i+2).text());
			}
			if (temp.equals("Bye")) {
				start = 1;
			}
			if (start != 0 && (i-4) % 8 == 0) {
				scores.add(scoreStatsRow.get(i).text());
				scores.add(scoreStatsRow.get(i+2).text());
			}
			if (temp.equals("Wk")) {
				break;
			} 
		}
		return scores;
	}
	
	/**
	 * Removes a player
	 * @param list
	 * @param team
	 */
	public boolean checkNewGame(ArrayList<String> list, String team) {
		int games = list.size()/2;
		String query = "SELECT COUNT(Week) FROM footballstats.scores WHERE Team = '" + team + "';";
		int answer = 0;
		try {
            Statement userStatement = connection.createStatement();
            ResultSet result = userStatement.executeQuery(query);
            while (result.next()) {
                answer = result.getInt("COUNT(Week)");
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, "Could not select count of games.");
        }
		if ((games - 1) != answer) {
			return false;
		}
		return true;
	}
	
	
	public void addGame(ArrayList<String> list, String team, String fullName) {
		int lastItem = list.size() - 2;
		String week = list.get(lastItem);
		int weekInt = Integer.parseInt(week);
		String score = list.get(lastItem + 1);
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
			} else if (s1 < s2){
				outcome = "W";
			} else {
				outcome = "T";
			}
		}
		String label = fullName + weekInt;
		String insert = "INSERT INTO footballstats.scores VALUES" + " ('" + fullName + "','" + weekInt + "','" + score
				+ "','" + outcome + "','" + label + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the total record
	 * @param list
	 * @param team
	 */
	public String getRecord(String team) {
		String arr[] = { "W", "L", "T" };
		String record = "";
		int count = 0;
		int answer = 0;
		for (String a : arr) {
			String query = "SELECT COUNT(Week) FROM footballstats.scores WHERE Team = '" + team + "' AND Outcome = '"
					+ a + "';";
			try {
				Statement userStatement = connection.createStatement();
				ResultSet result = userStatement.executeQuery(query);
				while (result.next()) {
					answer = result.getInt("COUNT(Week)");
				}
			} catch (SQLException e) {
				logger.log(Level.FINE, "Could not select record count.");
			}
			if (count != 2) {
				record = record + answer + " - ";
			} else {
				record = record + answer;
			}
			count++;
		}
		return record;
	}

}
