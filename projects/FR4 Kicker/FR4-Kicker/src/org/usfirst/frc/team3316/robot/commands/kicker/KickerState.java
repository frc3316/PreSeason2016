/*
 * Class for representing the kicker's current state so it will not be disgusting with random ints
 */
package org.usfirst.frc.team3316.robot.commands.kicker;

public class KickerState {
    public static class State { 
        public final int value;
        public final String name;

        private State(int value, String name) {
            this.value = value;
            this.name = name;
        }       
        
        public boolean equals(State other)
        {
            return this.value == other.value;
        }

    }
    
    public static final State Off = new State(0, "Off");
    public static final State Raising = new State(1, "Raising");
    public static final State Resting = new State(2, "InRest");
    public static final State Shaken = new State(3, "Shaken");
    public static final State Kicking = new State(4, "Kick");
    public static final State Braking = new State(5, "Brake");
    public static final State Zeroing = new State(6, "Zero");
} 
