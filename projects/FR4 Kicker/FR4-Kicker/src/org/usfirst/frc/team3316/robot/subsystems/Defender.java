/*
 * The defenders at the back of kicker
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Defender extends Subsystem 
{
    //Solenoid cotrolling values
    public static final Value closeDefenderValue  = DoubleSolenoid.Value.kForward;
    public static final Value openDefenderValue = DoubleSolenoid.Value.kReverse;
   
    
    public void initDefaultCommand() {}
    
    public void open ()
    {
        RobotMap.defenderSolenoid.set(openDefenderValue);
    }
    
    public void close ()
    {
        RobotMap.defenderSolenoid.set(closeDefenderValue);
    }
    
    public boolean isOut () //true for out, false for in
    {
        return RobotMap.defenderSolenoid.get().value == openDefenderValue.value;
    }
}