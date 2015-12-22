
/*
 * this file contains instance for all of the mechanic things and controllers
 * in the robot.
 */

package org.usfirst.frc.team3316.robot;
    
import org.usfirst.frc.team3316.robot.utils.ChassisSpeedControllers;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap 
{
    /*General*/
    //public static AxisCamera camera = null;
    public static Compressor compressor;
      
    /*Chassis*/
    public static ChassisSpeedControllers leftMotors, rightMotors;
    public static Encoder chassisEncoder;
    public static Gyro gyro;
    
    /*Shifter*/
    public static DoubleSolenoid shifterSolenoid;
    
    /*Gripper*/
    public static Talon rollerControllerRight, rollerControllerLeft;
    public static DoubleSolenoid moveGripperSolenoid, gripperClawSolenoid, ballKeeperSolenoid;
    
    public static DigitalInput gripperMSLeft;
    public static DigitalInput gripperMSRight;
    public static DigitalInput gripperMSMiddle;
    
    /*Kicker*/
    public static Talon kickerLeftCIM, kickerLeftMiniCIM, kickerRightCIM, kickerRightMiniCIM;
    public static DigitalInput kickerRestHE;
    public static AnalogInput ballIRSensor, testIRSensor;
    public static DoubleSolenoid defenderSolenoid;
    public static Encoder kickerEncoder;
   
    public static void init() 
    {
        /*
         * General initialization
         */
    	
    	
        compressor = new Compressor(0);
        compressor.setClosedLoopControl(true);
        LiveWindow.addActuator("Pneumatics", "compressor", compressor);

        Compressor noCompressor = new Compressor(1);
        noCompressor.setClosedLoopControl(false);
        
        //camera = AxisCamera.getInstance();
        
        /*
         * Shifter Initialization
         */
        shifterSolenoid = new DoubleSolenoid(
                RobotConstants.getInt("SHIFTER_SOLENOID_FORWARD"),
                RobotConstants.getInt("SHIFTER_SOLENOID_REVERSE"));    
        LiveWindow.addActuator("Pneumatics", "shifterSolenoid", shifterSolenoid);
        
        /* 
         * Chassis Initialization
         */
        leftMotors = new ChassisSpeedControllers(
                
                RobotConstants.getInt("LEFT_CIMS_CHANNEL"),
                
                RobotConstants.getInt("LEFT_MINICIM_CHANNEL"));
        
        rightMotors = new ChassisSpeedControllers(
                
                RobotConstants.getInt("RIGHT_CIMS_CHANNEL"),
                
                RobotConstants.getInt("RIGHT_MINICIM_CHANNEL"));
        
        chassisEncoder = new Encoder(
                RobotConstants.getInt("CHASSIS_ENCODER_CHANNEL_A"), 
                RobotConstants.getInt("CHASSIS_ENCODER_CHANNEL_B") 
                , true, CounterBase.EncodingType.k4X);
        
        chassisEncoder.setDistancePerPulse(0.0001 / 0.58602484472049689440993788819876 * 1.4612903225806451612903225806452);
        // 4'' wheels, 3:1 reduction, 360 ticks per round, converted to meters, with correction for percision
        //the value was taken from the calculator by measuring a distance the robot drove and dividing it by the distance the encoder showed
        //works surprisingly well, but needs further tweaking
        
        chassisEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance);
        chassisEncoder.reset();
        
        LiveWindow.addActuator("chassis motors", "leftMotors1", leftMotors.CIMS);
        LiveWindow.addActuator("chassis motors", "leftMotorsMiniCim", leftMotors.miniCIM);
        
        LiveWindow.addActuator("chassis motors", "rightMotors1", rightMotors.CIMS);
        LiveWindow.addActuator("chassis motors", "rightMotorsMiniCim", rightMotors.miniCIM);
        
	//LiveWindow.addSensor("chassis sensors", "chassis encoder", chassisEncoder);
        
        gyro = new Gyro(
                RobotConstants.getInt("CHASSIS_GYRO_CHANNEL"));
        gyro.setSensitivity(0.007);
        LiveWindow.addSensor("chassis sensors", "Gyro", gyro);
     
        /*
         * Gripper Initialization
         */
        rollerControllerRight = new Talon(
                RobotConstants.getInt("ROLLER_CONTROLLER_RIGHT_CHANNEL"));
        
        rollerControllerLeft = new Talon( 
                RobotConstants.getInt("ROLLER_CONTROLLER_LEFT_CHANNEL"));
        
        LiveWindow.addActuator("roller", "right", rollerControllerRight);
        LiveWindow.addActuator("roller", "left", rollerControllerLeft);
        
        moveGripperSolenoid = new DoubleSolenoid (
                RobotConstants.getInt("MOVE_GRIPPER_FORWARD"),
                RobotConstants.getInt("MOVE_GRIPPER_REVERSE"));
        
        gripperClawSolenoid = new DoubleSolenoid(
                RobotConstants.getInt("OPEN_GRIPPER_FORWARD"),
                RobotConstants.getInt("OPEN_GRIPPER_REVERSE"));
        
        defenderSolenoid = new DoubleSolenoid(
                RobotConstants.getInt("DEFENDER_CHANNEL_FORWARD"), 
                RobotConstants.getInt("DEFENDER_CHANNEL_REVERSE"));
        
       
        
        LiveWindow.addActuator("gripper", "moveBody", moveGripperSolenoid);
        LiveWindow.addActuator("gripper", "claw", gripperClawSolenoid);
        LiveWindow.addActuator("defender", "defenderClose", defenderSolenoid);

        
        gripperMSLeft = new DigitalInput(
                RobotConstants.getInt("BALL_IN_LEFT_MS_CHANNEL"));
        gripperMSRight = new DigitalInput(
                RobotConstants.getInt("BALL_IN_RIGHT_MS_CHANNEL")); 
        gripperMSMiddle = new DigitalInput(
                RobotConstants.getInt("BALL_IN_MIDDLE_MS_CHANNEL")); 
        
        LiveWindow.addSensor("gripper", "MS l", gripperMSLeft);
        LiveWindow.addSensor("gripper", "MS r", gripperMSRight);
        LiveWindow.addSensor("gripper", "MS m", gripperMSMiddle);
        /*
         * Kicker Initialization
         */
        kickerLeftCIM = new Talon(
                RobotConstants.getInt("KICKER_LEFT_CIM_CHANNEL"));
        kickerLeftMiniCIM = new Talon(
                RobotConstants.getInt("KICKER_LEFT_MINICIM_CHANNEL"));
        kickerRightCIM = new Talon(
                RobotConstants.getInt("KICKER_RIGHT_CIM_CHANNEL"));
        kickerRightMiniCIM = new Talon( 
                RobotConstants.getInt("KICKER_RIGHT_MINICIM_CHANNEL"));

        LiveWindow.addActuator("kicker", "leftCim", kickerLeftCIM);
        LiveWindow.addActuator("kicker", "leftMiniCim", kickerLeftMiniCIM);
        LiveWindow.addActuator("kicker", "rightCim", kickerRightCIM);
        LiveWindow.addActuator("kicker", "rightMiniCim", kickerRightMiniCIM);
        
        ballKeeperSolenoid = new DoubleSolenoid(RobotConstants.getInt("12V_PCM_PORT"), RobotConstants.getInt("BALL_KEEPER_CLOSE_CHANNEL") , RobotConstants.getInt("BALL_KEEPER_OPEN_CHANNEL")); 
        
        
        kickerRestHE = new DigitalInput(
                RobotConstants.getInt("KICKER_REST_MS"));
        
        LiveWindow.addSensor("kicker", "restMS", kickerRestHE);
        
        kickerEncoder = new Encoder(
                RobotConstants.getInt("KICKER_ENCODER_A_CHANNEL"),
                
                RobotConstants.getInt("KICKER_ENCODER_B_CHANNEL"),
                false, CounterBase.EncodingType.k1X);
        kickerEncoder.setDistancePerPulse(360.0/1024.0);
        kickerEncoder.reset();
        
        LiveWindow.addSensor("gripper", "Ball Keeper", ballKeeperSolenoid);
        ballIRSensor = new AnalogInput(RobotConstants.getInt("BALL_IR_CHANNEL"));
        testIRSensor = new AnalogInput(2);
        
    }   
    
    public static boolean getRestSwitch()
    {
        return !kickerRestHE.get();
    }
}
