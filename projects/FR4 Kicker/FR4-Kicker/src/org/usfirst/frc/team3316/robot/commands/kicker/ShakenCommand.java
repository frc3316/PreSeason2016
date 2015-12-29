/*
 * Moves the kicker back up after begin shaken from resting position
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakenCommand extends Command 
{

    int counter = 0;
    double velocity = 0;
    
    public ShakenCommand() {
        requires(Robot.kickerMovement);
        counter = 0;
    }

    protected void initialize() {
    	velocity = RobotConstants.get("SHAKEN_KICKER_VELOCITY");
        counter = 0;
    }

    protected void execute() 
    {
        Robot.kicker.move(velocity);
        //Robot.kicker.move(-0.4);
    }

    protected boolean isFinished() 
    {
        if(RobotMap.getRestSwitch())
        {
            counter++;
        }
        SmartDashboard.putNumber("COUNTER", counter);
//        System.out.println("Shaken_Kicker_Counte" + counter);
        //return counter >= RobotConstants.get("SHAKEN_KICKER_COUNTER");
        return counter >= 3;//SmartDashboard.getNumber("SHAKEN_KICKER_COUNTER");
    }

    protected void end() 
    {
        ManageKicker.changeState(KickerState.RESTING);
    }

    protected void interrupted() 
    {
            end();
    }
}