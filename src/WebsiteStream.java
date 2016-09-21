import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebsiteStream {
    private static final Logger log = Logger.getLogger("WebsiteStream.class");
    public Document document;
    public HashMap<String, String> homeStatistics = new HashMap<String, String>();
    public HashMap<String, String> opponentStatistics = new HashMap<String, String>();
    
    public HashMap<String, String> getHomeStatistics() {
		return homeStatistics;
	}

	public HashMap<String, String> getOpponentStatistics() {
		return opponentStatistics;
	}
    
    public void streamWebsite() {
        loadWebsite();
    	getTeamStats();
    }
  
    public void loadWebsite() {
    	try {
    		document = Jsoup.connect("http://www.nfl.com/teams/statistics?team=ATL").get();
    	} catch (IOException e) {
            log.info("Failed to get information.");
        }
    }
    
	public void getTeamStats() {
		Elements teamStats = document.getElementsByClass("team-stats");
		Elements teamStatsRow = teamStats.select("td");
		for (int i = 0; i < teamStatsRow.size(); i++) {
			String keyHash = "";
			String valueHash = "";
			String oppValueHash = "";
			if (((i - 1) % 3) == 0 && ((i + 2) < teamStatsRow.size())) {
				keyHash = teamStatsRow.get(i).text();
				valueHash = teamStatsRow.get(i + 1).text();
				oppValueHash = teamStatsRow.get(i + 2).text();
			}
			homeStatistics.put(keyHash, valueHash);
			opponentStatistics.put(keyHash, oppValueHash);
		}
	}
    
    
    
        
}