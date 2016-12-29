package Tests;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Model.Model;

public class RankingTest {
	private Model database = new Model();
	private final Connection connection = database.establishConnection();
	public Document document;

	public void getData() {
		ArrayList<String> arr = new ArrayList<String>();
		try {
			document = Jsoup
					.connect(
							"http://www.nfl.com/stats/categorystats?archive=false&conference=null&role=TM&offensiveStatisticCategory=TOTAL_YARDS&defensiveStatisticCategory=null&season=2016&seasonType=REG&tabSeq=2&qualified=false&Submit=Go")
					.get();
		} catch (IOException e) {
			
		}
		Elements elements = document.getElementsByClass("thd1");
		Elements row = elements.select("td");
		for (int i = 1; i < row.size(); i++) {
			if ((i - 1 ) % 21 == 0) {
				String temp = row.get(i+1).text();
				String t2 = row.get(i+3).text();
				String t3 = row.get(i+4).text();
				String t4 = row.get(i+6).text();
				System.out.println(temp + " " + t2 + " " + t3 + " " + t4);
			}
		}

	}

}
