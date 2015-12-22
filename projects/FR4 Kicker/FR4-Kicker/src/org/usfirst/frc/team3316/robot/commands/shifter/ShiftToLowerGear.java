/*
 * Shifts to lower gear during manual shifting
 */
package org.usfirst.frc.team3316.robot.commands.shifter;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftToLowerGear extends Command 
{
    public ShiftToLowerGear() {}

    protected void initialize() {}

    protected void execute() 
    {
        Robot.shifter.gearDown();
    }

    protected boolean isFinished() {return true;}
    
    protected void end() {}

    protected void interrupted() {}
}