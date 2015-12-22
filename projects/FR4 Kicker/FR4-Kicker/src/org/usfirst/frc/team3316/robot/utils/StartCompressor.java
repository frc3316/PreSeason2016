/*
 * Starts the compressor
 */
package org.usfirst.frc.team3316.robot.utils;

import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
public class StartCompressor extends Command 
{
    public StartCompressor() {}

    protected void initialize() {}

    protected void execute() 
    {
        RobotMap.compressor.setClosedLoopControl(true);
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}