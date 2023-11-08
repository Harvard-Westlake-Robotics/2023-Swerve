package frc.robot.Components;

import frc.robot.Devices.AnyMotor;

// The Intake class manages the intake mechanism of a robot which includes controlling the angler motor and the intake motor.
public class Intake {
    AnyMotor angler; // Motor that adjusts the angle of the intake mechanism
    AnyMotor intake; // Motor that powers the actual intake mechanism
    double wristTarget = 0; // Target position for the wrist/angler, not used in this snippet

    // Constructor for the Intake class. It initializes the motors and sets current limits for the intake motor.
    public Intake(AnyMotor angler, AnyMotor intake) {
        this.angler = angler; // Assign the angler motor
        this.intake = intake; // Assign the intake motor
        intake.setCurrentLimit(30); // Set a current limit of 30 amps on the intake motor to protect it from high current draw
    }

    // Method to set the voltage for the intake motor.
    public void setIntakeVolage(double voltage) {
        intake.setVoltage(voltage); // Apply the given voltage to the intake motor
    }

    // Method to reset the encoder that tracks the position of the angler motor.
    public void resetAnglerEncoder() {
        angler.resetEncoder(); // Reset the angler's encoder value to zero
    }

    // Method to set the voltage for the angler motor.
    public void setAnglerVoltage(double voltage) {
        angler.setVoltage(voltage); // Apply the given voltage to the angler motor
    }

    // Method to get the current position of the angler in degrees.
    public double getAnglerPositionDeg() {
        // The method calculates the angle by converting the revolutions of the motor into degrees.
        // The conversion factor (90.0 / 13.63) relates the specific gearing or pulley system of the robot's angler mechanism.
        // The '- 50' is an offset that might represent a zeroing value for the angler's initial position.
        return angler.getRevs() * (90.0 / 13.63) - 50;
    }
}
