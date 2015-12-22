/*
 * Class for reading and exporting the constants in the 
 * const.txt file on the cRio for use in the rest of the code
 */
package org.usfirst.frc.team3316.robot;

import java.util.Hashtable;

public class RobotConstants 
{
    private static Hashtable<String, Double> constants;
     
    static 
    {
        constants = new Hashtable<String, Double>();
        addDefaults();
    }
    
    private static void addDefaults()
    {
        add("ErrorCode", 3316);
        
        add("12V_PCM_PORT", 1);
        add("24V_PCM_PORT", 0);
        
        // OI
        add("JOYSTICK_RIGHT_CHANNEL", 1);
        add("JOYSTICK_LEFT_CHANNEL", 0);
        add("JOYSTICK_OPERATOR_CHANNEL", 2);

        //Buttons
        add("OPEN_GRIPPER_BUTTON", 5);
        add("CLOSE_GRIPPER_BUTTON", 4);
        add("EXTEND_GRIPPER_BUTTON", 1);
        add("RETRACT_GRIPPER_BUTTON", 2);
        
        add("COLLECT_BALL_BUTTON", 10);
        add("CATCH_BALL_BUTTON", 11);
        
        add("ROLLER_IN_BUTTON", 8);
        add("ROLLER_OUT_BUTTON", 9);
        
        add("KICK_BUTTON", 7);
        add("KICKER_TO_REST_BUTTON", 3);
        
        add("LOW_GEAR_BUTTON", 2);
        add("HIGH_GEAR_BUTTON", 2);
        
        //Vision
        add("H_MIN", 105);
        add("H_MAX", 155);
        add("S_MIN", 198);
        add("S_MAX", 255);
        add("V_MIN", 94);
        add("V_MAX", 156);
        
        add("AREA_MIN", 45);
        add("MAX_PARTICLE_NUM", 4);
        add("OFFSET", .15);
        add("VIEW_ANGLE_Y", 47.37973796786812472218613677598);

                
   ////RobotMap - Electronics Wiring////
        
        //General
        
        
        //Shifter
        add("SHIFTER_SOLENOID_FORWARD", 3);
        add("SHIFTER_SOLENOID_REVERSE", 2);
        
        //Chassis
        add("LEFT_CIMS_CHANNEL", 1);
        add("LEFT_MINICIM_CHANNEL", 8);
        
        add("RIGHT_CIMS_CHANNEL", 0);
        add("RIGHT_MINICIM_CHANNEL", 3);
        
        add("CHASSIS_ENCODER_CHANNEL_A", 5);
        add("CHASSIS_ENCODER_CHANNEL_B", 4);

        add("CHASSIS_GYRO_CHANNEL", 0);
        
        //Gripper
        
        
        add("ROLLER_CONTROLLER_RIGHT_CHANNEL", 2);
        add("ROLLER_CONTROLLER_LEFT_CHANNEL", 7);
        
        add("MOVE_GRIPPER_FORWARD", 5);
        add("MOVE_GRIPPER_REVERSE", 7);
        
        add("OPEN_GRIPPER_FORWARD", 1);
        add("OPEN_GRIPPER_REVERSE", 0); 
        
        add("DEFENDER_CHANNEL_FORWARD", 6);
        add("DEFENDER_CHANNEL_REVERSE", 4);

        add("BALL_IN_RIGHT_MS_CHANNEL", 1);
        add("BALL_IN_LEFT_MS_CHANNEL", 2);        
        add("BALL_IN_MIDDLE_MS_CHANNEL",3);
        add("BALL_IR_CHANNEL", 1);

        //Kicker
        add("KICKER_LEFT_CIM_CHANNEL", 6);
        add("KICKER_LEFT_MINICIM_CHANNEL",9);

        add("KICKER_RIGHT_CIM_CHANNEL", 4);
        add("KICKER_RIGHT_MINICIM_CHANNEL", 5);

        add("BALL_KEEPER_CLOSE_CHANNEL", 0);
        add("BALL_KEEPER_OPEN_CHANNEL", 1);


        add("KICKER_REST_MS", 0);

        add("KICKER_ENCODER_A_CHANNEL", 6);
        add("KICKER_ENCODER_B_CHANNEL", 7);
        //Chassis
        add("MINICIM_SCALE", 1);
                

        ////Kicker////
        
        //Kick
        
        add("KICKER_NUM_CYCLES", 5);
        add("KICK_TIMEOUT", 0.75);
        add("POST_KICK_TIMEOUT", 1);
        add("KICKER_VELOCITY", 1);
         
        
        
        //MoveKickerToRest
        add("KICKER_TO_REST_VELOCITY", -0.3316);
        add("KICKER_TO_REST_TIMEOUT", 0.75);
        
        add("SHAKEN_KICKER_VELOCITY", -0.3316);
        add("SHAKEN_KICKER_COUNTER", 6);
        
        
        //BrakeKicker
        add("KICKER_BRAKE_TIMOUT", 0.15);
        add("KICKER_BRAKE_VELOCITY", -0.3);
       
        //ZeroKicker
        add("ZERO_KICKER_TIMEOUT", 0.8);
        add("ZERO_KICKER_VELOCITY", 0.25);
        
        ////Roller////
        
        add("ROLLER_COLLECTION_VELOCITY", -0.82);
        add("ROLLER_EJECTION_VELOCITY", 0.82);
        //CollectBall
        add("BALL_COLLECT_DELAY", 0.9);
        //EjectBall
        add("BALL_EJECT_DELAY", 1);
               
        ////Auton////
        add("SHOOTING_DISTANCE", 4);
        add("DEFUALT_AUTON_DRIVE_DISTANCE", 3.1);
    }
    
    
    public static void add(String name, double value)
    {
        Double valueToPut = Double.valueOf(value);
        constants.put(name, valueToPut);
    }
        
    public static double get(String name)
    {
	double x = ((Double)constants.get("ErrorCode")).doubleValue();
	if(constants.containsKey(name))
		x = ((Double)(constants.get(name))).doubleValue();
        else
            System.out.println("error with: " + name);
      	return x;
    }
    
    public static int getInt(String name)
    {
      	return (int)get(name);
    }
}

 