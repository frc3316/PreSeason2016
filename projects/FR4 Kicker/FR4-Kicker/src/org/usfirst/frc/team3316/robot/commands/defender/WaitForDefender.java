package org.usfirst.frc.team3316.robot.commands.defender;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForDefender extends Command {
	
	double timeout;
	
	public WaitForDefender(double timeout) {
		this.timeout = timeout;
	}

    protected void initialize() {
    	setTimeout(timeout);
    }

    protected void execute() {}

    protected boolean isFinished() {
    	/*if (RobotMap.defenderSwitch.get()) {
    		return true;
    	}*/
        return isTimedOut();
    }

    protected void end() {}

    protected void interrupted() {}
}
