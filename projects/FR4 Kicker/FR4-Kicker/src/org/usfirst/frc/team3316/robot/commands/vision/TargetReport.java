/*
 * this class is for holding the important data from TargetVision
 */
package org.usfirst.frc.team3316.robot.commands.vision;

import org.usfirst.frc.team3316.robot.RobotConstants;

public class TargetReport
{
    public boolean leftHot = false;
    public boolean rightHot = false;
    public double rightDistance, leftDistance, leftAngle, rightAngle;
    
    public TargetReport ()
    {
        rightHot = leftHot = false;
        rightAngle = leftAngle = rightDistance = leftDistance = RobotConstants.getInt("ErrorCode");
    }
    
    public double getDistance ()
    {
        return Math.min(leftDistance, rightDistance);
    }
}
