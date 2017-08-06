package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class InjuryStat extends Stat{
	private Model database = new Model();
	private static final Logger logger = Logger.getLogger("InjuryStat.class");
	private final Connection connection = database.EstablishConnection();
	public Document document;
	
	//empty constructor
	public InjuryStat() {
		
	}
	
	public InjuryStat(String team, String player, String position, String injury, String GameStatus) {
		super(team);
		String insert = "INSERT INTO footballstats.injuries VALUES" + " ('" + team + "','" + player + "','" + position
				+ "','" + injury + "','" + GameStatus + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getAllInjuries(String team) {
		ArrayList<String> returnList = new ArrayList<String>();
		ArrayList<String> list1 = getMainInjuries(team);
		returnList.addAll(list1);
		ArrayList<String> list2 = getGameInjuries(team);
		returnList.addAll(list2);
		return returnList;
	}
	
	/**
	 * Gets Injury statistics off NFL website
	 * @param team
	 * @return
	 */
	public ArrayList<String> getMainInjuries(String team) {
		ArrayList<String> injuries = new ArrayList<String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/roster?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get main injuries statistics.");
		}
		Elements injuryStats = document.getElementsByClass("data-table1");
		Elements injuryStatsRow = injuryStats.select("td");
		for (int i = 10; i < injuryStatsRow.size(); i++) {
			if (((i - 4) % 9) == 0) {
				String temp = injuryStatsRow.get(i).text();
				if (temp.equals("RES") || temp.equals("PUP")) {
					String newTemp = injuryStatsRow.get(i-2).text();
					String[] arr = newTemp.split(", ");
					if (arr[1].contains("'")) {
						arr[1] = arr[1].replace("'", "");
					} else if (arr[1].contains(".")) {
						arr[1] = arr[1].replace(".", "");
					}
					if (arr[0].contains("-")) {
						arr[0] = arr[0].replace("-", "");
					}
					newTemp = arr[1] + " " + arr[0];
					injuries.add(newTemp);
					injuries.add(injuryStatsRow.get(i-1).text());
					injuries.add("NULL");
					injuries.add(temp);
				}
			}
		}
		return injuries;
	}
	
	/**
	 * Gets Injury statistics off week by week
	 * @param team
	 * @return
	 */
	public ArrayList<String> getGameInjuries(String team) {
		ArrayList<String> injuries = new ArrayList<String>();
		try {
			document = Jsoup.connect("http://www.nfl.com/teams/injuries?team=" + team).get();
		} catch (IOException e) {
			logger.info("Failed to get game injuries statistics.");
		}
		Elements injuryStats = document.getElementsByClass("data-table1");
		Elements injuryStatsRow = injuryStats.select("td");
		for (int i = 6; i < injuryStatsRow.size() - 5; i++) {
				if (((i-1) % 5) == 0) {
					String name = injuryStatsRow.get(i).text();
					if (name.contains("'")) {
						name = name.replace("'", "");
					} else if (name.contains(".")) {
						name = name.replace(".", "");
					} else if (name.contains("-")) {
						name = name.replace("-", "");
					}
					injuries.add(name);
					injuries.add(injuryStatsRow.get(i+1).text());
					if (injuryStatsRow.get(i+2).text().equals("--")) {
						injuries.add("NULL");
					} else {
						injuries.add(injuryStatsRow.get(i+2).text());
					} 
					if (injuryStatsRow.get(i+4).text().equals("--")) {
						injuries.add("NULL");
					} else if (injuryStatsRow.get(i+4).text().equals("Out")) {
						injuries.add(injuryStatsRow.get(i+4).text().toUpperCase());
					} else {
						injuries.add(injuryStatsRow.get(i+4).text());
					}
					
				}
			}
		return injuries;
	}
	
	/**
	 * Checks to see if there's a player and returns the index
	 * @param list
	 * @return
	 */
	public ArrayList<Integer> checkForNewInjury(ArrayList<String> list, String table) {
		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		int i;
		for (i = 0; i < list.size(); i++) {
			if ((i % 4) == 0) {
				if ((getPlayer(list.get(i), table)) == "") {
					returnArray.add(i);
				}
			}
		}
		return returnArray;
	}
	
	/**
	 * Gets the information for a new player
	 * @param i
	 * @param team
	 */	
	public void scrapeNewPlayer(ArrayList<String> list, ArrayList<Integer> intList, String team) {
		for (Integer i : intList) {
			if ((i % 4) == 0) {
				String player = list.get(i);
				String position = list.get(i + 1);
				String injury = list.get(i + 2);
				String gs = list.get(i + 3);
		
				addNewPlayer(team, player, position, injury, gs);
			}
		}
	}
	
	/**
	 * Add a new player
	 * @param team
	 * @param player
	 * @param pos
	 * @param injury
	 * @param gs
	 */
	public void addNewPlayer(String team, String player, String pos, String injury, String gs) {
		String insert = "INSERT INTO footballstats.injuries VALUES ('" + team + "','" + player + "','" + pos + "','"
				+ injury + "','" + gs + "');";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insert);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.FINE, "Could not add a new player.");
		}
	}
	
	/**
	 * Removes a player
	 * @param list
	 * @param team
	 */
	public boolean removeNewPlayer(ArrayList<String> list, String team) {
		int players = list.size()/4;
		String query = "SELECT COUNT(Player) FROM footballstats.injuries WHERE Team = '" + team + "';";
		int answer = 0;
		try {
            Statement userStatement = connection.createStatement();
            ResultSet result = userStatement.executeQuery(query);
            while (result.next()) {
                answer = result.getInt("COUNT(PLAYER)");
            }
        } catch (SQLException e) {
            logger.log(Level.FINE, "Could not select count of players.");
        }
		if (players != answer) {
			checkPlayers(team, list, answer);
			return false;
		}
		return true;
	}
	
	/**
	 * Checks to see if a player is in database that isn't in the list
	 * @param team
	 * @param list
	 * @param i
	 */
	
	public void checkPlayers(String team, ArrayList<String> list, int i) {
		String query = "SELECT Player FROM footballstats.injuries WHERE TEAM = '" + team + "';";
		String player = "";
		for (int j = 0; j <= i; j++) {
			try {
	            Statement userStatement = connection.createStatement();
	            ResultSet result = userStatement.executeQuery(query);
	            while (result.next()) {
	            	player = result.getString(j);
	            	if (!list.contains(player)) {
	            		remove(player);
	            	}
	            }
	        } catch (SQLException e) {
	            logger.log(Level.FINE, "Could not check players.");
	        }
		}
	}
	
	/**
	 * Removes a player
	 * @param player
	 */
	public void remove(String player) {
    	String query = "DELETE FROM footballstats.injuries WHERE Player = '" + player + "';";
    	try {
            Statement userStatement = connection.createStatement();
            userStatement.execute(query);
        } catch (SQLException e) {
            logger.log(Level.FINE, "Could not delete player.");
        }
    }
	
	/**
	 * Update the database
	 * 
	 * @param list
	 * @param team
	 */
	public void UpdateDatabase(ArrayList<String> list, String team) {
		String player = "";
		String update = "";
		for (int i = 0; i < list.size(); i++) {
			if ((i % 4) == 0) {
				player = list.get(i);
				if (player.contains("'")) {
		    		player = player.replace("'", "");
		    	} else if (player.contains(".")) {
		    		player = player.replace(".", "");
		    	} else if (player.contains("-")) {
		    		player = player.split("-")[0];
		    	}
			} else if ((i % 4) != 0) {
				String result = list.get(i);
				if (((i - 3) % 4) == 0) {
					update = "UPDATE footballstats.injuries SET GameStatus = '" + result + "' WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else if (((i - 2) % 4) == 0) {
					update = "UPDATE footballstats.injuries SET Injury = '" + result + "' WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				} else {
					update = "UPDATE footballstats.injuries SET Position = '" + result + "' WHERE Team = '" + team
							+ "' AND Player = '" + player + "';";
				}
				try {
					PreparedStatement prepStatement = connection.prepareStatement(update);
					prepStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
