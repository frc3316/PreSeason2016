/*
 * Turns the robot a certain number of angles
 * Positive to turn clockwise
 * Negative to turn counter-clockwise
 */
package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.utils.Point;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToHeading extends Command 
{
    double angleTo;
    public TurnToHeading(double angle) 
    {
        requires(Robot.chassis);
        angleTo = angle;
    }

    protected void initialize() 
    {
        Robot.chassis.resetChassisSensors();
    }

    protected void execute() {
        double angle = RobotMap.gyro.getAngle();
        double value = Utils.scale(angle, new Point(0, 0.5), new Point(angleTo, 0.25));
        
        SmartDashboard.putNumber("Angle by gyro", angle);
        
        Robot.chassis.setMotors(value*1.2, -value*0.9);
    }

    protected boolean isFinished() 
    {
        double angle = RobotMap.gyro.getAngle();
        return angle >= angleTo;
    }
    
    protected void end() {}

    protected void interrupted() {}
}