/*
 * Command that is called when the kicker is in Resting position
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class RestCommand extends Command 
{

    public RestCommand() 
    {
        requires(Robot.kickerMovement);
    }

    protected void initialize() 
    {
        RobotMap.kickerEncoder.reset();
    }

    protected void execute() 
    {
        Robot.kicker.move(0);
    }

    protected boolean isFinished() 
    {
        return !RobotMap.getRestSwitch();
    }

    protected void end() 
    {
        ManageKicker.changeState(KickerState.SHAKEN);
    }

    protected void interrupted() {}
}