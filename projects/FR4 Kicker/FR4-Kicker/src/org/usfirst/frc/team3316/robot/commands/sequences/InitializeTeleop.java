/*
 * Initializes the robot at the start of the teleop
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.RetractGripper;
import org.usfirst.frc.team3316.robot.commands.shifter.ShiftToUpperGear;
import org.usfirst.frc.team3316.robot.utils.StartCompressor;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InitializeTeleop extends CommandGroup {
    
    public InitializeTeleop() {
    	addParallel(new StartCompressor());
        addParallel(new ShiftToUpperGear());
        addParallel(new CloseDefender());
        addSequential(new CloseGripperClaw());
        addSequential(new RetractGripper());
        addSequential(new CloseBallKeeper());
    }
}