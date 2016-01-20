package individual;

import java.math.BigInteger;
import java.util.ArrayList;

public class DoubleIndividual implements Individual {

	private Double fitness = null;
	private double[] values;
	private ArrayList<boolean []> binaryValues;
	private double parameters[] = { 1 };
	
	public DoubleIndividual( double[] values ) {
		this.values = new double[values.length];
		for( int i = 0; i < values.length; i++ ){
			this.values[i] = values[i];
		}
		generateBinaries();
	}
	
	public DoubleIndividual( ArrayList<boolean []> binaryValues ) {
		this.binaryValues = new ArrayList<boolean[]>();
		for( int i = 0; i < binaryValues.size(); i++ ){
			int n = binaryValues.get( i ).length;
			boolean tmp[] = new boolean[n];
			for( int j = 0; j < n; j++ ){
				tmp[j] = binaryValues.get( i )[j];
			}
			this.binaryValues.add( tmp );
		}
		generateValues();
	}

	private void generateValues(){
		int n = binaryValues.size();
		values = new double[n];
		for( int i = 0; i < n; i++ ){
			boolean tmp[] = binaryValues.get(i);
			int m = tmp.length;
			String binary = "";
		    for ( int j = 0; j < m; j++ ) {
		    	binary += tmp[j] ? "1" : "0";
		    }
			values[i] = Double.longBitsToDouble(new BigInteger(binary, 2).longValue());
			
		}
	}
	
	private void generateBinaries( ){
		binaryValues = new ArrayList<boolean[]>();
		for( int i = 0; i < values.length; i++ ){
			double d = values[i];
			char[] binaryString = Long.toBinaryString( Double.doubleToRawLongBits( d ) ).toCharArray();
			int binarySize = binaryString.length;
			
			int index = 0;
			
			boolean[] binary = new boolean[64];
			
			while( binarySize++ < 64 ){
				binary[index++] = false;
			}
			
			for( int j = 0; j < binaryString.length; j++ ){
				binary[index++] = binaryString[j] == '1';
			}
			binaryValues.add(binary);
		}
	}
	
	@Override
	public String toString() {
		String ret = "{ ";
		for ( int i = 0; i < values.length; i++ ) {
			
			ret += values[i] + " : [";
			
//			for (int j = 0; j < binaryValues.get(i).length; j++) {
//				if( j % 8 == 0 && j != 0){
//					ret += " "; 
//				}
//				ret += ( binaryValues.get(i)[j] ? "1" : "0" );
//			}
//			if(i != values.length - 1){
//				ret += "], ";
//			}
		}
		ret += "] }";
		return ret;
	}

	@Override
	public Object getValues() {
		double ret[] = new double[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = values[i];
		}
		return ret;
	}
	
	@Override
	public ArrayList<boolean []> getBinaryValue() {
		ArrayList<boolean []> ret = new ArrayList<boolean[]>();
		for (int i = 0; i < binaryValues.size(); i++) {
			boolean []bin = binaryValues.get(i);
			boolean []tmp = new boolean[bin.length];
			for (int j = 0; j < bin.length; j++) {
				tmp[j] = bin[j];
			}
			ret.add( tmp );
		}
		return ret;
	}	
	
	public Individual clone2(){
		return new DoubleIndividual( values );
	}

	@Override
	public void setValues( Object obj ) {
		double []values = ( double [] ) obj;
		for (int i = 0; i < values.length; i++) {
			this.values[i] = values[i];
		}
		generateBinaries();
	}

	@Override
	public void setBinaryValue( ArrayList<boolean[]> binary ) {
		
		for ( int i = 0; i < binaryValues.size(); i++ ) {
			boolean[] tmp = binaryValues.get(i);
			
			for ( int j = 0; j < tmp.length; j++ ) {
				tmp[j] = binary.get(i)[j];
			}
		}
		
		generateValues();
	}

	@Override
	public double distance( Individual other ) {
		double []val = ( double[] ) other.getValues();
		double distance = 0.0;
		for ( int i = 0; i < values.length; i++ ) {
			distance += Math.abs( val[i] - values[i] );
		}
		return distance;
	}
	
	@Override
	public Double getFitness() {
		return fitness;
	}
	
	@Override
	public void setFitness(double d) {
		fitness = d;
	}

	@Override
	public double[] getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(double[] parameters) {
		for (int i = 0; i < parameters.length; i++) {
			this.parameters[i] = parameters[i];
		}
		
	}
	
}
