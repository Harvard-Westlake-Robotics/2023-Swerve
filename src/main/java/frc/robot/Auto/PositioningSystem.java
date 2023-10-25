package frc.robot.Auto;

import frc.robot.Auto.Drive.RobotPosition;
import frc.robot.Util.Vector2;

public interface PositioningSystem {
    double getAngle();
    Vector2 getPosition();
    Vector2 getSpeed();
    Vector2 getAcceleration();
}