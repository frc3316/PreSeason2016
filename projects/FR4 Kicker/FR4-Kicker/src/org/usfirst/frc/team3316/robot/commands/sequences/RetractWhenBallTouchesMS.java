/*
 * Waits for the ball to enter the gripper and then retracts
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.gripper.RetractGripper;
import org.usfirst.frc.team3316.robot.commands.roller.WaitForBallMicroswitches;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RetractWhenBallTouchesMS extends CommandGroup 
{
    public RetractWhenBallTouchesMS() 
    {
        addSequential(new WaitForBallMicroswitches());
        addSequential(new RetractGripper());
    }
}