/*
 * Changes the kicker's state to Raising
 */
package org.usfirst.frc.team3316.robot.commands.kicker.StateCommands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.kicker.KickerState;
import org.usfirst.frc.team3316.robot.commands.kicker.ManageKicker;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseTrigger extends Command 
{
    public RaiseTrigger() {}

    protected void initialize() 
    {
        ManageKicker.changeState(KickerState.RAISING);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return Robot.kicker.currentState.equals(KickerState.RESTING);
    }

    protected void end() {}

    protected void interrupted() {}
}