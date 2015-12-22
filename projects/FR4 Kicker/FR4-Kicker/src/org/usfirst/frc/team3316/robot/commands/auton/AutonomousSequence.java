/*
 * Basic autonomous sequence
 * Drives forward the distance needed for shooting
 * if in front of a hot goal then shoot
 * else wait 5 secs then shoot
 */
package org.usfirst.frc.team3316.robot.commands.auton;

import org.usfirst.frc.team3316.robot.commands.chassis.DriveDistance;
import org.usfirst.frc.team3316.robot.commands.chassis.DriveDistanceForShoot;
import org.usfirst.frc.team3316.robot.commands.chassis.ResetChassisSensors;
import org.usfirst.frc.team3316.robot.commands.chassis.TurnToHeading;
import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.defender.OpenDefender;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.RetractGripper;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.KickTrigger;
import org.usfirst.frc.team3316.robot.commands.shifter.ShiftToLowerGear;
import org.usfirst.frc.team3316.robot.commands.shifter.ShiftToUpperGear;
import org.usfirst.frc.team3316.robot.utils.StartCompressor;
import org.usfirst.frc.team3316.robot.utils.StopCompressor;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class AutonomousSequence extends CommandGroup 
{
    
    public AutonomousSequence() 
    {
        addParallel(new OpenDefender());
        addParallel(new ShiftToUpperGear());
        addSequential(new CloseGripperClaw());
        addSequential(new RetractGripper());

        addParallel(new StartCompressor());
        addParallel(new OpenBallKeeper());
        
        addParallel(new RaiseKickerForAuton());
        
        addSequential(new ResetChassisSensors());
        addSequential(new GetDistanceForShooting());
        addSequential(new DriveDistanceForShoot(3)); // 3 sec timeout
         
        addSequential(new ShootToHotGoal());
        addParallel(new OpenGripperClaw());
        addSequential(new WaitCommand(0.5));
        
        addSequential(new StopCompressor());
        
        addSequential(new KickTrigger());

        addSequential(new WaitCommand(0.7));
        
        addSequential(new ShiftToLowerGear());
        addParallel(new TurnToHeading(180));
        addParallel(new StartCompressor());
        
        addSequential(new CloseBallKeeper());
        addSequential(new CloseGripperClaw());
        
        addParallel(new RaiseKickerForAuton());
        
        addSequential(new WaitForChildren());
         
        addSequential(new ShiftToUpperGear());
        addSequential(new DriveDistance(1));
                
        
        addParallel(new WaitForChildren());
        addSequential(new CloseDefender());
        
    }
    
    public String toString()
    {
        return "Vision Based Auton Sequence";
    }
}
