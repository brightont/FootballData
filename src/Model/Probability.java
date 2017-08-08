package Model;

import java.util.ArrayList;

public class Probability {
	private ProbabilityQueries pq = new ProbabilityQueries();
	private double offenseSum = 0;
	private double defenseSum = 0;
	private int offenseCategory = 25;
	private int defenseCategory = 30;
	private double injurySum = 17;
	private double strengthSum = 0;
	private double winSum = 0;
	private double homeSum = 0;
	private double rankSum = 0;
	private double desperationSum = 0;
	/**
	 * Formula for calculating the probability of a win 
	 * OFFENSE/DEFENSE(55 points total): 
	 * Original allotment = 25 pts per offense and 35 pts per defense (stc)
	 * INJURIES(17 points total)
	 * SCHEDULE STRENGTH (7 points total)
	 * WINS AND LOSSES (5 points total)
	 * HOME LOCATION (6 points total)
	 * RANK (5 points total)
	 * DESPERATION (5 points total)
	 */
	
	
	public void reset() {
		offenseSum = 0;
		defenseSum = 0;
		offenseCategory = 25;
		defenseCategory = 30;
		injurySum = 15;
		strengthSum = 0;
		winSum = 0;
		homeSum = 0;
		rankSum = 0;
		desperationSum = 0;
	}
	
	public double calculateProbability(String team, String opp) {
		double od = calculateOffenseDefense(team, opp);
		double i = calculateInjuries(team, opp);
		double s = calculateStrength(team, opp);
		double w = calculateWins(team, opp);
		double hs = 0;
		if (team.equals("Dogs")) {
			hs = calculateHomeStrength(team, opp, "home");
		} else {
			hs = calculateHomeStrength(team, opp, "away");
		}
		double r = CalculateRank(team, opp);
		double d = calculateDesperation(team, opp);
		double prob = od + i + s + w + hs + r + d;
		
		/*double prob = calculateOffenseDefense(team, opp) + calculateInjuries(team, opp) + calculateStrength(team, opp)
				+ calculateWins(team, opp) + calculateHomeStrength(team, opp, "away") + calculateRank(team, opp)
				+ calculateDesperation(team, opp);*/
		return prob;
	}

	public double calculateOffenseDefense(String team, String opp) {
		checkRanking(team, opp);
		AddTeamStats(team, opp);
		AddQBStats(team, opp);
		addRushStats(team, opp);
		addRecStats(team, opp);
		AddFGStats(team, opp);
		addDefStats(team, opp);
		addIntStats(team, opp);

		double oc = offenseCategory * .01;
		double dc = defenseCategory * .01;

		double offResult = offenseSum * oc;
		double defResult = defenseSum * dc;

		return (offResult + defResult);
	}
	
	public double calculateInjuries(String team, String opp) {
		addQBInjury(team);
		addInjuries(team);
		addKeyInjuries(team);
		addWeakSide(team);
		addGreaterInjuries(team, opp);
		return injurySum;
	}
	
	public double calculateStrength(String team, String opp) {
		//addDifficulty(team, opp);
		addIndDifficulty(team);
		addBetterStrength(team, opp);
		return (strengthSum * .07);
	}
	
	public double calculateWins(String team, String opp) {
		compareWins(team, opp);
		getWinsOverTime(team);
		return (winSum * .05);
	}
	
	public double calculateHomeStrength(String team, String opp, String location) {
		addHomeStrength(team, location);
		compareLocationStrengths(team, opp, location);
		isHome(location);
		return (homeSum * .06);
	}

	
	public double calculateDesperation(String team, String opp) {
		previousEncounter(team, opp);
		noWins(team);
		isSecure("no", team);
		return (desperationSum * .05);
	}

