/*
 * Extends the gripper and waits for it to finish if it isn't out already
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendGripperCotrolled extends Command 
{
    public ExtendGripperCotrolled() {}

    protected void initialize() 
    {
        Robot.gripper.isCatchOn = false;
        if(Robot.gripper.isGripperOut())
        {
            setTimeout(0);
        }
        else
        {
            setTimeout(1);
        }
        
        Robot.gripper.extendGripper();
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return isTimedOut();
    }

    protected void end() {}

    protected void interrupted() {}
}