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

public class SackRankStat extends Stat {
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("SackRankStat.class");
	private final Connection connection = database.establishConnection();
	public Document document;

	// empty constructor
	public SackRankStat() {

	}
	
	public SackRankStat(String team, double sacks) {
		super(team);

		String insert = "INSERT INTO footballstats.sackrank VALUES" + " ('" + team + "','" + sacks + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getSackStats() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?archive=false&conference=null&role=OPP&offensiveStatisticCategory=null&defensiveStatisticCategory=SACKS&season=2016&seasonType=REG&tabSeq=2&qualified=false&Submit=Go")
					.get();
		} catch (IOException e) {
			logger.log(Level.FINE,"Cannot get sack information.");
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 18 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				arr.add(t0);
				String t1 = row.get(i + 7).text();
				arr.add(t1);
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
			if ((i % 2) != 0) {
				String update = "";
				String result = list.get(i);
				Double resultDouble = Double.parseDouble(result);
				update = "UPDATE footballstats.sackrank SET Sacks = " + resultDouble + " WHERE Team = '" + team
						+ "';";
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
