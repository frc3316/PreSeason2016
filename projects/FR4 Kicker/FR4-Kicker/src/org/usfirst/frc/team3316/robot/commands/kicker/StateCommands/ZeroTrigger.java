/*
 * Changes the kicker's state to Zeroing
 */
package org.usfirst.frc.team3316.robot.commands.kicker.StateCommands;

import org.usfirst.frc.team3316.robot.commands.kicker.KickerState;
import org.usfirst.frc.team3316.robot.commands.kicker.ManageKicker;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroTrigger extends Command 
{
    public ZeroTrigger() {}

    protected void initialize() {}

    protected void execute() 
    {
        ManageKicker.changeState(KickerState.ZERO);
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}