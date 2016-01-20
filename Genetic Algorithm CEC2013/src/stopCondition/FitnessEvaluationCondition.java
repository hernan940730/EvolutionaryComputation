package stopCondition;

import individual.Individual;
import fitness.FitnessFunction;

public class FitnessEvaluationCondition implements StopCondition {

	private int iter = 3000000;
	
	@Override
	public boolean stop(Individual[] ind, FitnessFunction fitness, int iteration) {
		return fitness.callsNumber() >= iter;
	}

}
