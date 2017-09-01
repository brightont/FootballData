package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OppPlayerStatTable {
	private SimpleStringProperty oppPlayer;

	private SimpleStringProperty opp1;

	private SimpleStringProperty opp2;
	
	private SimpleStringProperty opp3;
	
	private SimpleStringProperty opp4;
	
	private SimpleStringProperty opp5;
	
	/**
	 * Constructor
	 * @param oppPlayer
	 * @param opp1
	 * @param opp2
	 * @param opp3
	 * @param opp4
	 * @param opp5
	 */
	public OppPlayerStatTable(String oppPlayer, String opp1, String opp2, String opp3, String opp4, String opp5) {
		this.oppPlayer = new SimpleStringProperty(oppPlayer);
		this.opp1 = new SimpleStringProperty(opp1);
		this.opp2 = new SimpleStringProperty(opp2);
		this.opp3 = new SimpleStringProperty(opp3);
		this.opp4 = new SimpleStringProperty(opp4);
		this.opp5 = new SimpleStringProperty(opp5);
	}
	
	public String getOppPlayer() {
		return oppPlayer.get();
	}

	public String getopp1() {
		return opp1.get();
	}

	public String getopp2() {
		return opp2.get();
	}
	
	public String getopp3() {
		return opp3.get();
	}
	
	public String getopp4() {
		return opp4.get();
	}
	
	public String getopp5() {
		return opp5.get();
	}
	
	public void setOppPlayer(String stat) {
		oppPlayer.set(stat);
	}
	
	public void setopp1(String stat) {
		opp1.set(stat);
	}
	
	public void setopp2(String stat) {
		opp2.set(stat);
	}
	
	public void setopp3(String stat) {
		opp3.set(stat);
	}
	
	public void setopp4(String stat) {
		opp4.set(stat);
	}
	
	public void setopp5(String stat) {
		opp5.set(stat);
	}
	
	public StringProperty oppPlayerProperty() {
		return oppPlayer;
	}
	
	public StringProperty opp1Property() {
		return opp1;
	}
	
	public StringProperty opp2Property() {
		return opp2;
	}
	
	public StringProperty opp3Property() {
		return opp3;
	}
	
	public StringProperty opp4Property() {
		return opp4;
	}
	
	public StringProperty opp5Property() {
		return opp5;
	}
}
