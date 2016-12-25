package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stat {
	
	private SimpleStringProperty statName;

	private SimpleStringProperty homeStat;

	private SimpleStringProperty oppStat;
	
	
	/**
	 * Constructor
	 * @param statName
	 * @param homeStat
	 * @param oppStat
	 */
	public Stat(String statName, String homeStat, String oppStat) {
		this.statName = new SimpleStringProperty(statName);
		this.homeStat = new SimpleStringProperty(homeStat);
		this.oppStat = new SimpleStringProperty(oppStat);
	}
	
	public String getStatName() {
		return statName.get();
	}
	

	public String getHomeStat() {
		return homeStat.get();
	}

	public String getOppStat() {
		return oppStat.get();
	}
	
	public void setStatName(String stat) {
		statName.set(stat);
	}
	
	public void setHomeStat(String stat) {
		homeStat.set(stat);
	}
	
	public void setOppStat(String stat) {
		oppStat.set(stat);
	}
	
	public StringProperty statNameProperty() {
		return statName;
	}
	
	public StringProperty homeStatProperty() {
		return homeStat;
	}
	
	public StringProperty oppStatProperty() {
		return oppStat;
	}
	
}
