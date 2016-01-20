package main;

import replace.ReplaceAlgorithm;
import selection.SelectionAlgorithm;
import stopCondition.StopCondition;
import util.Statistics;
import factory.Factory;
import factory.Factory.TypeOfAlgorithm;
import factory.Factory.TypeOfFitnessFunction;
import factory.Factory.TypeOfGenerationAlgorithm;
import factory.Factory.TypeOfReplaceAlgorithm;
import factory.Factory.TypeOfSelectionAlgorithm;
import factory.Factory.TypeOfStopCondition;
import fitness.FitnessFunction;
import generation.GenerationAlgorithm;
import algorithm.Algorithm;

public class Main {

	private static Algorithm currentAlgorithm;
	private static StopCondition currentStopCondition;
	private static FitnessFunction currentFitnessFunction;
	private static SelectionAlgorithm currentSelectionAlgorithm;
	private static GenerationAlgorithm currentGenerationAlgorithm;
	private static ReplaceAlgorithm currentReplaceAlgorithm;
	
	private static int currLambda = 50; 
	
	private static void init( TypeOfAlgorithm t1, TypeOfFitnessFunction t2, TypeOfStopCondition t3, 
			TypeOfSelectionAlgorithm t4, TypeOfGenerationAlgorithm t5, TypeOfReplaceAlgorithm t6 ){
		
		Factory.selectAlgorithm( t1 );
		currentAlgorithm = Factory.createAlgorithm();
		
		Factory.selectFitnessFunction( t2 );
		currentFitnessFunction = Factory.createFitnessFunction();
		
		Factory.selectStopCondition( t3 );
		currentStopCondition = Factory.createStopCondition();
		
		Factory.selectSelectionAlgorithm( t4 );
		currentSelectionAlgorithm = Factory.createSelectionAlgorithm();
		
		Factory.selectGenerationAlgorithm( t5 );
		currentGenerationAlgorithm = Factory.createGenerationAlgorithm();
		
		Factory.selectReplaceAlgorithm( t6 );
		currentReplaceAlgorithm = Factory.createReplaceAlgorithm();
	}
	
	public static void main( String[] args ) {
		
		double time = System.currentTimeMillis();
		TypeOfFitnessFunction tf = TypeOfFitnessFunction.F1;
		if(args.length != 0){
			int cases = Integer.parseInt(args[0]);
			switch( cases ){
				case 1:
					tf = TypeOfFitnessFunction.F1;
					Statistics.fName = "F1.ods";
					break;
				case 2:
					tf = TypeOfFitnessFunction.F2;
					Statistics.fName = "F2.ods";
					break;
				case 3:
					tf = TypeOfFitnessFunction.F3;
					Statistics.fName = "F3.ods";
					break;
				case 4:
					tf = TypeOfFitnessFunction.F4;
					Statistics.fName = "F4.ods";
					break;
				case 5:
					tf = TypeOfFitnessFunction.F5;
					Statistics.fName = "F5.ods";
					break;
				case 6:
					tf = TypeOfFitnessFunction.F6;
					Statistics.fName = "F6.ods";
					break;
				case 7:
					tf = TypeOfFitnessFunction.F7;
					Statistics.fName = "F7.ods";
					break;
				case 8:
					tf = TypeOfFitnessFunction.F8;
					Statistics.fName = "F8.ods";
					break;
				case 9:
					tf = TypeOfFitnessFunction.F9;
					Statistics.fName = "F9.ods";
					break;
				case 10:
					tf = TypeOfFitnessFunction.F10;
					Statistics.fName = "F10.ods";
					break;
				case 11:
					tf = TypeOfFitnessFunction.F11;
					Statistics.fName = "F11.ods";
					break;
				case 12:
					tf = TypeOfFitnessFunction.F12;
					Statistics.fName = "F12.ods";
					break;
				case 13:
					tf = TypeOfFitnessFunction.F13;
					Statistics.fName = "F13.ods";
					break;
				case 14:
					tf = TypeOfFitnessFunction.F14;
					Statistics.fName = "F14.ods";
					break;
				case 15:
					tf = TypeOfFitnessFunction.F15;
					Statistics.fName = "F15.ods";
					break;
			}
		}
		
		init( TypeOfAlgorithm.MY_ALGORITHM, tf, TypeOfStopCondition.FITNESS_EVALUATION, 
			 TypeOfSelectionAlgorithm.TOURNAMENT, TypeOfGenerationAlgorithm.DOUBLE_VALUES, 
			 TypeOfReplaceAlgorithm.CLASSIC );
		
		currentAlgorithm.run( currentStopCondition, currentFitnessFunction, 
				currentSelectionAlgorithm, currentGenerationAlgorithm, currentReplaceAlgorithm, currLambda );
		
		Statistics.print();
		
		System.out.println( Statistics.fName + ": " + ( System.currentTimeMillis() - time ) / 1000.0);
	}
}
