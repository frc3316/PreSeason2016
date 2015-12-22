/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team3316.robot.commands.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author user
 */
public class CatchBallTrigger extends Command {
    
    private CatchBallSequence catchBallSequence = null;
    private CloseGripperClaw closeGripperClaw = null;
    
    public CatchBallTrigger() {
        
    }

    protected void initialize() {
        if(catchBallSequence == null)
            catchBallSequence = new CatchBallSequence();
        if(closeGripperClaw == null)
            closeGripperClaw = new CloseGripperClaw();
    }

    protected void execute() {
         if(Robot.gripper.isCatchOn) // should stop catch
        {
            catchBallSequence.cancel();
            closeGripperClaw.start();
            Robot.gripper.isCatchOn = false;
        }
        else // should start catch
        {
            catchBallSequence.start();
            Robot.gripper.isCatchOn = true;
        }
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}