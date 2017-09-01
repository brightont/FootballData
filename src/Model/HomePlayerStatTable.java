package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HomePlayerStatTable {
	private SimpleStringProperty homePlayer;

	private SimpleStringProperty home1;

	private SimpleStringProperty home2;
	
	private SimpleStringProperty home3;
	
	private SimpleStringProperty home4;
	
	private SimpleStringProperty home5;
	
	/**
	 * Constructor
	 * @param homePlayer
	 * @param home1
	 * @param home2
	 * @param home3
	 * @param home4
	 * @param home5
	 */
	public HomePlayerStatTable(String homePlayer, String home1, String home2, String home3, String home4, String home5) {
		this.homePlayer = new SimpleStringProperty(homePlayer);
		this.home1 = new SimpleStringProperty(home1);
		this.home2 = new SimpleStringProperty(home2);
		this.home3 = new SimpleStringProperty(home3);
		this.home4 = new SimpleStringProperty(home4);
		this.home5 = new SimpleStringProperty(home5);
	}
	
	public String getHomePlayer() {
		return homePlayer.get();
	}

	public String gethome1() {
		return home1.get();
	}

	public String gethome2() {
		return home2.get();
	}
	
	public String gethome3() {
		return home3.get();
	}
	
	public String gethome4() {
		return home4.get();
	}
	
	public String gethome5() {
		return home5.get();
	}
	
	public void setHomePlayer(String stat) {
		homePlayer.set(stat);
	}
	
	public void sethome1(String stat) {
		home1.set(stat);
	}
	
	public void sethome2(String stat) {
		home2.set(stat);
	}
	
	public void sethome3(String stat) {
		home3.set(stat);
	}
	
	public void sethome4(String stat) {
		home4.set(stat);
	}
	
	public void sethome5(String stat) {
		home5.set(stat);
	}
	
	public StringProperty homePlayerProperty() {
		return homePlayer;
	}
	
	public StringProperty home1Property() {
		return home1;
	}
	
	public StringProperty home2Property() {
		return home2;
	}
	
	public StringProperty home3Property() {
		return home3;
	}
	
	public StringProperty home4Property() {
		return home4;
	}
	
	public StringProperty home5Property() {
		return home5;
	}
}
