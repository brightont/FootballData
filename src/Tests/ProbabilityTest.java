package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

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
		Model model = new Model();
		prob.addIndDifficulty("Bengals");
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
