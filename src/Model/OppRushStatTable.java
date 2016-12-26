package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OppRushStatTable {
	private SimpleStringProperty oppPlayer;

	private SimpleStringProperty oppAtt;

	private SimpleStringProperty oppYds;
	
	private SimpleStringProperty oppYA;
	
	private SimpleStringProperty oppLong;
	
	private SimpleStringProperty oppTD;
	
	
	
	public OppRushStatTable(String oppPlayer, String oppAtt, String oppYds, String oppYA, String oppLong, String oppTD) {
		this.oppPlayer = new SimpleStringProperty(oppPlayer);
		this.oppAtt = new SimpleStringProperty(oppAtt);
		this.oppYds = new SimpleStringProperty(oppYds);
		this.oppYA = new SimpleStringProperty(oppYA);
		this.oppLong = new SimpleStringProperty(oppLong);
		this.oppTD = new SimpleStringProperty(oppTD);
	}
	
	public String getOppPlayer() {
		return oppPlayer.get();
	}

	public String getOppAtt() {
		return oppAtt.get();
	}

	public String getOppYds() {
		return oppYds.get();
	}
	
	public String getOppYA() {
		return oppYA.get();
	}
	
	public String getOppLong() {
		return oppLong.get();
	}
	
	public String getOppTD() {
		return oppTD.get();
	}
	
	public void setOppPlayer(String stat) {
		oppPlayer.set(stat);
	}
	
	public void setOppAtt(String stat) {
		oppAtt.set(stat);
	}
	
	public void setOppYds(String stat) {
		oppYds.set(stat);
	}
	
	public void setOppYA(String stat) {
		oppYA.set(stat);
	}
	
	public void setOppLong(String stat) {
		oppLong.set(stat);
	}
	
	public void setOppTD(String stat) {
		oppTD.set(stat);
	}
	
	public StringProperty oppPlayerProperty() {
		return oppPlayer;
	}
	
	public StringProperty oppAttProperty() {
		return oppAtt;
	}
	
	public StringProperty oppYdsProperty() {
		return oppYds;
	}
	
	public StringProperty oppYAProperty() {
		return oppYA;
	}
	
	public StringProperty oppLongProperty() {
		return oppLong;
	}
	
	public StringProperty oppTDProperty() {
		return oppTD;
	}
}
