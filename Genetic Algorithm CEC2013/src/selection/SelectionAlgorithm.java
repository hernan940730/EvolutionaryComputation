package selection;

import individual.Individual;
import fitness.FitnessFunction;

public interface SelectionAlgorithm {
	
	/**
	 * 
	 * @param individues Individuals
	 * @param fun Fitness function to evaluate individuals
	 * @param minimize minimize if this value is true otherwise maximize
	 * @return return a array of selected individuals
	 */
	Individual[] selection(Individual[] individues, FitnessFunction fun, boolean minimize);
	
}
