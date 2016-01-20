package algorithm;

import java.util.ArrayList;

import individual.Individual;
import replace.ReplaceAlgorithm;
import selection.SelectionAlgorithm;
import stopCondition.StopCondition;
import util.Statistics;
import fitness.FitnessFunction;
import generation.GenerationAlgorithm;

public class GeneticAlgorithm implements Algorithm {
	
	private boolean minimize;
	public GeneticAlgorithm( ) {
		minimize = true;
	}
	
	public GeneticAlgorithm( boolean minimize ) {
		this.minimize = minimize;
	}
	
	@Override
	public Individual [] run( StopCondition T, FitnessFunction f, SelectionAlgorithm selector, 
			GenerationAlgorithm generator, ReplaceAlgorithm replacer, int lambda ) {
		
		int t = 0;
		Individual[] P = f.initialize( lambda );
		
		boolean first = true;
		boolean second = true;
		ArrayList<Double> values = new ArrayList<Double>(); 
		
		ArrayList<Double> minis = new ArrayList<Double>();
		ArrayList<Double> maxis = new ArrayList<Double>();
		ArrayList<Double> promis = new ArrayList<Double>();
		ArrayList<Double> desvis = new ArrayList<Double>();
		
		while( !T.stop( P, f, t ) ){
			Individual Q[] = selector.selection( P, f, minimize ); // lambda-individuos, seleccion por ruleta, torneo, ranqueo o USS -> repetir lambda veces
			Individual H[] = generator.generate( Q, f, minimize ); // generar lambda-individuos:
					//1. se hacen parejas
					//se hace cruce si cumple probabilidad P=0.6 para cada hijo
					//se hace una mutacion si cumple probabilidad por cada bit P = 1/len(cromosoma) para cada hijo
					//se ponen en H
			P = replacer.replace( P, H );
			
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			double desEst = 0;
			double prom = 0;
			
			for( int i = 0; i < P.length; i++ ){
				double fit = f.fitness( P[i] );
				values.add( fit );
				if( fit < min ){
					min = fit;
				}
				if( fit > max ){
					max = fit;
				}
				prom += fit;
			}
			prom /= P.length;
			
			for (int i = 0; i < P.length; i++) {
				double fit = f.fitness(P[i]);
				desEst = ( fit - prom) * ( fit - prom );
			}
			desEst /= P.length - 1;
			
			minis.add(min);
			maxis.add(max);
			promis.add(prom);
			desvis.add(desEst);
			
			System.out.println(min);
			
			if( f.callsNumber() >= 120000 && first ){
				first = false;
				Statistics.run( values, f, minimize );
			}
			if( f.callsNumber() >= 600000 && second ){
				second = false;
				Statistics.run( values, f, minimize );
			}
			
			
			t++;
		}
		Statistics.run( values, f, minimize );
		System.out.println("ITER;MIN;MAX;PROM;D.EST");
		for (int i = 0; i < minis.size(); i++) {
			System.out.print((i+1) + ";");
			System.out.print(minis.get(i)+";");
			System.out.print(maxis.get(i)+";");
			System.out.print(promis.get(i)+";");
			System.out.println(desvis.get(i));
		}
		
		return P;
	}
	
}
