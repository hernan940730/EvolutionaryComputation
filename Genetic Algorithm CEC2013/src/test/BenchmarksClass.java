package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BenchmarksClass implements BenchmarksInterface {

	long M;
	long A;
	long m_seed;
	long MASK;
	double m_nextGaussian;
	boolean m_havenextGaussian;
	boolean setOvectorToZero;
	long ID;
	IndexMap indexMap[];
	int s_size;
	int overlap;
	double[] Ovector;
	double[][] OvectorVec;
	int[] Pvector;
	double[] RotMatrix;
	double[][] MultiRotMatrix1D;

	double[] anotherz;
	double[] anotherz1;
	double[] anotherz2;

	boolean[] interArray;

	int minX;
	int maxX;
	int dimension;
	int nonSeparableGroupSize;
	long functionInitRandomSeed;
	long arrSize;

	double[][] r25;
	double[][] r50;
	double[][] r100;
	int[] s;
	double[] w;

	public static final double PI = 3.141592653589793238462643383279;
	public static final double E = 2.718281828459045235360287471352;

	public BenchmarksClass() {
		dimension = 1000;
		nonSeparableGroupSize = 50;
		MASK = ((L( 1 )) << (L( 48 ))) - (L( 1 ));
		m_havenextGaussian = false;

		if ( dimension < nonSeparableGroupSize ){
			System.out.println( "errooooooooooor" );
		}
		setOvectorToZero = false;
	}

	public long L( int i ) {
		return (long) i;
	}

	public double D( double i ) {
		return ((double) i);
	}

	public double log( double s ) {
		return Math.log( s );
	}

	public double sqrt( double s ) {
		return Math.sqrt( s );
	}

	public double pow( double a, double b ) {
		return Math.pow( a, b );
	}

	public double cos( double s ) {
		return Math.cos( s );
	}

	public double sin( double s ) {
		return Math.sin( s );
	}

	public double exp( double s ) {
		return Math.exp( s );
	}

	public double abs( double s ) {
		return Math.abs( s );
	}

	@Override
	public int next( int bits ) {
		long s;
		long result;
		m_seed = s = (((m_seed * M) + A) & MASK);
		result = (s >> (L( 48 - bits )));
		return ((int) result);
	}

	@Override
	public int nextInt( int n ) {
		int bits, val;
		if ( (n & (-n)) == n ){
			return ((int) ((n * L( next( 31 ) )) >> L( 31 )));
		}
		do{
			bits = next( 31 );
			val = bits % n;
		} while ( bits - val + (n - 1) < 0 );
		return (val);
	}

	@Override
	public double nextDouble() {
		return ((((L( next( 26 ) )) << (L( 27 ))) + (L( next( 27 ) ))) / (double) ((L( 1 )) << (L( 53 ))));
	}

	@Override
	public double nextGaussian() {
		double multiplier, v1, v2, s;

		if ( m_havenextGaussian ){
			m_havenextGaussian = false;
			return (m_nextGaussian);
		}

		do{
			v1 = ((D( 2.0 ) * nextDouble()) - D( 1.0 ));
			v2 = ((D( 2.0 ) * nextDouble()) - D( 1.0 ));
			s = ((v1 * v1) + (v2 * v2));
		} while ( (s >= D( 1.0 )) || (s <= D( 0.0 )) );

		multiplier = sqrt( D( -2.0 ) * log( s ) / s );
		m_nextGaussian = (v2 * multiplier);
		m_havenextGaussian = true;
		return (v1 * multiplier);
	}

	@Override
	public double[] createShiftVector( int dim, double min, double max ) {

		double[] d;
		double hw, middle;
		double s;
		int i;
		hw = (D( 0.5 ) * (max - min));
		middle = (min + hw);
		d = new double[dim];
		for( i = (dim - 1); i >= 0; i-- ){
			if ( setOvectorToZero == true ){
				d[i] = 0;
			} else{
				do{
					double tempGauss = nextGaussian();
					s = (middle + (tempGauss * hw));
				} while ( (s < min) || (s > max) );
				d[i] = s;
			}
		}
		return (d);
	}

	@Override
	public int[] createPermVector( int dim ) {
		int[] d;
		int i, j, k, t;
		d = new int[dim];
		for( i = (dim - 1); i >= 0; i-- ){
			d[i] = i;
		}
		for( i = (dim << 3); i >= 0; i-- ){
			j = nextInt( dim );
			do{
				k = nextInt( dim );
			} while ( k == j );
			t = d[j];
			d[j] = d[k];
			d[k] = t;
		}
		return (d);
	}

	@Override
	public double[][] createRotMatrix( int dim ) {
		double[][] m;
		int i, j, k;
		double dp, t;
		// m = (double**)malloc(sizeof(double*) * dim);
		m = new double[dim][];

		for( i = 0; i < dim; i++ ){
			m[i] = new double[dim];
		}

		loop: while ( true ){
			for( i = (dim - 1); i >= 0; i-- ){
				for( j = (dim - 1); j >= 0; j-- ){
					m[i][j] = nextGaussian();
				}
			}
			boolean flag = false;
			// main loop of gram/schmidt
			tt: for( i = (dim - 1); i >= 0; i-- ){
				for( j = (dim - 1); j > i; j-- ){
					// dot product
					dp = 0;

					for( k = (dim - 1); k >= 0; k-- ){
						dp += (m[i][k] * m[j][k]);
					}

					// subtract
					for( k = (dim - 1); k >= 0; k-- ){
						m[i][k] -= (dp * m[j][k]);
					}
				}

				// normalize
				dp = 0;

				for( k = (dim - 1); k >= 0; k-- ){
					t = m[i][k];
					dp += (t * t);
				}

				// linear dependency -> restart
				if ( dp <= 0 ){
					flag = true;
					break tt;
				}

				dp = (1 / sqrt( dp ));

				for( k = (dim - 1); k >= 0; k-- ){
					m[i][k] *= dp;
				}
			}
			if ( flag == false )
				return (m);

		}
	}

	@Override
	public double[] createRotMatrix1D( int dim ) {
		double[][] a;
		double[] b;
		int i, j, k;
		a = createRotMatrix( dim );
		b = new double[dim * dim];
		k = 0;

		for( i = 0; i < dim; i++ ){
			for( j = 0; j < dim; j++ ){
				b[k++] = a[i][j];
			}
		}
		return (b);
	}

	@Override
	public double[][] createMultiRotateMatrix1D( int dim, int num ) {
		double[][] a;
		int i;
		/* allocate storage for an array of pointers */
		a = new double[num][];
		/* for each pointer, allocate storage for an array of ints */
		for( i = 0; i < num; i++ ){
			a[i] = createRotMatrix1D( dim );
		}

		return (a);
	}

	@Override
	public double[] multiply( double[] vector, double[] matrix, int dim ) {

		int i, j;
		// double*result = (double*)malloc(sizeof(double) * dim);
		double[] result = new double[dim];
		for( i = dim - 1; i >= 0; i-- ){
			result[i] = 0;

			for( j = dim - 1; j >= 0; j-- ){
				result[i] += vector[j] * matrix[dim * j + i];
			}
		}
		return (result);
	}

	@Override
	public double[] multiply( double[] vector, double[][] matrix, int dim ) {
		int i, j;
		// double*result = (double*)malloc(sizeof(double) * dim);
		double[] result = new double[dim];
		for( i = dim - 1; i >= 0; i-- ){
			result[i] = 0;

			for( j = dim - 1; j >= 0; j-- ){
				result[i] += vector[j] * matrix[i][j];
			}
		}
		return (result);
	}

	@Override
	public double elliptic( double[] x, int dim ) {
		double result = 0.0;
		int i;
		transform_osz( x, dim );
		for( i = 0; i < dim; i++ ){
			result += pow( 1.0e6, i / ((double) (dim - 1)) ) * x[i] * x[i];
		}
		return (result);
	}

	@Override
	public double rastrigin( double[] x, int dim ) {
		double sum = 0;
		int i;
		// T_{osz}
		transform_osz( x, dim );
		// T_{asy}^{0.2}
		transform_asy( x, 0.2, dim );
		// lambda
		Lambda( x, 10, dim );
		for( i = dim - 1; i >= 0; i-- ){
			sum += x[i] * x[i] - 10.0 * cos( 2 * PI * x[i] ) + 10.0;
		}
		return (sum);
	}

	@Override
	public double rastrigin( double[] x, int dim, int k ) {
		double result = 0.0;
		int i;
		for( i = dim / k - 1; i >= 0; i-- ){
			result += x[Pvector[dim / k + i]] * x[Pvector[dim / k + i]] - 10.0
					* cos( 2 * PI * x[Pvector[dim / k + i]] ) + 10.0;
		}
		return (result);
	}

	@Override
	public double ackley( double[] x, int dim ) {
		double sum1 = 0.0;
		double sum2 = 0.0;
		double sum;
		int i;

		// T_{osz}
		transform_osz( x, dim );

		// T_{asy}^{0.2}
		transform_asy( x, 0.2, dim );

		// lambda
		Lambda( x, 10, dim );

		for( i = dim - 1; i >= 0; i-- ){
			sum1 += (x[i] * x[i]);
			sum2 += cos( 2.0 * PI * x[i] );
		}

		sum = -20.0 * exp( -0.2 * sqrt( sum1 / dim ) ) - exp( sum2 / dim )
				+ 20.0 + E;
		return (sum);
	}

	@Override
	public double ackley( double[] x, int dim, int k ) {
		double sum1 = 0.0;
		double sum2 = 0.0;
		double result = 0.0;
		int i;

		for( i = dim / k - 1; i >= 0; i-- ){
			sum1 += x[Pvector[dim / k + i]] * x[Pvector[dim / k + i]];
			sum2 += cos( 2.0 * PI * x[Pvector[dim / k + i]] );
		}

		result = -20.0 * exp( -0.2 * sqrt( sum1 / (dim / k) ) )
				- exp( sum2 / (dim / k) ) + 20.0 + E;

		return (result);
	}

	@Override
	public double rot_rastrigin( double[] x, int dim ) {
		double result = 0.0;
		double[] z = multiply( x, RotMatrix, dim );
		result = rastrigin( z, dim );
		return (result);
	}

	@Override
	public double rot_rastrigin( double[] x, int dim, int k ) {
		double result = 0.0;
		int i, j;
		for( i = dim - 1; i >= 0; i-- ){
			anotherz1[i] = 0;
			for( j = dim - 1; j >= 0; j-- ){
				anotherz1[i] += x[Pvector[(k - 1) * dim + j]]
						* RotMatrix[dim * j + i];
			}
		}
		for( i = dim - 1; i >= 0; i-- ){
			result += anotherz1[i] * anotherz1[i] - 10.0
					* cos( 2 * PI * anotherz1[i] ) + 10.0;
		}
		return (result);
	}

	@Override
	public double rot_ackley( double[] x, int dim ) {
		double result = 0.0;
		double[] z = multiply( x, RotMatrix, dim );
		result = ackley( z, dim );
		return (result);
	}

	@Override
	public double rot_ackley( double[] x, int dim, int k ) {
		double result = 0.0;
		double sum1 = 0.0;
		double sum2 = 0.0;
		int i, j;
		for( i = dim - 1; i >= 0; i-- ){
			anotherz1[i] = 0;
			for( j = dim - 1; j >= 0; j-- ){
				anotherz1[i] += x[Pvector[(k - 1) * dim + j]]
						* RotMatrix[dim * j + i];
			}
		}
		for( i = dim - 1; i >= 0; i-- ){
			sum1 += anotherz1[i] * anotherz1[i];
			sum2 += cos( 2.0 * PI * anotherz1[i] );
		}
		result = -20.0 * exp( -0.2 * sqrt( sum1 / dim ) ) - exp( sum2 / dim )
				+ 20.0 + E;
		return (result);
	}

	@Override
	public double schwefel( double[] x, int dim ) {
		int j;
		double s1 = 0;
		double s2 = 0;

		// T_{osz}
		transform_osz( x, dim );

		// T_{asy}^{0.2}
		transform_asy( x, 0.2, dim );

		for( j = 0; j < dim; j++ ){
			s1 += x[j];
			s2 += (s1 * s1);
		}

		return (s2);
	}

	@Override
	public double schwefel( double[] x, int dim, int k ) {
		double sum1 = 0.0;
		double sum2 = 0.0;
		int i;
		for( i = 0; i < dim; i++ ){
			sum1 += x[Pvector[(k - 1) * dim + i]];
			sum2 += sum1 * sum1;
		}
		return (sum2);
	}

	@Override
	public double sphere( double[] x, int dim ) {
		double sum = 0;
		int i;
		for( i = dim - 1; i >= 0; i-- ){
			sum += pow( x[i], 2 );
		}
		return (sum);
	}

	@Override
	public double sphere( double[] x, int dim, int k ) {
		double result = 0.0;
		int i;
		for( i = dim / k - 1; i >= 0; i-- ){
			result += x[Pvector[dim / k + i]] * x[Pvector[dim / k + i]];
		}
		return (result);
	}

	@Override
	public double rosenbrock( double[] x, int dim ) {
		int j;
		double oz, t;
		double s = 0.0;
		j = dim - 1;

		for( --j; j >= 0; j-- ){
			oz = x[j + 1];
			t = ((x[j] * x[j]) - oz);
			s += (100.0 * t * t);
			t = (x[j] - 1.0);
			s += (t * t);
		}
		return (s);
	}

	@Override
	public double rosenbrock( double[] x, int dim, int k ) {
		int j;
		double oz, t;
		double result = 0.0;
		j = dim - 1;
		for( --j; j >= 0; j-- ){
			oz = x[Pvector[(k - 1) * dim + j + 1]];
			t = ((x[Pvector[(k - 1) * dim + j]] * x[Pvector[(k - 1) * dim + j]]) - oz);
			result += (100.0 * t * t);
			t = (x[Pvector[(k - 1) * dim + j]] - 1.0);
			result += (t * t);
		}
		return (result);
	}

	@Override
	public long convertMatrixToArrayIndex( long i, long j ) {
		return (i * (2 * dimension - i - 3) / 2 + j - 1);
	}

	@Override
	public void createIndexMapping() {
		long N = dimension, indexCounter = 0;
		indexMap = new IndexMap[(int) arrSize];
		for( long i = 0; i < N; i++ ){
			for( long j = i + 1; j < N; j++ ){
				indexMap[(int) indexCounter].arrIndex1 = i;
				indexMap[(int) indexCounter].arrIndex2 = j;
				indexCounter++;
			}
		}
	}

	@Override
	public int getMinX() {
		return minX;
	}

	@Override
	public int getMaxX() {
		return maxX;
	}

	@Override
	public void setMinX( int x ) {
		minX = x;
	}

	@Override
	public void setMaxX( int x ) {
		maxX = x;
	}

	@Override
	public void setSeed( long x ) {
		functionInitRandomSeed = x;

	}

	@Override
	public void setDimension( int dim ) {
		dimension = dim;
	}

	@Override
	public void setNonSeparableGroupSize( int a ) {
		nonSeparableGroupSize = a;
	}

	@Override
	public boolean[] getInterArray() {
		return interArray;
	}

	@Override
	public double[] readOvector() {
		double[] d = new double[dimension];
		try{
			BufferedReader in = new BufferedReader( new FileReader( new File(
					"cdatafiles/F" + ID + "-xopt.txt" ) ) );
			String a;
			int cont = 0;
			while ( (a = in.readLine()) != null ){
				String line[] = a.split( "," );
				for( int i = 0; i < line.length; i++ ){
					d[cont++] = Double.parseDouble( a );
				}
			}

		} catch ( FileNotFoundException e ){
			e.printStackTrace();
		} catch ( IOException e ){
			e.printStackTrace();
		}
		return d;
	}

	/*
	 * */
	@Override
	public double[][] readOvectorVec() {

		double[][] d = new double[s_size][];
		int c = 0; // index over 1 to dim
		int i = -1; // index over 1 to s_size
		int up = 0;

		try{
			BufferedReader in = new BufferedReader( new FileReader( new File(
					"cdatafiles/F" + ID + "-xopt.txt" ) ) );
			String a;
			int cont = 0;
			while ( (a = in.readLine()) != null ){
				if ( c == up ){
					i++;
					d[i] = new double[s[i]];
					up += s[i];
				}
				String line[] = a.split( "," );
				for( int j = 0; j < line.length; j++ ){
					d[i][c - (up - s[i])] = Double.parseDouble( a );
					c++;
				}

			}
		} catch ( FileNotFoundException e ){
			e.printStackTrace();
		} catch ( IOException e ){
			e.printStackTrace();
		}
		return d;
	}

	@Override
	public int[] readPermVector() {
		int[] d;

		d = new int[dimension];
		int c = 0;
		try{
			BufferedReader in = new BufferedReader( new FileReader( new File(
					"cdatafiles/F" + ID + "-p.txt" ) ) );
			String a;
			while ( (a = in.readLine()) != null ){
				String s[] = a.split( "," );
				for( int i = 0; i < s.length; i++ ){
					d[c++] = Integer.parseInt( s[i] ) - 1;
				}

			}

		} catch ( Exception e ){
			System.out.println( "error en -p " + e.getMessage() );
		}
		return (d);
	}

	@Override
	public double[][] readR( int sub_dim ) {
		double[][] m;

		m = new double[sub_dim][];
		for( int i = 0; i < sub_dim; i++ ){
			m[i] = new double[sub_dim];
		}
		try{
			BufferedReader in = new BufferedReader( new FileReader( new File(
					"cdatafiles/F" + ID + "-R" + sub_dim + ".txt" ) ) );
			String a;
			int i = 0;
			while ( (a = in.readLine()) != null ){
				String line[] = a.split( "," );
				for( int j = 0; j < line.length; j++ ){
					m[i][j] = Double.parseDouble( line[j] );
				}
				i++;
			}
		} catch ( Exception e ){
			System.out.println( "error en : -R" + e.getMessage() );
		}

		return m;
	}

	@Override
	public int[] readS( int num ) {
		s = new int[num];

		try{
			BufferedReader in = new BufferedReader( new FileReader( new File(
					"cdatafiles/F" + ID + "-s.txt" ) ) );
			String a;
			int c = 0;
			while ( (a = in.readLine()) != null ){
				s[c++] = Integer.parseInt( a );
			}
		} catch ( Exception e ){
			System.out.println( "error en -s : " + e.getMessage() );
		}
		return s;
	}

	@Override
	public double[] readW( int num ) {
		w = new double[num];
		try{
			BufferedReader in = new BufferedReader( new FileReader( new File(
					"cdatafiles/F" + ID + "-w.txt" ) ) );
			String a;
			int c = 0;
			while ( (a = in.readLine()) != null ){
				w[c++] = Double.parseDouble( a );
			}

		} catch ( Exception e ){
			System.out.println( "error en -w" + e.getMessage() );
		}
		return w;
	}

	@Override
	public void transform_osz( double[] z, int dim ) {
		// apply osz transformation to z
		for( int i = 0; i < dim; ++i ){
			z[i] = sign( z[i] )
					* exp( hat( z[i] )
							+ 0.049
							* (sin( c1( z[i] ) * hat( z[i] ) ) + sin( c2( z[i] )
									* hat( z[i] ) )) );
		}

	}

	@Override
	public void transform_asy( double[] z, double beta, int dim ) {
		for( int i = 0; i < dim; ++i ){
			if ( z[i] > 0 ){
				z[i] = pow( z[i], 1 + beta * i / ((double) (dim - 1))
						* sqrt( z[i] ) );
			}
		}

	}

	@Override
	public void Lambda( double[] z, double alpha, int dim ) {
		for( int i = 0; i < dim; ++i ){
			z[i] = z[i] * pow( alpha, 0.5 * i / ((double) (dim - 1)) );
		}

	}

	@Override
	public int sign( double x ) {
		if ( x > 0 )
			return 1;
		if ( x < 0 )
			return -1;
		return 0;
	}

	@Override
	public double hat( double x ) {
		if ( x == 0 ){
			return 0;
		} else{
			return log( abs( x ) );
		}
	}

	@Override
	public double c1( double x ) {
		if ( x > 0 ){
			return 10;
		} else{
			return 5.5;
		}
	}

	@Override
	public double c2( double x ) {
		if ( x > 0 ){
			return 7.9;
		} else{
			return 3.1;
		}
	}

	@Override
	public double F1( double x[] ) {
		if ( ID != 1 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 1;
		dimension = 1000;
		anotherz = new double[dimension];
		double result;
		int i;
		if ( Ovector == null ){
			Ovector = readOvector();
		}
		for( i = dimension - 1; i >= 0; i-- ){
			anotherz[i] = x[i] - Ovector[i];
		}
		result = elliptic( anotherz, dimension );
		return (result);
	}

	@Override
	public double F2( double[] x ) {
		if ( ID != 2 )
			Ovector = null;
		minX = -5;
		maxX = 5;
		ID = 2;
		dimension = 1000;
		anotherz = new double[dimension];
		int i;
		double result;
		if ( Ovector == null ){
			Ovector = readOvector();
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}

		result = rastrigin( anotherz, dimension );
		return (result);
	}

	@Override
	public double F3( double[] x ) {
		if ( ID != 3 )
			Ovector = null;
		minX = -32;
		maxX = 32;
		ID = 3;
		dimension = 1000;
		anotherz = new double[dimension];
		int i;
		double result;

		if ( Ovector == null ){

			Ovector = readOvector();
		}

		for( i = dimension - 1; i >= 0; i-- ){
			anotherz[i] = x[i] - Ovector[i];
		}

		result = ackley( anotherz, dimension );
		return (result);
	}

	@Override
	public double F4( double[] x ) {
		if ( ID != 4 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 4;
		s_size = 7;
		dimension = 1000;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}

		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * elliptic( anotherz1, s[i] );
			anotherz1 = null;
		}

		double[] z = new double[dimension - c.c];
		for( i = c.c; i < dimension; i++ ){
			z[i - c.c] = anotherz[Pvector[i]];
		}
		result += elliptic( z, dimension - c.c );

		return result;
	}

	@Override
	public double F5( double[] x ) {
		if ( ID != 5 )
			Ovector = null;
		minX = -5;
		maxX = 5;
		ID = 5;
		s_size = 7;
		dimension = 1000;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}

		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * rastrigin( anotherz1, s[i] );
		}

		double[] z = new double[dimension - c.c];
		for( i = c.c; i < dimension; i++ ){
			z[i - c.c] = anotherz[Pvector[i]];
		}
		result += rastrigin( z, dimension - c.c );
		return (result);
	}

	@Override
	public double F6( double[] x ) {
		if ( ID != 6 )
			Ovector = null;
		minX = -32;
		maxX = 32;
		ID = 6;
		s_size = 7;
		dimension = 1000;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}
		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * ackley( anotherz1, s[i] );
		}
		double[] z = new double[dimension - c.c];
		for( i = c.c; i < dimension; i++ ){
			z[i - c.c] = anotherz[Pvector[i]];
		}
		result += ackley( z, dimension - c.c );
		return (result);
	}

	@Override
	public double F7( double[] x ) {
		if ( ID != 7 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 7;
		s_size = 7;
		dimension = 1000;
		anotherz = new double[dimension];
		int i;
		double result = 0.0;
		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}
		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * schwefel( anotherz1, s[i] );
		}
		double[] z = new double[dimension - c.c];
		for( i = c.c; i < dimension; i++ ){
			z[i - c.c] = anotherz[Pvector[i]];
		}
		result += sphere( z, dimension - c.c );
		return (result);
	}

	@Override
	public double F8( double[] x ) {
		if ( ID != 8 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 8;
		s_size = 20;
		dimension = 1000;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}

		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * elliptic( anotherz1, s[i] );
		}
		return (result);
	}

	@Override
	public double F9( double[] x ) {
		if ( ID != 9 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 9;
		s_size = 20;
		dimension = 1000;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}
		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}
		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * rastrigin( anotherz1, s[i] );
		}
		return (result);
	}

	@Override
	public double F10( double[] x ) {
		if ( ID != 10 )
			Ovector = null;
		minX = -5;
		maxX = 5;
		ID = 10;
		s_size = 20;
		dimension = 1000;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}
		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}

		// s_size non-separable part with rotation
		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * ackley( anotherz1, s[i] );
		}
		return (result);
	}

	@Override
	public double F11( double[] x ) {
		if ( ID != 11 )
			Ovector = null;
		minX = -32;
		maxX = 32;
		ID = 11;
		s_size = 20;
		dimension = 1000;
		anotherz = new double[dimension];
		int i;
		double result = 0.0;
		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}
		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVector( i, c );
			result += w[i] * schwefel( anotherz1, s[i] );
		}

		return (result);
	}

	@Override
	public double F12( double[] x ) {
		if ( ID != 12 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 12;
		dimension = 1000;
		anotherz = new double[dimension];
		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
		}
		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}
		result = rosenbrock( anotherz, dimension );
		return (result);
	}

	@Override
	public double F13( double[] x ) {
		m_havenextGaussian = false;
		if ( ID != 13 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 13;
		s_size = 20;
		dimension = 905; // because of overlapping
		overlap = 5;
		anotherz = new double[dimension];

		int i;
		double result = 0.0;

		if ( Ovector == null ){
			Ovector = readOvector();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			s = readS( s_size );
			w = readW( s_size );
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}

		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVectorConform( i, c );
			result += w[i] * schwefel( anotherz1, s[i] );
		}

		return (result);

	}

	@Override
	public double F14( double[] x ) {
		if ( ID != 14 )
			OvectorVec = null;
		minX = -100;
		maxX = 100;
		ID = 14;
		s_size = 20;
		dimension = 905; // because of overlapping
		overlap = 5;

		int i;
		double result = 0.0;
		if ( OvectorVec == null ){
			s = readS( s_size );
			OvectorVec = readOvectorVec();
			Pvector = readPermVector();
			r25 = readR( 25 );
			r50 = readR( 50 );
			r100 = readR( 100 );
			w = readW( s_size );
		}

		Node c = new Node( 0 );
		for( i = 0; i < s_size; i++ ){
			anotherz1 = rotateVectorConflict( i, c, x );
			result += w[i] * schwefel( anotherz1, s[i] );
		}

		return (result);
	}

	@Override
	public double F15( double[] x ) {
		if ( ID != 15 )
			Ovector = null;
		minX = -100;
		maxX = 100;
		ID = 15;
		dimension = 1000;
		anotherz = new double[dimension];
		
		int i;
		double result = 0.0;
		if ( Ovector == null ){
			Ovector = readOvector();
		}

		for( i = 0; i < dimension; i++ ){
			anotherz[i] = x[i] - Ovector[i];
		}
		result = schwefel( anotherz, dimension );
		return (result);
	}

	@Override
	public double[] rotateVector( int i, Node c ) {

		double[] z = new double[s[i]];
		for( int j = c.c; j < c.c + s[i]; ++j ){
			z[j - c.c] = anotherz[Pvector[j]];
		}
		if ( s[i] == 25 ){
			anotherz1 = multiply( z, r25, s[i] );
		} else if ( s[i] == 50 ){
			anotherz1 = multiply( z, r50, s[i] );
		} else if ( s[i] == 100 ){
			anotherz1 = multiply( z, r100, s[i] );
		}
		c.c = c.c + s[i];
		return anotherz1;
	}

	@Override
	public double[] rotateVectorConform( int i, Node c ) {
		double[] z = new double[s[i]];

		for( int j = c.c - i * overlap; j < c.c + s[i] - i * overlap; ++j ){
			z[j - (c.c - i * overlap)] = anotherz[Pvector[j]];
		}
		if ( s[i] == 25 ){
			anotherz1 = multiply( z, r25, s[i] );
		} else if ( s[i] == 50 ){
			anotherz1 = multiply( z, r50, s[i] );
		} else if ( s[i] == 100 ){
			anotherz1 = multiply( z, r100, s[i] );
		}

		c.c = c.c + s[i];
		return anotherz1;
	}

	@Override
	public double[] rotateVectorConflict( int i, Node c, double[] x ) {
		double z[] = new double[s[i]];
		for( int j = c.c - i * overlap; j < c.c + s[i] - i * overlap; ++j ){
			z[j - (c.c - i * overlap)] = x[Pvector[j]]
					- OvectorVec[i][j - (c.c - i * overlap)];
		}
		if ( s[i] == 25 ){
			anotherz1 = multiply( z, r25, s[i] );
		} else if ( s[i] == 50 ){
			anotherz1 = multiply( z, r50, s[i] );
		} else if ( s[i] == 100 ){
			anotherz1 = multiply( z, r100, s[i] );
		}
		c.c = c.c + s[i];
		return anotherz1;
	}

}
