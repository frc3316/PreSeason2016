/*
 * The subsystem that manages the kicker
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.RobotConstants;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.commands.kicker.KickerState;
import org.usfirst.frc.team3316.robot.commands.kicker.ManageKicker;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Kicker extends Subsystem 
{   
    private double miniCIMScale;
    public ManageKicker manageKicker;
    
    public KickerState currentState = KickerState.OFF;
    
    public void initDefaultCommand() {
        manageKicker = new ManageKicker();
        setDefaultCommand(manageKicker);
        miniCIMScale = RobotConstants.get("MINICIM_SCALE");
    }
    
    public void move(double velocity)
    {        
        RobotMap.kickerLeftCIM.set(-velocity);
        RobotMap.kickerLeftMiniCIM.set(-velocity * miniCIMScale);

        RobotMap.kickerRightCIM.set(velocity);
        RobotMap.kickerRightMiniCIM.set(velocity * miniCIMScale);
    }
    
    public double getTestIRDist ()
    {
    	double dist = RobotMap.testIRSensor.getVoltage()*100;
    	return dist;
    }
}