/*
 * This sequence is for ejecting the ball once inside
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.RetractGripper;
import org.usfirst.frc.team3316.robot.commands.roller.EjectBall;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BallEjectionSequence extends CommandGroup 
{
    public BallEjectionSequence() 
    {
        addSequential(new CloseGripperClaw());
        addSequential(new CloseBallKeeper());
        addSequential(new RetractGripper());
        addSequential(new WaitCommand(0.15));
        addSequential(new EjectBall());
    }
}