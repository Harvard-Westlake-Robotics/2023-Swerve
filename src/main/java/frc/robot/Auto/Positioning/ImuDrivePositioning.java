package frc.robot.Auto.Positioning;

import frc.robot.Devices.Imu;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.PositionHistory;
import frc.robot.Util.Tickable;
import frc.robot.Util.Vector2;

public class ImuDrivePositioning implements PositioningSystem, Tickable {
    Imu imu;
    PositionedDrive drive;
    PositionHistory angle = new PositionHistory();
    PositionHistory x = new PositionHistory();
    PositionHistory y = new PositionHistory();

    public ImuDrivePositioning(Imu imu, PositionedDrive drive) {
        this.imu = imu;
        this.drive = drive;
    }

    @Override
    public double getAngle() {
        return imu.getTurnAngle();
    }

    @Override
    public Vector2 getPosition() {
        return drive.getPosition();
    }

    @Override
    public Vector2 getSpeed() {
        return new Vector2(x.getSpeed(), y.getSpeed());
    }

    @Override
    public Vector2 getAcceleration() {
        return new Vector2(x.getAcceleration());
    }

    @Override
    public void tick(double secsSinceLastTick) {
        x.addPos(getPosition().x);
        y.addPos(getPosition().y);
        angle.addPos(getAngle());
    }

    @Override
    public void zero() {
        x.reset();
        y.reset();
        angle.reset();
    }

}
