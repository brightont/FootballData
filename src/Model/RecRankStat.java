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

public class RecRankStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("RushRankStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	// empty constructor
	public RecRankStat() {

	}

	public RecRankStat(String team, double pg, int tp, int rec, int yds, double avg, double yg, int td) {
		super(team);
		String insert = "INSERT INTO footballstats.recrank VALUES" + " ('" + team + "','" + pg + "','" + tp + "','"
				+ rec + "','" + yds + "','" + avg + "','" + yg + "','" + td + "');";

		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getRecRankStat() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?archive=false&conference=null&role=TM&offensiveStatisticCategory=TEAM_RECEIVING&defensiveStatisticCategory=null&season=2016&seasonType=REG&tabSeq=2&qualified=false&Submit=Go")
					.get();
		} catch (IOException e) {
			logger.log(Level.FINE, "Cannot get rec rank stat.");
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 16 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				arr.add(t0);
				String t1 = row.get(i + 2).text();
				arr.add(t1);
				String t2 = row.get(i + 3).text();
				arr.add(t2);
				String t3 = row.get(i + 4).text();
				arr.add(t3);
				String t4 = row.get(i + 5).text();
				t4 = t4.replace(",", "");
				arr.add(t4);
				String t5 = row.get(i + 6).text();
				arr.add(t5);
				String t6 = row.get(i + 7).text();
				arr.add(t6);
				String t7 = row.get(i + 9).text();
				arr.add(t7);
			}
		}
		return arr;
	}

	/**
	 * Update the database
	 * @param list
	 * @param team
	 */
	public void updateDatabase(ArrayList<String> list, String team) {
		for (int i = 0; i < list.size(); i++) {
			if ((i % 8) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (((i - 7) % 8) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.recrank SET TD = " + resultInt + " WHERE Team = '" + team + "';";
				} else if (((i - 6) % 8) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.recrank SET Yds_G = " + resultDouble + " WHERE Team = '" + team
							+ "';";
				} else if (((i - 5) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.recrank SET Avge = " + resultDouble + " WHERE Team = '" + team + "';";
				} else if (((i - 4) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.recrank SET Yds = " + resultInt + " WHERE Team = '" + team + "';";
				} else if (((i - 3) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.recrank SET Rec = " + resultInt + " WHERE Team = '" + team + "';";
				} else if (((i - 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.recrank SET TotPts = " + resultInt + " WHERE Team = '" + team
							+ "';";
				} else {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.recrank SET Pts_G = " + resultDouble + " WHERE Team = '" + team
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
