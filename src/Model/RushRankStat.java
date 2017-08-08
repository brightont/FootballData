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

public class RushRankStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("RushRankStat.class");
	private final Connection connection = database.EstablishConnection();
	public Document document;

	/**
	 * Empty constructor
	 */
	public RushRankStat() {

	}
	
	/**
	 * Insert rush rank stat into database
	 * @param team
	 * @param pg
	 * @param tp
	 * @param yds
	 * @param avg
	 * @param yg
	 * @param td
	 */
	public RushRankStat(String team, double pg, int tp, int yds, double avg, double yg, int td) {
		String insert = "INSERT INTO footballstats.rushrank VALUES" + " ('" + team + "','" + pg + "','" + tp
				+ "','" + yds + "','" + avg + "','" + yg + "','" + td + "');";

		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the rushing rank
	 * @return
	 */
	public ArrayList<String> GetRushRankStat() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?seasonType=REG&offensiveStatisticCategory=RUSHING&d-447263-n=1&d-447263-o=2&d-447263-p=1&d-447263-s=RUSHING_ATTEMPTS&tabSeq=2&season=2016&role=TM&Submit=Go&archive=false&conference=null&defensiveStatisticCategory=null&qualified=false")
					.get();
		} catch (IOException e) {
			logger.log(Level.FINE, "Cannot get rush rank stats.");
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 17 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				arr.add(t0);
				String t1 = row.get(i + 2).text();
				arr.add(t1);
				String t2 = row.get(i + 3).text();
				arr.add(t2);
				String t3 = row.get(i + 6).text();
				t3 = t3.replace(",", "");
				arr.add(t3);
				String t4  = row.get(i+7).text();
				arr.add(t4);
				String t5 = row.get(i + 8).text();
				arr.add(t5);
				String t6 = row.get(i+9).text();
				arr.add(t6);
			}
		}
		return arr;
	}
	
	/**
	 * Update the database
	 * @param list
	 * @param team
	 */
	public void UpdateDatabase(ArrayList<String> list) {
		String team = "";
		int j = 0;
		for (int i = 0; i < list.size(); i++) {
			if ((j % 7) == 0) {
				team = list.get(i);
				j++;
			} else if ((j % 7) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (((j - 6) % 7) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushrank SET TD = " + resultInt + " WHERE Team = '" + team + "';";
					j = 0;
				} else if (((j - 5) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.rushrank SET Yds_G = " + resultDouble + " WHERE Team = '" + team + "';";
				} else if (((j - 4) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.rushrank SET Avge = " + resultDouble + " WHERE Team = '" + team + "';";
				} else if (((j - 3) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushrank SET Yds = " + resultInt + " WHERE Team = '" + team + "';";
				} else if (((j- 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.rushrank SET TotPts = " + resultInt + " WHERE Team = '" + team + "';";
				} else {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.rushrank SET Pts_G = " + resultDouble + " WHERE Team = '" + team + "';";
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
