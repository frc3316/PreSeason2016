/*
 * Sets the roller motors to collect the ball
 */
package org.usfirst.frc.team3316.robot.commands.roller;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class CollectBall extends Command 
{
    boolean wasBallInside = false;
    private double timeOut;
    private int counter = 0;
    
    public CollectBall() 
    {
        requires(Robot.roller);
        wasBallInside = false;
        counter = 0;
    }

    protected void initialize() 
    {
        timeOut = RobotConstants.get("BALL_COLLECT_DELAY");
        wasBallInside = false;
        counter = 0;
    }
   
    
    protected void execute() 
    {
        Robot.roller.rollInGripperMotors();
    }

    protected boolean isFinished() 
    {
        //when the microswitch is turned on, a delay is set till the rollers are stopped
        if(Robot.roller.isBallInside() && !wasBallInside)
        {
            wasBallInside = true;
        }
        if(wasBallInside)
        {
            counter++;
        }
        return (counter >= (timeOut*50));
    }

    protected void end() 
    {
        Robot.roller.stopGripperMotors();
    }

    protected void interrupted() {end();}
}