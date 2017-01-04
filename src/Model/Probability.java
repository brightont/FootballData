package Model;

import java.util.ArrayList;

public class Probability {
	private ProbabilityQueries pq = new ProbabilityQueries();
	private double offenseSum = 0;
	private double defenseSum = 0;
	private int offenseCategory = 25;
	private int defenseCategory = 30;
	/**
	 * Formula for calculating the probability of a win
	 * OFFENSE/DEFENSE(55 points total):
	 * Original allotment = 25 pts per offense and 35 pts per defense
	 */
	
	/**
	 * Checks the ranking of a team for offense/defense calculations
	 * @param team
	 * @param opp
	 */
	public void checkRanking(String team, String opp) {
		int teamOff = pq.getRank(team, "yards");
		int oppOff = pq.getRank(opp, "yards");
		int teamDef = pq.getRank(team, "defyards");
		int oppDef = pq.getRank(opp, "defyards");
		
		//if offense/defense is top 5, add 10 to sum. Add 5 if either is top 10
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
		
		// path 1: Add 15 to both sums if team is top 5 offense/defense and is facing an opp that
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
		
		// path 3: add 5 to category, if a category is top 5 and the others are not in top 10
		if (teamOff <= 5 && oppDef > 10) {
			offenseCategory = offenseCategory + 5;
			defenseCategory = defenseCategory - 5;
		} else if (teamDef <= 5 && oppOff > 10) {
			offenseCategory = offenseCategory - 5;
			defenseCategory = defenseCategory + 5;
		}
		
	}
	
	/**
	 * Add team stats, 1 pt per better stat
	 * @param team
	 * @param opponent
	 */
	public void addTeamStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getTeamStats(team);
		ArrayList<Double> oppStat = pq.getTeamStats(opponent);
		int index = 0;
		for (double t : teamStat) {
			if (t > oppStat.get(index)) {
				offenseSum++;
				defenseSum++;
			}
			index++;
		}
	}
	
	/**
	 * Allows you to get probability team stats
	 */
	public double calculateTeamProbability(String team, String opponent) {
		addTeamStats(team, opponent);
		double returnValue = (offenseSum / 16);
		return returnValue;
	}
	
	/**
	 * Checks to see if it's a passing offense
	 * @param team
	 * @return
	 */
	public boolean isPassOffense(String team) {
		int passing = pq.getRank(team, "rec");
		int rushing = pq.getRank(team, "rush");
		if (passing < rushing) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if it's a passing defense
	 * @param team
	 * @return
	 */
	public boolean isPassDefense(String team) {
		int passing = pq.getRank(team, "defrec");
		int rushing = pq.getRank(team, "defrush");
		if (passing < rushing) {
			return true;
		}
		return false;
	}
	
	/**
	 * Add qb stats, 1 pt per better stat multiplied by either 2 or 1.5 depending types
	 * @param team
	 * @param opponent
	 */
	public void addQBStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getQBStats(team);
		ArrayList<Double> oppStat = pq.getQBStats(opponent);
		int index = 0;
		int temp = 0;
		for (Double t: teamStat) {
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
		
		//multiply it by correct number based on offensive type and opponent's defensive type
		if (isPassOffense(team) && !isPassDefense(opponent)) {
			offenseSum = offenseSum + (temp * 2);
		} else if (isPassOffense(team)) {
			offenseSum = offenseSum + (temp * 1.5);
		} else {
			offenseSum = offenseSum + temp;
		}
	}
	
	/**
	 * Allows you to get probability team stats
	 */
	public double calculateQBProbability(String team, String opponent) {
		double returnValue = 0;
		addQBStats(team, opponent);
		if (isPassOffense(team) && !isPassDefense(opponent)) {
			returnValue = (offenseSum / 24);
		} else if (isPassOffense(team)) {
			returnValue = (offenseSum / 18);
		} else {
			returnValue = (offenseSum / 12); 
		}
		return returnValue;
	}
	
	/**
	 * Adds pts for rush stats 1 pt each, 2pt per top 2 RB
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
		
		//stops at the smaller size
		if (teamSize >= oppSize) {
			stop = oppSize;
		} else {
			stop = teamSize;
		}
		
		for (int i = 0; i < stop; i++) {
			//for the top 2 players, their stats are worth 2 points
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

		//checks what type of offense it is
		if (!isPassOffense(team) && isPassDefense(opponent)) {
			offenseSum = offenseSum + (temp * 2);
		} else if (!isPassOffense(team)) {
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
		if (!isPassOffense(team) && isPassDefense(opponent)) {
			returnValue = offenseSum / 30;
		} else if (!isPassOffense(team)) {
			returnValue = offenseSum / 22.5;
		} else {
			returnValue = offenseSum/15;
		}
		return returnValue;
	}
	
	/**
	 * Adds pts for rec stats 1 pt each, 2pt per top 3 WR
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
		
		//stops at the smaller size
		if (teamSize >= oppSize) {
			stop = oppSize;
		} else {
			stop = teamSize;
		}
		
		for (int i = 0; i < stop; i++) {
			//for the top 3 players, their stats are worth 2 points
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

		//checks what type of offense it is
		if (isPassOffense(team) && !isPassDefense(opponent)) {
			offenseSum = offenseSum + (temp * 2);
		} else if (isPassOffense(team)) {
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
		if (isPassOffense(team) && !isPassDefense(opponent)) {
			returnValue = offenseSum / 60;
		} else if (isPassOffense(team)) {
			returnValue = offenseSum / 45;
		} else {
			returnValue = offenseSum/30;
		}
		return returnValue;
	}
	
	/**
	 * Adds pts for fg stats .5 pt each for 39M and less, 1 pt each for above 39M
	 * @param team
	 * @param opponent
	 */
	public void addFGStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getFGStats(team);
		ArrayList<Double> oppStat = pq.getFGStats(opponent);
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
	 * Calculates probability of fg stats
	 */
	public double calculateFGProbability(String team, String opponent) {
		addFGStats(team, opponent);
		double returnValue = offenseSum/3;
		return returnValue;
	}
	
	/**
	 * Adds pts for def stats, 1 pts per stat
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
		
		if ((isPassDefense(team) && !isPassOffense(opponent)) || (!isPassDefense(team) && isPassOffense(opponent))) {
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
		double returnValue = defenseSum/60;
		return returnValue;
	}
	
	/**
	 * Adds pts for int stats, 1 pts per stat
	 * @param team
	 * @param opponent
	 */
	public void addIntStats(String team, String opponent) {
		ArrayList<Double> teamStat = pq.getIntStats(team);
		ArrayList<Double> oppStat = pq.getIntStats(opponent);
		int index = 0;
		int temp = 0;
		
		for (Double t : teamStat) {
			if (((index - 1) % 5 == 0 ) || ((index - 2) % 5 == 0 ) || ((index - 4) % 5 == 0 )) {
				if (t > oppStat.get(index)) {
					temp++;
				}
			} 
			index++;
		}
		
		defenseSum = defenseSum + temp;
	}
	
	/**
	 * Calculates probability of def stats
	 */
	public double calculateIntProbability(String team, String opponent) {
		addIntStats(team, opponent);
		double returnValue = defenseSum/18;
		return returnValue;
	}
}
