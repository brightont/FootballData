package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HomeDefStatTable {
	private SimpleStringProperty dhomePlayer;

	private SimpleStringProperty dhomeComb;

	private SimpleStringProperty dhomeTotal;
	
	private SimpleStringProperty dhomeAssist;
	
	private SimpleStringProperty dhomeSck;
	
	private SimpleStringProperty dhomeFumb;
	
	
	public HomeDefStatTable(String dhomePlayer, String dhomeComb, String dhomeTotal, String dhomeAssist,
			String dhomeSck, String dhomeFumb) {
		this.dhomePlayer = new SimpleStringProperty(dhomePlayer);
		this.dhomeComb = new SimpleStringProperty(dhomeComb);
		this.dhomeTotal = new SimpleStringProperty(dhomeTotal);
		this.dhomeAssist = new SimpleStringProperty(dhomeAssist);
		this.dhomeSck = new SimpleStringProperty(dhomeSck);
		this.dhomeFumb = new SimpleStringProperty(dhomeFumb);
	}
	
	public String getDhomePlayer() {
		return dhomePlayer.get();
	}

	public String getDhomeComb() {
		return dhomeComb.get();
	}

	public String getDhomeTotal() {
		return dhomeTotal.get();
	}
	
	public String getDhomeAssist() {
		return dhomeAssist.get();
	}
	
	public String getDhomeSck() {
		return dhomeSck.get();
	}
	
	public String getDhomeFumb() {
		return dhomeFumb.get();
	}
	
	public void setDhomePlayer(String stat) {
		dhomePlayer.set(stat);
	}
	
	public void setDhomeComb(String stat) {
		dhomeComb.set(stat);
	}
	
	public void setDhomeTotal(String stat) {
		dhomeTotal.set(stat);
	}
	
	public void setDhomeAssist(String stat) {
		dhomeAssist.set(stat);
	}
	
	public void setDhomeSck(String stat) {
		dhomeSck.set(stat);
	}
	
	public void setDhomeFumb(String stat) {
		dhomeFumb.set(stat);
	}
	
	public StringProperty dhomePlayerProperty() {
		return dhomePlayer;
	}
	
	public StringProperty dhomeCombProperty() {
		return dhomeComb;
	}
	
	public StringProperty dhomeTotalProperty() {
		return dhomeTotal;
	}
	
	public StringProperty dhomeAssistProperty() {
		return dhomeAssist;
	}
	
	public StringProperty dhomeSckProperty() {
		return dhomeSck;
	}
	
	public StringProperty dhomeFumbProperty() {
		return dhomeFumb;
	}

}
