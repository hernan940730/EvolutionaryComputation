package mutation;

import fitness.FitnessFunction;
import util.StdRandom;
import individual.Individual;

public class MyMutator implements MutationAlgorithm {

	@Override
	public Individual mutate( Individual individual, FitnessFunction function ) {
		Individual ind = individual.clone();
		double[] values = ( double [] ) ind.getValues();
		
		int n = values.length;

		double p = 1.0 / (double)n;
		
		for( int i = 0; i < n; i++ ){
			if( StdRandom.uniform() < p ){
				values[i] = StdRandom.gaussian( 0, ind.getParameters()[Individual.DELTA] );
			}
		}	
		function.constraintSuccess( ind );	
		return ind;
	}

}
