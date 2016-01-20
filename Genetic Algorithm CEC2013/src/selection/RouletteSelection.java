package selection;

import individual.Individual;
import util.StdRandom;
import fitness.FitnessFunction;

public class RouletteSelection implements SelectionAlgorithm {
	
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
	
	@Override
	public Individual[] selection( Individual[] individuals, FitnessFunction f, boolean minimize ){ //TODO change point form
		final int n = individuals.length;
		
		Individual ret[] = new Individual[n];
		double points[] = new double[n];
		double selectCondition = 0;
		double secondCondition = 0;
		
		if( minimize ){
			selectCondition = Double.MIN_VALUE;
			selectCondition = Double.MIN_VALUE + 1;
		}
		else{
			selectCondition = Double.MAX_VALUE;
			selectCondition = Double.MAX_VALUE - 1;
		}
		for( int i = 0; i < n; i++ ){
			points[i] = f.fitness( individuals[i] );
			if( minimize ? points[i] > selectCondition : points[i] < selectCondition ){
				if( minimize ? selectCondition > secondCondition : selectCondition < secondCondition ){
					secondCondition = selectCondition;
				}
				selectCondition = points[i];
			}else{
				if( minimize ? points[i] > secondCondition: points[i] < secondCondition ){
					secondCondition = points[i];
				}
			}
		}
		double up = Math.abs( selectCondition - secondCondition ) / 2.0;
		if( up <= 0 ){
			up = 0.5;
		}
		double delta = StdRandom.uniform( 0, up );
		
		selectCondition += minimize ? delta: delta * -1;
		double sum = 0;
		
		for( int i = 0; i < n; i++ ){
			points[i] = Math.abs( points[i] - selectCondition );
			sum += points[i];
		}
		
		double distances[] = new double[n - 1];
		for( int iter = 0; iter < n; iter++ ){
			if( Double.isInfinite( sum ) || Double.isNaN( sum ) || sum <= 0){
				sum = Double.MAX_VALUE;
			}
			double choose = StdRandom.uniform( 0, sum );
			double curr = 0.0;
			boolean enter = false;
			for ( int i = 0; i < n; i++ ) {
				curr += points[i];
				if( choose < curr ){
					put( ret, distances, individuals[i].clone2(), iter );
//					ret[iter] = individuals[i].clone2();
					enter = true;
					break;
				} 
			}
			if( !enter ){
				put( ret, distances, individuals[n - 1].clone2(), iter );
//				ret[iter] = individuals[n - 1].clone2();
			}
		}
		
//		for( int i = 0; i < n - 1; i++ ){
//			System.out.print( ret[i].distance(ret[i + 1]) + " ");
//		}
//		System.out.println();
		
		return ret;
	}

}
