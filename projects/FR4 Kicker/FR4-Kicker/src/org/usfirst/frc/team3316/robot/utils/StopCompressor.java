/*
 * Stops the compressor
 */
package org.usfirst.frc.team3316.robot.utils;

import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class StopCompressor extends Command 
{
    public StopCompressor() {}

    protected void initialize() {}

    protected void execute() 
    {
        //RobotMap.compressor.stop();
        RobotMap.compressor.setClosedLoopControl(false);
    }

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}