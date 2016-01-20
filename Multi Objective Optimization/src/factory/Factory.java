package factory;

import mutation.MutationAlgorithm;
import mutation.MyMutator;
import fitness.*;
import generation.*;
import replace.*;
import selection.*;
import stopCondition.*;
import algorithm.*;
import individual.*;


public class Factory {
	
	public enum TypeOfAlgorithm{
		MY_ALGORITHM
	}
	
	public enum TypeOfIndividual{
		DOUBLE
	}
	
	public enum TypeOfStopCondition{
		CLASSIC
	}
	
	public enum TypeOfFitnessFunction{
		MY_FUNCTION, F1, F2, CONES_PROBLEM, ZITZLER, FONSECA, VIENNET
	}
	
	public enum TypeOfSelectionAlgorithm{
		RANDOM
	}
	
	public enum TypeOfGenerationAlgorithm{
		MY_GENERATOR
	}
	
	public enum TypeOfMutationAlgorithm{
		MY_MUTATOR
	}
	
	public enum TypeOfReplaceAlgorithm{
		CLASSIC, RANDOM_REPLACE
	}
	
	public static Algorithm createAlgorithm( TypeOfAlgorithm currAlgorithm ){
		Algorithm ret;
		switch( currAlgorithm ){
			case MY_ALGORITHM:
				ret = new MyAlgorithm();
				break; 
			default:
				ret = new MyAlgorithm();
				break;
		}
		return ret;
	}
	
	public static Individual createIndividual( TypeOfIndividual currIndividual, Object values ){
		Individual ret;
		switch( currIndividual ){
			case DOUBLE:
				ret = new DoubleIndividual( ( double[] )values );
				break; 
			
			default:
				ret = new DoubleIndividual( ( double[] ) values );
				break;
		}
		return ret;
	}
	
	public static StopCondition createStopCondition( TypeOfStopCondition currStopCondition ){
		StopCondition ret;
		switch( currStopCondition ){
			case CLASSIC:
				ret = new ClassicStopCondition();
				break; 
			default:
				ret = new ClassicStopCondition();
				break;
		}
		return ret;
	}
	
	public static FitnessFunction createFitnessFunction( TypeOfFitnessFunction currFitnessFunction ){
		FitnessFunction ret;
		switch( currFitnessFunction ){
			case F1:
				ret = new F1();
				break;
			case F2:
				ret = new F2();
				break;
			case MY_FUNCTION:
				ret = new MyFunction();
				break;
			case CONES_PROBLEM:
				ret = new ConesProblem();
				break;
			case ZITZLER:
				ret = new Zitzler();
				break;
			case VIENNET:
				ret = new Viennet();
				break;
			case FONSECA:
				ret = new Fonseca();
				break;
			default:
				ret = new F1();
				break;
		}
		return ret;
	}
	
	public static SelectionAlgorithm createSelectionAlgorithm( TypeOfSelectionAlgorithm currSelectionAlgorithm ){
		SelectionAlgorithm ret;
		switch( currSelectionAlgorithm ){
			case RANDOM:
				ret = new RandomSelection();
				break;
			default:
				ret = new RandomSelection();
				break;
		}
		return ret;
	}
	
	public static SelectionAlgorithm [] createAllSelectionAlgorithm( ){
		TypeOfSelectionAlgorithm val[] = TypeOfSelectionAlgorithm.values();
		SelectionAlgorithm sel[] = new SelectionAlgorithm[val.length];
		for (int i = 0; i < sel.length; i++) {
			sel[i] = Factory.createSelectionAlgorithm( val[i] );
		}
		return sel;
	}
	
	public static GenerationAlgorithm createGenerationAlgorithm( TypeOfGenerationAlgorithm currGenerationAlgorithm ){
		GenerationAlgorithm ret;
		switch( currGenerationAlgorithm ){
			case MY_GENERATOR:
				ret = new MyGenerator();
				break; 
			default:
				ret = new MyGenerator();
				break;
		}
		return ret;
	}
	
	public static GenerationAlgorithm[] createAllGenerationAlgorithm( ){
		TypeOfGenerationAlgorithm val[] = TypeOfGenerationAlgorithm.values();
		GenerationAlgorithm sel[] = new GenerationAlgorithm[val.length];
		for (int i = 0; i < sel.length; i++) {
			sel[i] = Factory.createGenerationAlgorithm( val[i] );
		}
		return sel;
	}
	
	public static MutationAlgorithm createMutationAlgorithm( TypeOfMutationAlgorithm currMutationAlgorithm ){
		MutationAlgorithm ret;
		switch( currMutationAlgorithm ){
			case MY_MUTATOR:
				ret = new MyMutator();
				break; 
			default:
				ret = new MyMutator();
				break;
		}
		return ret;
	}
	
	public static MutationAlgorithm[] createAllMutationAlgorithm( ){
		TypeOfMutationAlgorithm val[] = TypeOfMutationAlgorithm.values();
		MutationAlgorithm sel[] = new MutationAlgorithm[val.length];
		for (int i = 0; i < sel.length; i++) {
			sel[i] = Factory.createMutationAlgorithm( val[i] );
		}
		return sel;
	}
	
	
	public static ReplaceAlgorithm createReplaceAlgorithm( TypeOfReplaceAlgorithm currReplaceAlgorithm ){
		ReplaceAlgorithm ret;
		switch( currReplaceAlgorithm ){
			case CLASSIC:
				ret = new ClassicReplacer();
				break;
			case RANDOM_REPLACE:
				ret = new RandomReplacer();
				break; 
			default:
				ret = new ClassicReplacer();
				break;
		}
		return ret;
	}
	
	public static ReplaceAlgorithm [] createAllReplaceAlgorithm( ){
		TypeOfReplaceAlgorithm val[] = TypeOfReplaceAlgorithm.values();
		ReplaceAlgorithm sel[] = new ReplaceAlgorithm[val.length];
		for (int i = 0; i < sel.length; i++) {
			sel[i] = Factory.createReplaceAlgorithm( val[i] );
		}
		return sel;
	}
	
}
