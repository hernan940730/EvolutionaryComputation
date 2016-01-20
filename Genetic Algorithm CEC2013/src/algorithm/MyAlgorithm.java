package algorithm;

import java.util.ArrayList;

import factory.Factory;
import fitness.FitnessFunction;
import generation.GenerationAlgorithm;
import individual.Individual;
import replace.ReplaceAlgorithm;
import selection.SelectionAlgorithm;
import stopCondition.StopCondition;
import util.Statistics;
import util.StdRandom;

public class MyAlgorithm implements Algorithm {
	
	private boolean minimize = true;
	
	
	@Override
	public Individual[] run(StopCondition cond, FitnessFunction fitness,
			SelectionAlgorithm selector, GenerationAlgorithm generator,
			ReplaceAlgorithm replacer, int lambda) {
		
		SelectionAlgorithm sel[] = Factory.createAllSelectionAlgorithm();
		
		return run( cond, fitness, sel, generator, replacer, lambda );
	}
	
	
	public Individual[] run( StopCondition cond, FitnessFunction fitness,
			SelectionAlgorithm []selector, GenerationAlgorithm generator,
			ReplaceAlgorithm replacer, int lambda ) {
		
		int t = 0;
		
		double prob[] = new double[selector.length];
		
		for (int i = 0; i < prob.length; i++) {
			prob[i] = 1 / prob.length;
		}
		
		Individual parents[] = fitness.initialize( lambda );
		
		double mini = minimize ? Double.MAX_VALUE : Double.MIN_VALUE;
		
		double better = minimize ? Double.MAX_VALUE : Double.MIN_VALUE;
		
		for (int i = 0; i < parents.length; i++) {
			double fit = fitness.fitness( parents[i] );
			if( minimize ) {
				if( better > fit ){
					better = fit;
				}
			}else{
				if( better < fit ){
					better = fit;
				}
			}
		}
		
		double previous = better;
		
		boolean first = true;
		boolean second = true;
		ArrayList<Double> values = new ArrayList<Double>(); 
		
		ArrayList<Double> minis = new ArrayList<Double>();
		ArrayList<Double> maxis = new ArrayList<Double>();
		ArrayList<Double> promis = new ArrayList<Double>();
		ArrayList<Double> desvis = new ArrayList<Double>();
		
		while( !cond.stop( parents, fitness, t ) ){
			for( int l = 0; l < lambda; l += 2 ){
				Individual[] marriage = new Individual[2];
				marriage[0] = parents[StdRandom.uniform(lambda)];
				marriage[1] = parents[StdRandom.uniform(lambda)];
				marriage = generator.generate( marriage, fitness, minimize );
				parents[l] = marriage[0];
				if( l+1 < lambda ){
					parents[l+1] = marriage[1];
				}
			}
			double rnd = StdRandom.uniform( 0.0, 1.0 );
			double sum = 0.0;
			SelectionAlgorithm sel = selector[selector.length - 1];
			int index = selector.length - 1;
			for ( int i = 0; i < prob.length; i++ ) {
				if( (sum += prob[i] ) >= rnd ){
					sel = selector[i];
					index = i;
					break;
				}
			}
			parents = sel.selection( parents, fitness, minimize );
			
			
			double prom = 0.0;
			better = minimize ? Double.MAX_VALUE : Double.MIN_VALUE;
			
			for( int i = 0; i < parents.length; i++ ){
				double fit = fitness.fitness( parents[i] ); 
				values.add( fit );
				prom += fit;
				if( minimize ) {
					if( better > fit ){
						better = fit;
					}
				}else{
					if( better < fit ){
						better = fit;
					}
				}
			}
			prom /= parents.length;
			if( prom != mini ){
				
				prob[index] += minimize && prom < mini? ( 1 - prob[index] ) / prob.length : (-1 * prob[index] ) / prob.length;
				
				for (int i = 0; i < prob.length; i++) {
					if( i != index){
						prob[i] += minimize && prom < mini ? -1 * ( 1 - prob[index] ) / (prob.length * (prob.length - 1) ) : ( prob[index] ) / (prob.length * (prob.length - 1) );
					}
				}
				
				mini = minimize && prom < mini ? prom : mini;
				mini = !minimize && prom > mini ? prom : mini;
			}
			
			if( Math.abs( previous - better ) < StdRandom.uniform(0.0, 1)){
				
				for (int i = 0; i < parents.length; i++) {
					double par [] = parents[i].getParameters();
					par[Individual.DELTA] += StdRandom.uniform(0.5,fitness.getConstraintMax() * 2);
					parents[i].setParameters( par );
				}
			}else if ( Math.abs( previous - better ) > StdRandom.uniform(100, 10000) ) {		
				for (int i = 0; i < parents.length; i++) {
					double par [] = parents[i].getParameters();
					if( par[Individual.DELTA] > 0 ){
						par[Individual.DELTA] -= StdRandom.uniform( 0, par[Individual.DELTA]/2 );						
					}
					
					parents[i].setParameters( par );
				}
			}
			
			previous = better;
			
			t++;
			
			if( fitness.callsNumber() >= 120000 && first ){
				first = false;
				Statistics.run( values, fitness, minimize );
			}
			if( fitness.callsNumber() >= 600000 && second ){
				second = false;
				Statistics.run( values, fitness, minimize );
			}
			
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			double desEst = 0;
			double prome = 0;
			
			for( int i = 0; i < parents.length; i++ ){
				double fit = fitness.fitness( parents[i] );
				values.add( fit );
				if( fit < min ){
					min = fit;
				}
				if( fit > max ){
					max = fit;
				}
				prome += fit;
			}
			prome /= parents.length;
			
			for (int i = 0; i < parents.length; i++) {
				double fit = fitness.fitness(parents[i]);
				desEst = ( fit - prome ) * ( fit - prome );
			}
			desEst /= parents.length - 1;
			
			minis.add(min);
			maxis.add(max);
			promis.add(prome);
			desvis.add(desEst);
			System.out.println(min);
		}
		Statistics.run( values, fitness, minimize );
		
		System.out.println("ITER;MIN;MAX;PROM;D.EST");
		for (int i = 0; i < minis.size(); i++) {
			System.out.print((i+1) + ";");
			System.out.print(minis.get(i)+";");
			System.out.print(maxis.get(i)+";");
			System.out.print(promis.get(i)+";");
			System.out.println(desvis.get(i));
		}
		
		return parents;
	}




}