	/**
	 * Checks the ranking of a team for offense/defense calculations
	 * @param team
	 * @param opp
	 */
	public void checkRanking(String team, String opp) {
		int teamOff = pq.GetRank(team, "yards");
		int oppOff = pq.GetRank(opp, "yards");
		int teamDef = pq.GetRank(team, "defyards");
		int oppDef = pq.GetRank(opp, "defyards");

		// if offense/defense is top 5, add 10 to sum. Add 5 if either is top 10
		if (teamOff <= 5) {
			offenseSum = offenseSum + 10;
		} else if (teamOff <= 10) {
			offenseSum = offenseSum + 5;
		}

		if (teamDef <= 5) {
			defenseSum = defenseSum + 10;
		} else if (teamDef <= 10) {
			defenseSum = defenseSum + 5;
		}

		// path 1: Add 15 to both sums if team is top 5 offense/defense and is
		// facing an opp that
		// is bottom 5 offense/defense
		if ((teamOff <= 5 && teamDef <= 5) && (oppOff >= 28 && oppDef >= 28)) {
			offenseSum = offenseSum + 15;
			defenseSum = defenseSum + 15;
		} else if ((teamOff >= 28 && teamDef >= 28) && (oppOff <= 5 && oppDef <= 5)) {
			offenseSum = offenseSum - 15;
			defenseSum = defenseSum - 15;
		}

		// path 2: add 10 to category if, team off/def is top 5 and is facing
		// the opposite that is bottom 5
		if (teamOff <= 5 && oppDef >= 28) {
			offenseCategory = offenseCategory + 10;
			defenseCategory = defenseCategory - 10;
		} else if (teamDef <= 5 && oppOff >= 28) {
			offenseCategory = offenseCategory - 10;
			defenseCategory = defenseCategory + 10;
		}

		// path 3: add 5 to category, if a category is top 5 and the others are
		// not in top 10
		if (teamOff <= 5 && oppDef > 10) {
			offenseCategory = offenseCategory + 5;
			defenseCategory = defenseCategory - 5;
		} else if (teamDef <= 5 && oppOff > 10) {
			offenseCategory = offenseCategory - 5;
			defenseCategory = defenseCategory + 5;
		}

	}

	/**
	 * Calculate the probability of winning based on team stats
	 * @param team
	 * @param opponent
	 * @return
	 */
	public double CalculateTeamProb(String team, String opponent) {
		AddTeamStats(team, opponent);
		double returnValue = (offenseSum / 16);
		return returnValue;
	}
	
