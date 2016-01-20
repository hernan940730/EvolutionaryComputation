package algorithm;

import factory.Factory.TypeOfFitnessFunction;
import factory.Factory.TypeOfGenerationAlgorithm;
import factory.Factory.TypeOfMutationAlgorithm;
import factory.Factory.TypeOfReplaceAlgorithm;
import factory.Factory.TypeOfStopCondition;

public interface Algorithm {
	/**
	 * 
	 * @param n population
	 * @param lambda number of individuals
	 */

	void run(int n, int lambda, TypeOfGenerationAlgorithm G,
			 TypeOfMutationAlgorithm M, TypeOfReplaceAlgorithm R,
			 TypeOfFitnessFunction F, TypeOfStopCondition S);
}
