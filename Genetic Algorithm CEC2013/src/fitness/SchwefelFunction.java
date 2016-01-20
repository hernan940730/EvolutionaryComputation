package fitness;

import individual.DoubleIndividual;
import individual.Individual;
import util.StdRandom;

public class SchwefelFunction implements FitnessFunction {
	
	private double constraintMin = -512;
	private double constraintMax = 512;
	private int numVariables = 10;
	private int call = 0;
	
	public SchwefelFunction(){ }
	
	@Override
	public double fitness( Individual indiv ) {
		if( indiv.getFitness() != null ){
			return indiv.getFitness();
		}
		call++;
		double[] x = ( double[] ) indiv.getValues();
		int n = x.length;
		double ret = 418.9829 * n;
		for ( int i = 0; i < n; i++ ){
			ret += -x[i] * Math.sin( Math.sqrt( Math.abs( x[i] ) ) );
		}
		indiv.setFitness( ret );
		return ret;
	}

	@Override
	public Individual[] initialize( int lambda ) {
		Individual individuals[] = new DoubleIndividual[lambda];
		for( int i = 0; i < individuals.length; i++ ){
			individuals[i] = rndValues();
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

	@Override
	public void constraintSuccess( Individual variables ) {
		double[] var = ( double[] )variables.getValues();
		boolean isChanged = false;
		
		for ( int i = 0; i < var.length; i++ ) {
			if( Double.isNaN(var[i]) ){
				isChanged = true;
				var[i] = 0;
			}
			else if( Double.isInfinite( var[i] ) ){
				isChanged = true;
				if( var[i] > 0 ){
					var[i] = constraintMax;
				}
				else{
					var[i] = constraintMin;
				}
			}
			else if( var[i] > constraintMax ){
				isChanged = true;
				var[i] = constraintMax;
			}
			else if( var[i] < constraintMin ){
				isChanged = true;
				var[i] = constraintMin;
			}
		}
		if( isChanged ){
			variables.setValues( var );
		}
	}

	@Override
	public int callsNumber() {
		return call;
	} 
	
	@Override
	public double getConstraintMax(){
		return constraintMax;
	}
	
}
