package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RankStatTable {
	private SimpleStringProperty rankStat;

	private SimpleStringProperty homeRank;

	private SimpleStringProperty oppRank;
	
	/**
	 * Constructor
	 * @param rankStat
	 * @param homeRank
	 * @param oppRank
	 */
	public RankStatTable(String rankStat, String homeRank, String oppRank) {
		this.rankStat = new SimpleStringProperty(rankStat);
		this.homeRank = new SimpleStringProperty(homeRank);
		this.oppRank = new SimpleStringProperty(oppRank);
	}
	
	public String getRankStat() {
		return rankStat.get();
	}
	

	public String getHomeRank() {
		return homeRank.get();
	}

	public String getOppRank() {
		return oppRank.get();
	}
	
	public void setRankStat(String stat) {
		rankStat.set(stat);
	}
	
	public void setHomeRank(String stat) {
		homeRank.set(stat);
	}
	
	public void setOppRank(String stat) {
		oppRank.set(stat);
	}
	
	public StringProperty rankStatProperty() {
		return rankStat;
	}
	
	public StringProperty homeRankProperty() {
		return homeRank;
	}
	
	public StringProperty oppRankProperty() {
		return oppRank;
	
	}
}
