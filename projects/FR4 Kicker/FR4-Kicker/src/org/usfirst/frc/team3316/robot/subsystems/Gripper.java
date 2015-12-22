/*
 * The subsystem that manages the gripper movements only
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gripper extends Subsystem 
{    
    public static final Value openGripperClawValue = DoubleSolenoid.Value.kReverse;
    public static final Value closeGripperClawValue = DoubleSolenoid.Value.kForward;
    
    public static final Value retractGripperValue = DoubleSolenoid.Value.kReverse;
    public static final Value extendGripperValue = DoubleSolenoid.Value.kForward;
    
    public boolean isCatchOn = false;
    
    public void initDefaultCommand () { isCatchOn = false; }
    
    public void extendGripper ()
    {
        RobotMap.moveGripperSolenoid.set(extendGripperValue);
    }
    
    public void retractGripper ()
    {
        RobotMap.moveGripperSolenoid.set(retractGripperValue);
    }
    
    public void openClaw ()
    {
        RobotMap.gripperClawSolenoid.set(openGripperClawValue);
    }
    
    public void closeClaw ()
    {
        RobotMap.gripperClawSolenoid.set(closeGripperClawValue);
    }
    
    public void openBallKeeper ()
    {
        RobotMap.ballKeeperSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    public void closeBallKeeper ()
    {
    	RobotMap.ballKeeperSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    public boolean isGripperOpen ()
    {
        return RobotMap.gripperClawSolenoid.get().value == openGripperClawValue.value;
    }
    
    public boolean isGripperOut ()
    {
        return RobotMap.moveGripperSolenoid.get().value == extendGripperValue.value;
    }
    
    public boolean isKeeperOut ()
    {
        return RobotMap.ballKeeperSolenoid.get().value == DoubleSolenoid.Value.kForward.value;
    }
    
    public boolean ballIn () {
    	return RobotMap.ballIRSensor.getAverageVoltage() <= SmartDashboard.getNumber("BALL_IR_LIMIT");
    }
    
    public boolean ballOut () {
    	return RobotMap.ballIRSensor.getAverageVoltage() >  SmartDashboard.getNumber("BALL_IR_LIMIT");
    }
}