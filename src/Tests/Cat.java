package Tests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Model.Model;

public class Cat {
	private Model database = new Model();
	private final Connection connection = database.establishConnection();
	public Document document;

	public void getData() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?archive=false&conference=null&role=OPP&offensiveStatisticCategory=null&defensiveStatisticCategory=SACKS&season=2016&seasonType=REG&tabSeq=2&qualified=false&Submit=Go")
					.get();
		} catch (IOException e) {
			
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 18 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				String t1 = row.get(i + 7).text();
				double d1 = Double.parseDouble(t1);
		
				String insert = "INSERT INTO footballstats.sackrank VALUES" + " ('" + t0 + "','" + d1 + "');";

				//System.out.println(insert);
				try {
					PreparedStatement prepStatement = connection.prepareStatement(insert);
					prepStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

}
