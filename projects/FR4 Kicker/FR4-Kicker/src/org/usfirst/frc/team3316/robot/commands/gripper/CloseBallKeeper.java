/*
 * Closes the ball keepers infront of the robot
 */
package org.usfirst.frc.team3316.robot.commands.gripper;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CloseBallKeeper extends Command 
{
    public CloseBallKeeper() 
    {
        requires(Robot.gripper);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.gripper.closeBallKeeper();
    }

    protected boolean isFinished() {return true;}
    
    protected void end() {}

    protected void interrupted() {}
}