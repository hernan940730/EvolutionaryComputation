package fitness;

import individual.DoubleIndividual;
import individual.Individual;
import util.StdRandom;

public class RosenbrockFunction implements FitnessFunction {
	
	private double constraintMin = -2.048;
	private double constraintMax = 2.048;
	private int call = 0;
	
	public RosenbrockFunction(){ }
	
	@Override
	public double fitness( Individual indiv ) {
		if( indiv.getFitness() != null ){
			return indiv.getFitness();
		}
		call++;
		double[] x = ( double[] ) indiv.getValues();
		indiv.setFitness( 100.0 * ( x[0] * x[0] - x[1] ) * ( x[0] * x[0] - x[1] ) + ( 1 - x[0] ) * ( 1 - x[0] ) );
		return indiv.getFitness();
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
		
		double[] values = new double[2];
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
