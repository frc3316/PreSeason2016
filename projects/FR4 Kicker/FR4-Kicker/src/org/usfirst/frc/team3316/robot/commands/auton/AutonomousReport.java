/*
 * This class s for autonomous vision report
 */
package org.usfirst.frc.team3316.robot.commands.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;

public class AutonomousReport 
{
    boolean wasLeftHotFirst;
    double staringDistanceFromLeftWall;
    double startingDistanceFromRightWall;
    boolean startedInFrontOfLeft;
    
    public Goal HotGoal = ErrorGoal;
    public Goal InFrontGoal = ErrorGoal;
    public double AutonDistanceToDrive = RobotConstants.get("DEFUALT_AUTON_DRIVE_DISTANCE");
    
    public AutonomousReport ()
    {
        startedInFrontOfLeft = wasLeftHotFirst = false;
        startingDistanceFromRightWall = staringDistanceFromLeftWall = 6.11;
    }
    
    public void getAutonInFrontGoal()
    {
        if((Robot.target.report.leftDistance == RobotConstants.getInt("ErrorCode")
                && Robot.target.report.rightDistance == RobotConstants.getInt("ErrorCode")))
        {
            this.InFrontGoal = ErrorGoal;
        }
        if ((Robot.target.report.rightDistance == RobotConstants.getInt("ErrorCode"))
                || Robot.target.report.leftDistance <= Robot.target.report.rightDistance)
        {
            this.InFrontGoal = LeftGoal;
        } 
        if ((Robot.target.report.leftDistance == RobotConstants.getInt("ErrorCode"))
                || Robot.target.report.leftDistance > Robot.target.report.rightDistance)
        {
            this.InFrontGoal = RightGoal;
        }
    }
    
    // used to show which goal is hot.
    public static class Goal
    {
        int value = 0;
        private Goal (int value)
        {
            this.value = value;
        }
        
        public boolean equals(Goal other)
        {
            return (other.value == this.value);
        }
    }
    public static final Goal LeftGoal = new Goal(1);
    public static final Goal RightGoal = new Goal(2);
    public static final Goal ErrorGoal = new Goal(0);
}