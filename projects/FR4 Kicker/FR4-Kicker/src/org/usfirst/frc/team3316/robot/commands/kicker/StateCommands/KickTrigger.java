/*
 * Changes state to Kicking
 */
package org.usfirst.frc.team3316.robot.commands.kicker.StateCommands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.kicker.KickerState;
import org.usfirst.frc.team3316.robot.commands.kicker.ManageKicker;

import edu.wpi.first.wpilibj.command.Command;


public class KickTrigger extends Command 
{
    public KickTrigger() {}

    protected void initialize() {
        ManageKicker.changeState(KickerState.KICKING);
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return Robot.kicker.currentState.equals(KickerState.RAISING);
    }

    protected void end() {}

    protected void interrupted() {}
}