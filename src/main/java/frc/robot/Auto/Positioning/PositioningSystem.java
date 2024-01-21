package frc.robot.Auto.Positioning;

import frc.robot.Util.Vector2;

public interface PositioningSystem {
    double getTurnAngle();

    Vector2 getPosition();
}
