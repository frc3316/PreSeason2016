/*
 * Sequence for zeroing the kicker from the resting position
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.defender.OpenDefender;
import org.usfirst.frc.team3316.robot.commands.kicker.MoveKickerToZero;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ZeroSequence extends CommandGroup 
{
    public ZeroSequence() 
    {
        addParallel(new OpenDefender());
        addSequential(new WaitCommand(0.5));
        addSequential(new MoveKickerToZero());
        addSequential(new CloseDefender());
    }
}