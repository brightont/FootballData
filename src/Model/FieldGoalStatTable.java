package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FieldGoalStatTable {
	private SimpleStringProperty fgStatName;

	private SimpleStringProperty fgHomeStat;

	private SimpleStringProperty fgOppStat;
	
	
	/**
	 * Constructor
	 * @param fgStatName
	 * @param fgHomeStat
	 * @param fgOppStat
	 */
	public FieldGoalStatTable(String fgStatName, String fgHomeStat, String fgOppStat) {
		this.fgStatName = new SimpleStringProperty(fgStatName);
		this.fgHomeStat = new SimpleStringProperty(fgHomeStat);
		this.fgOppStat = new SimpleStringProperty(fgOppStat);
	}
	
	public String getfgStatName() {
		return fgStatName.get();
	}
	

	public String getfgHomeStat() {
		return fgHomeStat.get();
	}

	public String getfgOppStat() {
		return fgOppStat.get();
	}
	
	public void setfgStatName(String stat) {
		fgStatName.set(stat);
	}
	
	public void setfgHomeStat(String stat) {
		fgHomeStat.set(stat);
	}
	
	public void setfgOppStat(String stat) {
		fgOppStat.set(stat);
	}
	
	public StringProperty fgStatNameProperty() {
		return fgStatName;
	}
	
	public StringProperty fgHomeStatProperty() {
		return fgHomeStat;
	}
	
	public StringProperty fgOppStatProperty() {
		return fgOppStat;
	}
}
