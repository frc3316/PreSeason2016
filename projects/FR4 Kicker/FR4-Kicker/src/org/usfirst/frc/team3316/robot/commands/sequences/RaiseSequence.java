/*
 * Sequence for raising the kicker
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.defender.OpenDefender;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.RaiseTrigger;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RaiseSequence extends CommandGroup 
{
    public RaiseSequence() 
    {
        addParallel(new OpenDefender());
        addSequential(new WaitCommand(0.5));
        addSequential(new RaiseTrigger());
        addSequential(new CloseDefender());
    }
}