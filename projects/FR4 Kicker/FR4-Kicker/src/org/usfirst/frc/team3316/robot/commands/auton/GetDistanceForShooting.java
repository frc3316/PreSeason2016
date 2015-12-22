/*
 * Sets the distance that is needed for driving in the AutonomousReport
 */
package org.usfirst.frc.team3316.robot.commands.auton;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotConstants;

import edu.wpi.first.wpilibj.command.Command;

public class GetDistanceForShooting extends Command 
{
    double shootingDistance;
    double distanceToDrive;
    
    public GetDistanceForShooting() 
    {
        shootingDistance = RobotConstants.get("SHOOTING_DISTANCE");
    }

    protected void initialize() 
    {
        double leftDistance, rightDistance, distanceToTarget;
        int counter = 0;
        boolean leftHot = false, rightHot = false;
        
        try 
        {
            do 
            {
                //Robot.target.process();
                leftDistance = Robot.target.report.leftDistance;
                rightDistance = Robot.target.report.rightDistance;
                counter++;
                
                if (Robot.target.report.leftHot && Robot.target.report.rightHot)
                {
                    Robot.autonReport.HotGoal = AutonomousReport.ErrorGoal;
                    System.out.println("error goal");
                }
                else if (Robot.target.report.leftHot)
                {
                    Robot.autonReport.HotGoal = AutonomousReport.LeftGoal;
                    System.out.println("left goal");
                }
                else if (Robot.target.report.rightHot)
                {
                    Robot.autonReport.HotGoal = AutonomousReport.RightGoal;
                    System.out.println("right goal");
                }

                String log = "Counter: " + counter;
                log += "\nleftDistance: " + leftDistance;
                log += "\nrightDistance: " + rightDistance;
                System.out.println(log);
            } 
            
            while ((leftDistance == RobotConstants.get("ErrorCode") || rightDistance == RobotConstants.get("ErrorCode")) 
                    && counter < 3);
            
            if (leftDistance == RobotConstants.get("ErrorCode") && rightDistance == RobotConstants.get("ErrorCode"))
            {
                String log = "Error processing distance!";
                log += "\nfinal leftDistance: " + leftDistance;
                log += "\nfinal rightDistance: " + rightDistance;
                log += "\nhot: " + Robot.autonReport.HotGoal.value;
                System.out.println(log);
                return;
            }
            
            System.out.println("Finished processing");
            System.out.println("final leftDistance: " + leftDistance);
            System.out.println("final rightDistance: " + rightDistance);
            System.out.println("hot: " + Robot.autonReport.HotGoal.value);
            
            
            distanceToTarget = Math.min(leftDistance, rightDistance);
            Robot.autonReport.AutonDistanceToDrive = distanceToTarget - shootingDistance;
            System.out.println("driving: " + Robot.autonReport.AutonDistanceToDrive);
            
            leftHot = Robot.target.report.leftHot;
            rightHot = Robot.target.report.rightHot;
            System.out.println("leftHot = " + leftHot);
            System.out.println("rightHot = " + rightHot); 
            
            if (leftHot)
            {
                Robot.autonReport.wasLeftHotFirst = true;
            }
            else if (rightHot)
            {
                Robot.autonReport.wasLeftHotFirst = false;
            }
            Robot.autonReport.getAutonInFrontGoal();
            
        } 
        catch (Exception exp)
        {
        	System.out.println("Exception in getDistanceForShooting");
            exp.printStackTrace();
        }
    
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}
    
    protected void interrupted() {}
}
