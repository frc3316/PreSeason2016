/*
 * Subsystem for shifter
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifter extends Subsystem 
{
    public static Value highGearValue = DoubleSolenoid.Value.kReverse;
    public static Value lowGearValue = DoubleSolenoid.Value.kForward;

    public boolean currentGear; //true for high gear, false for low gear
    
    public void initDefaultCommand()
    {
    }
    
    public void gearUp ()
    {
        RobotMap.shifterSolenoid.set(highGearValue);
        currentGear = true;
    }
    
    public void gearDown ()
    {
        RobotMap.shifterSolenoid.set(lowGearValue);
        currentGear = false;
    }
    
    public boolean getCurrentGear ()
    {
        return highGearValue.value == RobotMap.shifterSolenoid.get().value;
    }
}