package Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Team {
	private static final Logger log = Logger.getLogger("Model.class");
	
	public Document document;
    public HashMap<String, String> homeStatistics = new HashMap<String, String>();
    
    public HashMap<String, String> getHomeStatistics() {
		return homeStatistics;
	}
	
    /**
     * Gets the statistics off NFL website
     * @param team
     */
	public void getTeamStats(String team) {
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=" + team).get();
		} catch (IOException e) {
			log.info("Failed to get information.");
		}
		Elements teamStats = document.getElementsByClass("data-table1 team-stats");
		Elements teamStatsRow = teamStats.select("td");
		System.out.println(teamStatsRow.size());
		for (int i = 0; i < teamStatsRow.size(); i++) {
			if (((i-1) % 3) == 0) {
				String keyHash = "";
				String valueHash = "";

				keyHash = teamStatsRow.get(i).text();
				valueHash = teamStatsRow.get(i + 1).text();
				homeStatistics.put(keyHash, valueHash);
			}
		}
				
	}
}
