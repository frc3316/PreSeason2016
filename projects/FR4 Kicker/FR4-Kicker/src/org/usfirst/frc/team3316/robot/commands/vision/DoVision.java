/*
 * Processes the VisionTarget
 */
package org.usfirst.frc.team3316.robot.commands.vision;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DoVision extends Command 
{
    public DoVision() {
        setRunWhenDisabled(true);
    }

    protected void initialize() 
    {
        try 
        {
           
            SmartDashboard.putNumber("left distance", Robot.target.report.leftDistance);
            SmartDashboard.putNumber("right distance", Robot.target.report.rightDistance);
            SmartDashboard.putBoolean("left hot", Robot.target.report.leftHot);
            SmartDashboard.putBoolean("right hot", Robot.target.report.rightHot);
        } 
        catch (Exception ex) {ex.printStackTrace();}
        
        
    }

    protected void execute() {}

    protected boolean isFinished() {return true;}

    protected void end() {}

    protected void interrupted() {}
}