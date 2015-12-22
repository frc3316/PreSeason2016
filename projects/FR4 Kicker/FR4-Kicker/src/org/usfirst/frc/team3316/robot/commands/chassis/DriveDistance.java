package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command 
{
    double [][] values = new double [][]
    {
        {0, 0.5, 1},
        {1, 0.63, 0.1}
    };

    double distanceToDrive = 0;
    boolean targetPassed = false;
    Gyro gyro = RobotMap.gyro;
    int counter = 0;
    public DriveDistance(double distance) 
    {
        requires(Robot.chassis);
        
        distanceToDrive = distance;
    }
    
    public void setDistance(double distance)
    {
        distanceToDrive = distance;
    }

    protected void initialize() {
        
        Robot.chassis.resetEncoder();
        Robot.chassis.resetGyro();
        targetPassed = false;
        counter = 0;
    }

    protected void execute() 
    {       
        //double value =  Utils.scale(Robot.chassis.getDistance(), 
        //        new Point(0,1), new Point(distanceToDrive, 0.1));
        double value = Utils.valueInterpolation(Robot.chassis.getDistance()/distanceToDrive, values);
        
        if(value < 0) value = 0;
        
        if (Robot.chassis.getDistance() < distanceToDrive)
        {
            Robot.chassis.setMotors(value-correctAngle(), value+correctAngle());
        }
        else if (Robot.chassis.getDistance() > distanceToDrive)
        {
            Robot.chassis.setMotors(-value-correctAngle(), -value+correctAngle());
            targetPassed = true;
        }
    }

    //finishes when passed the target distance and starts jittering around the setpoint
    protected boolean isFinished() {
        if ((Utils.deadband(RobotMap.chassisEncoder.getRate(), 0.1) == 0) && targetPassed)
        {
            counter++;
        }
        return counter > 2;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    //sets an arbitrary value for the motors to fix the robot's angle so it will drive in a straight line
    private double correctAngle ()
    {
        double d = gyro.getAngle();
        d = Utils.limitValue(d, 12);
        d = Utils.deadband(d/12 * 0.3, 0.07);
        return d;
    }
}