/*
 * Opens the ball keepers from the front of the chassis
 */
package org.usfirst.frc.team3316.robot.commands.gripper;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenBallKeeper extends Command 
{
    public OpenBallKeeper() 
    {
        requires(Robot.gripper);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.gripper.openBallKeeper();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}