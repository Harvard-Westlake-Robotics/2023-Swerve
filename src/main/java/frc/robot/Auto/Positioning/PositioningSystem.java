package frc.robot.Auto.Positioning;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.Util.Vector2;

public interface PositioningSystem {
    double getTurnAngle();

    Vector2 getPosition();

    ChassisSpeeds getRobotRelativeSpeeds();

}
