/*
 * This command is for extending the gripper out of the robot
 */
package org.usfirst.frc.team3316.robot.commands.gripper;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendGripper extends Command 
{
    public ExtendGripper() 
    {
        requires(Robot.gripper);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.gripper.extendGripper();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}