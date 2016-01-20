package individual;

import java.util.ArrayList;

public interface Individual {
	
	static final int DELTA = 0;
	
	/**
	 * 
	 * @return an array of values of the Individual 
	 */
	Object getValues();//TODO Hacer que retorne un <T>
	
	
	/**
	 * 
	 * @param obj the object to be setter
	 */
	void setValues(Object obj);
	
	/**
	 * 
	 * @return an array of bits that represent the values
	 */
	ArrayList<boolean []> getBinaryValue();
	
	/**
	 * 
	 * @param binary an array of bits to be setter
	 */
	void setBinaryValue(ArrayList<boolean[]> binary);
	
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
	Individual clone2();
	
	/**
	 * 
	 * @return if fitness was calculated return the fitness otherwise return null
	 */
	Double getFitness();
	
	/**
	 * 
	 * @param d set a value o fitness for this individual
	 */
	void setFitness(double d);
	
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
