/*
 * Sequence for shooting to hot goal
 * if in front of a goal and it is currnetly hot then shoot
 * else wait 5 secs and then shoot
 */
package org.usfirst.frc.team3316.robot.commands.auton;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class ShootToHotGoal extends Command 
{  
    public ShootToHotGoal()  {}

    protected void initialize() 
    {
        setTimeout(0);
        boolean shootNow = (Robot.autonReport.InFrontGoal.equals(Robot.autonReport.HotGoal));
        if (!shootNow)
        {
            System.out.println("not a now shoot");
            setTimeout(Math.max(4.7 - DriverStation.getInstance().getMatchTime(), 0)); 
            /* wait for other hot goal. 
             * 5 sec from start of auton.
             * because of certain waits, we reduce it to 4.7
             */ 
        }
    }

    protected void execute() 
    {
    }

    protected boolean isFinished() 
    {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    
    }
}
