package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import fitness.FitnessFunction;
import individual.Individual;

public final class Statistics {
	
	private static BufferedReader br;
	private static BufferedWriter bw;
	public static String fName = "Test.ods";
	private static ArrayList<Double> results = new ArrayList<Double>();
	
	private Statistics(){}
	
	private static double median( ArrayList<Double> list ){
		return list.get( list.size() / 2 );
	}
	
	private static double mean( ArrayList<Double> list ) {
		double mean = 0.0;
		for( int i = 0; i < list.size(); i++ ){
			mean += list.get( i );
		}
		return mean / list.size();
	}

	private static double standardDeviation( ArrayList<Double> list, double mean ){
		double stDev = 0.0;
		for (int i = 0; i < list.size(); i++) {
			stDev += ( list.get( i ) - mean ) * ( list.get( i ) - mean );
		}
		
		return Math.sqrt( stDev / ( list.size() - 1 ) );
	}
	
	private static double min( ArrayList<Double> list ){
		return list.get( 0 );
	}
	
	private static double max( ArrayList<Double> list ){
		return list.get( list.size() - 1 );
	}
	
	public static void run( Individual[] group, FitnessFunction ff, boolean minimize ){ //TODO put fitness on individual
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 0; i < group.length; i++) {
			list.add( ff.fitness( group[i] ) );
		}
			
		run( list, ff, minimize );
		
	}
	
	public static void run( ArrayList<Double> list, FitnessFunction ff, boolean minimize ){
	 
		
		Collections.sort( list );
		double best = minimize ? min(list) : max(list);
		double median = median(list);
		double worst = minimize ? max(list) : min(list);
		double mean = mean(list);
		double stDev = standardDeviation(list, mean);
		
		results.add(best);
		results.add(median);
		results.add(worst);
		results.add(mean);
		results.add(stDev);
		
	}
	
	public static void print(){
		
		File file = new File( fName );
		ArrayList<String> doc = new ArrayList<String>();
		
		try{
			if(file.exists()){
				br = new BufferedReader( new FileReader( file ) );
			}
		} catch( IOException e ){
			System.err.println("Error 001 - File Error");
		}
		
		if(file.exists()){
			String tmp;
			try{
				while( (tmp = br.readLine()) != null ){
					doc.add( tmp );
				}
				br.close();
			}catch( IOException e ){
				System.err.println("Error 001 - File error");
			}
		}
		
		try{
			bw = new BufferedWriter( new FileWriter( file ) );
			
			for( int i = 0; i < results.size(); i++ ){
				bw.write( ( doc.isEmpty() ? ";": doc.get( i ) + ";" ) + 
						String.format( Locale.US, "%1.2e", results.get( i )) + "\n" );
			}
						
			bw.close();
			
		}catch( IOException e ){
			System.err.println("Error 001 - File error");
		}
		
	}
	
}
