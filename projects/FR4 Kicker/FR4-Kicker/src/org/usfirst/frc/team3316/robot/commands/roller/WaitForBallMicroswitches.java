/*
 * Command that finishes when the ball microswitches are pressed
 */
package org.usfirst.frc.team3316.robot.commands.roller;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForBallMicroswitches extends Command 
{
    public WaitForBallMicroswitches() {}

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() 
    {
        return Robot.roller.areSidesPressed() || Robot.roller.isBallInside();
    }

    protected void end() {
        Robot.gripper.isCatchOn = false;
    }

    protected void interrupted() {}
}