package test;

import java.io.IOException;

public class Temp {

	public static void main( String[] args ) throws IOException {

//		BufferedReader in = new BufferedReader( new FileReader( new File(
//				"cdatafiles\\F3-xopt.txt" ) ) );
//		String a;
//		int cont = 0;
		double x[] = new double[1000];

		BenchmarksClass functions = new BenchmarksClass();
		long time = System.currentTimeMillis();
		for( int i = 0; i < 1; i++ ){

			x = inicializar();
			System.out.println( "F1 : " + functions.F1( x ) );
			x = inicializar();
			System.out.println( "F2 : " + functions.F2( x ) );
			x = inicializar();
			System.out.println( "F3 : " + functions.F3( x ) );
			x = inicializar();
			System.out.println( "F4 : " + functions.F4( x ) );
			x = inicializar();
			System.out.println( "F5 : " + functions.F5( x ) );
			x = inicializar();
			System.out.println( "F6 : " + functions.F6( x ) );
			x = inicializar();
			System.out.println( "F7 : " + functions.F7( x ) );
			x = inicializar();
			System.out.println( "F8 : " + functions.F8( x ) );
			x = inicializar();
			System.out.println( "F9 : " + functions.F9( x ) );
			x = inicializar();
			System.out.println( "F10 : " + functions.F10( x ) );
			x = inicializar();
			System.out.println( "F11 : " + functions.F11( x ) );
			x = inicializar();
			System.out.println( "F12 : " + functions.F12( x ) );
			x = inicializar();
			System.out.println( "F13 : " + functions.F13( x ) );
			x = inicializar();
			System.out.println( "F14 : " + functions.F14( x ) );
			x = inicializar();
			System.out.println( "F15 : " + functions.F15( x ) );
		}
		//System.out.println( (System.currentTimeMillis() - time) );
	}

	public static double[] inicializar() {
		double[] x = new double[1000];
		for( int i = 0; i < x.length; i++ ){
			x[i] = 1;
		}
		return x;
	}
}
