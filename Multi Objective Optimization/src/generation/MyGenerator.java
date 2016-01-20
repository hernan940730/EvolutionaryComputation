package generation;

import individual.Individual;

import util.StdRandom;
import fitness.FitnessFunction;

public class MyGenerator implements GenerationAlgorithm {
	
	private double crossingProb = 0.6;
	
	@Override
	public Individual[] generate( Individual i1, Individual i2, FitnessFunction f ){
		
		Individual[] children = new Individual[2];
		
		children[0] = i1.clone();
		children[1] = i2.clone();
		
		if( StdRandom.uniform(0.0, 1.0) <= crossingProb ){
			
			double[] p1 = ( double [] ) children[0].getValues();
			double[] p2 = ( double [] ) children[1].getValues();
			
			int rndPos1 = StdRandom.uniform( p1.length );
			
			for( int i = rndPos1; i < p1.length; i++ ){
				double tmp = p1[i];
				p1[i] = p2[i];
				p2[i] = tmp;
			}
			
			f.constraintSuccess( children[0] );
			f.constraintSuccess( children[1] );
			
		}
		return children;
	}

}
