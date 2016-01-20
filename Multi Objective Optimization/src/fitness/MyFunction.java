package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class MyFunction implements FitnessFunction {

	private double constraintMin = 0;
	private double constraintMax = 1000;
	private int numVariables = 100;
	
	private double f( Individual ind, int p ){
		double []val = (double[]) ind.getValues();
		double ret = 0;
		
		for (int i = 0; i < val.length; i++) {
			ret += Math.pow(val[i], p );
		}
		
		return ret;
	}
	
	@Override
	public double[] evaluate(Individual individual) {
		double[] ret = { f( individual, 2 ), f( individual, 3 ) };
		
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
					var[i] = constraintMin;
				}
			}
			else if( var[i] > constraintMax ){
				var[i] = constraintMax;
			}
			else if( var[i] < constraintMin ){
				var[i] = constraintMin;
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
		
		for (int i = 0; i < values.length; i++) {
			values[i] = StdRandom.uniform( constraintMin, constraintMax );
		}
		
		ind = new DoubleIndividual( values );
		
		return ind;
	}
	
}
