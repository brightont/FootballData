package Model;

public enum TeamName {
	ARI("Cardinals", "Arizona"),
	ATL("Falcons", "Atlanta"),
	BAL("Ravens", "Baltimore"),
	BUF("Bills", "Buffalo"),
	CAR("Panthers", "Carolina"),
	CHI("Bears", "Chicago"),
	CIN("Bengals", "Cincinnati"),
	CLE("Browns", "Cleveland"),
	DAL("Cowboys", "Dallas"),
	DEN("Broncos", "Denver"),
	DET("Lions", "Detroit"),
	GB("Packers", "GreenBay"),
	HOU("Texans", "Houston"),
	IND("Colts", "Indianapolis"),
	JAX("Jaguars", "Jacksonville"),
	KC("Chiefs", "Kansas City"),
	LA("Rams", "Los Angeles"),
	MIA("Dolphins", "Miami"),
	MIN("Vikings", "Minesota"),
	NWE("Patriots", "New England"),
	NOR("Saints", "New Orleans"),
	NYG("Giants", "New York"),
	NYJ("Jets", "New York"),
	OAK("Raiders", "Oakland"),
	PHI("Eagles", "Philadelphia"),
	PHX("Cardinals", "Phoenix"),
	PIT("Steelers", "Pitsburgh"),
	SDO("Chargers", "San Diego"),
	SEA("Seattle", "Seahawks"),
	SFO("49ers", "San Francisco"),
	TBY("Buccaneers", "Tampa Bay"),
	TEN("Titans", "Tennessee"),
	WAS("Redskins", "Washington");
	
	private final String team;
	private final String city;
	
	TeamName(String steam, String scity) {
		team = steam;
		city = scity;
	}
	
	public String getTeam() {
		return team;
	}
}
