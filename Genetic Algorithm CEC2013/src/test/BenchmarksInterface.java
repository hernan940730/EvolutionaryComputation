package test;

public interface BenchmarksInterface {

	int next(int bits);

	int nextInt(int n);

	double nextDouble();

	double nextGaussian();

	double[] createShiftVector(int dim, double min, double max);

	int[] createPermVector(int dim);

	double[][] createRotMatrix(int dim);

	double[] createRotMatrix1D(int dim);

	double[][] createMultiRotateMatrix1D(int dim, int num);


	double[] multiply(double[] vector, double[] matrix, int dim);

	double[] multiply(double[] vector, double[][] matrix, int dim);

	double elliptic(double[] x, int dim);

	double rastrigin(double[] x, int dim);

	double rastrigin(double[] x, int dim, int k);

	double ackley(double[] x, int dim);

	double ackley(double[] x, int dim, int k);


	double rot_rastrigin(double[] x, int dim);

	double rot_rastrigin(double[] x, int dim, int k);

	double rot_ackley(double[] x, int dim);

	double rot_ackley(double[] x, int dim, int k);

	double schwefel(double[] x, int dim);

	double schwefel(double[] x, int dim, int k);

	double sphere(double[] x, int dim);

	double sphere(double[] x, int dim, int k);

	double rosenbrock(double[] x, int dim);

	double rosenbrock(double[] x, int dim, int k);

	long convertMatrixToArrayIndex(long i, long j);

	void createIndexMapping();

	/* void extractElemByPerm(); */
	double[] rotateVector(int i, Node c);
	double[] rotateVectorConform(int i, Node c);
	double[] rotateVectorConflict(int i, Node c, double[] x);

	
	
	int getMinX();

	int getMaxX();

	void setMinX(int x);

	void setMaxX(int x);

	void setSeed(long x);

	void setDimension(int dim);

	void setNonSeparableGroupSize(int a);

	boolean[] getInterArray();

	// void ArrToMat ( long I1, long I2, long &matIndex );
	// void MatToArr ( long &I1, long &I2, long matIndex );
	//
	/* for CEC2013SS */
	double[] readOvector();

	double[][] readOvectorVec();

	int[] readPermVector();

	double[][] readR(int sub_dim);

	int[] readS(int num);

	double[] readW(int num);

	void transform_osz(double[] z, int dim);

	void transform_asy(double[] z, double beta, int dim);

	void Lambda(double[] z, double alpha, int dim);

	int sign(double x);

	double hat(double x);

	double c1(double x);

	double c2(double x);

	double F1(double x[]);

	double F2(double x[]);

	double F3(double x[]);

	double F4(double x[]);

	double F5(double x[]);

	double F6(double x[]);

	double F7(double x[]);

	double F8(double x[]);

	double F9(double x[]);

	double F10(double x[]);

	double F11(double x[]);

	double F12(double x[]);

	double F13(double x[]);

	double F14(double x[]);

	double F15(double x[]);

}
