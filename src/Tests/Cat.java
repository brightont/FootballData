package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
							"http://www.nfl.com/teams/atlantafalcons/statistics?team=ATL")
					.get();
		} catch (IOException e) {
			
		}
		Elements elements = document.getElementsByClass("team-quick-stat-body");
		for (int i = 0; i < 4; i++) {
			String temp = elements.get(i).text();
			String[] tempArr = temp.split("\\(");
			System.out.println(tempArr[0] + "DOD");
			String[] tempArr2 = tempArr[1].split("\\)");
			System.out.println(tempArr2[0] + "DDD");
			char place = tempArr[1].charAt(0);
			System.out.println(place + "place");
			
		}
		/*for (Element e : elements) {
			System.out.println(e.text() + "DOD");
		}*/
		/*for (int i = 0; i < miniClass.size(); i++) {
			System.out.println(i);
		}*/

	}

}
