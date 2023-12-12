package frc.robot.Devices;

import com.ctre.phoenixpro.hardware.Pigeon2;

/**
 * The Imu class encapsulates the Pigeon2 Inertial Measurement Unit (IMU) sensor functionality.
 * It provides methods to access the yaw, pitch, and roll values of the robot's orientation.
 */
public class Imu {
    private Pigeon2 imu; // The Pigeon2 IMU sensor.

    /**
     * Constructor for the Imu class.
     * @param port The CAN port where the IMU is connected.
     */
    public Imu(int port) {
        this.imu = new Pigeon2(port); // Initialize the Pigeon2 IMU on the specified port.

        var config = imu.getConfigurator(); // Obtain the configuration object for the IMU.
        
        // The following commented lines show how the IMU's configuration can be set to defaults
        // and how to set the mounting orientation of the IMU.
        // Uncomment these as needed for your specific robot setup.

        // config.configFactoryDefault(); // Reset the configuration to factory defaults.
        // config.configMountPoseYaw(90); // Set the yaw (Z-axis) orientation offset.
        // config.configMountPosePitch(0); // Set the pitch (Y-axis) orientation offset.
        // config.configMountPoseRoll(0); // Set the roll (X-axis) orientation offset.
        config.setYaw(90); // Directly set the current yaw position to 90 degrees.
        // config.configEnableCompass(false); // Disable the compass if not used.
    }

    /**
     * Gets the robot's current yaw (rotation around the vertical axis).
     * @return The current yaw angle in degrees.
     */
    public double getTurnAngle() {
        return imu.getYaw().getValue(); // Retrieve the yaw value from the IMU sensor.
    }

    /**
     * Gets the robot's current pitch (rotation around the lateral axis).
     * @return The current pitch angle in degrees.
     */
    public double getPitch() {
        return imu.getPitch().getValue(); // Retrieve the pitch value from the IMU sensor.
    }
    
    /**
     * Gets the robot's current roll (rotation around the longitudinal axis).
     * @return The current roll angle in degrees.
     */
    public double getRoll() {
        return imu.getRoll().getValue(); // Retrieve the roll value from the IMU sensor.
    }

    /**
     * Resets the yaw angle to zero. This can be used to recalibrate the robot's heading.
     */
    public void resetYaw() {
        imu.setYaw(0); // Reset the yaw angle to 0 degrees.
    }

    public void setYaw(double val) {
        imu.setYaw(val);
    }
}
