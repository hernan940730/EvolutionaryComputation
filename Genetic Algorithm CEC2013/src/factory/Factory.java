package factory;

import java.util.ArrayList;

import fitness.*;
import generation.*;
import replace.*;
import selection.*;
import stopCondition.*;
import algorithm.*;
import individual.*;


public class Factory {
	
	private static TypeOfAlgorithm currAlgorithm = TypeOfAlgorithm.GENETIC;
	private static TypeOfIndividual currIndividual = TypeOfIndividual.DOUBLE;
	private static TypeOfStopCondition currStopCondition = TypeOfStopCondition.CLASSIC;
	private static TypeOfFitnessFunction currFitnessFunction = TypeOfFitnessFunction.ROSENBROCK;
	private static TypeOfSelectionAlgorithm currSelectionAlgorithm = TypeOfSelectionAlgorithm.ROULETTE;
	private static TypeOfGenerationAlgorithm currGenerationAlgorithm = TypeOfGenerationAlgorithm.CLASSIC;
	private static TypeOfReplaceAlgorithm currReplaceAlgorithm = TypeOfReplaceAlgorithm.CLASSIC;
	
	public enum TypeOfAlgorithm{
		GENETIC, MY_ALGORITHM
	}
	
	public enum TypeOfIndividual{
		DOUBLE
	}
	
	public enum TypeOfStopCondition{
		CLASSIC, FITNESS_EVALUATION
	}
	
	public enum TypeOfFitnessFunction{
		ROSENBROCK, SCHWEFEL, RASTRIGIN, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, CHANGE_FUN
	}
	
	public enum TypeOfSelectionAlgorithm{
		ROULETTE, TOURNAMENT
	}
	
	public enum TypeOfGenerationAlgorithm{
		CLASSIC, DOUBLE_VALUES
	}
	
	public enum TypeOfReplaceAlgorithm{
		CLASSIC, SIMPLE_REPLACE
	}
	
	public static void selectAlgorithm( TypeOfAlgorithm type  ){
		currAlgorithm = type;
	}
	
	public static void selectStopCondition( TypeOfStopCondition type  ){
		currStopCondition = type;
	}
	
	public static void selectFitnessFunction( TypeOfFitnessFunction type  ){
		currFitnessFunction = type;
	}
	
	public static void selectSelectionAlgorithm( TypeOfSelectionAlgorithm type  ){
		currSelectionAlgorithm = type;
	}
	
	public static void selectGenerationAlgorithm( TypeOfGenerationAlgorithm type  ){		
		currGenerationAlgorithm = type;		
	}
	
	public static void selectReplaceAlgorithm( TypeOfReplaceAlgorithm type  ){		
		currReplaceAlgorithm = type;		
	}
	
	public static Algorithm createAlgorithm( ){
		Algorithm ret;
		switch( currAlgorithm ){
			case GENETIC:
				ret = new GeneticAlgorithm();
				break; 
			case MY_ALGORITHM:
				ret = new MyAlgorithm();
				break; 
			default:
				ret = new GeneticAlgorithm();
				break;
		}
		return ret;
	}
	
	public static Individual createIndividual( Object values ){
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
	
	public static Individual createIndividual( ArrayList<boolean[]> values ){
		Individual ret;
		switch( currIndividual ){
			case DOUBLE:
				ret = new DoubleIndividual( values );
				break; 
			
			default:
				ret = new DoubleIndividual( values );
				break;
		}
		return ret;
	}
	
	public static StopCondition createStopCondition( ){
		StopCondition ret;
		switch( currStopCondition ){
			case CLASSIC:
				ret = new ClassicStopCondition();
				break; 
			case FITNESS_EVALUATION:
				ret = new FitnessEvaluationCondition();
				break;
			default:
				ret = new ClassicStopCondition();
				break;
		}
		return ret;
	}
	
	public static FitnessFunction createFitnessFunction( ){
		FitnessFunction ret;
		switch( currFitnessFunction ){
			case CHANGE_FUN:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new ChangeFitness();
			break; 
			case ROSENBROCK:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new RosenbrockFunction();
				break; 
			case SCHWEFEL:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new SchwefelFunction();
				break;
			case RASTRIGIN:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new RastriginFunction();
				break;
			case F1:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F1();
				break;
			case F2:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F2();
				break;
			case F3:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F3();
				break;
			case F4:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F4();
				break;
			case F5:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F5();
				break;
			case F6:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F6();
				break;
			case F7:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F7();
				break;
			case F8:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F8();
				break;
			case F9:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F9();
				break;
			case F10:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F10();
				break;
			case F11:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F11();
				break;
			case F12:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F12();
				break;
			case F13:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F13();
				break;
			case F14:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F14();
				break;
			case F15:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new F15();
				break; 
			default:
				currIndividual = TypeOfIndividual.DOUBLE;
				ret = new RosenbrockFunction();
				break;
		}
		return ret;
	}
	
	public static SelectionAlgorithm createSelectionAlgorithm( ){
		SelectionAlgorithm ret;
		switch( currSelectionAlgorithm ){
			case ROULETTE:
				ret = new RouletteSelection();
				break; 
			case TOURNAMENT:
				ret = new TournamentSelection();
				break;
			default:
				ret = new RouletteSelection();
				break;
		}
		return ret;
	}
	
	public static SelectionAlgorithm [] createAllSelectionAlgorithm( ){
		TypeOfSelectionAlgorithm val[] = TypeOfSelectionAlgorithm.values();
		SelectionAlgorithm sel[] = new SelectionAlgorithm[val.length];
		for (int i = 0; i < sel.length; i++) {
			Factory.selectSelectionAlgorithm(val[i]);
			sel[i] = Factory.createSelectionAlgorithm();
		}
		return sel;
	}
	
	public static GenerationAlgorithm createGenerationAlgorithm( ){
		GenerationAlgorithm ret;
		switch( currGenerationAlgorithm ){
			case CLASSIC:
				ret = new ClassicGeneration();
				break; 
			case DOUBLE_VALUES:
				ret = new DoubleGenerator();
				break; 
			default:
				ret = new ClassicGeneration();
				break;
		}
		return ret;
	}
	
	public static ReplaceAlgorithm createReplaceAlgorithm( ){
		ReplaceAlgorithm ret;
		switch( currReplaceAlgorithm ){
			case CLASSIC:
				ret = new ClassicReplacer();
				break;
			case SIMPLE_REPLACE:
				ret = new SimpleReplacer();
				break; 
			default:
				ret = new ClassicReplacer();
				break;
		}
		return ret;
	}
}
