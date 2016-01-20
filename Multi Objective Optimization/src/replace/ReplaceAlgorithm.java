package replace;
import fitness.FitnessFunction;
import individual.Individual;
public interface ReplaceAlgorithm {
	/**
	 * 
	 * @param function fitness function
	 * @param parents
	 * @param children 
	 * @return the new Individuals
	 */
	Individual[] replace(FitnessFunction function, Individual[] parents, Individual[] children);
}
