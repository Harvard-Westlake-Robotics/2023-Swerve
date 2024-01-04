package frc.robot.Auto.Positioning;

import frc.robot.Components.LimeLight;
import frc.robot.Core.ScheduledComponent;
// Importing necessary classes and interfaces.
import frc.robot.Devices.Imu;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.Vector2;

// ImuDrivePositioning integrates IMU and drive data to implement a positioning system for the robot.
public class LimeLightExtended extends ScheduledComponent implements PositioningSystem {
    ImuDrivePositioning imuDrivePositioning;

    LimeLightExtended(PositionedDrive drive, Imu imu, double assumedStartRotation) {
        imuDrivePositioning = new ImuDrivePositioning(imu, drive);
        imuDrivePositioning.setAngle(assumedStartRotation);
    }

    @Override
    public double getAngle() {
        return imuDrivePositioning.getAngle();
    }

    @Override
    public Vector2 getAcceleration() {
        return imuDrivePositioning.getAcceleration();
    }

    @Override
    public Vector2 getVelocity() {
        return imuDrivePositioning.getVelocity();
    }

    @Override
    public Vector2 getPosition() {
        return imuDrivePositioning.getPosition();
    }

    @Override
    protected void tick(double dTime) {
        if (LimeLight.foundTarget()) {
            imuDrivePositioning.setAngle(LimeLight.getRobotYaw());
            imuDrivePositioning.setPosition(LimeLight.getRobotX(), LimeLight.getRobotY());
        }
    }

    @Override
    protected void cleanUp() {

    }
}