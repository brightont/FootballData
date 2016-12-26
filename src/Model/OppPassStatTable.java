package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OppPassStatTable {
	private SimpleStringProperty poppPlayer;

	private SimpleStringProperty poppRec;

	private SimpleStringProperty poppYds;
	
	private SimpleStringProperty poppYR;
	
	private SimpleStringProperty poppLong;
	
	private SimpleStringProperty poppTD;
	
	
	
	public OppPassStatTable(String poppPlayer, String poppRec, String poppYds, String poppYR, String poppLong, String poppTD) {
		this.poppPlayer = new SimpleStringProperty(poppPlayer);
		this.poppRec = new SimpleStringProperty(poppRec);
		this.poppYds = new SimpleStringProperty(poppYds);
		this.poppYR = new SimpleStringProperty(poppYR);
		this.poppLong = new SimpleStringProperty(poppLong);
		this.poppTD = new SimpleStringProperty(poppTD);
	}
	
	public String getpoppPlayer() {
		return poppPlayer.get();
	}

	public String getpoppRec() {
		return poppRec.get();
	}

	public String getpoppYds() {
		return poppYds.get();
	}
	
	public String getpoppYA() {
		return poppYR.get();
	}
	
	public String getpoppLong() {
		return poppLong.get();
	}
	
	public String getpoppTD() {
		return poppTD.get();
	}
	
	public void setpoppPlayer(String stat) {
		poppPlayer.set(stat);
	}
	
	public void setpoppRec(String stat) {
		poppRec.set(stat);
	}
	
	public void setpoppYds(String stat) {
		poppYds.set(stat);
	}
	
	public void setpoppYR(String stat) {
		poppYR.set(stat);
	}
	
	public void setpoppLong(String stat) {
		poppLong.set(stat);
	}
	
	public void setpoppTD(String stat) {
		poppTD.set(stat);
	}
	
	public StringProperty poppPlayerProperty() {
		return poppPlayer;
	}
	
	public StringProperty poppRecProperty() {
		return poppRec;
	}
	
	public StringProperty poppYdsProperty() {
		return poppYds;
	}
	
	public StringProperty poppYRProperty() {
		return poppYR;
	}
	
	public StringProperty poppLongProperty() {
		return poppLong;
	}
	
	public StringProperty poppTDProperty() {
		return poppTD;
	}
}
