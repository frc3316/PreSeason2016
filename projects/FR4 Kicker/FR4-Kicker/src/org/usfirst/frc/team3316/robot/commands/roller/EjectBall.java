/*
 * Sets the roller motors for ejecting a ball
 */
package org.usfirst.frc.team3316.robot.commands.roller;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class EjectBall extends Command 
{
    boolean wasBallInside = true;
    
    public EjectBall() 
    {
        requires(Robot.gripper);
        wasBallInside = true;
    }
    
    protected void initialize() 
    {
        wasBallInside = true;
    }

    protected void execute() 
    {
        Robot.roller.ejectGripperMotors();
    }

    protected boolean isFinished() 
    {
        //when the microswitch is turned off, a delay is set till the rollers are stopped
        if (!Robot.roller.isBallInside() && wasBallInside)
        {
            wasBallInside = false;
            setTimeout(RobotConstants.get("BALL_EJECT_DELAY"));
        }
        return isTimedOut();
    }

    protected void end() 
    {
        Robot.roller.stopGripperMotors();
    }

    protected void interrupted() {end();}
}