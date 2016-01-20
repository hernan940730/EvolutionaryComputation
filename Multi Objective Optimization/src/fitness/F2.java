package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class F2 implements FitnessFunction {
	
	private double constraintMin = -1;
	private double constraintMax = 1;
	private int numVariables = 30;
	
	
	private double f1( double[] x ){
		double ret = x[0];
		double sum = 0;
		for( int j = 3; j <= numVariables; j += 2 ){
			sum += Math.pow( x[j - 1] - ( 0.3 * x[0] * x[0] * Math.cos( 24 * Math.PI * x[0] + ( 4 * j * Math.PI ) / numVariables ) + 0.6 * x[0] ) * Math.cos( 6 * Math.PI * x[0] + ( j * Math.PI ) / numVariables ), 2 );
		}
		sum *= 1 / 7;
		return ret + sum;
	}
	
	private double f2( double[] x ){
		double ret = 1 - Math.sqrt( x[0] );
		double sum = 0;
		for( int j = 2; j <= numVariables; j += 2 ){
			sum += Math.pow( x[j - 1] - ( 0.3 * x[0] * x[0] * Math.cos( 24 * Math.PI * x[0] + ( 4 * j * Math.PI ) / numVariables ) + 0.6 * x[0] ) * Math.sin( 6 * Math.PI * x[0] + ( j * Math.PI ) / numVariables ), 2 );
		}
		sum *= 2 / 15;
		return ret + sum;
	}
	
	
	@Override
	public double[] evaluate(Individual individual) {
		double ret[] = new double[2];
		double x[] = (double []) individual.getValues(); 
		
		ret[0] = f1(x);
		ret[1] = f2(x);
		
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
	public void constraintSuccess( Individual variables ) {
		double[] var = ( double[] )variables.getValues();

		for ( int i = 0; i < var.length; i++ ) {
			if( Double.isNaN(var[i]) ){
				var[i] = 0;
			}
			else if( Double.isInfinite( var[i] ) ){
				if( var[i] > 0 ){
					var[i] = constraintMax;
				}
				else{
					var[i] = i == 0 ? 0 : constraintMin;
				}
			}
			else if( var[i] > constraintMax ){
				var[i] = constraintMax;
			}
			else if( var[i] < constraintMin ){
				var[i] = i == 0 ? 0 : constraintMin;
			}
		}
	}

	@Override
	public Individual[] initialize( int lambda ) {
		Individual individuals[] = new DoubleIndividual[lambda];
		for( int i = 0; i < individuals.length; i++ ){
			individuals[i] = rndValues();
			double par[] = individuals[i].getParameters();
			par[Individual.DELTA] = ( constraintMax - constraintMin )/100.0;
			individuals[i].setParameters(par);
		}
		return individuals;
	}

	private DoubleIndividual rndValues(){
		DoubleIndividual ind;
		
		double[] values = new double[numVariables];
		
		values[0] = StdRandom.uniform( 0, constraintMax );
		
		for (int i = 1; i < values.length; i++) {
			values[i] = StdRandom.uniform( constraintMin, constraintMax );
		}
		
		ind = new DoubleIndividual( values );
		
		return ind;
	}
	
}
