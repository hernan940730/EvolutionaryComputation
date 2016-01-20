package generation;

import individual.Individual;

import java.util.ArrayList;

import util.StdRandom;
import factory.Factory;
import fitness.FitnessFunction;

public class ClassicGeneration implements GenerationAlgorithm {
	
	private double crossingProb = 0.6;	
	
	private void mutation( Individual ind, FitnessFunction f ){
		
		ArrayList<boolean[]> binary = ind.getBinaryValue();
		
		int n = binary.size();
		int m = binary.get( 0 ).length;
		double p = 1.0 / (double)(n * m);
		
		for( int i = 0; i < n; i++ ){
			for ( int j = 0; j < m; j++ ) {
				binary.get( i )[j] = StdRandom.uniform() < p ? !binary.get( i )[j] : binary.get( i )[j];
			}
		}	
		ind.setBinaryValue( binary );
		f.constraintSuccess( ind );
	}
	
	private Individual[] crossing( Individual i1, Individual i2, FitnessFunction f, boolean minimize ){
		
		ArrayList<boolean []> p1 = i1.getBinaryValue();
		ArrayList<boolean []> p2 = i2.getBinaryValue();
		
		Individual[] children = new Individual[2];
		
		int rndPos1 = StdRandom.uniform( p1.size() );
		int rndPos2 = StdRandom.uniform( p1.get( rndPos1 ).length );
		
		
		for( int i = rndPos1; i < p1.size(); i++ ){
			boolean []bin1 = p1.get(i);
			boolean []bin2 = p2.get(i);
			for( int j = ( i == rndPos1 ? rndPos2 : 0 ); j < bin1.length; j++ ){
				bin1[j] = bin2[j];
				bin2[j] = bin1[j];
			}
		}
		
		children[0] = Factory.createIndividual( p1 );
		children[1] = Factory.createIndividual( p2 );
		
		f.constraintSuccess( children[0] );
		f.constraintSuccess( children[1] );
		
		return children;
	}
	
	@Override
	public Individual[] generate( Individual[] ind, FitnessFunction f, boolean minimize ) {//TODO buscar un mejor modo
		
		int n = ind.length;
		Individual[] ret = new Individual[n];
		
		for( int i = 0; i < n; i += 2 ){
			if( StdRandom.uniform() < crossingProb ){
				if( i != n - 1 ){
					Individual []ch = crossing( ind[i], ind[i + 1], f, minimize );
					ret[i] = ch[0];
					ret[i + 1] = ch[1];
				}
				else{
					ret[i] = ind[i].clone2();
				}
			}else{
				ret[i] = ind[i].clone2();
				if( i != n - 1 ){
					ret[i + 1] = ind[i + 1].clone2();
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
