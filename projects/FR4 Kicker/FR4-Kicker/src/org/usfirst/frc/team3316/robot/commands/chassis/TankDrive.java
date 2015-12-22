/*
 * Bloody tank drive for the driver
 */
package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;

public class  TankDrive extends Command 
{      
    public TankDrive() 
    {
        requires(Robot.chassis);
    }
   
    protected void initialize() 
    {
        Robot.chassis.resetGyro();
    }

    protected void execute() 
    {       
        double left = -Robot.oi.driverJoystick.getRawAxis(1);
        double right = -Robot.oi.driverJoystick.getRawAxis(5);

        left = Utils.deadband(left, 0.025);
        right = Utils.deadband(right, 0.025);

        Robot.chassis.setMotors(left, right);
    }
    
    protected boolean isFinished() {return false;}

    protected void end() {}

    protected void interrupted() {}
}