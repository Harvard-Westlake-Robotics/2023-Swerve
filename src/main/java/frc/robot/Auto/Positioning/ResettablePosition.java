package frc.robot.Auto.Positioning;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.Util.AngleMath;
import frc.robot.Util.Vector2;

public class ResettablePosition {
    FieldPositioning positioner;

    Position zeroPosition = new Position(0, new Vector2(0, 0));

    public ResettablePosition(FieldPositioning positioner) {
        this.positioner = positioner;
    }

    public void reset(Pose2d newP) {
        zeroPosition = new Position(newP.getRotation().getDegrees(),
                new Vector2(newP.getTranslation().getX(), newP.getTranslation().getY()));
    }

    public Pose2d getPosition() {
        Vector2 currentTranslation = positioner.getPosition().minus(zeroPosition.position);
        double rotation = AngleMath.getDelta(zeroPosition.angle, positioner.getTurnAngle());

        return new Pose2d(new Translation2d(currentTranslation.x, currentTranslation.y),
                new Rotation2d(rotation * Math.PI / 180));
    }
}
