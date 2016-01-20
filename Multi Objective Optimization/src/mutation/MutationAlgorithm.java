package mutation;

import fitness.FitnessFunction;
import individual.Individual;

public interface MutationAlgorithm {
	/**
	 * 
	 * @param individual the individual you want to mutate
	 * @param function fitness function
	 * @return an individual mutated
	 */
	Individual mutate(Individual individual, FitnessFunction function);
}
