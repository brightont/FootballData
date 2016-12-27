package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OppDefStatTable {
	private SimpleStringProperty doppPlayer;

	private SimpleStringProperty doppComb;

	private SimpleStringProperty doppTotal;
	
	private SimpleStringProperty doppAssist;
	
	private SimpleStringProperty doppSck;
	
	private SimpleStringProperty doppFumb;
	
	
	public OppDefStatTable(String doppPlayer, String doppComb, String doppTotal, String doppAssist,
			String doppSck, String doppFumb) {
		this.doppPlayer = new SimpleStringProperty(doppPlayer);
		this.doppComb = new SimpleStringProperty(doppComb);
		this.doppTotal = new SimpleStringProperty(doppTotal);
		this.doppAssist = new SimpleStringProperty(doppAssist);
		this.doppSck = new SimpleStringProperty(doppSck);
		this.doppFumb = new SimpleStringProperty(doppFumb);
	}
	
	public String getDoppPlayer() {
		return doppPlayer.get();
	}

	public String getDoppComb() {
		return doppComb.get();
	}

	public String getDoppTotal() {
		return doppTotal.get();
	}
	
	public String getDoppAssist() {
		return doppAssist.get();
	}
	
	public String getDoppSck() {
		return doppSck.get();
	}
	
	public String getDoppFumb() {
		return doppFumb.get();
	}
	
	public void setDoppPlayer(String stat) {
		doppPlayer.set(stat);
	}
	
	public void setDoppComb(String stat) {
		doppComb.set(stat);
	}
	
	public void setDoppTotal(String stat) {
		doppTotal.set(stat);
	}
	
	public void setDoppAssist(String stat) {
		doppAssist.set(stat);
	}
	
	public void setDoppSck(String stat) {
		doppSck.set(stat);
	}
	
	public void setDoppFumb(String stat) {
		doppFumb.set(stat);
	}
	
	public StringProperty doppPlayerProperty() {
		return doppPlayer;
	}
	
	public StringProperty doppCombProperty() {
		return doppComb;
	}
	
	public StringProperty doppTotalProperty() {
		return doppTotal;
	}
	
	public StringProperty doppAssistProperty() {
		return doppAssist;
	}
	
	public StringProperty doppSckProperty() {
		return doppSck;
	}
	
	public StringProperty doppFumbProperty() {
		return doppFumb;
	}
}
