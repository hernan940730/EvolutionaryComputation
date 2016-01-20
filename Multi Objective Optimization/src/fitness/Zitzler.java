package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class Zitzler implements FitnessFunction {

	private int numVariables = 30;
	private double minConstraint = 0;
	private double maxConstraint = 1;
	
	@Override
	public double[] evaluate(Individual individual) {
		double []x = (double []) individual.getValues();
		double f1 = x[0];
		double f2 = 0;
		double g = 0;
		double h = 0;
		
		for( int i = 1; i < numVariables; i++ ){
			g += x[i];
		}
		
		g *= 9.0 / 29.0;
		g += 1;
		
		h = 1 - Math.sqrt( f1 / g ) - ( f1 / g ) * Math.sin( 10 * Math.PI * f1 );
		f2 = g * h;
		double ret[] = {f1, f2};
		
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
