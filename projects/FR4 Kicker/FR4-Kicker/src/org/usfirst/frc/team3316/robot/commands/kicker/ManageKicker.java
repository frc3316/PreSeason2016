/*
 * Manages the kicker's state at all times
 * This command is called to all the time by the Kicker subsystem
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.RobotMap;
import org.usfirst.frc.team3316.robot.commands.kicker.KickerState.State;
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
            Robot.kicker.currentState = KickerState.Resting;
        }
        else
        {
            Robot.kicker.currentState = KickerState.Off;
        }
        SmartDashboard.putString("Current State", Robot.kicker.currentState.name);
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
        if(Robot.kicker.currentState.value == KickerState.Off.value)
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

    public static State changeState(State to) // returns: current state
    {
        System.out.println("change from:" + Robot.kicker.currentState.name + " to: " + to.name);
        
        if(to.equals(KickerState.Raising))
        {
            if(Robot.kicker.currentState.equals(KickerState.Off))
            {
                //cr: add cancel command before nulling on all the other shit
                raiseCommand.cancel();
                raiseCommand = null;
                Robot.kicker.currentState = KickerState.Raising;
            }
        }
        else if(to.equals(KickerState.Resting))
        {
            if(Robot.kicker.currentState.equals(KickerState.Raising))
            {
                restCommand.cancel();
                restCommand = null;
                Robot.kicker.currentState = KickerState.Resting;
            }
            else if(Robot.kicker.currentState.equals(KickerState.Shaken))
            {
                restCommand.cancel();
                restCommand = null;
                Robot.kicker.currentState = KickerState.Resting;
            }
        }
        else if(to.equals(KickerState.Shaken))
        {
            if(Robot.kicker.currentState.equals(KickerState.Resting))
            {
                shakeCommand.cancel();
                shakeCommand = null;
                Robot.kicker.currentState = KickerState.Shaken;
            }
        }
        else if(to.equals(KickerState.Kicking))
        {
            if(Robot.kicker.currentState.equals(KickerState.Resting))
            {
                kickCommand.cancel();
                kickCommand = null;
                Robot.kicker.currentState = KickerState.Kicking;
            }
            else if(Robot.kicker.currentState.equals(KickerState.Off))
            {
                kickCommand.cancel();
                kickCommand = null;
                Robot.kicker.currentState = KickerState.Kicking;
            }
        }
        else if(to.equals(KickerState.Braking))
        {
            if(Robot.kicker.currentState.equals(KickerState.Kicking))
            {
                brakeCommand.cancel();
                brakeCommand = null;
                Robot.kicker.currentState = KickerState.Braking;
            }
        }
        else if(to.equals(KickerState.Zeroing))
        {
            if(Robot.kicker.currentState.equals(KickerState.Resting))
            {
                zeroCommand.cancel();
                zeroCommand = null;
                Robot.kicker.currentState = KickerState.Zeroing;
            }
            else if(Robot.kicker.currentState.equals(KickerState.Raising))
            {
                zeroCommand.cancel();
                zeroCommand = null;
                Robot.kicker.currentState = KickerState.Zeroing;
            }
        }
        else if(to.equals(KickerState.Off))
        {
            if(Robot.kicker.currentState.equals(KickerState.Braking))
            {
                Robot.kicker.currentState = KickerState.Off;
            }
            else if(Robot.kicker.currentState.equals(KickerState.Zeroing))
            {
                Robot.kicker.currentState = KickerState.Off;
            }
        }
        
        SmartDashboard.putString("Current State", Robot.kicker.currentState.name);
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