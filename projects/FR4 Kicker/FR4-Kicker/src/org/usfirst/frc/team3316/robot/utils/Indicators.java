/*
 * Puts the indicators in the SmartDashboard
 */
package org.usfirst.frc.team3316.robot.utils;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Indicators extends Command 
{
 
    public Indicators() 
    {
        requires(Robot.indicators);
        setRunWhenDisabled(true);
    }
    
    protected void initialize() 
    {

    }
    double lastRate = 0;
    protected void execute() 
    {
        SmartDashboard.putBoolean("Is Ball In Left", RobotMap.gripperMSLeft.get());
        SmartDashboard.putBoolean("Is Ball In Middle", RobotMap.gripperMSMiddle.get());
        SmartDashboard.putBoolean("Is Ball In Right", RobotMap.gripperMSRight.get());
        SmartDashboard.putBoolean("Rest Switch", RobotMap.getRestSwitch());
        
        SmartDashboard.putNumber("Kicker Angular Velocity", RobotMap.kickerEncoder.getRate());
        SmartDashboard.putNumber("Kicker Angle", RobotMap.kickerEncoder.getDistance());
        
        SmartDashboard.putNumber("Kicker Left Cim", RobotMap.kickerLeftCIM.get());
        SmartDashboard.putNumber("Kicker Left MiniCim", RobotMap.kickerLeftMiniCIM.get());
        SmartDashboard.putNumber("Kicker Right Cim", RobotMap.kickerRightCIM.get());
        SmartDashboard.putNumber("Kicker Right MiniCim", RobotMap.kickerRightMiniCIM.get());
        
        
        SmartDashboard.putNumber("Ball IR", RobotMap.ballIRSensor.getVoltage());
        SmartDashboard.putNumber("Ball IR Constant", 0.0);
        SmartDashboard.putNumber("Test IR", Robot.kicker.getTestIRDist());
        SmartDashboard.putNumber("Ball IR Averaged", RobotMap.ballIRSensor.getAverageVoltage());
        SmartDashboard.putNumber("Chassis Distance", RobotMap.chassisEncoder.getDistance());
    
        SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
    }

    protected boolean isFinished() {return false;}

    protected void end() {}

    protected void interrupted() {}
}