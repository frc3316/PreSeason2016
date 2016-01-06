/*
 * Sequence for raising the kicker
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.defender.OpenDefender;
import org.usfirst.frc.team3316.robot.commands.defender.WaitForDefender;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.kicker.BrakeCommand;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.RaiseTrigger;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RaiseSequence extends CommandGroup 
{
    public RaiseSequence() 
    {
    	addParallel(new CloseBallKeeper());
    	addParallel(new BrakeCommand());
        addParallel(new OpenDefender());
        addSequential(new WaitForDefender(5.0));
        addSequential(new RaiseTrigger());
        addSequential(new CloseDefender());
    }
}