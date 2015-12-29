package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KalmanRecord extends Command 
{

    public KalmanRecord() 
    {
    	setRunWhenDisabled(true);
    }

    protected void initialize() 
    {
    	Robot.chassis.recording = !Robot.chassis.recording;
    }

    protected void execute() {
    }
    
    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
