/*
 * This Command raises kicker if kicker isn't resting. It ends when the kicker is resting.
 * Made For Auton.
 */
package org.usfirst.frc.team3316.robot.commands.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.kicker.KickerState;
import org.usfirst.frc.team3316.robot.commands.kicker.ManageKicker;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseKickerForAuton extends Command 
{
    int counterToStart;

    public RaiseKickerForAuton() 
    {

    }

    protected void initialize() 
    {
        counterToStart = 0;
    }

    protected void execute() 
    {
        counterToStart++;
        if (counterToStart >= 25) //.5 sec delay until the raising starts if needed
        {
            ManageKicker.changeState(KickerState.Raising);
        }
    }

    protected boolean isFinished() {
        return Robot.kicker.currentState.equals(KickerState.Resting);
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}