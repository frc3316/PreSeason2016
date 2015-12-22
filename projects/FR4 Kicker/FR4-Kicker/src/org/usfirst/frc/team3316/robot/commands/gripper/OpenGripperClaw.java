/*
 * This command is for opening the gripper claw
 */
package org.usfirst.frc.team3316.robot.commands.gripper;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenGripperClaw extends Command 
{
    public OpenGripperClaw() 
    {
        requires(Robot.gripper);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.gripper.openClaw();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}