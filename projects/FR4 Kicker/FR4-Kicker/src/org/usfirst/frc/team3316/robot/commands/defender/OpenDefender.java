/*
 * Opens the kicker defenders at the back
 */
package org.usfirst.frc.team3316.robot.commands.defender;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenDefender extends Command 
{
    public OpenDefender () 
    {
        requires(Robot.defender);
    }

    protected void initialize() {}

    protected void execute() 
    {
        Robot.defender.open();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}