package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Model.DefStat;
import Model.Model;
import Model.Probability;
import Model.ProbabilityQueries;

public class ProbabilityTest {
	private static final int TIMEOUT = 10000;
	private Model database = new Model();
	
	@Before
    public void beforeTest() {
    	database = new Model();
		database.readDatabase();
	}
	
	@Test(timeout = TIMEOUT)
	public void test() throws InterruptedException {
		Probability prob = new Probability();
		ProbabilityQueries pq = new ProbabilityQueries();
		DefStat ds = new DefStat();
		Model model = new Model();
		String dog = "ATL_30_@_BAL_15";
		if (dog.contains(ds.getTeamAbb("Panthers"))) {
			System.out.println("FOAM");
		} else {
			System.out.println("AAAAAAAAAAAAAAAAAA");
		}
		//prob.addIndDifficulty("Bengals");
		//double d = prob.calculateDefProbability("Ravens", "Bengals");
		//System.out.println(prob.calculateProbability("Falcons", "Saints"));
		/*ArrayList<Integer> temp = prob.convertRanks("Falcons");
		System.out.println(temp.size());
		for (int t : temp) {
			System.out.println(t);
		}
		*/
		
		assertEquals(1,1);
	}
	
}
