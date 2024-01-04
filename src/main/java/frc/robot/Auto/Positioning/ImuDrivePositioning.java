package frc.robot.Auto.Positioning;

import frc.robot.Core.ScheduledComponent;
// Importing necessary classes and interfaces.
import frc.robot.Devices.Imu;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.PositionHistory;
import frc.robot.Util.Vector2;

// ImuDrivePositioning integrates IMU and drive data to implement a positioning system for the robot.
public class ImuDrivePositioning extends ScheduledComponent implements PositioningSystem {
    Imu imu; // Inertial Measurement Unit for obtaining orientation data
    PositionedDrive drive; // Drive system that likely includes methods for position tracking
    PositionHistory angle = new PositionHistory(); // History of angle data for calculating speed/acceleration
    PositionHistory x = new PositionHistory(); // History of x position data
    PositionHistory y = new PositionHistory(); // History of y position data

    // Constructor initializes the IMU and positioned drive system.
    public ImuDrivePositioning(Imu imu, PositionedDrive drive) {
        this.imu = imu;
        this.drive = drive;
    }

    // Returns the current orientation angle from the IMU.
    @Override
    public double getAngle() {
        return imu.getTurnAngle();
    }

    // Returns the current position from the drive system as a 2D vector.
    @Override
    public Vector2 getPosition() {
        return drive.getPosition();
    }

    // Calculates and returns the current speed as a 2D vector using the position
    // history.
    @Override
    public Vector2 getVelocity() {
        return new Vector2(x.getSpeed(), y.getSpeed());
    }

    // Calculates and returns the current acceleration as a 2D vector using the
    // position history.
    // Note: It seems there might be a mistake here as it only uses the
    // x.getAcceleration() for both vector components.
    @Override
    public Vector2 getAcceleration() {
        return new Vector2(x.getAcceleration(), y.getAcceleration()); // Corrected to use y.getAcceleration() for the y
                                                                      // component
    }

    // The tick method is called periodically and updates the position and angle
    // histories with the latest data.
    protected void tick(double secsSinceLastTick) {
        x.addPos(getPosition().x); // Adds the current x position to the history
        y.addPos(getPosition().y); // Adds the current y position to the history
        angle.addPos(getAngle()); // Adds the current angle to the history
        drive.forceSetAngle(getAngle());
    }

    protected void cleanUp() {
    }

    public void setAngle(double angle) {
        drive.forceSetAngle(angle);
        this.angle.reset();
    }

    public void setPosition(double x, double y) {
        drive.forceSetPosition(x, y);
        this.x.reset();
        this.y.reset();
    }
}