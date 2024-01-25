package frc.robot;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.robot.Core.Schedulable;
import frc.robot.Devices.LimeLight;

public class BotPosePrediction extends Schedulable {
    SwerveDrivePoseEstimator poseEstimator;
    SwerveModulePosition[] modulePositions;
    SwerveDriveKinematics kinematics;
    LimeLight limeLight;

    public BotPosePrediction(double imuAngle, LimeLight limeLight) {
        Translation2d[] wheelsMeters = new Translation2d[] { new Translation2d(0.26035, 0.26035),
                new Translation2d(-0.26035, 0.26035), new Translation2d(-0.26035, -0.26035),
                new Translation2d(0.26035, -0.26035) };
        kinematics = new SwerveDriveKinematics(wheelsMeters);
        modulePositions = new SwerveModulePosition[] { new SwerveModulePosition(11,
                new Rotation2d(0)),
                new SwerveModulePosition(11, new Rotation2d(0)), new SwerveModulePosition(11,
                        new Rotation2d(0)),
                new SwerveModulePosition(11, new Rotation2d(0)) };
        poseEstimator = new SwerveDrivePoseEstimator(kinematics, new Rotation2d(imuAngle), modulePositions,
                new Pose2d(0.0, 0.0, new Rotation2d(0)));
        this.limeLight = limeLight;
    }

    // Returns predicted robot position in [x, y, angle]
    public double[] predictPosition() {
        Pose2d position = poseEstimator.getEstimatedPosition();
        double[] positionList = new double[] { position.getX(), position.getY(),
                position.getRotation().getDegrees() };
        return positionList;
    }

    public void start() {

    }

    public void tick(double dTime) {
        long receiveTimeSinceEpoch = limeLight.getLastReceiveTime();
        double latencyImageTakenToNow = limeLight.getRobotLatency() / 1000;
        if (limeLight.botPoseChanged()) {
            poseEstimator.addVisionMeasurement(
                    new Pose2d(limeLight.getRobotX(), limeLight.getRobotY(), new Rotation2d(limeLight.getRobotYaw())),
                    receiveTimeSinceEpoch - latencyImageTakenToNow);
        }
    }

    public void end() {

    }

}
