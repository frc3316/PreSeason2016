package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class FieldOrientedDrive extends RobotOrientedDrive 
{	
	protected void set ()
	{
		if (Robot.chassis.navxEnabled)
		{
			setFieldVector(getRightX(), getRightY(), Robot.chassis.getHeading());
		}
		else
		{
			setFieldVector(getRightX(), getRightY(), 0);
		}
		
		setRotation(getLeftX());
	}
	
	protected void setFieldVector (double x, double y, double robotAngle)
	{	
		double r = Math.sqrt(x*x + y*y); //The vector's magnitude
		double vectorAngle =  Math.atan2(x, y); //Vector angle from y axis, 
												//clockwise is positive and 
												//counter-clockwise is negative
		double robotAngleRad = Math.toRadians(robotAngle); //Robot angle
		
		double angleDiff = vectorAngle - robotAngleRad; //The angle of the vector oriented to the robot
		
		/*
		 * Breaks down the field vector to the robot's axes
		 */
		double outputX = Math.sin(angleDiff)*r;
		double outputY = Math.cos(angleDiff)*r;
		
		//Normalize outputs
		double max = Math.max(Math.abs(outputX), Math.abs(outputY));
		if (max > 1)
		{
			outputX /= max;
			outputY /= max;
		}

		setRobotVector(outputX, outputY);
	}
}