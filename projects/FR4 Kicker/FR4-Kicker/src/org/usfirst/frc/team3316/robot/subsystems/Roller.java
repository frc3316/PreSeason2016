/*
 * The subsystem that manages the rollers on the gripper
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Roller extends Subsystem 
{
    private final double collectionVelocity = RobotConstants.get("ROLLER_COLLECTION_VELOCITY");
    private final double ejectionVelocity = RobotConstants.get("ROLLER_EJECTION_VELOCITY");
    
    public void initDefaultCommand() {}
    
    public void rollInGripperMotors ()
    {
        RobotMap.rollerControllerLeft.set(collectionVelocity);
        RobotMap.rollerControllerRight.set(collectionVelocity);
    }
    
    public void rollMotorsAtSpeed (double x)
    {
        RobotMap.rollerControllerLeft.set(x);
        RobotMap.rollerControllerRight.set(x);
    }
    
    public void ejectGripperMotors ()
    {
        RobotMap.rollerControllerLeft.set(ejectionVelocity);
        RobotMap.rollerControllerRight.set(ejectionVelocity);
    }
    
    public void stopGripperMotors ()
    {
        RobotMap.rollerControllerLeft.set(0);
        RobotMap.rollerControllerRight.set(0);
    }
    
    public boolean areSidesPressed()
    {
        return RobotMap.gripperMSLeft.get() || RobotMap.gripperMSRight.get();
    }
    
    public boolean isBallInside()
    {
        return RobotMap.gripperMSMiddle.get();
    }
}