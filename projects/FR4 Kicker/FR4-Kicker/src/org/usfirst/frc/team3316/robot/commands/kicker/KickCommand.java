/*
 * Command for kicking the ball
 * sets the kicker's state when finished to Braking
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

public class KickCommand extends Command 
{ 
    private double kickVelocity;
    boolean left = true, middle = true, right = true;
    DBugLogger logger = new DBugLogger();
    
    public KickCommand() 
    {
        requires(Robot.kickerMovement);
    }

    protected void initialize() 
    {
        setTimeout( RobotConstants.get("KICK_TIMEOUT") );
        kickVelocity = RobotConstants.get("KICKER_VELOCITY");
        
        brakeCounter = 0;
    }

    protected void execute() {
        Robot.kicker.move( kickVelocity );
    }
    
    int brakeCounter = 0;

    protected boolean isFinished() 
    {
        if (Robot.gripper.ballOut())
        {
            //System.out.println(RobotMap.kickerEncoder.getDistance());
            brakeCounter++;
            logger.finest("Ball has been recognized by the IR sensor");
        }
        else if ((!RobotMap.gripperMSMiddle.get() && !RobotMap.gripperMSLeft.get()) || (!RobotMap.gripperMSMiddle.get() && !RobotMap.gripperMSRight.get()) || (!RobotMap.gripperMSRight.get() && !RobotMap.gripperMSLeft.get())) {
        	brakeCounter++;
        	logger.finest("Ball has been recognized by the switches");
        }
        
        if(isTimedOut())
        {
            ManageKicker.changeState(KickerState.KICKING);
            System.out.println("Kicker Timed Out");
            return true;
        }
        else if(brakeCounter >= RobotConstants.getInt("KICKER_NUM_CYCLES"))
        {
            ManageKicker.changeState(KickerState.BRAKE);
            System.out.println("Ball Out of Gripper");
            return true;
        }
        return false;
    }

    protected void end() 
    {
        //System.out.println("Ended: " + RobotMap.kickerEncoder.getDistance());
    }

    protected void interrupted() {end();}
}