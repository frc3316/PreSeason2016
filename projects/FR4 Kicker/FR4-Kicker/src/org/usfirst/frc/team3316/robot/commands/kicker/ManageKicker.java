/*
 * Manages the kicker's state at all times
 * This command is called to all the time by the Kicker subsystem
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.commands.sequences.ZeroSequence;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManageKicker extends Command 
{
    public static Command raiseCommand  = null;
    public static Command  restCommand  = null;
    public static Command  kickCommand  = null;
    public static Command brakeCommand  = null;
    public static Command  zeroCommand  = null;
    public static Command shakeCommand  = null;
    
    public ManageKicker()
    {
        setRunWhenDisabled(false);
        requires(Robot.kicker);
    }

    private void setInitialState()
    {
        if(RobotMap.getRestSwitch())
        {
            restCommand.cancel();
            restCommand = null;
            Robot.kicker.currentState = KickerState.RESTING;
        }
        else
        {
            Robot.kicker.currentState = KickerState.OFF;
        }
        SmartDashboard.putString("Current State", Robot.kicker.currentState.toString());
    }
    
    protected void initialize() 
    {
        restCommand = new RestCommand();
        raiseCommand = new RaiseCommand();
        kickCommand = new KickCommand();
        brakeCommand = new BrakeCommand();
        zeroCommand = new ZeroSequence();
        shakeCommand = new ShakenCommand();
        
        setInitialState();
    }
    
    private void manageOff()
    {
        Robot.kicker.move(0);
    }
    
    private void manageRaising()
    {
        if(raiseCommand == null)
        {
            raiseCommand = new RaiseCommand();
            raiseCommand.start();
        }
    }
    private void manageInRest()
    {        
        if(restCommand == null)
        {
            restCommand = new RestCommand();
            restCommand.start();
        }
    }
    private void manageShaken() 
    {
        if(shakeCommand == null)
        {
            shakeCommand = new ShakenCommand();
            shakeCommand.start();
        }
    }
    private void manageKick()
    {        
        if(kickCommand == null)
        {
            kickCommand = new KickCommand();
            kickCommand.start();
        }
    }
    private void manageBrake()
    {        
        if(brakeCommand == null)
        {
            brakeCommand = new BrakeCommand();
            brakeCommand.start();
        }
    }
    private void manageZero ()
    {        
       if(zeroCommand == null)
        {
            zeroCommand = new ZeroSequence();
            zeroCommand.start();
        }
    }

    protected void execute() 
    {
        if(Robot.kicker.currentState.equals(other)KickerState.OFF)
        {
            manageOff();
        }
        else if(Robot.kicker.currentState.value == KickerState.Raising.value)
        {
            manageRaising();
        }
        else if(Robot.kicker.currentState.value == KickerState.Resting.value)
        {
            manageInRest();
        }
        else if(Robot.kicker.currentState.value == KickerState.Shaken.value)
        {
            manageShaken();
        }
        else if(Robot.kicker.currentState.value == KickerState.Kicking.value)
        {
            manageKick();
        }
        else if(Robot.kicker.currentState.value == KickerState.Braking.value)
        {
            manageBrake();
        }
        else if(Robot.kicker.currentState.value == KickerState.Zeroing.value)
        {
            manageZero();
        }

    }

    public static KickerState changeState(KickerState to) // returns: current state
    {
        System.out.println("change from:" + Robot.kicker.currentState.toString() + " to: " + to.name);
        switch (to) {
        	case RAISING:
        		if(Robot.kicker.currentState.equals(KickerState.OFF))
                {
                    raiseCommand.cancel();
                    raiseCommand = null;
                    Robot.kicker.currentState = KickerState.RAISING;
                }
        		
        	case RESTING:
        		if(Robot.kicker.currentState.equals(KickerState.RAISING) || Robot.kicker.currentState.equals(KickerState.SHAKEN))
                {
                    raiseCommand.cancel();
                    raiseCommand = null;
                    Robot.kicker.currentState = KickerState.RESTING;
                }
        	case SHAKEN:
        		if(Robot.kicker.currentState.equals(KickerState.RESTING))
                {
                    shakeCommand.cancel();
                    shakeCommand = null;
                    Robot.kicker.currentState = KickerState.SHAKEN;
                }
        	case KICKING:
        		if(Robot.kicker.currentState.equals(KickerState.RESTING) || Robot.kicker.currentState.equals(KickerState.OFF))
                {
                    kickCommand.cancel();
                    kickCommand = null;
                    Robot.kicker.currentState = KickerState.KICKING;
                }
        	case BRAKE:
        		if(Robot.kicker.currentState.equals(KickerState.KICKING))
                {
                    brakeCommand.cancel();
                    brakeCommand = null;
                    Robot.kicker.currentState = KickerState.BRAKE;
                }
        	case ZERO:
        	{
        		if(Robot.kicker.currentState.equals(KickerState.RESTING) || Robot.kicker.currentState.equals(KickerState.RAISING))
                {
                    zeroCommand.cancel();
                    zeroCommand = null;
                    Robot.kicker.currentState = KickerState.ZERO;
                }
        	}
        	case OFF:
        		if(Robot.kicker.currentState.equals(KickerState.BRAKE))
                {
                    Robot.kicker.currentState = KickerState.OFF;
                }
                else if(Robot.kicker.currentState.equals(KickerState.ZERO))
                {
                    Robot.kicker.currentState = KickerState.OFF;
                }
        }
        
        SmartDashboard.putString("Current State", Robot.kicker.currentState.toString());
        return Robot.kicker.currentState;
    }
    
    
    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() 
    {
        setInitialState();
    }

    protected void interrupted() 
    {
        end();
    }

    
}