package frc.robot.Devices;

import frc.robot.Util.MathPlus;

/**
 * AnyMotor is an abstract base class representing a motor with basic
 * functionalities.
 * This class should be extended to implement specific types of motors.
 */
public abstract class AnyMotor {
    Double maxSlew; // Maximum rate of voltage change over time.
    boolean isReversed; // Flag indicating whether the motor's direction is reversed.

    /**
     * Gets the unique identifier of the motor.
     * 
     * @return The motor's ID.
     */
    public abstract int getID();

    /**
     * Constructs an AnyMotor with specified reversal status and slew rate.
     * 
     * @param isReversed Whether the motor direction is reversed.
     * @param maxSlew    The maximum slew rate for the motor.
     */
    public AnyMotor(boolean isReversed, double maxSlew) {
        this.maxSlew = maxSlew;
        this.isReversed = isReversed;
    }

    /**
     * Constructs an AnyMotor with specified reversal status.
     * 
     * @param isReversed Whether the motor direction is reversed.
     */
    public AnyMotor(boolean isReversed) {
        this.isReversed = isReversed;
        maxSlew = null;
    }

    /**
     * Sets the current limit of the motor.
     * 
     * @param amps The current limit in amperes.
     */
    public abstract void setCurrentLimit(int amps);

    /**
     * Internal method to set the voltage of the motor.
     * 
     * @param voltage The voltage to be set.
     */
    protected abstract void uSetVoltage(double voltage);

    /**
     * Internal method to get the revolutions of the motor.
     * 
     * @return The number of revolutions.
     */
    protected abstract double uGetRevs();

    double resetPos = 0; // The position of the encoder when last reset.

    /**
     * Resets the motor encoder to zero.
     */
    public void resetEncoder() {
        resetPos = uGetRevs();
    }

    /**
     * Gets the number of revolutions since the last reset, accounting for motor
     * direction.
     * 
     * @return The number of revolutions.
     */
    public double getRevs() {
        var pos = uGetRevs() - resetPos;
        return (isReversed ? -pos : pos);
    }

    /**
     * Converts revolutions to degrees.
     * 
     * @return The number of degrees rotated since the last reset.
     */
    public double getDegrees() {
        return getRevs() * 360.0;
    }

    /**
     * Converts revolutions to radians.
     * 
     * @return The number of radians rotated since the last reset.
     */
    public double getRadians() {
        return getRevs() * 2.0 * Math.PI;
    }

    /**
     * Stops the motor by setting the voltage to zero.
     */
    public abstract void stop();

    /**
     * Sets the voltage applied to the motor, respecting the motor direction.
     * 
     * @param volts The voltage to be applied.
     */
    public void setVoltage(double volts) {
        if (Math.abs(volts) > 12.0)
            uSetVoltage(volts > 0 ? MathPlus.boolFac(!isReversed) : MathPlus.boolFac(isReversed));
        // In a real scenario, you might want to throw an error or cap the voltage
        volts = Math.max(volts, -2);
        volts = Math.min(volts, 2);
        uSetVoltage(isReversed ? -volts : volts);
    }

    /**
     * Sets the voltage as a percentage of the maximum voltage.
     * 
     * @param percent The percentage of the maximum voltage to apply.
     */
    public void setPercentVoltage(double percent) {
        setVoltage(percent * (12.0 / 100.0));
    }

    /**
     * The tick method that updates the motor state, meant to be overridden if
     * needed.
     * 
     * @param dTime The time delta since the last tick.
     */
    public void tick(double dTime) {
        // Implementation would go here for motor updates each control cycle.
    }
}
