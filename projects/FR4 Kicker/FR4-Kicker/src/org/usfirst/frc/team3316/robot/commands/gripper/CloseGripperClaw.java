/*
 * Closes the gripper claw
 */
package org.usfirst.frc.team3316.robot.commands.gripper;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CloseGripperClaw extends Command 
{
    public CloseGripperClaw() 
    {
        requires(Robot.gripper);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.gripper.closeClaw();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}