/*
 * triple speed conroller for the chassis because each side has 3 engines
 */
package org.usfirst.frc.team3316.robot.utils;

import org.usfirst.frc.team3316.robot.RobotConstants;

import edu.wpi.first.wpilibj.Talon;

public class ChassisSpeedControllers
{
    public Talon CIMS;
    public Talon miniCIM;
    
    private double miniCIMScale;
    
    public ChassisSpeedControllers (int CIMSChannel, int miniCIMChannel)
    {
        CIMS = new Talon(CIMSChannel);
        miniCIM = new Talon(miniCIMChannel);
        miniCIMScale = RobotConstants.get("MINICIM_SCALE");
    }
    
    public void set (double d)
    {
        CIMS.set(d);
        miniCIM.set(d*miniCIMScale);
    }
}
