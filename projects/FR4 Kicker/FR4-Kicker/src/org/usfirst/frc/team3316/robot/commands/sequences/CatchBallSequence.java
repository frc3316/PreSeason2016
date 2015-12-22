/*
 * This sequence is for extending the gripper and rolling in the ball
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.RetractGripper;
import org.usfirst.frc.team3316.robot.commands.roller.CollectBall;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CatchBallSequence extends CommandGroup 
{   
    public CatchBallSequence() 
    {
        addSequential(new RetractGripper());
        addSequential(new OpenGripperClaw());
        addSequential(new CloseBallKeeper());

        addParallel(new CollectBall());
        addSequential(new CloseWhenBallTouchesMS());

    }
       
    
}