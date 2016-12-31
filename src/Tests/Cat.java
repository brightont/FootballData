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
							"http://www.nfl.com/stats/categorystats?seasonType=REG&offensiveStatisticCategory=RUSHING&d-447263-n=1&d-447263-o=2&d-447263-p=1&d-447263-s=RUSHING_ATTEMPTS&tabSeq=2&season=2016&role=TM&Submit=Go&archive=false&conference=null&defensiveStatisticCategory=null&qualified=false")
					.get();
		} catch (IOException e) {
			
		}
		Elements elements = document.getElementsByClass("data-table1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1) % 21 == 0) {
				String temp = row.get(i).text();
				String[] tarr = temp.split(" ");
				String t0 = tarr[tarr.length - 1];
				String t1 = row.get(i + 2).text();
				double d1 = Double.parseDouble(t1);
				String t2 = row.get(i + 3).text();
				int i2 = Integer.parseInt(t2);
				String t3 = row.get(i + 6).text();
				t3 = t3.replace(",", "");
				int i3 = Integer.parseInt(t3);
				String t4  = row.get(i+7).text();
				double d4 = Double.parseDouble(t4);
				String t5 = row.get(i + 8).text();
				double d5 = Double.parseDouble(t5);

				String insert = "INSERT INTO footballstats.yardsrank VALUES" + " ('" + t0 + "','" + d1 + "','" + i2
						+ "','" + i3 + "','" + d4 + "','" + d5 + "');";

				System.out.println(insert);
				/*try {
					PreparedStatement prepStatement = connection.prepareStatement(insert);
					prepStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}*/
				
			}
		}

	}

}
