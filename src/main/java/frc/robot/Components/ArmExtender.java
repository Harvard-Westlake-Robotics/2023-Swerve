package frc.robot.Components;

// Importing the AnyMotor class, which likely abstracts the motor functionality.
import frc.robot.Devices.AnyMotor;

// ArmExtender class manages the extension mechanism of a robot arm, controlling two motors.
public class ArmExtender {
    AnyMotor left;  // The motor on the left side of the arm extender
    AnyMotor right; // The motor on the right side of the arm extender

    // Constructor for the ArmExtender class.
    // Initializes the left and right motors for the arm extender.
    public ArmExtender(AnyMotor left, AnyMotor right) {
        this.left = left;   // Assign the left motor
        this.right = right; // Assign the right motor
    }

    // Sets the voltage for both left and right motors, with a safety check for retraction.
    public void setVoltage(double voltage) {
        // If the voltage is negative (which would retract the extender) and the current degree of extension
        // is at or below 0, indicating a fully retracted state, prevent further retraction by setting the
        // voltage to 0 to avoid damaging the mechanism.
        if (voltage < 0 && left.getDegrees() <= 0) {
            setVoltage(0); // Prevents any further retraction
            return;
        }
        // If the above condition is not met, set the given voltage to both motors.
        left.setVoltage(voltage);
        right.setVoltage(voltage);
    }

    // Resets the encoders for both the left and right motors.
    // This is typically used for zeroing the position of the arm extender.
    public void reset() {
        left.resetEncoder();
        right.resetEncoder();
    }

    // Gets the current extension of the arm in inches.
    // This method assumes a specific gear ratio or conversion from revolutions to linear movement.
    public double getExtensionInches() {
        // Converts the number of revolutions from the encoder to inches of extension.
        // The conversion factor (41.0 / 22.79) relates the specific gearing or pulley system of the robot's arm extender.
        return left.getRevs() / 22.79 * 41.0;
    }
}
