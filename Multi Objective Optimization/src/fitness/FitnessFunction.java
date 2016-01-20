package fitness;

import individual.Individual;

public interface FitnessFunction {
	
	/**
	 * This method evaluate a group of variables in a specific function 
	 * and return a fitness value.
	 * @param individual These are the variables to evaluate in the function 
	 * @return the fitness value 
	 */
	double[] evaluate(Individual individual);
	
	/**
	 * 
	 * @param i1 individual 1
	 * @param i2 individual 2
	 * @return a value x, if x < 0.5 individual 2 is better than 1 else if x > 0.5 individual 1 is better
	 * than 2 otherwise both individuals are equals;
	 */
	double compare(Individual i1, Individual i2);
	
	/**
	 * 
	 * @param variables an individual to be fixed
	 */
	void constraintSuccess(Individual variables);
	
	/**
	 * 
	 * @param lambda Number of individuals
	 * @return individual array;
	 */
	Individual[] initialize(int lambda);
	
}
