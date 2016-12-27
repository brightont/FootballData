package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HomeIntStatTable {
	private SimpleStringProperty ihomePlayer;

	private SimpleStringProperty ihomeInt;

	private SimpleStringProperty ihomeYds;
	
	private SimpleStringProperty ihomeYI;
	
	private SimpleStringProperty ihomeLong;
	
	private SimpleStringProperty ihomeTD;
	
	
	
	public HomeIntStatTable(String ihomePlayer, String ihomeInt, String ihomeYds, String ihomeYI, String ihomeLong, String ihomeTD) {
		this.ihomePlayer = new SimpleStringProperty(ihomePlayer);
		this.ihomeInt = new SimpleStringProperty(ihomeInt);
		this.ihomeYds = new SimpleStringProperty(ihomeYds);
		this.ihomeYI = new SimpleStringProperty(ihomeYI);
		this.ihomeLong = new SimpleStringProperty(ihomeLong);
		this.ihomeTD = new SimpleStringProperty(ihomeTD);
	}
	
	public String getihomePlayer() {
		return ihomePlayer.get();
	}

	public String getihomeInt() {
		return ihomeInt.get();
	}

	public String getihomeYds() {
		return ihomeYds.get();
	}
	
	public String getihomeYA() {
		return ihomeYI.get();
	}
	
	public String getihomeLong() {
		return ihomeLong.get();
	}
	
	public String getihomeTD() {
		return ihomeTD.get();
	}
	
	public void setihomePlayer(String stat) {
		ihomePlayer.set(stat);
	}
	
	public void setihomeInt(String stat) {
		ihomeInt.set(stat);
	}
	
	public void setihomeYds(String stat) {
		ihomeYds.set(stat);
	}
	
	public void setihomeYI(String stat) {
		ihomeYI.set(stat);
	}
	
	public void setihomeLong(String stat) {
		ihomeLong.set(stat);
	}
	
	public void setihomeTD(String stat) {
		ihomeTD.set(stat);
	}
	
	public StringProperty ihomePlayerProperty() {
		return ihomePlayer;
	}
	
	public StringProperty ihomeIntProperty() {
		return ihomeInt;
	}
	
	public StringProperty ihomeYdsProperty() {
		return ihomeYds;
	}
	
	public StringProperty ihomeYIProperty() {
		return ihomeYI;
	}
	
	public StringProperty ihomeLongProperty() {
		return ihomeLong;
	}
	
	public StringProperty ihomeTDProperty() {
		return ihomeTD;
	}
}
