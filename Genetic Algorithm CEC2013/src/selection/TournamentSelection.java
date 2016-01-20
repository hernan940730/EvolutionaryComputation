package selection;

import java.util.ArrayList;

import util.StdRandom;
import fitness.FitnessFunction;
import individual.Individual;

public class TournamentSelection implements SelectionAlgorithm{

	private void put( Individual[] group, double[] distances, Individual indiv, int size ){
		if( size == 0 ){
			group[size] = indiv;
		}
		else if( size == 1 ){
			group[size] = indiv;
			distances[size - 1] = group[size - 1].distance( group[size] );
		}
		else{
			int position = -1;
			double max = Double.MIN_VALUE;
			
			for( int i = 0; i < size - 1; i++ ){
				double tmp = group[i].distance( indiv );
				if( tmp < distances[i] && tmp > max ){
					position = i;
					max = distances[i] - tmp;
					// Aqui puede ir lo del else de abajo
				}
			}
			if( position == -1 ){
				group[size] = indiv;
				distances[size - 1] = group[size - 1].distance( indiv );
			}else{
				for( int j = size; j > position + 1; j-- ){
					group[j] = group[j - 1];
					distances[j - 1] = distances[j - 2];
				}
				group[position + 1] = indiv;
				distances[position] = group[position].distance( indiv );
				distances[position + 1] = indiv.distance( group[position + 2] );
			}
		}
	}
	
	private Individual compite( Individual[] individues, FitnessFunction f ){

		double delta = 1;
		ArrayList<Integer> competitors = new ArrayList<Integer>();
		int n = individues.length;
		
		for( int i = 0; i < n; i++ ){
			competitors.add(i);
		}
		
		while( competitors.size() > 1 ){
			for( int i = 0; i < competitors.size(); i += 2 ){
				if( i + 1 < competitors.size() ){
					double f1 = f.fitness( individues[competitors.get( i )] );
					double f2 = f.fitness( individues[competitors.get( i + 1 )] );
					if( Math.min( f1, f2 ) <= 0 ){
						f1 -= Math.min( f1, f2 ) - delta;
						f2 -= Math.min( f1, f2 ) - delta;
					}
					
					double rnd = StdRandom.uniform( 0.0, f1 + f2 );
					
					competitors.remove( f1 < rnd ? i + 1 : i );
					
				}
			}
		}
		
		return individues[competitors.get(0)].clone2();
	}
	
	@Override
	public Individual[] selection(Individual[] individues, FitnessFunction fun,
			boolean minimize) {
		int n = individues.length;
		
		double distances[] = new double[n - 1];
		Individual[] ind = new Individual[n];
		for( int i = 0; i < n; i++ ){
			put(ind, distances, compite(individues, fun), i);
		}
		return ind;
	}

}
