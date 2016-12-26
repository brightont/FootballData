package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HomeRushStatTable {
	private SimpleStringProperty homePlayer;

	private SimpleStringProperty homeAtt;

	private SimpleStringProperty homeYds;
	
	private SimpleStringProperty homeYA;
	
	private SimpleStringProperty homeLong;
	
	private SimpleStringProperty homeTD;
	
	
	
	public HomeRushStatTable(String homePlayer, String homeAtt, String homeYds, String homeYA, String homeLong, String homeTD) {
		this.homePlayer = new SimpleStringProperty(homePlayer);
		this.homeAtt = new SimpleStringProperty(homeAtt);
		this.homeYds = new SimpleStringProperty(homeYds);
		this.homeYA = new SimpleStringProperty(homeYA);
		this.homeLong = new SimpleStringProperty(homeLong);
		this.homeTD = new SimpleStringProperty(homeTD);
	}
	
	public String getHomePlayer() {
		return homePlayer.get();
	}

	public String getHomeAtt() {
		return homeAtt.get();
	}

	public String getHomeYds() {
		return homeYds.get();
	}
	
	public String getHomeYA() {
		return homeYA.get();
	}
	
	public String getHomeLong() {
		return homeLong.get();
	}
	
	public String getHomeTD() {
		return homeTD.get();
	}
	
	public void setHomePlayer(String stat) {
		homePlayer.set(stat);
	}
	
	public void setHomeAtt(String stat) {
		homeAtt.set(stat);
	}
	
	public void setHomeYds(String stat) {
		homeYds.set(stat);
	}
	
	public void setHomeYA(String stat) {
		homeYA.set(stat);
	}
	
	public void setHomeLong(String stat) {
		homeLong.set(stat);
	}
	
	public void setHomeTD(String stat) {
		homeTD.set(stat);
	}
	
	public StringProperty homePlayerProperty() {
		return homePlayer;
	}
	
	public StringProperty homeAttProperty() {
		return homeAtt;
	}
	
	public StringProperty homeYdsProperty() {
		return homeYds;
	}
	
	public StringProperty homeYAProperty() {
		return homeYA;
	}
	
	public StringProperty homeLongProperty() {
		return homeLong;
	}
	
	public StringProperty homeTDProperty() {
		return homeTD;
	}
}
