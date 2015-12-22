/*
 * Shifts to higher gear during manual shifting
 */
package org.usfirst.frc.team3316.robot.commands.shifter;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftToUpperGear extends Command 
{
    public ShiftToUpperGear () {}

    protected void initialize() {}

    protected void execute() {
        Robot.shifter.gearUp();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}