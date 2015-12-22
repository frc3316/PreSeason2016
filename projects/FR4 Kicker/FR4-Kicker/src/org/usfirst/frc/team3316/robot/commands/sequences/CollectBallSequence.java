/*
 * This sequence is for extending the gripper and rolling in the ball
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.roller.CollectBall;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectBallSequence extends CommandGroup 
{   
    public CollectBallSequence() 
    {
        addSequential(new CloseDefender());
        addSequential(new CloseGripperClaw());
        addSequential(new CloseBallKeeper());
        addSequential(new CloseGripperClaw());
        
        addSequential(new ExtendGripperCotrolled());
        
        //delay between the extention of the gripper and the roller activation
        
        addParallel(new CollectBall());
        addSequential(new RetractWhenBallTouchesMS());

    }
       
    
}