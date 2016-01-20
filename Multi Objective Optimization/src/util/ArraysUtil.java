package util;

public class ArraysUtil {
	
	public static int binarySearch( double values[], double search ){
		int min = 0 , max = values.length;
		int index = max / 2;
		
		while( max - min > 1 ){
			if( values[index] == search ){
				return index;
			}
			if( values[index] < search ){
				min = index;
			}else{
				max = index;
			}
			index = (max + min) / 2;
		}
		if( values[index] < search ){
			index++;
		}
		return index;
	}
	
}
