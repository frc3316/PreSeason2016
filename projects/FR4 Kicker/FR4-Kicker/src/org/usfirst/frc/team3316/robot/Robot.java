/*
 * Le robot main class
 */
package org.usfirst.frc.team3316.robot;

import org.usfirst.frc.team3316.robot.commands.auton.AutonomousReport;
import org.usfirst.frc.team3316.robot.commands.auton.AutonomousSequence;
import org.usfirst.frc.team3316.robot.commands.kicker.ManageKicker;
import org.usfirst.frc.team3316.robot.commands.sequences.InitializeTeleop;
import org.usfirst.frc.team3316.robot.commands.vision.TargetVision;
import org.usfirst.frc.team3316.robot.subsystems.Chassis;
import org.usfirst.frc.team3316.robot.subsystems.Defender;
import org.usfirst.frc.team3316.robot.subsystems.Gripper;
import org.usfirst.frc.team3316.robot.subsystems.Kicker;
import org.usfirst.frc.team3316.robot.subsystems.Roller;
import org.usfirst.frc.team3316.robot.subsystems.Shifter;
import org.usfirst.frc.team3316.robot.utils.NullSubsystem;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.IterativeRobot;
import java.util.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot 
{
    Command autonomousCommand;

    public static Chassis chassis;
    public static Shifter shifter;
    public static Gripper gripper;
    public static Kicker kicker;
    public static Defender defender;
    public static Roller roller;
    public static IndicatorsSystem indicators;
    public static NullSubsystem kickerMovement;
    
    public static RobotConstants constants;
    public static TargetVision target;
    public static AutonomousReport autonReport;
    public static OI oi;
    public static ManageKicker manageKicker;
    public static DBugLogger Logger = new DBugLogger();
    
    private static Timer timer;
    
    public void robotInit() 
    {       
        RobotMap.init();  
        
        /*Inits the robot subsystems*/
        chassis = new Chassis();
        shifter = new Shifter();
        gripper = new Gripper();
        kicker = new Kicker();
        indicators = new IndicatorsSystem();
        kickerMovement = new NullSubsystem();

        target = new TargetVision();
        defender = new Defender();
        roller = new Roller();
        autonReport = new AutonomousReport();
        manageKicker = new ManageKicker();
        
        oi = new OI();
        
        autonomousCommand = new AutonomousSequence();     
    }
    
    public static synchronized Timer GetTimer() {
    	if(timer == null) {
    		timer = new Timer(true);
    	}
    	return timer;
    }

    public void autonomousInit() 
    {            
        if (autonomousCommand != null)
        {
            autonomousCommand.start();
        }
    }

    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit()
    {
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }
        
        new InitializeTeleop().start();
    }

    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    public void testPeriodic() 
    {
        LiveWindow.run();
    }
    
    public void disabledPeriodic()
    {
        Scheduler.getInstance().run();
    }
}
