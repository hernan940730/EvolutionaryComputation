package fitness;

import test.BenchmarkCEC2013_LSGO;
import test.BenchmarksClass;
import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class F7 implements FitnessFunction {
	
	private BenchmarksClass bm = new BenchmarksClass();
	private double constraintMin = -100;
	private double constraintMax = 100;
	private int numVariables = 1000;
	private int call = 0;
	
	@Override
	public double fitness(Individual variables) {
		if( variables.getFitness() != null ){
			return variables.getFitness();
		}
		call++;
		double []x = (double[])variables.getValues();
		variables.setFitness( BenchmarkCEC2013_LSGO.f7(x) );
		
		return variables.getFitness();
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
	public Individual[] initialize( int lambda ) {
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
		return call;
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
	public double getConstraintMax(){
		return constraintMax;
	}
	
}
