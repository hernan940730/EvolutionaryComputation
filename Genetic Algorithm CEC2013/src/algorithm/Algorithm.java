package algorithm;

import individual.Individual;
import replace.ReplaceAlgorithm;
import selection.SelectionAlgorithm;
import stopCondition.StopCondition;
import fitness.FitnessFunction;
import generation.GenerationAlgorithm;

public interface Algorithm {
	/**
	 * 
	 * @param cond stop condition for run
	 * @param fitness function that evaluate the fitness of an individual
	 * @param selector Selection algorithm
	 * @param generator Generation algorithm  
	 * @param lambda number of individuals
	 * @return return an array of individual in each iteration 
	 */
	
	Individual[] run(StopCondition cond, FitnessFunction fitness, SelectionAlgorithm selector,
					 GenerationAlgorithm generator, ReplaceAlgorithm replacer, int lambda);
}
