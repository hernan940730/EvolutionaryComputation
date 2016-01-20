package individual;

public interface Individual {
	
	static final int DELTA = 0;
	
	/**
	 * 
	 * @return an array of values of the Individual 
	 */
	Object getValues();
	
	
	/**
	 * 
	 * @param obj the object to be setter
	 */
	void setValues(Object obj);
	
	/**
	 * 
	 * @param other an individual 
	 * @return The distance between two individuals, this and other.
	 */
	double distance(Individual other);
	
	
	/**
	 * 
	 * @return a clone of the individual
	 */
	Individual clone();
	
	/**
	 * 
	 * @return the parameters of the individual
	 */
	double[] getParameters();
	
	/**
	 * 
	 * @param parameters the parameters to set
	 */
	void setParameters(double[] parameters);
	
}
