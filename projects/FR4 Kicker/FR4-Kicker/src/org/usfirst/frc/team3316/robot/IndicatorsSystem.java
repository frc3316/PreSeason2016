/*
 * Subsystem for displaying indicators on the SmartDashboard
 */
package org.usfirst.frc.team3316.robot;

import org.usfirst.frc.team3316.robot.utils.Indicators;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IndicatorsSystem extends Subsystem 
{
    public void initDefaultCommand() 
    {
        setDefaultCommand(new Indicators());
    }
}