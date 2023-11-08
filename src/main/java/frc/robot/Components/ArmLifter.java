package frc.robot.Components;

// Importing the AnyMotor class, which is a generic motor class that the ArmLifter will use.
import frc.robot.Devices.AnyMotor;

// The ArmLifter class is responsible for controlling the robotic arm's lifting mechanism.
public class ArmLifter {
    AnyMotor left;  // Motor on the left side of the arm lifter
    AnyMotor right; // Motor on the right side of the arm lifter

    // Constructor for the ArmLifter class. It initializes the left and right motors and resets their positions.
    public ArmLifter(AnyMotor left, AnyMotor right) {
        this.left = left;   // Assign the left motor
        this.right = right; // Assign the right motor
        resetPos();         // Resets the position of both motors by clearing their encoders
    }

    // Method to apply the same voltage to both the left and right motors of the arm lifter.
    // This synchronizes their movement.
    public void setVoltage(double volts) {
        left.setVoltage(volts);  // Set voltage to the left motor
        right.setVoltage(volts); // Set voltage to the right motor
    }

    // Method to reset the position of the arm lifter by resetting the encoders on both motors.
    public void resetPos() {
        left.resetEncoder();  // Reset the encoder for the left motor
        right.resetEncoder(); // Reset the encoder for the right motor
    }

    // Method to get the current angle of the arm lifter in degrees.
    // This assumes a specific conversion factor between the revolutions of the motor (from the encoder)
    // and the angle of the arm in degrees. The negative sign in the conversion factor indicates that
    // the encoder counts in the opposite direction of the angle increase.
    public double getAngleDeg() {
        return left.getRevs() * (90.0 / -57.0); // Convert motor revolutions to degrees
    }
}
