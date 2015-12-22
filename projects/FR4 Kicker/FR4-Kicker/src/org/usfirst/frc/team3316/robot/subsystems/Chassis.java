/*
 * the subsystem that manages the chassis
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;

import edu.wpi.first.wpilibj.command.Subsystem;


public class Chassis extends Subsystem 
{   
    public double leftMotorValue = 0, rightMotorValue = 0;
        
    public double AutonDistanceToDrive = 0;
    
    public void initDefaultCommand() 
    {
        setDefaultCommand(new TankDrive());
    }
    
    public void setMotors (double left, double right)
    {        
        leftMotorValue = left;
        rightMotorValue = right;

        RobotMap.leftMotors.set(-left);
        RobotMap.rightMotors.set(right);
    }
    
    public double getDistance()
    {
        return RobotMap.chassisEncoder.getDistance();
    }
    
    public void resetGyro()
    {
        RobotMap.gyro.reset();
    }
    
    public void resetChassisSensors()
    {
        resetEncoder();
        resetGyro();
    }
    
    public double getAngle()
    {
        return RobotMap.gyro.getAngle();
    }
    
    public void resetEncoder()
    {
        RobotMap.chassisEncoder.reset();
    }
}

