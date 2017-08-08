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

public class YardsRankStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("YardsRankStat.class");
	private final Connection connection = database.EstablishConnection();
	public Document document;

	/**
	 * Empty constructor
	 */
	public YardsRankStat() {

	}
	
	/**
	 * Add yard rank stat to the database
	 * @param team
	 * @param pg
	 * @param totpts
	 * @param sp
	 * @param yg
	 * @param yp
	 */
	public YardsRankStat(String team, double pg, int totpts, int sp, double yg, double yp) {
		super(team);

		String insert = "INSERT INTO footballstats.yardsrank VALUES" + " ('" + team + "','" + pg + "','" + totpts
				+ "','" + sp + "','" + yg + "','" + yp + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get yard stats from the NFL website
	 * @return
	 */
	public ArrayList<String> GetYardsStats() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?archive=false&conference=null&role=TM&offensiveStatisticCategory=TOTAL_YARDS&defensiveStatisticCategory=null&season=2016&seasonType=REG&tabSeq=2&qualified=false&Submit=Go")
					.get();
		} catch (IOException e) {
			logger.info("Failed to get yards rank statistics.");
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 21 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				arr.add(t0);
				String t1 = row.get(i + 2).text();
				arr.add(t1);
				String t2 = row.get(i + 3).text();
				arr.add(t2);
				String t3 = row.get(i + 4).text();
				t3 = t3.replace(",", "");
				arr.add(t3);
				String t4 = row.get(i + 5).text();
				arr.add(t4);
				String t5 = row.get(i + 6).text();
				arr.add(t5);
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
		for (int i = 0; i < list.size(); i++) {
			if ((i % 6) == 0) {
				team = list.get(i);
			} else if ((i % 6) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (((i - 5) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.yardsrank SET Yds_P = " + resultDouble + " WHERE Team = '" + team + "';";
				} else if (((i - 4) % 6) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.yardsrank SET Yds_G = " + resultDouble + " WHERE Team = '" + team + "';";
				} else if (((i - 3) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.yardsrank SET Scrm_Plys = " + resultInt + " WHERE Team = '" + team + "';";
				} else if (((i - 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.yardsrank SET TotPts = " + resultInt + " WHERE Team = '" + team + "';";
				} else {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.yardsrank SET Pts_G = " + resultDouble + " WHERE Team = '" + team + "';";
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
