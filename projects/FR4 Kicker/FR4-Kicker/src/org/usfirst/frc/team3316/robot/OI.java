/*
 * this file is for the operator interface (joysticks, gamepads, button)
 */
package org.usfirst.frc.team3316.robot;



import org.usfirst.frc.team3316.robot.commands.chassis.DriveDistance;
import org.usfirst.frc.team3316.robot.commands.defender.CloseDefender;
import org.usfirst.frc.team3316.robot.commands.defender.OpenDefender;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.CloseGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.ExtendGripper;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenBallKeeper;
import org.usfirst.frc.team3316.robot.commands.gripper.OpenGripperClaw;
import org.usfirst.frc.team3316.robot.commands.gripper.RetractGripper;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.OverrideKickerToOff;
import org.usfirst.frc.team3316.robot.commands.kicker.StateCommands.TriggerRaiseAndZero;
import org.usfirst.frc.team3316.robot.commands.roller.CollectBall;
import org.usfirst.frc.team3316.robot.commands.roller.EjectBall;
import org.usfirst.frc.team3316.robot.commands.sequences.CatchBallTrigger;
import org.usfirst.frc.team3316.robot.commands.sequences.CollectBallSequence;
import org.usfirst.frc.team3316.robot.commands.sequences.KickingSequence;
import org.usfirst.frc.team3316.robot.commands.shifter.ShiftToLowerGear;
import org.usfirst.frc.team3316.robot.commands.shifter.ShiftToUpperGear;
import org.usfirst.frc.team3316.robot.commands.vision.DoVision;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI 
{
    /*Joysticks*/
	/*
    public Joystick leftJoystick;
    public Joystick rightJoystick;
    */
	public Joystick driverJoystick; //2 joystick version because of the lack of ATTACK3 joysticks
    public Joystick operatorJoystick;

    public OI()
    {
        /*
         * General initialization
         */
        /*
        rightJoystick = new Joystick(RobotConstants.getInt("JOYSTICK_RIGHT_CHANNEL"));
        leftJoystick = new Joystick(RobotConstants.getInt("JOYSTICK_LEFT_CHANNEL"));
        */
    	driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(RobotConstants.getInt("JOYSTICK_OPERATOR_CHANNEL"));
        
        /*
         * Shifters
         */
        /*
        JoystickButton lowGear = new JoystickButton(
                leftJoystick, 
                RobotConstants.getInt("LOW_GEAR_BUTTON"));
                */
        JoystickButton lowGear = new JoystickButton (driverJoystick, 1);
        lowGear.whenPressed(new ShiftToLowerGear());
        /*
        JoystickButton highGear = new JoystickButton(
                rightJoystick, 
                RobotConstants.getInt("HIGH_GEAR_BUTTON"));
                */
        JoystickButton highGear = new JoystickButton (driverJoystick, 2);
        highGear.whenPressed(new ShiftToUpperGear());
        
        /*
         * Gripper
         */
        JoystickButton openGripperButton = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("OPEN_GRIPPER_BUTTON"));
        openGripperButton.whenPressed(new OpenGripperClaw());
        
        JoystickButton closeGripperButton = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("CLOSE_GRIPPER_BUTTON"));
        closeGripperButton.whenPressed(new CloseGripperClaw());
        
        JoystickButton extendGripperButton = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("EXTEND_GRIPPER_BUTTON"));
        extendGripperButton.whenPressed(new ExtendGripper());
        
        JoystickButton retractGripperButton = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("RETRACT_GRIPPER_BUTTON"));
        retractGripperButton.whenPressed(new RetractGripper());
        
        JoystickButton collectBall = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("COLLECT_BALL_BUTTON"));
        collectBall.toggleWhenPressed(new CollectBallSequence());
        
        JoystickButton catchBall = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("CATCH_BALL_BUTTON"));
        catchBall.whenPressed(new CatchBallTrigger());
        
        JoystickButton kick = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("KICK_BUTTON"));
        kick.whenPressed(new KickingSequence());
        
        JoystickButton moveKickerToRest = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("KICKER_TO_REST_BUTTON"));
        moveKickerToRest.whenPressed(new TriggerRaiseAndZero());
        
        
        JoystickButton rollerIn = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("ROLLER_IN_BUTTON"));
        rollerIn.whileHeld(new CollectBall());
        JoystickButton rollerOut = new JoystickButton(
                operatorJoystick, 
                RobotConstants.getInt("ROLLER_OUT_BUTTON"));
        rollerOut.whileHeld(new EjectBall());
        
        
        SmartDashboard.putData("Open Defender", new OpenDefender());
        SmartDashboard.putData("Close Defender", new CloseDefender());
        SmartDashboard.putData("Open Tusks", new OpenBallKeeper());
        SmartDashboard.putData("Close Tusks", new CloseBallKeeper());
        
        SmartDashboard.putData("Drive 3.1M", new DriveDistance(3.1));
        
        SmartDashboard.putData("Offing Kicker", new OverrideKickerToOff()) ;
        
        /*
         * Vision
         */
        SmartDashboard.putData("Do Vision", new DoVision());  
        /*
        SmartDashboard.putNumber("SHAKEN_KICKER_VELOCITY", 0.3);
        SmartDashboard.putNumber("SHAKEN_KICKER_COUNTER", 1);*/
    }
    
}

