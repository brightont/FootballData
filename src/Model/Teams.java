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
	MIN("MIN", "Vikings", "Minesota"),
	NWE("NWE", "Patriots", "New England"),
	NOR("NOR", "Saints", "New Orleans"),
	NYG("NYG", "Giants", "New York"),
	NYJ("NYJ", "Jets", "New York"),
	OAK("OAK", "Raiders", "Oakland"),
	PHI("PHI", "Eagles", "Philadelphia"),
	PHX("PHX", "Cardinals", "Phoenix"),
	PIT("PIT", "Steelers", "Pitsburgh"),
	SDO("SDO", "Chargers", "San Diego"),
	SEA("SEA", "Seattle", "Seahawks"),
	SFO("SFO", "49ers", "San Francisco"),
	TBY("TBY", "Buccaneers", "Tampa Bay"),
	TEN("TEN", "Titans", "Tennessee"),
	WAS("WAS", "Redskins", "Washington");
	
	private final String code;
	private final String team;
	private final String city;
	
	Teams(String scode, String steam, String scity) {
		code = scode;
		team = steam;
		city = scity;
	}
}
