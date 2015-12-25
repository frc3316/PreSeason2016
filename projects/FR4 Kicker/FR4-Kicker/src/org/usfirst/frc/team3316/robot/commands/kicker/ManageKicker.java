/*
 * Manages the kicker's state at all times
 * This command is called to all the time by the Kicker subsystem
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import java.util.TimerTask;
import java.util.logging.Logger;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.commands.sequences.ZeroSequence;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManageKicker {
	public static Command raiseCommand = null;
	public static Command restCommand = null;
	public static Command kickCommand = null;
	public static Command brakeCommand = null;
	public static Command zeroCommand = null;
	public static Command shakeCommand = null;

	DBugLogger logger = Robot.Logger;
	
	UpdateManager updateManager;
	
	public ManageKicker() {
		restCommand = new RestCommand();
		raiseCommand = new RaiseCommand();
		kickCommand = new KickCommand();
		brakeCommand = new BrakeCommand();
		zeroCommand = new ZeroSequence();
		shakeCommand = new ShakenCommand();
		
		setInitialState();
	}

	private void setInitialState() {
		if (RobotMap.getRestSwitch()) {
			restCommand.cancel();
			restCommand = null;
			Robot.kicker.currentState = KickerState.RESTING;
		} else {
			Robot.kicker.currentState = KickerState.OFF;
		}
		SmartDashboard.putString("Current State", Robot.kicker.currentState.toString());
	}
	
	public class UpdateManager extends TimerTask {
		public UpdateManager() {
			logger.finest("UpdateManger task started");
		}
		
		public void run () {
			runCommand(Robot.kicker.currentState);
		}
		
		private void runCommand(KickerState to) {
			switch (to) {
			case OFF:
				Robot.kicker.move(0);
			case RAISING:
				if (raiseCommand == null) {
					raiseCommand = new RaiseCommand();
					raiseCommand.start();
				}
			case RESTING:
				if (restCommand == null) {
					restCommand = new RestCommand();
					restCommand.start();
				}
			case SHAKEN:
				if (shakeCommand == null) {
					shakeCommand = new ShakenCommand();
					shakeCommand.start();
				}
			case KICKING:
				if (kickCommand == null) {
					kickCommand = new KickCommand();
					kickCommand.start();
				}
			case BRAKE:
				if (brakeCommand == null) {
					brakeCommand = new BrakeCommand();
					brakeCommand.start();
				}
			case ZERO:
				if (zeroCommand == null) {
					zeroCommand = new ZeroSequence();
					zeroCommand.start();
				}
			}
		}
	}


	public static KickerState changeState(KickerState to) // returns: current
															// state
	{
		System.out.println("change from:" + Robot.kicker.currentState.toString() + " to: " + to.toString());
		switch (to) {
		case RAISING:
			if (Robot.kicker.currentState.equals(KickerState.OFF)) {
				raiseCommand.cancel();
				raiseCommand = null;
				Robot.kicker.currentState = KickerState.RAISING;
			}

		case RESTING:
			if (Robot.kicker.currentState.equals(KickerState.RAISING)
					|| Robot.kicker.currentState.equals(KickerState.SHAKEN)) {
				raiseCommand.cancel();
				raiseCommand = null;
				Robot.kicker.currentState = KickerState.RESTING;
			}
		case SHAKEN:
			if (Robot.kicker.currentState.equals(KickerState.RESTING)) {
				shakeCommand.cancel();
				shakeCommand = null;
				Robot.kicker.currentState = KickerState.SHAKEN;
			}
		case KICKING:
			if (Robot.kicker.currentState.equals(KickerState.RESTING)
					|| Robot.kicker.currentState.equals(KickerState.OFF)) {
				kickCommand.cancel();
				kickCommand = null;
				Robot.kicker.currentState = KickerState.KICKING;
			}
		case BRAKE:
			if (Robot.kicker.currentState.equals(KickerState.KICKING)) {
				brakeCommand.cancel();
				brakeCommand = null;
				Robot.kicker.currentState = KickerState.BRAKE;
			}
		case ZERO: {
			if (Robot.kicker.currentState.equals(KickerState.RESTING)
					|| Robot.kicker.currentState.equals(KickerState.RAISING)) {
				zeroCommand.cancel();
				zeroCommand = null;
				Robot.kicker.currentState = KickerState.ZERO;
			}
		}
		case OFF:
			if (Robot.kicker.currentState.equals(KickerState.BRAKE)) {
				Robot.kicker.currentState = KickerState.OFF;
			} else if (Robot.kicker.currentState.equals(KickerState.ZERO)) {
				Robot.kicker.currentState = KickerState.OFF;
			}
		}

		SmartDashboard.putString("Current State", Robot.kicker.currentState.toString());
		return Robot.kicker.currentState;
	}
	
	public void timerInit()
	{
		updateManager = new UpdateManager();
		Robot.timer.s
	}
}