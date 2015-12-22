/*
 * Drives the distance that was set in the AutonomousReport
 * made for auton
 */
package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistanceForShoot extends Command 
{    
    static DriveDistance drive;
    double timeout;
    
    public DriveDistanceForShoot(double toTimeout) 
    {
        timeout = toTimeout;
        drive = new DriveDistance(RobotConstants.get("DEFUALT_AUTON_DRIVE_DISTANCE"));
        requires(Robot.chassis);
    }

    protected void initialize() {
        setTimeout(timeout);
        drive.setDistance(Robot.autonReport.AutonDistanceToDrive);
        drive.initialize();
    }

    protected void execute() {
        drive.execute();
    }

    protected boolean isFinished() {

        return drive.isFinished() || isTimedOut();
    }

    protected void end() {
        drive.end();
    }
    
     protected void interrupted() {
         end();
    }

}