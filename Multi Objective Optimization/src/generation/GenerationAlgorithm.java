package generation;

import individual.Individual;
import fitness.FitnessFunction;

public interface GenerationAlgorithm {
	/**
	 * 
	 * @param indiv parents used for generate new individuals 
	 * @param f fitness function to evaluate the new individuals
	 * @param minimize true if the algorithm minimizes otherwise false
	 * @return return the new individuals
	 */
	Individual[] generate(Individual indiv1, Individual indiv2, FitnessFunction f);
}
