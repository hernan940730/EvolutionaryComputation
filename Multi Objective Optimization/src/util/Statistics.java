package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Statistics {

	private String name;
	private ArrayList<double[]> pareto;  
	private ArrayList<double[]> frontier;
	private ArrayList<double[]> points;  
	
	
	public Statistics( String fileName ){
		name = fileName;
		pareto = new ArrayList<double[]>();
		frontier = new ArrayList<double[]>();
		points = new ArrayList<double[]>();
	}
	
	public void addPareto( double []values ){
		double tmp[] = new double[values.length];
		
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = values[i];
		}
		
		pareto.add( tmp );
		
	}
	
	public void addFrontier( double []values ){
		double tmp[] = new double[values.length];
		
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = values[i];
		}
		
		frontier.add( tmp );
		
	}
	
	public void addPoints( double []values ){
		double tmp[] = new double[values.length];
		
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = values[i];
		}
		
		points.add( tmp );
		
	}
	
	public void printResults(){
		BufferedWriter bw1 = null;
		BufferedWriter bw2 = null;
		BufferedWriter bw3 = null;
		
		try {
			bw1 = new BufferedWriter( new FileWriter( new File( name + " pareto" ) ) );
			bw2 = new BufferedWriter( new FileWriter( new File( name + " frontier" ) ) );
			bw3 = new BufferedWriter( new FileWriter( new File( name + " points" ) ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for (double []el : pareto ) {
			try {
				for( double i : el ){
					bw1.write( i + "\t");
				}
				bw1.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (double []el : frontier ) {
			try {
				for( double i : el ){
					bw2.write( i + "\t");
				}
				bw2.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (double []el : points ) {
			try {
				for( double i : el ){
					bw3.write( i + "\t");
				}
				bw3.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			bw1.close();
			bw2.close();
			bw3.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		
	}
	
}
