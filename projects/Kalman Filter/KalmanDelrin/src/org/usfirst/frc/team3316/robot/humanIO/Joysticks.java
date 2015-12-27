/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.UseNavx;
import org.usfirst.frc.team3316.robot.chassis.commands.WiggleWiggle;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.AutoTotePickup;
import org.usfirst.frc.team3316.robot.sequences.DropoffSequence;
import org.usfirst.frc.team3316.robot.stacker.commands.Brake;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.UnBrake;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.HoldContainer;
import org.usfirst.frc.team3316.robot.stacker.commands.OpenGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.ReleaseContainer;
import org.usfirst.frc.team3316.robot.vision.SaveBinaryFrame;
import org.usfirst.frc.team3316.robot.vision.SaveFrame;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Joysticks
{
	/*
	 * Defines a button in a gamepad POV for an array of angles
	 */
	private class POVButton extends Button
	{
		Joystick m_joystick;
		int m_deg;

		public POVButton(Joystick joystick, int deg)
		{
			m_joystick = joystick;
			m_deg = deg;
		}

		public boolean get()
		{
			if (m_joystick.getPOV() == m_deg)
			{
				return true;
			}
			return false;
		}
	}

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	/*
	 * Joysticks
	 */
	public Joystick joystickLeft;
	public Joystick joystickRight;
	public Joystick joystickOperator;

	/*
	 * Joystick Buttons
	 */
	public POVButton buttonRollIn, buttonRollOut, buttonRollTurnClockwise,
			buttonRollTurnCounterClockwise;

	public JoystickButton moveStackerToFloor, moveStackerToStep,
			moveStackerToTote, totePickup, dropoff;

	public JoystickButton holdContainer, releaseContainer;

	public JoystickButton openGripper, closeGripper;

	public JoystickButton openAnschluss, closeAnschluss;

	public JoystickButton wiggleWiggle;
	
	public JoystickButton enableNavx, disableNavx;

	public POVButton openBrake, closeBrake;
	
	public Joysticks ()
	{
		try
		{
			/*
			 * Joysticks
			 */
			joystickLeft = new Joystick((int) config.get("JOYSTICK_LEFT"));
			joystickRight = new Joystick((int) config.get("JOYSTICK_RIGHT"));
			joystickOperator = new Joystick(
					(int) config.get("JOYSTICK_OPERATOR"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	public void initButtons()
	{
		/*
		 * Joystick Buttons
		 */
		try
		{
			/*
			 * Chassis
			 */
			wiggleWiggle = new JoystickButton(joystickRight, (int) config.get("BUTTON_WIGGLE_WIGGLE"));
			wiggleWiggle.whenPressed(new WiggleWiggle());

			/*
			 * Stacker
			 */
			moveStackerToFloor = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_MOVE_STACKER_TO_FLOOR"));
			moveStackerToFloor.whenPressed(new MoveStackerToFloor());

			moveStackerToStep = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_MOVE_STACKER_TO_STEP"));
			moveStackerToStep.whenPressed(new MoveStackerToStep());

			moveStackerToTote = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_MOVE_STACKER_TO_TOTE"));
			moveStackerToTote.whenPressed(new MoveStackerToTote());
			
			totePickup = new JoystickButton(joystickOperator, 
					(int) config.get("BUTTON_TOTE_PICKUP"));
			totePickup.whenPressed(new AutoTotePickup());
			
			dropoff = new JoystickButton(joystickOperator, 
					(int) config.get("BUTTON_DROPOFF"));
			dropoff.whenPressed(new DropoffSequence());

			holdContainer = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_HOLD_CONTAINER"));
			holdContainer.whenPressed(new HoldContainer());

			releaseContainer = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_RELEASE_CONTAINER"));
			releaseContainer.whenPressed(new ReleaseContainer());

			openGripper = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_OPEN_GRIPPER"));
			openGripper.whenPressed(new OpenGripper());

			closeGripper = new JoystickButton(joystickOperator,
					(int) config.get("BUTTON_CLOSE_GRIPPER"));
			closeGripper.whenPressed(new CloseGripper());

			closeBrake = new POVButton(joystickOperator, (int) config.get("BUTTON_CLOSE_BRAKE"));
			closeBrake.whenPressed(new Brake());

			openBrake = new POVButton(joystickOperator, (int) config.get("BUTTON_OPEN_BRAKE"));
			openBrake.whenPressed(new UnBrake());
			
			enableNavx = new JoystickButton(joystickRight, (int) config.get("BUTTON_ENABLE_NAVX"));
			enableNavx.whenPressed(new UseNavx(true));
			
			disableNavx = new JoystickButton(joystickRight, (int) config.get("BUTTON_DISABLE_NAVX"));
			disableNavx.whenPressed(new UseNavx(false));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
