/*
 * Resets the chassis' sensors
 */
package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class ResetChassisSensors extends Command 
{
    public ResetChassisSensors() {}

    protected void initialize() {}

    protected void execute() 
    {
        Robot.chassis.resetChassisSensors();
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}