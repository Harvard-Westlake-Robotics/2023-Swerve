package frc.robot.Auto.Positioning;

import frc.robot.Util.Vector2;

public interface PositioningSystem {
    double getAngle();
    Vector2 getPosition();
    Vector2 getSpeed();
    Vector2 getAcceleration();
    void zero();
}