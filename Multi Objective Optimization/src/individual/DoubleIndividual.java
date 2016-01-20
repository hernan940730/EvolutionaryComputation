package individual;

public class DoubleIndividual implements Individual {

	private double[] values;
	private double parameters[] = { 1 };
	
	public DoubleIndividual( double[] values ) {
		this.values = new double[values.length];
		for( int i = 0; i < values.length; i++ ){
			this.values[i] = values[i];
		}
	}
	
	@Override
	public String toString() {
		String ret = "{ ";
		for ( int i = 0; i < values.length; i++ ) {
			
			ret += values[i] + " ";
		}
		ret += "}";
		return ret;
	}

	@Override
	public Object getValues() {
		return values;
	}
	
	@Override
	public Individual clone(){
		return new DoubleIndividual( values );
	}

	@Override
	public void setValues( Object obj ) {
		double []values = ( double [] ) obj;
		for (int i = 0; i < values.length; i++) {
			this.values[i] = values[i];
		}
	}

	@Override
	public double distance( Individual other ) {
		double []val = ( double[] ) other.getValues();
		double distance = 0.0;
		for ( int i = 0; i < values.length; i++ ) {
			distance += ( val[i] - values[i] ) * ( val[i] - values[i] );
		}
		return distance;
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
