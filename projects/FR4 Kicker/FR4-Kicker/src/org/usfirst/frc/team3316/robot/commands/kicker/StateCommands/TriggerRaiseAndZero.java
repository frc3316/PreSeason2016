/*
 * Alternates between activating the RaiseTrigger and the ZeroTrigger
 * This command is for the Raising/Zeroing button
 * very useful
 */
package org.usfirst.frc.team3316.robot.commands.kicker.StateCommands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.kicker.KickerState;
import org.usfirst.frc.team3316.robot.commands.sequences.RaiseSequence;

import edu.wpi.first.wpilibj.command.Command;

public class TriggerRaiseAndZero extends Command 
{
    public TriggerRaiseAndZero() {}

    protected void initialize() 
    {
        if(Robot.kicker.currentState.equals(KickerState.Off))
        {
            new RaiseSequence().start();
        }
        else if(Robot.kicker.currentState.equals(KickerState.Resting))
        {
            new ZeroTrigger().start();
        }
    }

    protected void execute() {}

    protected boolean isFinished() {return true;}
    
    protected void end() {}

    protected void interrupted() {}
}