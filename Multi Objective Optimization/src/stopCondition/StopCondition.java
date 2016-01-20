package stopCondition;



import individual.Individual;
import fitness.FitnessFunction;

public interface StopCondition {
	/**
	 * 
	 * @param ind Individuals array
	 * @param fitness Fitness function to evaluate the individuals
	 * @param iteration number actual of algorithm iterations
	 * @return true if run condition must stop, false otherwise
	 */
	boolean stop(Individual ind[], FitnessFunction fitness, int iteration);
}
