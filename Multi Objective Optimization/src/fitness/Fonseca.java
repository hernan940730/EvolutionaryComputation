package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class Fonseca implements FitnessFunction {

	private int numVariables = 20;
	private double minConstraint = -4;
	private double maxConstraint = 4;
	
	@Override
	public double[] evaluate(Individual individual) {
		double []x = (double []) individual.getValues();
		double f1 = 0;
		double f2 = 0;
		for (int i = 0; i < x.length; i++) {
			f1 += ( x[i] - ( 1 / Math.sqrt( x.length ) ) ) * ( x[i] - ( 1 / Math.sqrt( x.length ) ) ); 
		}
		for (int i = 0; i < x.length; i++) {
			f2 += ( x[i] + ( 1 / Math.sqrt( x.length ) ) ) * ( x[i] + ( 1 / Math.sqrt( x.length ) ) ); 
		}
		f1 = 1 - Math.exp(-f1);
		f2 = 1 - Math.exp(-f2);
		
		double ret[] = {f1,f2};
		
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
