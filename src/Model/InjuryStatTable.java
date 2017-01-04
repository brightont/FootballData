package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InjuryStatTable {
	private SimpleStringProperty ihome;
	
	private SimpleStringProperty homeStatus;

	private SimpleStringProperty iopp;
	
	private SimpleStringProperty oppStatus;
	
	
	/**
	 * Constructor
	 * @param ihome
	 * @param iopp
	 * @param homeStatus
	 */
	public InjuryStatTable(String ihome, String homeStatus, String iopp, String oppStatus) {
		this.ihome = new SimpleStringProperty(ihome);
		this.homeStatus = new SimpleStringProperty(homeStatus);
		this.iopp = new SimpleStringProperty(iopp);
		this.oppStatus = new SimpleStringProperty(oppStatus);
	}
	
	public String getIhome() {
		return ihome.get();
	}

	public String getHomeStatus() {
		return homeStatus.get();
	}
	
	public String getIopp() {
		return iopp.get();
	}
	
	public String getOppStatus() {
		return oppStatus.get();
	}
	
	public void setIhome(String stat) {
		ihome.set(stat);
	}
	
	public void setHomeStatus(String stat) {
		homeStatus.set(stat);
	}
	
	
	public void setIopp(String stat) {
		iopp.set(stat);
	}
	
	public void setOppStatus(String stat) {
		oppStatus.set(stat);
	}
	
	public StringProperty ihomeProperty() {
		return ihome;
	}
	
	public StringProperty homeStatusProperty() {
		return homeStatus;
	}
	
	public StringProperty ioppProperty() {
		return iopp;
	}
	
	public StringProperty oppStatusProperty() {
		return oppStatus;
	}
}

