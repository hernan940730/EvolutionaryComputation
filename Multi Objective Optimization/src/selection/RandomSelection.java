package selection;

import fitness.FitnessFunction;
import individual.Individual;
import util.StdRandom;

public class RandomSelection implements SelectionAlgorithm{
	
	@Override
	public Individual select( Individual[] individuals, FitnessFunction function ){
		return individuals[StdRandom.uniform(individuals.length)];
	}
}
