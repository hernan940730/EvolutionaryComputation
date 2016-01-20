package main;

import factory.Factory;
import factory.Factory.TypeOfAlgorithm;
import factory.Factory.TypeOfFitnessFunction;
import factory.Factory.TypeOfGenerationAlgorithm;
import factory.Factory.TypeOfMutationAlgorithm;
import factory.Factory.TypeOfReplaceAlgorithm;
import factory.Factory.TypeOfStopCondition;
import algorithm.Algorithm;

public class Main {
	
	private static int n = 100;
	private static int lambda = 50;
	
	public static void main( String[] args ) {
		
		TypeOfFitnessFunction F = TypeOfFitnessFunction.VIENNET;
		TypeOfGenerationAlgorithm G = TypeOfGenerationAlgorithm.MY_GENERATOR;
		TypeOfMutationAlgorithm M = TypeOfMutationAlgorithm.MY_MUTATOR;
		TypeOfReplaceAlgorithm R = TypeOfReplaceAlgorithm.CLASSIC;
		TypeOfStopCondition S = TypeOfStopCondition.CLASSIC;
		TypeOfAlgorithm A = TypeOfAlgorithm.MY_ALGORITHM;
		
		double time = System.currentTimeMillis( );
		
		Algorithm algorithm = Factory.createAlgorithm( A );
		
		algorithm.run( n, lambda, G, M, R, F, S );
		
		System.out.println( "Time elapsed: " + ( System.currentTimeMillis() - time ) / 1000.0 );
	}
}
