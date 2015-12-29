/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.DriveToYellowTote;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedDrive;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.chassis.commands.TankDrive;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingPreset;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingSDB;
import org.usfirst.frc.team3316.robot.commands.KalmanRecord;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.AutoToteCollect;
import org.usfirst.frc.team3316.robot.sequences.AutoTotePickup;
import org.usfirst.frc.team3316.robot.sequences.DropoffSequence;
import org.usfirst.frc.team3316.robot.vision.AutonomousCamera;
import org.usfirst.frc.team3316.robot.vision.SaveBinaryFrame;
import org.usfirst.frc.team3316.robot.vision.SaveFrame;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB
{
	/*
	 * Runnable that periodically updates values from the robot into the
	 * SmartDashboard This is the place where all of the robot data should be
	 * displayed from
	 */
	private class UpdateSDBTask extends TimerTask
	{
		public UpdateSDBTask()
		{
			//logger.info("Created UpdateSDBTask");
		}

		public void run()
		{

			/*
			 * Camera
			 */
			if (Robot.sensors.isCameraFound())
			{
				NIVision.IMAQdxGrab(Robot.sensors.getCameraSession(), frame, 1);
				CameraServer.getInstance().setImage(frame);
			}
			/*
			 * Robot Info
			 */
			put("Navx Enabled", Robot.chassis.navxEnabled);
			
			put("Current Heading", Robot.chassis.getHeading());
			
			put("Encoder Left Speed", Robot.chassis.getSpeedLeft());
			put("Encoder Right Speed", Robot.chassis.getSpeedRight());
			put("Encoder Center Speed", Robot.chassis.getSpeedCenter());
			
			put("Accelerometer Y", Robot.chassis.getAccelY());
			put("Encoder Accel", Robot.chassis.getAccelEncoders());
			
			put("Kalman State", Robot.chassis.getKalmanState());
			put("Kalman Covariance", Robot.chassis.getKalmanCovariance());

			put("Kalman Recording", Robot.chassis.recording);
		}

		private void put(String name, double d)
		{
			SmartDashboard.putNumber(name, d);
		}

		private void put(String name, int i)
		{
			SmartDashboard.putInt(name, i);
		}

		private void put(String name, boolean b)
		{
			SmartDashboard.putBoolean(name, b);
		}

		private void put(String name, String s)
		{
			SmartDashboard.putString(name, s);
		}
	}

	//DBugLogger logger = Robot.logger;
	Config config = Robot.config;

	private Image frame;

	private UpdateSDBTask updateSDBTask;

	private Hashtable<String, Class<?>> variablesInSDB;

	public SDB()
	{
		variablesInSDB = new Hashtable<String, Class<?>>();

		initSDB();

		if (Robot.sensors.isCameraFound())
		{
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		}
	}

	public void timerInit()
	{
		updateSDBTask = new UpdateSDBTask();
		Robot.timer.schedule(updateSDBTask, 0, 10);
	}

	/**
	 * Adds a certain key in the config to the SmartDashboard
	 * 
	 * @param key
	 *            the key required
	 * @return whether the value was put in the SmartDashboard
	 */
	public boolean putConfigVariableInSDB(String key)
	{
		try
		{
			Object value = config.get(key);
			
			Class <?> type = value.getClass();
			
			boolean constant = Character.isUpperCase(key.codePointAt(key.length() - 1));

			if (type == Double.class)
			{
				SmartDashboard.putNumber(key, (double) value);
			}
			else if (type == Integer.class)
			{
				SmartDashboard.putNumber(key, (int) value);
			}
			else if (type == Boolean.class)
			{
				SmartDashboard.putBoolean(key, (boolean) value);
			}

			if (!constant)
			{
				variablesInSDB.put(key, type);
				//logger.info("Added to SDB " + key + " of type " + type
				//		+ "and allows for its modification");
			}
			else
			{
				//logger.info("Added to SDB " + key + " of type " + type
				//		+ "BUT DOES NOT ALLOW for its modification");
			}

			return true;
		}
		catch (ConfigException e)
		{
			//logger.severe(e);
		}
		return false;
	}

	public Set<Entry<String, Class<?>>> getVariablesInSDB()
	{
		return variablesInSDB.entrySet();
	}

	private void initSDB()
	{
		SmartDashboard.putData(new UpdateVariablesInConfig()); // NEVER REMOVE
																// THIS COMMAND
		
		SmartDashboard.putData(new KalmanRecord());

		/*
		 * Basic Drives
		 */
		/*
		 * TO REMOVE
		 */
		putConfigVariableInSDB("chassis_RobotOrientedDrivePIDRotation_UsePIDRotation");
		
		SmartDashboard.putData(new TankDrive());

		SmartDashboard.putData(new RobotOrientedDrive());
		SmartDashboard.putData(new FieldOrientedDrive());

		/*
		 * Set Heading SDB
		 */
		SmartDashboard.putData("Zero Heading", new SetHeadingPreset(0));

		SmartDashboard.putData(new SetHeadingSDB());

		putConfigVariableInSDB("chassis_HeadingToSet");
		
		//logger.info("Finished initSDB()");
	}
}