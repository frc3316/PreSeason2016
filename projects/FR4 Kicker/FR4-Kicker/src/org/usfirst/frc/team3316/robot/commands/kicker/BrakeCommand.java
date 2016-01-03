/*
 * Brakes the kicker after kicking and sets the kicker's state back to Off
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeCommand extends Command 
{
    double velocity;
    double timeOut;

    public BrakeCommand() 
    {
        requires(Robot.kickerMovement);
        velocity = RobotConstants.get("KICKER_BRAKE_VELOCITY");
        timeOut = RobotConstants.get("KICKER_BRAKE_TIMOUT");
    }

    protected void initialize() 
    {
        System.out.println("Brakeee: " + RobotMap.kickerEncoder.getDistance());
        setTimeout(timeOut);
    }

    protected void execute() 
    {
        Robot.kicker.move(velocity);
    }

    protected boolean isFinished() 
    {
        return isTimedOut();
    }

    protected void end() 
    {}

    protected void interrupted() {end();}
}