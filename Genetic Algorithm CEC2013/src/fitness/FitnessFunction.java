package fitness;

import individual.Individual;

public interface FitnessFunction {
	
	/**
	 * This method evaluate a group of variables in a specific function 
	 * and return a fitness value.
	 * @param variables These are the variables to evaluate in the function 
	 * @return the fitness value fitness >= 0 
	 */
	double fitness(Individual variables);
	
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
	
	/**
	 * 
	 * @return the number of calls to the fitness function
	 */
	int callsNumber();
	
	double getConstraintMax();
	
}
