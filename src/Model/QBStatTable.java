package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class QBStatTable {
	private SimpleStringProperty qbStatName;

	private SimpleStringProperty qbHomeStat;

	private SimpleStringProperty qbOppStat;
	
	
	/**
	 * Constructor
	 * @param qbStatName
	 * @param qbHomeStat
	 * @param qbOppStat
	 */
	public QBStatTable(String qbStatName, String qbHomeStat, String qbOppStat) {
		this.qbStatName = new SimpleStringProperty(qbStatName);
		this.qbHomeStat = new SimpleStringProperty(qbHomeStat);
		this.qbOppStat = new SimpleStringProperty(qbOppStat);
	}
	
	public String getqbStatName() {
		return qbStatName.get();
	}
	

	public String getqbHomeStat() {
		return qbHomeStat.get();
	}

	public String getqbOppStat() {
		return qbOppStat.get();
	}
	
	public void setqbStatName(String stat) {
		qbStatName.set(stat);
	}
	
	public void setqbHomeStat(String stat) {
		qbHomeStat.set(stat);
	}
	
	public void setqbOppStat(String stat) {
		qbOppStat.set(stat);
	}
	
	public StringProperty qbStatNameProperty() {
		return qbStatName;
	}
	
	public StringProperty qbHomeStatProperty() {
		return qbHomeStat;
	}
	
	public StringProperty qbOppStatProperty() {
		return qbOppStat;
	}
}
