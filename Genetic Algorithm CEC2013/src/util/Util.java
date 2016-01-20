package util;

import individual.Individual;

public class Util {
	public static void shuffle( Boolean [] binary ){
		
		int n = binary.length;
		
		for( int i = 0; i < n; i++ ){
			int p1 = StdRandom.uniform( n );
			int p2;
			while( ( p2 = StdRandom.uniform( n ) ) == p1 );
			Boolean tmp = binary[p1];
			binary[p1] = binary[p2];
			binary[p2] = tmp;
		}
	}
	
	public static void shuffle( Individual [] indiv ){
		
		int n = indiv.length;
		
		for( int i = 0; i < n; i++ ){
			int p1 = StdRandom.uniform( n );
			int p2;
			while( ( p2 = StdRandom.uniform( n ) ) == p1 );
			Individual tmp = indiv[p1];
			indiv[p1] = indiv[p2];
			indiv[p2] = tmp;
		}
	}
}