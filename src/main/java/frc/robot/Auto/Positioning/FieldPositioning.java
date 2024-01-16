package frc.robot.Auto.Positioning;

import frc.robot.Core.ScheduledComponent;
// Importing necessary classes and interfaces.
import frc.robot.Devices.Imu;
import frc.robot.Devices.LimeLight;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.DeSpam;
import frc.robot.Util.Vector2;

// ImuDrivePositioning integrates IMU and drive data to implement a positioning system for the robot.
public class FieldPositioning extends ScheduledComponent implements PositioningSystem {
    ImuDrivePositioning imuDrivePositioning;

    public FieldPositioning(PositionedDrive drive, Imu imu, double assumedStartRotation) {
        imuDrivePositioning = new ImuDrivePositioning(imu, drive);
        imuDrivePositioning.setAngle(assumedStartRotation);
    }

    @Override
    public double getTurnAngle() {
        return imuDrivePositioning.getTurnAngle();
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

    DeSpam dSpam = new DeSpam(0.3);

    @Override
    protected void tick(double dTime) {
        final double x = LimeLight.getRobotX();
        final double y = LimeLight.getRobotY();
        final double angle = LimeLight.getRobotYaw();
        if (LimeLight.foundTarget() && !((x == 0.0) && (y == 0.0))) {
            imuDrivePositioning.setAngle(angle);
            // dSpam.exec(() -> {
            // System.out.println("limelight x, y: " + LimeLight.getRobotX() + ", " +
            // LimeLight.getRobotY());
            // });
            imuDrivePositioning.setPosition(LimeLight.getRobotX(), LimeLight.getRobotY());
        }
    }

    @Override
    protected void cleanUp() {

    }
}