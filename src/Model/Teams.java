package Model;

public enum Teams {
	ARI("ARI", "Cardinals", "Arizona"),
	ATL("ATL", "Falcons", "Atlanta"),
	BAL("BAL", "Ravens", "Baltimore"),
	BUF("BUF", "Bills", "Buffalo"),
	CAR("CAR", "Panthers", "Carolina"),
	CHI("CHI", "Bears", "Chicago"),
	CIN("CIN", "Bengals", "Cincinnati"),
	CLE("CLE", "Browns", "Cleveland"),
	DAL("DAL", "Cowboys", "Dallas"),
	DEN("DEN", "Broncos", "Denver"),
	DET("DET", "Lions", "Detroit"),
	GB("GB", "Packers", "GreenBay"),
	HOU("HOU", "Texans", "Houston"),
	IND("IND", "Colts", "Indianapolis"),
	JAX("JAX", "Jaguars", "Jacksonville"),
	KC("KC", "Chiefs", "Kansas City"),
	LA("LA", "Rams", "Los Angeles"),
	MIA("MIA", "Dolphins", "Miami"),
	MIN("MIN", "Vikings", "Minesota");
	
	private final String code;
	private final String team;
	private final String city;
	
	Teams(String scode, String steam, String scity) {
		code = scode;
		team = steam;
		city = scity;
	}
}
