/*
 * Moves kicker to resting position on the top
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.utils.Point;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;

public class RaiseCommand extends Command 
{
    
    public RaiseCommand() 
    {
        requires(Robot.kickerMovement);
    }

    private double restVelocity;
    private double timeOut;
    boolean hasStartedTimeout = false;
    double timeWhenStartedTimeout = 0;

    protected void initialize() 
    {
        timeOut = RobotConstants.get("KICKER_TO_REST_TIMEOUT");
        restVelocity = RobotConstants.get("KICKER_TO_REST_VELOCITY");
        hasStartedTimeout = false;
        timeWhenStartedTimeout = 0;
    }

    protected void execute() 
    {
        //cr: add doc
        double scale = 1;
        if (hasStartedTimeout)
        {
            scale = Utils.scale(timeSinceInitialized()-timeWhenStartedTimeout, 
                    new Point(timeWhenStartedTimeout, 0.5), new Point(timeWhenStartedTimeout+timeOut , -.2));
        }
        Robot.kicker.move( restVelocity * scale );
    }
    

    protected boolean isFinished() 
    {
        if(!hasStartedTimeout)
        {
            if(RobotMap.getRestSwitch())
            {
                hasStartedTimeout = true;
                timeWhenStartedTimeout = timeSinceInitialized();
                setTimeout(timeOut);
            }
        }
        return isTimedOut();
    }

    protected void end() 
    {
        ManageKicker.changeState(KickerState.RESTING);
    }
    
    protected void interrupted() {end();}
}