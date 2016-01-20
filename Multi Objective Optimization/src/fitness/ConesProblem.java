package fitness;

import util.StdRandom;
import individual.DoubleIndividual;
import individual.Individual;

public class ConesProblem implements FitnessFunction {
	
	private int numVariables = 2;
	
	private double minr = 0;
	private double maxr = 10;
	
	private double minh = 0;
	private double maxh = 20;
	
	private double minV = 200;
	
	@Override
	public double[] evaluate( Individual individual ) {
		double []x = ( double [] ) individual.getValues();
		
		double r = x[0];
		double h = x[1];
		
		double s = Math.sqrt( r * r + h * h );
		
		double B = Math.PI * r * r;
		double S = Math.PI * r * s;
		double T = B + S;
		
		double[] ret = {S, T};
		
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
	public void constraintSuccess(Individual variable) {
		double values[] = (double []) variable.getValues();
		
		if( values[0] > maxr ){
			values[0] = maxr;
		}
		if( values[0] < minr ){
			values[0] = minr;
		}
		if( values[1] > maxh ){
			values[1] = maxh;
		}
		if( values[1] < minh ){
			values[1] = minh;
		}
		
		double V = Math.PI * values[0] * values[0] * values[1] / 3;
		
		while( V < minV ){
			if( values[0] + 0.25 <= maxr ){
				values[0] += 0.25;
			}
			if( values[1] + 0.5 <= maxh ){
				values[1] += 0.5;
			}
			
			V = Math.PI * values[0] * values[0] * values[1] / 3;
		}
		
	}

	@Override
	public Individual[] initialize( int lambda ) {
		Individual[] ret = new Individual[lambda];
		
		for( int i = 0; i < lambda; i++ ){
			double values[] = new double[numVariables];
			double r = 0;
			double minh = maxh + 1;
			while( minh > maxh ){
				r = StdRandom.uniform( minr, maxr );  
				minh = minV / ( Math.PI * r * r / 3 );
			}
			
			double h = StdRandom.uniform( minh, maxh );
			values[0] = r;
			values[1] = h;
			
			ret[i] = new DoubleIndividual( values );
		}
		
		return ret;
	}

}