	/**
	 * Add the team stats together
	 * @param team
	 * @param opponent
	 */
	private void AddTeamStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.GetTeamStats(team);
		ArrayList<Double> oppStat = pq.GetTeamStats(opponent);
		int index = 0;
		for (double ts : teamStat) {
			if (ts > oppStat.get(index)) {
				offenseSum++;
				defenseSum++;
			}
			index++;
		}
	}
	
	/**
	 * Calculates the qb probability
	 * @param team
	 * @param opponent
	 * @return double probability 
	 */
	public double CalculateQBProb(String team, String opponent) {
		double returnValue = 0;
		AddQBStats(team, opponent);
		if (IsPassOffense(team) && !IsPassDefense(opponent)) {
			returnValue = (offenseSum / 24);
		} else if (IsPassOffense(team)) {
			returnValue = (offenseSum / 18);
		} else {
			returnValue = (offenseSum / 12);
		}
		return returnValue;
	}
	
	/**
	 * Add the qb stats together, 1 pt per better stat multiplied by either 2 or 1.5 depending on
	 * better type
	 * @param team
	 * @param opponent
	 */
	private void AddQBStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.GetQBStats(team);
		ArrayList<Double> oppStat = pq.GetQBStats(opponent);
		int index = 0;
		int temp = 0;
		for (Double t : teamStat) {
			if (index < 6 || index == 8 || index == 11) {
				if (t > oppStat.get(index)) {
					temp++;
				}
			} else {
				if (t < oppStat.get(index)) {
					temp = temp + 2;
				}
			}
			index++;
		}

		//multiply the offensive sum by either 2 or 1.5 depending on its better type
		//and the opposing team's better type
		if (IsPassOffense(team) && !IsPassDefense(opponent)) {
			offenseSum = offenseSum + (temp * 2);
		} else if (IsPassOffense(team)) {
			offenseSum = offenseSum + (temp * 1.5);
		} else {
			offenseSum = offenseSum + temp;
		}
	}

	/**
	 * Determine if it's a passing offense
	 * @param team
	 * @return
	 */
	public boolean IsPassOffense(String team) {
		int passing = pq.GetRank(team, "rec");
		int rushing = pq.GetRank(team, "rush");
		if (passing < rushing) {
			return true;
		}
		return false;
	}

	/**
	 * Determine if it's a defense that stops passes better
	 * @param team
	 * @return
	 */
	public boolean IsPassDefense(String team) {
		int passing = pq.GetRank(team, "defrec");
		int rushing = pq.GetRank(team, "defrush");
		if (passing < rushing) {
			return true;
		}
		return false;
	}
	/**
	 * Adds pts for rush stats 1 pt each, 2pt per top 2 RB
	 * 
	 * @param team
	 * @param opponent
	 */
	public void addRushStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getRushStats(team);
		ArrayList<Double> oppStat = pq.getRushStats(opponent);
		int teamSize = teamStat.size();
		int oppSize = oppStat.size();
		int stop = 0;
		int temp = 0;

		// stops at the smaller size
		if (teamSize >= oppSize) {
			stop = oppSize;
		} else {
			stop = teamSize;
		}

		for (int i = 0; i < stop; i++) {
			// for the top 2 players, their stats are worth 2 points
			if (((i - 1) % 5 == 0) || ((i - 2) % 5 == 0) || ((i - 4) % 5 == 0)) {
				if (i < 10) {
					if (teamStat.get(i) > oppStat.get(i)) {
						temp = temp + 2;
					}
				} else {
					if (teamStat.get(i) > oppStat.get(i)) {
						temp++;
					}
				}
			}
		}

		// checks what type of offense it is
		if (!IsPassOffense(team) && IsPassDefense(opponent)) {
			offenseSum = offenseSum + (temp * 2);
		} else if (!IsPassOffense(team)) {
			offenseSum = offenseSum + (temp * 1.5);
		} else {
			offenseSum = offenseSum + temp;
		}
	}

	/**
	 * Allows you to get probability team stats
	 */
	public double calculateRushProbability(String team, String opponent) {
		double returnValue = 0;
		addRushStats(team, opponent);
		if (!IsPassOffense(team) && IsPassDefense(opponent)) {
			returnValue = offenseSum / 30;
		} else if (!IsPassOffense(team)) {
			returnValue = offenseSum / 22.5;
		} else {
			returnValue = offenseSum / 15;
		}
		return returnValue;
	}

	/**
	 * Adds pts for rec stats 1 pt each, 2pt per top 3 WR
	 * 
	 * @param team
	 * @param opponent
	 */
	public void addRecStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getRecStats(team);
		ArrayList<Double> oppStat = pq.getRecStats(opponent);
		int teamSize = teamStat.size();
		int oppSize = oppStat.size();
		int stop = 0;
		int temp = 0;

		// stops at the smaller size
		if (teamSize >= oppSize) {
			stop = oppSize;
		} else {
			stop = teamSize;
		}

		for (int i = 0; i < stop; i++) {
			// for the top 3 players, their stats are worth 2 points
			if (((i - 1) % 5 == 0) || ((i - 2) % 5 == 0) || ((i - 4) % 5 == 0)) {
				if (i < 15) {
					if (teamStat.get(i) > oppStat.get(i)) {
						temp = temp + 2;
					}
				} else {
					if (teamStat.get(i) > oppStat.get(i)) {
						temp++;
					}
				}
			}
		}

		// checks what type of offense it is
		if (IsPassOffense(team) && !IsPassDefense(opponent)) {
			offenseSum = offenseSum + (temp * 2);
		} else if (IsPassOffense(team)) {
			offenseSum = offenseSum + (temp * 1.5);
		} else {
			offenseSum = offenseSum + temp;
		}
	}

	/**
	 * Calculates probability of rec stats
	 */
	public double calculateRecProbability(String team, String opponent) {
		double returnValue = 0;
		addRushStats(team, opponent);
		if (IsPassOffense(team) && !IsPassDefense(opponent)) {
			returnValue = offenseSum / 60;
		} else if (IsPassOffense(team)) {
			returnValue = offenseSum / 45;
		} else {
			returnValue = offenseSum / 30;
		}
		return returnValue;
	}
	
	/**
	 * Calculates the field goal probability
	 * @param team
	 * @param opponent
	 * @return
	 */
	public double CalculateFGProb(String team, String opponent) {
		AddFGStats(team, opponent);
		double returnValue = offenseSum / 3;
		return returnValue;
	}

	
	/**
	 * Add the field goal stats, .5 pts for 39M and less, 1 pt for anything above 39M
	 * @param team
	 * @param opponent
	 */
	public void AddFGStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.GetFGStats(team);
		ArrayList<Double> oppStat = pq.GetFGStats(opponent);
		int index = 0;
		double temp = 0;

		for (Double t : teamStat) {
			if (((index % 2) == 0) && (t > oppStat.get(index))) {
				if (index < 3) {
					temp = temp + .5;
				} else {
					temp = temp + 1;
				}
			}
		}
		offenseSum = offenseSum + temp;
	}

	/**
	 * Adds pts for def stats, 1 pts per stat
	 * 
	 * @param team
	 * @param opponent
	 */
	public void addDefStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getDefStats(team);
		ArrayList<Double> oppStat = pq.getDefStats(opponent);
		int index = 0;
		int temp = 0;
		
		for (Double t : teamStat) {
			if (t < 5 && t > oppStat.get(index)) {
				temp = temp + 2;
			} else if (t > oppStat.get(index)) {
				temp++;
			}
			index++;
		}

		if ((IsPassDefense(team) && !IsPassOffense(opponent)) || (!IsPassDefense(team) && IsPassOffense(opponent))) {
			defenseSum = defenseSum + (2 * temp);
		} else {
			defenseSum = defenseSum + temp;
		}
	}

	/**
	 * Calculates probability of def stats
	 */
	public double calculateDefProbability(String team, String opponent) {
		addDefStats(team, opponent);
		double returnValue = defenseSum / 60;
		return returnValue;
	}

	/**
	 * Adds pts for int stats, 1 pts per stat
	 * 
	 * @param team
	 * @param opponent
	 */
	public void addIntStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getIntStats(team);
		ArrayList<Double> oppStat = pq.getIntStats(opponent);
		int temp = 0;
		int stop = 0;
		if (teamStat.size() < oppStat.size()) {
			stop = teamStat.size();
		} else {
			stop = oppStat.size();
		}
		for (int i = 0; i < stop; i++) {
			if (((i - 1) % 5 == 0) || ((i - 2) % 5 == 0) || ((i - 4) % 5 == 0)) {
				if (teamStat.get(i) > oppStat.get(i)) {
					temp++;
				}
			}
		}

		defenseSum = defenseSum + temp;
	}

	/**
	 * Calculates probability of def stats
	 */
	public double calculateIntProbability(String team, String opponent) {
		addIntStats(team, opponent);
		double returnValue = defenseSum / 18;
		return returnValue;
	}

	/**
	 * Subtract 10 points from injury sum if QB is injured
	 * 
	 * @param team
	 */
	public void addQBInjury(String team) {
		if (pq.isQBInjured(team)) {
			injurySum = injurySum - 12;
		}
	}

	/**
	 * Adds injuries, subtract .5 for each major injury and .1 for playing while
	 * injured
	 * @param team
	 */
	public void addInjuries(String team) {
		int importantInjuries = pq.getInjuryCount(team);
		int injuries = pq.getCount(team, "injuries");
		double temp = 0;
		temp = importantInjuries * .5;
		temp = temp + ((injuries - importantInjuries) * .10);
		injurySum = injurySum - temp;
	}

	/**
	 * Adds key injuries (top 2 players) .5 for very injured, .1 for slightly
	 * injured
	 * 
	 * @param team
	 */
	public void addKeyInjuries(String team) {
		ArrayList<String> arr = new ArrayList<String>();
		pq.getImportantPlayers(arr, team, "pass_stats", "Rec");
		pq.getImportantPlayers(arr, team, "rushstats", "Att");
		pq.getImportantDef(arr, team);

		double temp = 0;
		for (String a : arr) {
			if (pq.isPlayerInjured(a)) {
				temp = temp + .5;
			} else if (pq.isPlayerSlightly(a)) {
				temp = temp + .1;
			}
		}
		injurySum = injurySum - temp;
	}

	public void addWeakSide(String team) {
		int teamOff = pq.GetRank(team, "yards");
		int teamDef = pq.GetRank(team, "defyards");
		String pass = "Position = 'WR' OR Position = 'FB' OR Position = 'TE' OR Position = 'OL' OR Position = 'G' OR Position = 'LG' OR Position = 'RG' OR Position = 'C' OR Position = 'T' OR Position = 'LT' OR Position = 'RT'";
		String rush = "Position = 'RB' OR Position = 'FB' OR Position = 'TE' OR Position = 'OL' OR Position = 'G' OR Position = 'LG' OR Position = 'RG' OR Position = 'C' OR Position = 'T' OR Position = 'LT' OR Position = 'RT'";
		String def  = "Position = 'DL' OR Position = 'DE' OR Position = 'DT' OR Position = 'OLB' OR Position = 'ILB' OR Position = 'LB' OR Position = 'MLB' OR Position = 'DB' OR Position = 'CB' OR Position = 'S'";
		int rushInjuries = pq.countPositionInjuries(team, rush);
		int passInjuries = pq.countPositionInjuries(team, pass);
		int defInjuries = pq.countPositionInjuries(team, def);
		//determines weak side
		if (teamOff > teamDef) {
			if (IsPassOffense(team)) {
				if (rushInjuries > passInjuries && rushInjuries > defInjuries) {
					injurySum = injurySum - 3;
				}
			} else {
				if (passInjuries > rushInjuries && passInjuries > defInjuries) {
					injurySum = injurySum - 3;
				}
			}
		} else {
			if (defInjuries > rushInjuries && defInjuries > passInjuries) {
				injurySum = injurySum - 3;
			}
		}
	}
	
	/**
	 * Calculates who has the most injuries
	 * @param team
	 */
	public void addGreaterInjuries(String team, String opponent) {
		int impInjuriesTeam = pq.getInjuryCount(team);
		int impInjuriesOpp = pq.getInjuryCount(opponent);
		int injuriesTeam = pq.getCount(team, "injuries");
		int injuriesOpp = pq.getCount(opponent, "injuries");
		int temp = 0;
		if (impInjuriesTeam > impInjuriesOpp) {
			temp = temp + 2;
		}
		if (injuriesTeam > injuriesOpp) {
			temp = temp + 1;
		} 
		injurySum = injurySum - temp;
	}
	
	
	/**
	 * Calculates probability of injury stats
	 */
	public double calculateInjuryProbability(String team, String opponent) {
		calculateInjuries(team, opponent);
		double returnValue = (injurySum / 15);
		return returnValue;
	}
	
	/**
	 * Gets the average of all the teams you are facing
	 * @param team
	 * @param opponent
	 */
	public double getOppsAverage(String team) {
		double sum = 0;
		Model model = new Model();
		ArrayList<String> oppList = model.getScoresOpp(team);
		for (String o : oppList) {
			double record = pq.getRecord(o);
			sum = sum + record;
		}
		sum = sum / 16;
		return sum;
	}

	/**
	 * Add 10 points if the average of difficulty for opponents you face is higher
	 * @param team
	 * @param opponent
	 */
	public void addDifficulty(String team, String opponent) {
		double teamDiff = getOppsAverage(team);
		double oppDiff = getOppsAverage(opponent);
		if (teamDiff > oppDiff) {
			strengthSum = strengthSum + 10;
		}
	}
	
	/**
	 * Adds 2 pts if you win against a team of .6 avg, and 1 pt if you win against .5 avg 
	 * @param team
	 */
	public void addIndDifficulty(String team) {
		Model model = new Model();
		ArrayList<String> oppList = model.getScoresOpp(team);
		ArrayList<String> outcomes = pq.getOutcomes(team);
		int index = 0;
		int sum = 0;
		for (String o : outcomes) {
			if (o.equals("W")) {
				double record = pq.getRecord(oppList.get(index));
				if (record > .666) {
					sum = sum + 2;
				} else if (record > .550) {
					sum++;
				}
			} 
			index++;
		}
		strengthSum = strengthSum + sum;
	}
	
	/**
	 * If you have more wins, add 10 points for a medium, and 20 points for a hard
	 * @param team
	 * @param opp
	 */
	public void addBetterStrength(String team, String opp) {
		int mediumTeam = getStrengthCount(team, "medium");
		int mediumOpp = getStrengthCount(opp, "medium");
		int hardTeam = getStrengthCount(team, "hard");
		int hardOpp = getStrengthCount(opp, "hard");
		if (mediumTeam > mediumOpp) {
			strengthSum = strengthSum + 10;
		} 
		if (hardTeam > hardOpp) {
			strengthSum = strengthSum + 20;
		}
		
	}

	/**
	 * Count medium and hard games
	 * @param team
	 * @param type
	 * @return
	 */
	public int getStrengthCount(String team, String type) {
		Model model = new Model();
		ArrayList<String> oppList = model.getScoresOpp(team);
		ArrayList<String> outcomes = pq.getOutcomes(team);
		int index = 0;
		int count = 0;
		for (String o : outcomes) {
			if (o.equals("W")) {
				double record = pq.getRecord(oppList.get(index));
				if (type.equals("medium") && record > .5) {
					count++;
				} else if (type.equals("hard") && record > .6) {
					count++;
				}
			}
			index++;
		}
		return count;
	}

	/**
	 * Add 15 points to the team with the most wins
	 * @param team
	 * @param opp
	 */
	public void compareWins(String team, String opp) {
		if (getWinCount(team) > getWinCount(opp)) {
			winSum = winSum + 15;
		}
	}
	/**
	 * Gets count of wins
	 * @param team
	 * @return
	 */
	public int getWinCount(String team) {
		ArrayList<String> outcomes = pq.getOutcomes(team);
		int count = 0;
		for (String o : outcomes) {
			if (o.equals("W")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Gets the wins over time
	 * @param team
	 */
	public void getWinsOverTime(String team) {
		ArrayList<String> outcomes = pq.getOutcomes(team);
		int count = 0;
		int index = 0;
		for (String o : outcomes) {
			if (o.equals("W")) {
				if (index < 4) {
					count++;
				} else if (index >= 4 && index < 8) {
					count = count + 2;
				} else if (index >= 8 && index < 12) {
					count = count + 3;
				} else if (index >= 12 && index < 16) {
					count = count + 4;
				}
			}
			index++;
		}
		winSum = winSum + count;
	}
	
	/**
	 * Gets the score probability
	 * @param team
	 * @param opponent
	 * @return
	 */
	public double calculateScoreProbability(String team, String opponent) {
		double returnValue = (calculateWins(team, opponent)/.07);
		int weeks = pq.getCount(team, "scores");
		int divisor = 0;
		if (weeks <= 4) {
			divisor = 15 + 4;
		} else if (weeks > 4 && weeks <= 8) {
			divisor = 15 + 12;
		} else if (weeks > 8 && weeks <= 12) {
			divisor = 15 + 28;
		} else {
			divisor = 15 + 40;
		}
		returnValue = returnValue/divisor;
		return returnValue;
	}
	
	/**
	 * Checks to see if the game is at home
	 * @param team
	 * @param chunk
	 * @return
	 */
	public boolean isHome(String team, String chunk) {
		ScoreStat ss = new ScoreStat();
		String[] items = chunk.split("@");
		if (!items[0].contains(ss.getTeamAbb(team))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the wins over time
	 * @param team
	 */
	public boolean isHomeStrength(String team) {
		ArrayList<String> location = pq.getScoreLocation(team);
		ArrayList<String> outcomes = pq.getOutcomes(team);
		int homeCount = 0;
		int awayCount = 0;
		int index = 0;
		for (String l : location) {
			if (isHome(team, l)) {
				if (outcomes.get(index).equals("W")) {
					homeCount++;
				}
			} else {
				if (outcomes.get(index).equals("W")) {
					awayCount++;
				}
			}
			index++;
		}
		if (homeCount >= awayCount) {
			return true;
		}
		return false;
	}
	
	//TODO: This is hard coded, fix so you can get the location from online
	public void addHomeStrength(String team, String location) {
		if (isHomeStrength(team) && location.equals("home")) {
			homeSum = homeSum + 20;
		} else if (!isHomeStrength(team) && location.equals("away")) {
			homeSum = homeSum + 20;
		}
	}
	
	/**
	 * Compares whether a team is better away or home
	 * @param team
	 * @param opp
	 * @param location
	 */
	public void compareLocationStrengths(String team, String opp, String location) {
		//check their weakness
		if (isHomeStrength(opp) && location.equals("home")) {
			homeSum = homeSum + 15;
		} else if (!isHomeStrength(opp) && location.equals("away")) {
			homeSum = homeSum + 15;
		}
		
		//check their weakness with your strength
		if (location.equals("home")) {
			if (isHomeStrength(team) && isHomeStrength(opp)) {
				homeSum = homeSum + 30;
			} 
		} else {
			if (!isHomeStrength(team) && !isHomeStrength(opp)) {
				homeSum = homeSum + 30;
			}
		}
	}
	
	public void isHome(String location) {
		if (location.equals("home")) {
			homeSum = homeSum + 5;
		}
	}
	
	/**
	 * Calculates the rank probability
	 * @param team
	 * @param opponent
	 * @return rank probability
	 */
	public double CalculateRankProb(String team, String opponent) {
		double answer = CalculateRank(team, opponent)/.05;
		return answer/150;
	}
	
	/**
	 * Calculates the rank
	 * @param team
	 * @param opp
	 * @return
	 */
	private double CalculateRank(String team, String opp) {
		CompareRanks(team, opp);
		CheckRanks(team);
		CompareDifferences(team, opp);
		CompareExtremes(team, opp);
		return (rankSum * .05);
	}
	
	/**
	 * Compare ranks, sacks and interceptions are worth more
	 * @param team
	 * @param opp
	 */
	public void CompareRanks(String team, String opp) {
		int sum = 0;
		ArrayList<Integer> teamStat = ConvertRanks(team);
		ArrayList<Integer> oppStat = ConvertRanks(opp);
		for (int i = 0; i < 10; i++) {
			if (i != 6 && i != 7) {
				if (teamStat.get(i) < oppStat.get(i)) {
					sum = sum + 2;
				}
			} else {
				if (teamStat.get(i) < oppStat.get(i)) {
					sum = sum + 4;
				}
			}
		}
		rankSum = rankSum + sum;
	}

	/**
	 * Add points for rank
	 * @param team
	 */
	public void CheckRanks(String team) {
		int sum = 0;
		ArrayList<Integer> teamStat = ConvertRanks(team);
		for (int i = 0; i < 10; i++) {
			if (i != 6 && i != 7) {
				if (teamStat.get(i) <= 5) {
					sum = sum + 5;
				} else if (teamStat.get(i) <= 10) {
					sum = sum + 3;
				}
			} else {
				if (teamStat.get(i) <= 5) {
					sum = sum + 7;
				} else if (teamStat.get(i) <= 10) {
					sum = sum + 5;
				}
			}
		}
		rankSum = rankSum + sum;
	}
	
	/**
	 * Compare differences to see if team stat is in top 5 and opp stat is not in top 10
	 * @param team
	 * @param opp
	 */
	public void CompareDifferences(String team, String opp) {
		int sum = 0;
		ArrayList<Integer> teamStat = ConvertRanks(team);
		ArrayList<Integer> oppStat = ConvertRanks(opp);
		for (int i = 0; i < 10; i++) {
			if (i != 6 && i != 7) {
				if ((teamStat.get(i) <= 5) && (oppStat.get(i) > 10)) {
					sum = sum + 10;
				}
			} else {
				if ((teamStat.get(i) <= 5) && (oppStat.get(i) > 10)) {
					sum = sum + 12;
				}
			}
		}
		rankSum = rankSum + sum;
	}
	
	/**
	 * Compares extremes
	 * @param team
	 * @param opp
	 */
	public void CompareExtremes(String team, String opp) {
		int sum = 0;
		ArrayList<Integer> teamStat = ConvertRanks(team);
		ArrayList<Integer> oppStat = ConvertRanks(opp);
		for (int i = 0; i < 10; i++) {
			if (teamStat.get(i) <= 5 && oppStat.get(i) > 27) {
				sum = sum + 15;
			}
		}
		rankSum = rankSum + sum;
	}
	
	/**
	 * Converts the ranks
	 * @param team
	 * @param opp
	 * @return
	 */
	public ArrayList<Integer> ConvertRanks(String team) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		ArrayList<String> ranks = new ArrayList<String>();
		ranks.add("yards");
		ranks.add("rush");
		ranks.add("rec");
		ranks.add("defyards");
		ranks.add("defrush");
		ranks.add("defrec");
		for (String r : ranks) {
			returnList.add(pq.GetRank(team, r));
		}
		returnList.add(pq.getRankSpecial(team, "int", "Interceptions"));
		returnList.add(pq.getRankSpecial(team, "sack", "Sacks"));
		returnList.add(pq.getQuickStatRank(team, "Pass_Yards_Place"));
		returnList.add(pq.getQuickStatRank(team, "Pts_Place"));
		return returnList;
	}
	
	/**
	 * Get previous encounter
	 * @param team
	 * @param opp
	 */
	public void previousEncounter(String team, String opp) {
		ScoreStat ss = new ScoreStat();
		int sum = 0;
		ArrayList<String> encounters = pq.getScoreLocation(team);
		for (String e : encounters) {
			if (e.contains(ss.getTeamAbb(opp))) {
				String[] chunks = e.split("_");
				int score1 = Integer.parseInt(chunks[1]);
				int score2 = Integer.parseInt(chunks[4]);
				String[] items = e.split("@");
				if (items[0].contains(ss.getTeamAbb(opp))) {
					if ((score1 > score2) && ((score1 - score2) <= 5)) {
						sum = sum + 25;
					} else if ((score1 > score2) && ((score1 - score2) > 21)) {
						sum = sum + 25;
					}
				} else {
					if ((score2 > score1) && ((score2 - score1) <= 5)) {
						sum = sum + 25;
					} else if ((score2 > score1) && ((score2 - score1) > 21)) {
						sum = sum + 25;
					}
				}
			}
		}
		desperationSum = desperationSum + sum;
	}
	
	public void noWins(String team) {
		ArrayList<String> wins = pq.getOutcomes(team);
		int count = 0;
		int sum = 0;
		for (String w : wins) {
			if (w.equals("W")) {
				count++;
			}
		}
		if (wins.size() > 14 && count == 0) {
			sum = sum + 15;
		}
		desperationSum = desperationSum + sum;
	}
	
	//TODO: Make a less hard coded method for teams that put in 2nd string if they secured a playoff spot
	public void isSecure(String status, String team) {
		if (status.equals("yes") && team.equals("Eagles")) {
			desperationSum = desperationSum - 30;
		}
	}
	
	public void isBound(String status, String team, String opp) {
		if (status.equals("both no")) {
			ArrayList<String> wins = pq.getOutcomes(team);
			int count = 0;
			for (String w : wins) {
				if (w.equals("W")) {
					count++;
				}
			}
			
			ArrayList<String> oppwins = pq.getOutcomes(opp);
			int count1 = 0;
			for (String w : oppwins) {
				if (w.equals("W")) {
					count1++;
				}
			}
			
			if (count > count1) {
				desperationSum = desperationSum - 100;
			}
		}
	}
}
