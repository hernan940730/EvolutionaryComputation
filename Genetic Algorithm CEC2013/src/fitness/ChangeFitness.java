package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class ChangeFitness implements FitnessFunction {

	private int numVariables = 1;
	private double constraintMin = -100;
	private double constraintMax = 100;
	private int calls = 0;
	private int fun = 0;
	
	@Override
	public double fitness(Individual variables) {
		
		double []x = (double[])variables.getValues();
		
		if( calls % 100  == 0 ){
			fun = ( fun + 1 ) % 3;
			
		}

		double ret = 0;
		switch( fun ){
		case 0:
			ret = (x[0]*x[0]) +x[0] + 3;
			break;
		case 1:
			ret = (x[0]*x[0]*x[0]) - (2 * x[0] * x[0]) + 3;
			break;
		default:
			ret = (x[0] * x[0]) - (3 * x[0]) + 2;
			break;
		}
		
		return ret;
	}

	@Override
	public void constraintSuccess(Individual variables) {
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
	public Individual[] initialize(int lambda) {
		Individual individuals[] = new DoubleIndividual[lambda];
		for( int i = 0; i < individuals.length; i++ ){
			individuals[i] = rndValues();
			double par[] = individuals[i].getParameters();
			par[Individual.DELTA] = constraintMax/100.0;
			individuals[i].setParameters(par);
		}
		return individuals;
	}

	@Override
	public int callsNumber() {
		calls++;
		return calls;
	}

	@Override
	public double getConstraintMax() {
		return constraintMax;
	}

}
