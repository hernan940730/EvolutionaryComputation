package replace;

import util.StdRandom;
import fitness.FitnessFunction;
import individual.Individual;

public class RandomReplacer implements ReplaceAlgorithm {

	@Override
	public Individual[] replace( FitnessFunction function, Individual[] parents, 
			Individual[] children) {
		
		final int N = parents.length;
		final int M = children.length;
		Individual ret[] = new Individual[N];
		
		for( int i = 0; i < N; i++ ){
			int ind1 = StdRandom.uniform( N );
			int ind2 = StdRandom.uniform( M );
			
			ret[i] = function.compare( children[ind2], parents[ind1] ) >= 0.5 ? 
					children[ind2] : parents[ind1];
				
		}
		
		return ret;
	}

}
