package replace;

import fitness.FitnessFunction;
import util.StdRandom;
import individual.Individual;

public class ClassicReplacer implements ReplaceAlgorithm {
	
	double distance = 0;
	
	@Override
	public Individual[] replace ( FitnessFunction function, Individual[] parents, Individual[] children ) {
		
		distance = 0;
		double distances[] = new double[parents.length];
		
		
		Individual ret[] = new Individual[parents.length];
		for( int i = 0; i < parents.length; i++ ){
			distances[i] = parents[i].distance( children[i] );
			distance += distances[i];
		}
		
		distance /= ( parents.length / StdRandom.uniform( 0.5, 3.0 ) );
		
		for( int i = 0; i < parents.length; i++ ){
			ret[i] = distances[i] <= distance ? children[i] : parents[i];
		}
		return ret;
	}

}
