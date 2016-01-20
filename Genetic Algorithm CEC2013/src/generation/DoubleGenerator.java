package generation;

import individual.Individual;

import util.StdRandom;
import factory.Factory;
import fitness.FitnessFunction;

public class DoubleGenerator implements GenerationAlgorithm {
	
	private double crossingProb = 0.6;
	
	private void mutation( Individual ind, FitnessFunction f ){
		
		double[] values = ( double [] ) ind.getValues();
		
		int n = values.length;

		double p = 1.0 / (double)n;
		
		for( int i = 0; i < n; i++ ){
			if( StdRandom.uniform() < p ){
				values[i] = StdRandom.gaussian( 0, ind.getParameters()[Individual.DELTA] );
			}
		}	
		ind.setValues( values );
		f.constraintSuccess( ind );
		
	}
	
	private Individual[] crossing( Individual i1, Individual i2, FitnessFunction f, boolean minimize ){
		
		double[] p1 = ( double [] ) i1.getValues();
		double[] p2 = ( double [] ) i2.getValues();
		
		Individual[] children = new Individual[2];
		
		int rndPos1 = StdRandom.uniform( p1.length );
		
		for( int i = rndPos1; i < p1.length; i++ ){
			double tmp = p1[i];
			p1[i] = p2[i];
			p2[i] = tmp;
		}
		
		children[0] = Factory.createIndividual( p1 );
		children[1] = Factory.createIndividual( p2 );
		
		f.constraintSuccess( children[0] );
		f.constraintSuccess( children[1] );
		
		return children;
	}
	
	@Override
	public Individual[] generate( Individual[] indiv, FitnessFunction f, boolean minimize ) { //TODO buscar un mejor modo
		
		int n = indiv.length;
		Individual[] ret = new Individual[n];
		
		for( int i = 0; i < n; i += 2 ){
			if( StdRandom.uniform() < crossingProb ){
				if( i != n - 1 ){
					Individual[] children = crossing( indiv[i], indiv[i + 1], f, minimize );
					if( indiv[i].distance( children[0] ) + indiv[i + 1].distance( children[1] ) <
							indiv[i].distance( children[1] ) + indiv[i + 1].distance( children[0] ) ){
						ret[i] = children[0];
						ret[i + 1] = children[1];
					}else{
						ret[i] = children[1];
						ret[i + 1] = children[0];
					}
				}
				else{
					ret[i] = indiv[i].clone2();
				}
			}else{
				ret[i] = indiv[i].clone2();
				if( i != n - 1 ){
					ret[i + 1] = indiv[i + 1].clone2();
				}
			}
			
			mutation( ret[i], f );
			if( i != n - 1 ){
				mutation( ret[i + 1], f );
			}
			
		}
		return ret;
	}

}
