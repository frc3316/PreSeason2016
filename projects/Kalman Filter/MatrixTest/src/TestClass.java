import java.util.Random;

import org.ejml.data.DenseMatrix64F;

public class TestClass
{
	public static void main ( String [] args )
	{
		double trueNum = 5;

		double sensor1, sensor2;

		Random rnd = new Random();

		KalmanFilter kalman = new KalmanFilter();

		DenseMatrix64F F, B, Q, H;

		F = new DenseMatrix64F(new double [] []
		{
		{ 1 } });

		B = new DenseMatrix64F(new double [] []
		{
		{ 0 } });

		Q = new DenseMatrix64F(new double [] []
		{
		{ 0.0001 } });

		H = new DenseMatrix64F(new double [] []
		{
		{ 1 },
		{ 1 } });

		kalman.configure(F, B, Q, H);

		DenseMatrix64F x, P;

		x = new DenseMatrix64F(new double [] []
		{
		{ 50 } });

		P = new DenseMatrix64F(new double [] []
		{
		{ 1 } });

		kalman.setState(x, P);

		DenseMatrix64F u, z, R;

		u = new DenseMatrix64F(new double [] []
		{
		{ 0 } });

		z = new DenseMatrix64F(2, 1);

		R = new DenseMatrix64F(new double [] []
		{
		{ 0.1, 0 },
		{ 0, 0.1 } });

		double sensor1avg = 0, sensor2avg = 0;
		
		for ( int i = 0; i < 100; i++ )
		{
			kalman.predict(u);

			/*
			System.out.println("Kalman PREDICTION phase\nState:\n"
								+ kalman.getState() + "\nCovariance:\n"
								+ kalman.getCovariance() + "\n");
			*/
			
			sensor1 = trueNum + rnd.nextGaussian();
			sensor2 = trueNum + rnd.nextGaussian();

			System.out.println("Sensor values:\nSensor 1: " + sensor1
								+ "\nSensor 2: " + sensor2 + "\n");

			z.set(0, 0, sensor1);
			z.set(1, 0, sensor2);
			
			sensor1avg += sensor1;
			sensor2avg += sensor2;

			kalman.update(z, R);

			System.out.println("Kalman UPDATE phase\nState:\n"
								+ kalman.getState() + "\nCovariance:\n"
								+ kalman.getCovariance() + "\n");
		}
		
		sensor1avg /= 100;
		sensor2avg /= 100;
		
		System.out.println("Sensor averages:\nSensor 1: " + sensor1avg
							+ "\nSensor 2: " + sensor2avg + "\n");
	}
}
