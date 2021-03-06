package stopCondition;

import individual.Individual;
import fitness.FitnessFunction;

public class ClassicStopCondition implements StopCondition {

	@Override
	public boolean stop(Individual[] ind, FitnessFunction fitness, int iteration) {
		fitness.callsNumber();
		return iteration >= 100;
	}

}
