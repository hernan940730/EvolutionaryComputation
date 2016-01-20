package algorithm;

import java.util.ArrayList;

import mutation.MutationAlgorithm;
import replace.ReplaceAlgorithm;
import stopCondition.StopCondition;
import util.Statistics;
import factory.Factory;
import factory.Factory.TypeOfFitnessFunction;
import factory.Factory.TypeOfGenerationAlgorithm;
import factory.Factory.TypeOfMutationAlgorithm;
import factory.Factory.TypeOfReplaceAlgorithm;
import factory.Factory.TypeOfStopCondition;
import fitness.FitnessFunction;
import generation.GenerationAlgorithm;
import individual.Individual;

public class MyAlgorithm implements Algorithm {

	@Override
	public void run( int n, int lambda, TypeOfGenerationAlgorithm G, 
			TypeOfMutationAlgorithm M, TypeOfReplaceAlgorithm R, TypeOfFitnessFunction F, TypeOfStopCondition S ) {
		
		Statistics st = new Statistics( F.name() );
		
		FitnessFunction function = Factory.createFitnessFunction( F );
//		SelectionAlgorithm selector = Factory.createSelectionAlgorithm( TypeOfSelectionAlgorithm.RANDOM );
		GenerationAlgorithm generator = Factory.createGenerationAlgorithm( G );
		MutationAlgorithm mutator = Factory.createMutationAlgorithm( M );
		ReplaceAlgorithm replacer = Factory.createReplaceAlgorithm( R );
		
		StopCondition stopCondition = Factory.createStopCondition( S );
		
		Individual[] population = function.initialize( n );
		Individual[] children;
		
		
		ArrayList<Individual> paretoFrontier = generateParetoFrontier( population, function, st, lambda );		
		
		int t = 0;
		
		while( !stopCondition.stop( population, function, t++ ) ){
			
			children = generateChildren( population, paretoFrontier, function, generator, mutator );
			population = replacer.replace( function, population, children );
			
			if( t == 1 ){
				System.out.println("Pareto Frontier Individuals: ");
				for ( int i = 0; i < paretoFrontier.size(); i++ ) {
					double f[] = function.evaluate( paretoFrontier.get(i) );
					for (int j = 0; j < f.length; j++) {
						System.out.print( String.format("%.2e", f[j]) + " " );
					}
					System.out.println();
					
//					double vals[] = ( double[] )paretoFrontier.get( i ).getValues();
//					for ( int j = 0; j < vals.length; j++ ) {
//						System.out.print("x" + ( j + 1 ) + " = " + vals[j] + " " );
//					}
//					System.out.println();
//					System.out.println();
				}
				
			}
			updateParetoFrontier( paretoFrontier, population, function, st, lambda );
			
		}
		System.out.println();
		System.out.println("Pareto Frontier Individuals: ");
		for ( int i = 0; i < paretoFrontier.size(); i++ ) {
			double f[] = function.evaluate( paretoFrontier.get(i) );
			for (int j = 0; j < f.length; j++) {
				System.out.print( String.format("%.2e", f[j]) + " " );
			}
			System.out.println();
			
//			double vals[] = ( double[] )paretoFrontier.get( i ).getValues();
//			for ( int j = 0; j < vals.length; j++ ) {
//				System.out.print("x" + ( j + 1 ) + " = " + vals[j] + " " );
//			}
//			System.out.println();
//			System.out.println();
		}
		
		for( Individual ind : paretoFrontier ){
			st.addFrontier( function.evaluate( ind ) );
		}
		
		st.printResults();
		
	}
	
	private Individual[] generateChildren( Individual population[], ArrayList<Individual> paretoFrontier, FitnessFunction function, GenerationAlgorithm generator, MutationAlgorithm mutator ){
		
		final int N = population.length;
		final int M = paretoFrontier.size();
		Individual children[] = new Individual[N];
		
		int index = 0;
		boolean selected[] = new boolean[N];
		
		for ( int i = 0; i < M; i++ ) {
			int selInd = 0;
			Individual i1 = paretoFrontier.get(i);
			Individual i2;
			double dist = Double.MAX_VALUE;
			for( int j = 1; j < N; j++ ){
				double newDist = i1.distance( population[j] );
				if( !selected[j] && newDist < dist && newDist != 0 ){
					dist = newDist;
					selInd = j;
				}
			}
			i2 = population[selInd];
			selected[selInd] = true;
			Individual sons[] = generator.generate( i1, i2, function );
			children[index++] = mutator.mutate( sons[0], function );
			if( index == N ){
				break;
			}
			children[index++] = mutator.mutate( sons[1], function );
			if( index == N ){
				break;
			}
			
		}
		
		if( index < N ){
			for( int i = 0; i < N - 1; i++ ){
				if( !selected[i] ){
					int selInd = 0;
					Individual i1 = population[i];
					Individual i2;
					double dist = Double.MAX_VALUE;
					for( int j = i + 1; j < N; j++ ){
						double newDist = i1.distance( population[j] );
						if( !selected[j] && newDist < dist && newDist != 0 ){
							dist = newDist;
							selInd = j;
						}
					}
					i2 = population[selInd];
					selected[selInd] = true;
					selected[i] = true;
					
					Individual sons[] = generator.generate( i1, i2, function );
					children[index++] = mutator.mutate( sons[0], function );
					if( index == N ){
						break;
					}
					children[index++] = mutator.mutate( sons[1], function );
					if( index == N ){
						break;
					}
				}
			}
		}
		
		return children;
	}
	
	private ArrayList<Individual> generateParetoFrontier( Individual population[], FitnessFunction function, Statistics st, int lambda ){
		
		final int N = population.length;
		
		ArrayList<Individual> paretoFrontier = new ArrayList<Individual>();
		boolean possible[] = new boolean[N];
		for (int i = 0; i < possible.length; i++) {
			possible[i] = true;
		}
		
		for( int i = 0; i < N - 1; i++ ){
			if( possible[i] ){
				for( int j = i + 1; j < N; j++ ){
					double val = function.compare( population[i], population[j] );
					if( val < 0.5 ){
						possible[j] = false;
					}
					else if( val > 0.5 ){
						possible[i] = false;
					}else if( population[i].distance( population[j] ) == 0 ){
						possible[j] = false;
					}
				}
			}
		}
		
		for (int i = 0; i < possible.length; i++) {
			if( paretoFrontier.size() == lambda ){
				break;
			}
			if(possible[i]){
				Individual ind = population[i].clone();
				paretoFrontier.add( ind );
				st.addPareto( function.evaluate(ind) );
				st.addPoints( (double[])ind.getValues() );
			}
		}
		
		return paretoFrontier;
	} 
	
	private void updateParetoFrontier( ArrayList<Individual> paretoFrontier, Individual[] population, FitnessFunction function, Statistics st, int lambda ){
		final int N = population.length;
		
		for( int i = 0; i < N; i++ ){
			Individual i1 = population[i];
			boolean insert = true;
			for( int j = 0; j < paretoFrontier.size(); j++ ){
				Individual i2 = paretoFrontier.get( j );
				double val = function.compare( i1, i2 );
				if( val > 0.5 ){
					paretoFrontier.remove( j-- );
				}
				else if( val < 0.5 ){
					insert = false;
				}
				else if( i1.distance( i2 ) == 0 ){
					insert = false;
				}
			}
			if( insert && paretoFrontier.size() < lambda ){
				paretoFrontier.add( i1 );
				st.addPareto( function.evaluate( i1 ) );
				st.addPoints( (double[])i1.getValues() );
			}
		}
	}

}
