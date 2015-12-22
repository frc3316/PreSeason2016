/*
 * Waits for the ball to enter the gripper and then retracts
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.roller.WaitForBallMicroswitches;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CloseWhenBallTouchesMS extends CommandGroup 
{
    public CloseWhenBallTouchesMS() 
    {
        addSequential(new WaitForBallMicroswitches());
        addSequential(new CloseGripperClaw());
    }
}