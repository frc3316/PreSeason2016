/*
 * Kicking sequence
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.defender.OpenDefender;
import org.usfirst.frc.team3316.robot.commands.defender.WaitForDefender;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenGripperClaw;
import org.usfirst.frc.team3316.robot.commands.kicker.WaitForRest;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.KickTrigger;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.RaiseTrigger;
import org.usfirst.frc.team3316.robot.utils.StartCompressor;
import org.usfirst.frc.team3316.robot.utils.StopCompressor;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class KickingSequence extends CommandGroup 
{    
    public KickingSequence() 
    {
        
        //addSequential(new RollIn(0.15));

        addParallel(new OpenDefender());
        addSequential(new WaitForDefender(5.0));
        addSequential(new OpenBallKeeper());
        
        addParallel(new OpenGripperClaw());     
        addParallel(new StopCompressor());
        addSequential(new WaitCommand(0.4));
        
        addSequential(new WaitForRest());
        addSequential(new KickTrigger());
        
        addParallel(new StartCompressor());
        addSequential(new CloseBallKeeper());
        addSequential(new CloseGripperClaw());
        addSequential(new WaitForChildren());
        addSequential(new RaiseTrigger());
    }
}