/*
 * Rolls the rollers for a period of time
 */
package org.usfirst.frc.team3316.robot.commands.roller;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RollIn extends Command 
{
    public RollIn(double timeout) 
    {
        requires(Robot.roller);
        setTimeout(timeout);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.roller.rollInGripperMotors();
    }

    protected boolean isFinished() 
    {
        return isTimedOut();
    }

    protected void end() 
    {
        Robot.roller.stopGripperMotors();
    }

    protected void interrupted() {end();}
}