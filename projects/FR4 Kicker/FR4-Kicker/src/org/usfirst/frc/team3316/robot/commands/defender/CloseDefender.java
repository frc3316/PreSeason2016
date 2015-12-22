/*
 * Closes the defenders at the back of the robot
 */
package org.usfirst.frc.team3316.robot.commands.defender;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CloseDefender extends Command 
{
    public CloseDefender() 
    {
        requires(Robot.defender);
    }

    protected void initialize() 
    {
    }

    protected void execute() 
    {
        Robot.defender.close();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}