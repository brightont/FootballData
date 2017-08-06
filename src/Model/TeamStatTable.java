package Model;

import javafx.beans.property.SimpleStringProperty;

public class TeamStatTable {
	
	private SimpleStringProperty statName;

	private SimpleStringProperty homeStat;

	private SimpleStringProperty oppStat;
	
	
	/**
	 * Constructor
	 * @param statName
	 * @param homeStat
	 * @param oppStat
	 */
	public TeamStatTable(String statName, String homeStat, String oppStat) {
		this.statName = new SimpleStringProperty(statName);
		this.homeStat = new SimpleStringProperty(homeStat);
		this.oppStat = new SimpleStringProperty(oppStat);
	}
	
}
