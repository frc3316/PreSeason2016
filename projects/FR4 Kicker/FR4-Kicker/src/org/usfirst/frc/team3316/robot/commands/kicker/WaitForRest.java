/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author user
 */
public class WaitForRest extends Command {
    
    public WaitForRest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        counter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Robot.kicker.currentState.equals(KickerState.Resting) || Robot.kicker.currentState.equals(KickerState.Off))
            counter++;
    }

    int counter = 0;
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        return counter > 1;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}