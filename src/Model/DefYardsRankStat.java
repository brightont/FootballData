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

public class DefYardsRankStat extends Stat {
	
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("DefYardsRankStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	// empty constructor
	public DefYardsRankStat() {

	}
	
	public DefYardsRankStat(String team, double pg, int totpts, double yg) {
		super(team);

		String insert = "INSERT INTO footballstats.defyardsrank VALUES" + " ('" + team + "','" + pg + "','" + totpts + "','" + yg + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getDefYardsStats() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?archive=false&conference=null&role=OPP&offensiveStatisticCategory=null&defensiveStatisticCategory=TOTAL_YARDS&season=2016&seasonType=REG&tabSeq=2&qualified=false&Submit=Go")
					.get();
		} catch (IOException e) {
			
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 20 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				arr.add(t0);
				String t1 = row.get(i + 2).text();
				arr.add(t1);
				String t2 = row.get(i + 3).text();
				arr.add(t2);
				String t3 = row.get(i + 5).text();
				arr.add(t3);
			}
		}
		return arr;
	}
	
	/**
	 * Update the database
	 * @param list
	 * @param team
	 */
	public void updateDatabase(ArrayList<String> list) {
		String team = "";
		int j = 0;
		for (int i = 0; i < list.size(); i++) {
			if ((j % 4) == 0) {
				team = list.get(i);
				j++;
			} else if ((j % 4) != 0) {
				String update = "";
				String result = list.get(i);
				double resultDouble;
				int resultInt;
				if (((j - 3) % 4) == 0) {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.defyardsrank SET Yds_G = " + resultDouble + " WHERE Team = '" + team + "';";
					j = 0;
				} else if (((j - 2) % 6) == 0) {
					resultInt = Integer.parseInt(result);
					update = "UPDATE footballstats.defyardsrank SET TotPts = " + resultInt + " WHERE Team = '" + team + "';";
				} else {
					resultDouble = Double.parseDouble(result);
					update = "UPDATE footballstats.defyardsrank SET Pts_G = " + resultDouble + " WHERE Team = '" + team + "';";
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
