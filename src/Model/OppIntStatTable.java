package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OppIntStatTable {
	private SimpleStringProperty ioppPlayer;

	private SimpleStringProperty ioppInt;

	private SimpleStringProperty ioppYds;
	
	private SimpleStringProperty ioppYI;
	
	private SimpleStringProperty ioppLong;
	
	private SimpleStringProperty ioppTD;
	
	
	
	public OppIntStatTable(String ioppPlayer, String ioppInt, String ioppYds, String ioppYI, String ioppLong, String ioppTD) {
		this.ioppPlayer = new SimpleStringProperty(ioppPlayer);
		this.ioppInt = new SimpleStringProperty(ioppInt);
		this.ioppYds = new SimpleStringProperty(ioppYds);
		this.ioppYI = new SimpleStringProperty(ioppYI);
		this.ioppLong = new SimpleStringProperty(ioppLong);
		this.ioppTD = new SimpleStringProperty(ioppTD);
	}
	
	public String getioppPlayer() {
		return ioppPlayer.get();
	}

	public String getioppInt() {
		return ioppInt.get();
	}

	public String getioppYds() {
		return ioppYds.get();
	}
	
	public String getioppYA() {
		return ioppYI.get();
	}
	
	public String getioppLong() {
		return ioppLong.get();
	}
	
	public String getioppTD() {
		return ioppTD.get();
	}
	
	public void setioppPlayer(String stat) {
		ioppPlayer.set(stat);
	}
	
	public void setioppInt(String stat) {
		ioppInt.set(stat);
	}
	
	public void setioppYds(String stat) {
		ioppYds.set(stat);
	}
	
	public void setioppYI(String stat) {
		ioppYI.set(stat);
	}
	
	public void setioppLong(String stat) {
		ioppLong.set(stat);
	}
	
	public void setioppTD(String stat) {
		ioppTD.set(stat);
	}
	
	public StringProperty ioppPlayerProperty() {
		return ioppPlayer;
	}
	
	public StringProperty ioppIntProperty() {
		return ioppInt;
	}
	
	public StringProperty ioppYdsProperty() {
		return ioppYds;
	}
	
	public StringProperty ioppYIProperty() {
		return ioppYI;
	}
	
	public StringProperty ioppLongProperty() {
		return ioppLong;
	}
	
	public StringProperty ioppTDProperty() {
		return ioppTD;
	}
}
