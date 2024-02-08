package frc.robot.Drive;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Util.AngleMath;
import frc.robot.Util.MotionController;

/**
 * Represents a swerve module, which is part of a swerve drive system.
 * Each module has two motors: one for turning and one for driving (going).
 */
public class SwerveModule {
    private Falcon turn; // Motor responsible for turning the module.
    private Falcon go; // Motor responsible for driving the module forward.

    /**
     * Constructs a SwerveModule with specified motors for turning and driving.
     * 
     * @param turn The motor used to turn the module.
     * @param go   The motor used to drive the module.
     */
    public SwerveModule(Falcon turn, Falcon go, MotionController goController) {
        this.turn = turn;
        this.go = go;

        go.setVelocityPD(goController);

        // Set current limits on the motors to protect them from drawing too much power.
        turn.setCurrentLimit(35); // The current limit for the turning motor.
        go.setCurrentLimit(52); // The current limit for the driving motor.

        // Reset the encoders on both motors, establishing the current position as the
        // zero point.
        turn.resetEncoder();
        go.resetEncoder();
    }

    /**
     * Sets the voltage for the driving motor.
     * 
     * @param voltage The voltage to be applied to the driving motor.
     */
    public void setGoVoltage(double voltage) {
        go.setVoltage(voltage);
    }

    /**
     * sets go motor velocity in in/sec
     */
    public void setGoVelocity(double velocity) {
        go.setVelocity(velocity * inchesPerRotation);
    }

    /**
     * Sets the voltage for the turning motor.
     * 
     * @param voltage The voltage to be applied to the turning motor.
     */
    public void setTurnVoltage(double voltage) {
        turn.setVoltage(voltage);
    }

    /**
     * Resets the encoder reading for the turning motor.
     */
    public void resetTurnReading() {
        turn.resetEncoder();
    }

    /**
     * Gets the raw, unnormalized turn encoder reading.
     * 
     * @return The raw turn encoder value.
     */
    public double getUnconformedTurnReading() {
        return turn.getDegrees() / 12.8; // Convert encoder ticks to degrees.
    }

    /**
     * Gets the normalized angle of the module's turn.
     * 
     * @return The turn angle normalized to the range (-180, 180].
     */
    public double getTurnReading() {
        // Normalize the angle to be within the range (-180, 180].
        return AngleMath.conformAngle(getUnconformedTurnReading());
    }

    /**
     * Resets the encoder reading for the driving motor.
     */
    public void resetGoReading() {
        go.resetEncoder();
    }

    final double inchesPerRotation = 6.75 * (3.82 * Math.PI);

    /**
     * Gets the distance the module has traveled in inches.
     * 
     * @return The distance traveled by the driving motor, adjusted for wheel
     *         rotation.
     */
    public double getGoReading() {
        // Calculate the compensation for wheel turns based on the turn angle.
        final double turnCompensation = 3.75 * (getUnconformedTurnReading() / 360.0);
        // Convert the encoder reading to inches traveled.
        return turnCompensation + (go.getDegrees() / 360.0) / inchesPerRotation;
        // Note: 6.75 motor rotations per 1 wheel rotation, wheel diameter is 3.82
        // inches.
    }
}
