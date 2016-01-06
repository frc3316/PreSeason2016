/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import java.util.HashSet;
import java.util.TimerTask;

import org.ejml.data.DenseMatrix64F;
import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.KalmanFilter;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem {
	/*
	 * An object that is passed to NavigationThread for integration
	 */
	public static class NavigationIntegrator {
		private double x = 0, y = 0, heading = 0;

		public void add(double dX, double dY, double dTheta) {
			this.x += dX;
			this.y += dY;
			this.heading += dTheta;
			/*
			 * makes sure heading is in the range (-180) to (180)
			 */
			this.heading = fixHeading(heading);
		}

		/**
		 * Return change in X that has been integrated
		 */
		public double getX() {
			return x;
		}

		/**
		 * Return change in Y that has been integrated
		 */
		public double getY() {
			return y;
		}

		/**
		 * Return change in heading that has been integrated. Heading returned
		 * is in the range (-180) to (180)
		 */
		public double getHeading() {
			return heading;
		}
	}

	/*
	 * Runnable that calculates the robot's position in the field Calculates x,
	 * y, and theta delta and adds it to each integrator in the set Also
	 * calculates turning rate in chassis
	 */
	private class NavigationTask extends TimerTask {
		private HashSet <NavigationIntegrator> integratorSet;
		private double previousTime = 0;
		private double previousS = 0, previousF = 0, previousHeading = 0;

		public NavigationTask() {
			integratorSet = new HashSet<NavigationIntegrator>();
		}

		public void run() {
			// Makes sure the first time delta will not be since 1970
			if (previousTime == 0) {
				previousTime = System.currentTimeMillis();
			}
			/*
			 * Current variables
			 */
			double currentTime = System.currentTimeMillis();
			double currentHeading = getHeading();
			double currentS = getDistanceCenter(); // Sideways distance
			double currentF = (getDistanceLeft() + getDistanceRight()) / 2; // Forward
																			// distance

			/*
			 * Calculates deltas between current and previous
			 */
			double dT = (currentTime - previousTime) / 1000; // conversion to
																// seconds
			double dTheta = currentHeading - previousHeading;
			double dS = currentS - previousS;
			double dF = currentF - previousF;
			// Since heading is in the range (-180) to (180), when
			// completing a full turn dTheta will be an absurdly big value
			// Checks if dTheta is an absurdly big value and fixes it
			if (dTheta > 340) // 340 is a big number
			{
				dTheta -= 360;
			}
			if (dTheta < -340) // Math.abs(-340) is another big number
			{
				dTheta += 360;
			}

			/*
			 * Calculates angular velocity
			 */
			angularVelocity = (dTheta) / dT;

			/*
			 * Adds all of the deltas to each integrator, relatively to its
			 * starting position
			 */
			for (NavigationIntegrator integrator : integratorSet) {
				double dX, dY; // speeds relative to the orientation that the
								// integrator started at
				// headingRad is the average of the previous integrator angle
				// and the angle it will have (in radians)
				double headingRad = (Math.toRadians(integrator.getHeading() + dTheta / 2));
				dX = (dF * Math.sin(headingRad)) + (dS * Math.sin(headingRad + (0.5 * Math.PI)));
				dY = (dF * Math.cos(headingRad)) + (dS * Math.cos(headingRad + (0.5 * Math.PI)));

				integrator.add(dX, dY, dTheta);
			}

			/*
			 * Setting variables for next run
			 */
			previousTime = currentTime;
			previousHeading = currentHeading;
			previousS = currentS;
			previousF = currentF;
		}

		public boolean addIntegrator(NavigationIntegrator integrator) {
			return integratorSet.add(integrator);
		}

		public boolean removeIntegrator(NavigationIntegrator integrator) {
			return integratorSet.remove(integrator);
		}
	}

	private class KalmanTask extends TimerTask 
	{
		public KalmanFilter kalmanY;
		public KalmanFilter kalmanX;

		DenseMatrix64F fY, bY, qY, hY;
		DenseMatrix64F uY, zY, rY;
		
		DenseMatrix64F fX, bX, qX, hX;
		DenseMatrix64F uX, zX, rX;

		public double previousTime = 0;

		boolean predicting = true;

		public KalmanTask() 
		{
			/*
			 * Kalman filter on the Y axis of the robot
			 */
			kalmanY = new KalmanFilter();

			fY = new DenseMatrix64F(new double[][] { { 1, 0 }, { 0, 1 } });

			bY = new DenseMatrix64F(new double[][] { { 0 }, { 0 } });

			qY = new DenseMatrix64F(new double[][] { { 0.3, 0 }, { 0, 0.3 } });

			hY = new DenseMatrix64F(new double[][] { { 1, 0 }, { 0, 1 } });

			kalmanY.configure(fY, bY, qY, hY);

			DenseMatrix64F xY, pY;

			xY = new DenseMatrix64F(new double[][] { { 0 }, { 0 } });

			pY = new DenseMatrix64F(new double[][] { { 10, 0 }, { 0, 10 } });

			kalmanY.setState(xY, pY);

			uY = new DenseMatrix64F(new double[][] { { 0 } });

			zY = new DenseMatrix64F(2, 1);

			rY = new DenseMatrix64F(new double[][] { { 0.25, 0 }, { 0, 0.25 } });
			
			/*
			 * Kalman filter on the X axis of the robot
			 */
			kalmanX = new KalmanFilter();

			fX = new DenseMatrix64F(new double[][] { { 1, 0 }, { 0, 1 } });

			bX = new DenseMatrix64F(new double[][] { { 0 }, { 0 } });

			qX = new DenseMatrix64F(new double[][] { { 0.3, 0 }, { 0, 0.3 } });

			hX = new DenseMatrix64F(new double[][] { { 1, 0 }, { 0, 1 } });

			kalmanX.configure(fX, bX, qX, hX);

			DenseMatrix64F xX, pX;

			xX = new DenseMatrix64F(new double[][] { { 0 }, { 0 } });

			pX = new DenseMatrix64F(new double[][] { { 10, 0 }, { 0, 10 } });

			kalmanX.setState(xX, pX);

			uX = new DenseMatrix64F(new double[][] { { 0 } });

			zX = new DenseMatrix64F(2, 1);

			rX = new DenseMatrix64F(new double[][] { { 0.25, 0 }, { 0, 0.25 } });
		}

		public void run() {
			if (previousTime == 0) 
			{
				previousTime = System.currentTimeMillis();
			}

			if (predicting) 
			{
				kalmanY.predict(uY);
				kalmanX.predict(uX);
			}

			else 
			{
				double currentTime = System.currentTimeMillis();
				double currentEncoderSpeedY = (getSpeedLeft() + getSpeedRight()) / 2;
				double currentEncoderSpeedX = getSpeedCenter();

				double dt = (currentTime - previousTime) / 1000;
				
				fY.set(0, 1, dt);
				
				zY.set(0, 0, currentEncoderSpeedY);
				zY.set(1, 0, getAccelY());

				kalmanY.update(zY, rY);
				
				fX.set(0, 1, dt);
				
				zX.set(0, 0, currentEncoderSpeedX);
				zX.set(1, 0, getAccelX());
				
				kalmanX.update(zX, rX);

				previousTime = currentTime;
			}

			predicting = !predicting;
		}
	}
	
	public class KalmanNavigationTask extends TimerTask 
	{
		public NavigationIntegrator integrator;
		
		private double previousTime = 0;
		private double previousHeading = 0;

		public void run() {
			// Makes sure the first time delta will not be since 1970
			if (previousTime == 0) {
				previousTime = System.currentTimeMillis();
				
				integrator  = new NavigationIntegrator();
				navigationTask.addIntegrator(testIntegrator);
			}
			/*
			 * Current variables
			 */
			double currentTime = System.currentTimeMillis();
			double currentHeading = getHeading();
			double currentSpeedS = kalmanTask.kalmanX.getState().get(0); // Sideways distance
			double currentSpeedF = kalmanTask.kalmanY.getState().get(0); // Forward
																		  // distance

			/*
			 * Calculates deltas between current and previous
			 */
			double dT = (currentTime - previousTime) / 1000; // conversion to
																// seconds
			double dTheta = currentHeading - previousHeading;
			double dS = currentSpeedS * dT;
			double dF = currentSpeedF * dT;
			// Since heading is in the range (-180) to (180), when
			// completing a full turn dTheta will be an absurdly big value
			// Checks if dTheta is an absurdly big value and fixes it
			if (dTheta > 340) // 340 is a big number
			{
				dTheta -= 360;
			}
			if (dTheta < -340) // Math.abs(-340) is another big number
			{
				dTheta += 360;
			}

			double dX, dY; // speeds relative to the orientation that the
							// integrator started at
			// headingRad is the average of the previous integrator angle
			// and the angle it will have (in radians)
			double headingRad = (Math.toRadians(integrator.getHeading() + dTheta / 2));
			dX = (dF * Math.sin(headingRad)) + (dS * Math.sin(headingRad + (0.5 * Math.PI)));
			dY = (dF * Math.cos(headingRad)) + (dS * Math.cos(headingRad + (0.5 * Math.PI)));

			integrator.add(dX, dY, dTheta);

			/*
			 * Setting variables for next run
			 */
			previousTime = currentTime;
			previousHeading = currentHeading;
		}
	}

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	static final double G = 9.80665;

	private NavigationTask navigationTask;
	private KalmanTask kalmanTask;

	private SpeedController left1, left2;
	private SpeedController right1, right2;
	private SpeedController center;

	private IMUAdvanced navx;

	private Encoder encoderLeft, encoderRight, encoderCenter;

	private double leftScale, rightScale, centerScale;

	private double headingOffset = 0;

	private double angularVelocity = 0; // this is constantly calculated by
										// NavigationThread

	public boolean recording = true;
	
	public KalmanNavigationTask kalmanNavigationTask;
	public NavigationIntegrator testIntegrator;

	Drive defaultDrive;

	double leftEncoderScale = 1, rightEncoderScale = 1, centerEncoderScale = 1;

	public boolean navxEnabled = true;

	public Chassis() {
		left1 = Robot.actuators.chassisMotorControllerLeft1;
		left2 = Robot.actuators.chassisMotorControllerLeft2;

		right1 = Robot.actuators.chassisMotorControllerRight1;
		right2 = Robot.actuators.chassisMotorControllerRight2;

		center = Robot.actuators.chassisMotorControllerCenter;

		navx = Robot.sensors.navx;

		encoderLeft = Robot.sensors.chassisEncoderLeft;
		encoderRight = Robot.sensors.chassisEncoderRight;
		encoderCenter = Robot.sensors.chassisEncoderCenter;
		
		testIntegrator = new NavigationIntegrator();
	}

	public void timerInit() {
		navigationTask = new NavigationTask();
		Robot.timer.schedule(navigationTask, 0, 10);

		kalmanTask = new KalmanTask();
		Robot.timer.schedule(kalmanTask, 0, 5);
		
		kalmanNavigationTask = new KalmanNavigationTask();
		Robot.timer.schedule(kalmanNavigationTask, 0, 10);
	}

	public void initDefaultCommand() {
		defaultDrive = new 
				FieldOrientedDrive();
		setDefaultCommand(defaultDrive);
	}

	public boolean set(double left, double right, double center) {
		updateScales();

		this.left1.set(left * leftScale);
		this.left2.set(left * leftScale);

		this.right1.set(right * rightScale);
		this.right2.set(right * rightScale);

		this.center.set(center * centerScale);

		return true;
	}

	/*
	 * Heading
	 */
	public double getHeading() {
		double headingToReturn = navx.getYaw() + headingOffset;
		headingToReturn = fixHeading(headingToReturn);

		return headingToReturn;
	}

	/**
	 * Sets the current robot angle to the specified angle
	 * 
	 * @param headingToSet
	 *            the angle specified
	 */
	public void setHeading(double headingToSet) {
		double currentHeading = getHeading();
		double currentOffset = headingOffset;

		headingOffset = (headingToSet + currentOffset - currentHeading);
	}

	/*
	 * Angular velocity
	 */
	public double getAngularVelocity() {
		return angularVelocity;
	}

	/*
	 * Distance
	 */
	public double getDistanceLeft() {
		updateEncoderScales();
		return encoderLeft.getDistance() * leftEncoderScale;
	}

	public double getDistanceRight() {
		updateEncoderScales();
		return encoderRight.getDistance() * rightEncoderScale;
	}

	public double getDistanceCenter() {
		updateEncoderScales();
		return encoderCenter.getDistance() * centerEncoderScale;
	}

	/*
	 * Speed
	 */
	public double getSpeedLeft() {
		updateEncoderScales();
		return encoderLeft.getRate() * leftEncoderScale;
	}

	public double getSpeedRight() {
		updateEncoderScales();
		return encoderRight.getRate() * rightEncoderScale;
	}

	public double getSpeedCenter() {
		updateEncoderScales();
		return encoderCenter.getRate() * centerEncoderScale;
	}

	/*
	 * Acceleration
	 */
	public double getAccelX() {
		return navx.getWorldLinearAccelX() * G;
	}

	public double getAccelY() {
		return navx.getWorldLinearAccelY() * G;
	}

	/*
	 * Navigation integrator
	 */
	public boolean addNavigationIntegrator(NavigationIntegrator integrator) {
		return navigationTask.addIntegrator(integrator);
	}

	public boolean removeNavigationIntegrator(NavigationIntegrator integrator) {
		return navigationTask.removeIntegrator(integrator);
	}

	private void updateScales() {
		try {
			leftScale = (double) config.get("chassis_LeftScale");
			rightScale = (double) config.get("chassis_RightScale");
			centerScale = (double) config.get("chassis_CenterScale");
		} catch (ConfigException e) {
			logger.severe(e);
		}
	}

	private void updateEncoderScales() {
		try {
			leftEncoderScale = (double) config.get("chassis_LeftEncoderScale");
			rightEncoderScale = (double) config.get("chassis_RightEncoderScale");
			centerEncoderScale = (double) config.get("chassis_CenterEncoderScale");
		} catch (ConfigException e) {
			logger.severe(e);
		}
	}

	// Returns the same heading in the range (-180) to (180)
	private static double fixHeading(double heading) {
		double toReturn = heading % 360;

		if (toReturn < -180) {
			toReturn += 360;
		} else if (toReturn > 180) {
			toReturn -= 360;
		}
		return toReturn;
	}
}