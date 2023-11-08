package frc.robot.Devices.Motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Devices.AnyMotor;

/**
 * The SparkMax class extends the AnyMotor abstract class to provide an interface
 * to control a Spark Max motor controller.
 */
public class SparkMax extends AnyMotor {
    private CANSparkMax maxspark; // The Spark Max motor controller object.
    private RelativeEncoder encoder; // The encoder associated with the motor.

    /**
     * Sets the current limit for the motor.
     * @param amps The maximum current in Amperes.
     */
    public void setCurrentLimit(int amps) {
        maxspark.setSmartCurrentLimit(amps);
    }

    final int id; // Unique identifier for the motor controller.
    
    /**
     * Retrieves the ID of the motor controller.
     * @return The CAN ID of the motor controller.
     */
    public int getID() {
        return id;
    }

    /**
     * Constructor for the SparkMax motor controller.
     * @param canID The CAN ID for the motor controller.
     * @param isReversed Indicates whether the motor output should be reversed.
     */
    public SparkMax(int canID, boolean isReversed) {
        super(isReversed);

        id = canID;

        // Initialize the CANSparkMax motor controller with the given CAN ID and motor type.
        this.maxspark = new CANSparkMax(canID, MotorType.kBrushless);
        this.maxspark.restoreFactoryDefaults(); // Reset settings to factory defaults.
        this.encoder = maxspark.getEncoder(); // Retrieve the encoder object.
        maxspark.setIdleMode(IdleMode.kCoast); // Set the motor to coast when not driven.

        // Set the current limits for the motor controller.
        maxspark.setSmartCurrentLimit(40, 100); // 40A when stalled, 100A under normal operation.
    }

    /**
     * Sets the brake mode for the motor controller.
     * @param brake If true, sets the motor to brake mode; otherwise sets it to coast mode.
     */
    public void setBrake(boolean brake) {
        maxspark.setIdleMode((brake) ? IdleMode.kBrake : IdleMode.kCoast);
    }

    /**
     * Overloaded constructor to set the brake mode during initialization.
     * @param canID The CAN ID for the motor controller.
     * @param isReversed Indicates whether the motor output should be reversed.
     * @param brakeMode If true, initialize the motor in brake mode; otherwise in coast mode.
     */
    public SparkMax(int canID, boolean isReversed, boolean brakeMode) {
        this(canID, isReversed);
        setBrake(brakeMode); // Set the initial brake mode.
    }

    /**
     * Sets the voltage output of the motor.
     * @param volts The desired voltage.
     */
    protected void uSetVoltage(double volts) {
        maxspark.setVoltage(volts);
    }

    /**
     * Retrieves the number of revolutions from the encoder.
     * @return The position of the encoder in revolutions.
     */
    protected double uGetRevs() {
        return encoder.getPosition();
    }

    /**
     * Sets current limits for the motor with different values when stalled or free.
     * @param stall The current limit when the motor is stalled.
     * @param free The current limit when the motor is running freely.
     */
    public void setCurrentLimit (int stall, int free) {
        maxspark.setSmartCurrentLimit(stall, free);
    }

    /**
     * Stops the motor immediately.
     */
    public void stop() {
        maxspark.stopMotor();
    }
}
