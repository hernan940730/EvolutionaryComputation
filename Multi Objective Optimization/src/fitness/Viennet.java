package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class Viennet implements FitnessFunction {

	private int numVariables = 2;
	private double minConstraint = -3;
	private double maxConstraint = 3;
	
	@Override
	public double[] evaluate(Individual individual) {
		double []x = (double []) individual.getValues();
		
		double f1 = 0.5 * ( x[0] * x[0] + x[1] * x[1] ) + Math.sin( x[0] * x[0] + x[1] * x[1] );
		double f2 = Math.pow( 3 * x[0] - 2 * x[1] + 4, 2 ) / 8.0 + Math.pow( x[0] - x[1] + 1, 2 ) / 27.0 + 15;
		double f3 = 1 / ( x[0] * x[0]  + x[1] * x[1] + 1 ) - 1.1 * Math.exp( -( x[0] * x[0] + x[1] * x[1] ) );
		
		double ret[] = { f1, f2, f3 };
		return ret;
	}

	@Override
	public double compare(Individual i1, Individual i2) {
		double f1[] = evaluate( i1 );
		double f2[] = evaluate( i2 );
		
		int better = 0;
		int equal = 0;
		int worst = 0;
		
		for( int i = 0; i < f1.length; i++ ){
			if( f1[i] < f2[i] ){
				better++;
			}
			else if( f1[i] == f2[i] ){
				equal++;
			}
			else{
				worst++;
			}
		}
		
		if( equal == f1.length ){
			return 0.5;
		}
		else if( worst + equal == f1.length ){
			return 0;
		}
		else if( better + equal == f1.length ){
			return 1;
		}
		return 0.5;
	}

	@Override
	public void constraintSuccess(Individual variables) {
		double[] var = ( double[] )variables.getValues();

		for ( int i = 0; i < var.length; i++ ) {
			if( Double.isNaN(var[i]) ){
				var[i] = 0;
			}
			else if( Double.isInfinite( var[i] ) ){
				if( var[i] > 0 ){
					var[i] = maxConstraint;
				}
				else{
					var[i] = i == 0 ? 0 : minConstraint;
				}
			}
			else if( var[i] > maxConstraint ){
				var[i] = maxConstraint;
			}
			else if( var[i] < minConstraint ){
				var[i] = i == 0 ? 0 : minConstraint;
			}
		}
	}

	@Override
	public Individual[] initialize( int lambda ) {
		Individual individuals[] = new DoubleIndividual[lambda];
		for( int i = 0; i < individuals.length; i++ ){
			individuals[i] = rndValues();
			double par[] = individuals[i].getParameters();
			par[Individual.DELTA] = ( maxConstraint - minConstraint ) / 100.0;
			individuals[i].setParameters(par);
		}
		return individuals;
	}

	private DoubleIndividual rndValues(){
		DoubleIndividual ind;
		
		double[] values = new double[numVariables];
		
		values[0] = StdRandom.uniform( 0, maxConstraint );
		
		for (int i = 1; i < values.length; i++) {
			values[i] = StdRandom.uniform( minConstraint, maxConstraint );
		}
		
		ind = new DoubleIndividual( values );
		
		return ind;
	}

}
