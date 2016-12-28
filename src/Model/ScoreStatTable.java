package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScoreStatTable {
	
	private SimpleStringProperty week;

	private SimpleStringProperty opp;

	private SimpleStringProperty score;
	
	
	/**
	 * Constructor
	 * @param week
	 * @param opp
	 * @param score
	 */
	public ScoreStatTable(String week, String opp, String score) {
		this.week = new SimpleStringProperty(week);
		this.opp = new SimpleStringProperty(opp);
		this.score = new SimpleStringProperty(score);
	}
	
	public String getWeek() {
		return week.get();
	}
	

	public String getOpp() {
		return opp.get();
	}

	public String getScore() {
		return score.get();
	}
	
	public void setWeek(String stat) {
		week.set(stat);
	}
	
	public void setopp(String stat) {
		opp.set(stat);
	}
	
	public void setScore(String stat) {
		score.set(stat);
	}
	
	public StringProperty weekProperty() {
		return week;
	}
	
	public StringProperty oppProperty() {
		return opp;
	}
	
	public StringProperty scoreProperty() {
		return score;
	}

}
