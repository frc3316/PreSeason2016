/*
 * this class is for retract the gripper into the robot
 */
package org.usfirst.frc.team3316.robot.commands.gripper;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RetractGripper extends Command 
{
    public RetractGripper() 
    {
        requires(Robot.gripper);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.gripper.retractGripper();
    }
    
    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}