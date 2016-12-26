package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HomePassStatTable {
	private SimpleStringProperty phomePlayer;

	private SimpleStringProperty phomeRec;

	private SimpleStringProperty phomeYds;
	
	private SimpleStringProperty phomeYR;
	
	private SimpleStringProperty phomeLong;
	
	private SimpleStringProperty phomeTD;
	
	
	
	public HomePassStatTable(String phomePlayer, String phomeRec, String phomeYds, String phomeYR, String phomeLong, String phomeTD) {
		this.phomePlayer = new SimpleStringProperty(phomePlayer);
		this.phomeRec = new SimpleStringProperty(phomeRec);
		this.phomeYds = new SimpleStringProperty(phomeYds);
		this.phomeYR = new SimpleStringProperty(phomeYR);
		this.phomeLong = new SimpleStringProperty(phomeLong);
		this.phomeTD = new SimpleStringProperty(phomeTD);
	}
	
	public String getPhomePlayer() {
		return phomePlayer.get();
	}

	public String getPhomeRec() {
		return phomeRec.get();
	}

	public String getPhomeYds() {
		return phomeYds.get();
	}
	
	public String getPhomeYA() {
		return phomeYR.get();
	}
	
	public String getPhomeLong() {
		return phomeLong.get();
	}
	
	public String getPhomeTD() {
		return phomeTD.get();
	}
	
	public void setPhomePlayer(String stat) {
		phomePlayer.set(stat);
	}
	
	public void setPhomeRec(String stat) {
		phomeRec.set(stat);
	}
	
	public void setPhomeYds(String stat) {
		phomeYds.set(stat);
	}
	
	public void setPhomeYR(String stat) {
		phomeYR.set(stat);
	}
	
	public void setPhomeLong(String stat) {
		phomeLong.set(stat);
	}
	
	public void setPhomeTD(String stat) {
		phomeTD.set(stat);
	}
	
	public StringProperty phomePlayerProperty() {
		return phomePlayer;
	}
	
	public StringProperty phomeRecProperty() {
		return phomeRec;
	}
	
	public StringProperty phomeYdsProperty() {
		return phomeYds;
	}
	
	public StringProperty phomeYRProperty() {
		return phomeYR;
	}
	
	public StringProperty phomeLongProperty() {
		return phomeLong;
	}
	
	public StringProperty phomeTDProperty() {
		return phomeTD;
	}

}
