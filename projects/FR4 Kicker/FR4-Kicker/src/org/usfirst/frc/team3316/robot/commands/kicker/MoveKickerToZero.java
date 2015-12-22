/*
 * Moves the kicker down from resting position
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.utils.Point;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;

public class MoveKickerToZero extends Command 
{
    double velocity;
    double timeout;
    
    public MoveKickerToZero() 
    {
        requires(Robot.kickerMovement);
        velocity = RobotConstants.get("ZERO_KICKER_VELOCITY");
        timeout = RobotConstants.get("ZERO_KICKER_TIMEOUT");
    }

    protected void initialize() 
    {
        setTimeout(timeout);
    }

    protected void execute() 
    {
        double scale = Utils.scale(timeSinceInitialized(), new Point(0, 1), new Point(timeout, -1));
        Robot.kicker.move(velocity * scale);
    }

    protected boolean isFinished() 
    {
        return isTimedOut();
    }

    protected void end() 
    {
        ManageKicker.changeState(KickerState.OFF);
    }

    protected void interrupted() {end();}
}