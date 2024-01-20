package frc.robot.Drive;

import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
import frc.robot.Util.Getter;
import frc.robot.Util.Vector2;

/**
 * PositionedDrive extends the base Drive class to incorporate positioning.
 * It tracks the x, y position and angle of the robot based on the movement of
 * its swerve modules.
 */
public class PositionedDrive extends Drive {
    private double x = 0;
    private double y = 0;
    private double angle = 90;

    public void forceSetAngle(double ang) {
        angle = ang;
    }

    public void forceSetPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    private double[][] lastWheelPositions = new double[4][2];

    public void reset() {
        updateLastWheelPositions();
        x = 0;
        y = 0;
        angle = 90;
    }

    public PositionedDrive(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft,
            SwerveModulePD backRight, double widthInches, double lengthInches) {
        super(frontLeft, frontRight, backLeft, backRight, widthInches, lengthInches);
        updateLastWheelPositions();
    }

    // used to get the amount each wheel has moved each tick
    public void updateLastWheelPositions() {
        lastWheelPositions[0] = new double[] { frontRight.getAngle(), frontRight.getDist() };
        lastWheelPositions[1] = new double[] { frontLeft.getAngle(), frontLeft.getDist() };
        lastWheelPositions[2] = new double[] { backLeft.getAngle(), backLeft.getDist() };
        lastWheelPositions[3] = new double[] { backRight.getAngle(), backRight.getDist() };
    }

    DeSpam dSpam = new DeSpam(0.3);

    @Override
    public void tick(double dTime) {
        // Calculate the distance each wheel has traveled since the last update.
        // double frontRightDist = lastWheelPositions[0][1] - frontRight.getDist();
        // double frontLeftDist = lastWheelPositions[1][1] - frontLeft.getDist();
        // double backLeftDist = lastWheelPositions[2][1] - backLeft.getDist();
        // double backRightDist = lastWheelPositions[3][1] - backRight.getDist();

        double frontRightDist = lastWheelPositions[0][1];
        double frontLeftDist = lastWheelPositions[1][1];
        double backLeftDist = lastWheelPositions[2][1];
        double backRightDist = lastWheelPositions[3][1];

        // Calculate the contribution of each wheel to the robot's rotation.
        // double frontRightTurn = getTurnVec(1)
        // .dotProduct(Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist));
        // double frontLeftTurn =
        // getTurnVec(2).dotProduct(Vector2.fromAngleAndMag(frontLeft.getAngle(),
        // frontLeftDist));
        // double backLeftTurn =
        // getTurnVec(3).dotProduct(Vector2.fromAngleAndMag(backLeft.getAngle(),
        // backLeftDist));
        // double backRightTurn =
        // getTurnVec(4).dotProduct(Vector2.fromAngleAndMag(backRight.getAngle(),
        // backRightDist));

        // Average the wheel turns to get the robot's rotation.
        // double turnInches = (frontRightTurn + frontLeftTurn + backLeftTurn +
        // backRightTurn) / 4;
        // double turnDegrees = turnInches / (circumferenceInches) * 360.0;

        // Calculate the robot's movement in inches based on the wheels' contributions.
        Vector2 driveInchesRobot = Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist)
                .add(Vector2.fromAngleAndMag(frontLeft.getAngle(), frontLeftDist))
                .add(Vector2.fromAngleAndMag(backLeft.getAngle(), backLeftDist))
                .add(Vector2.fromAngleAndMag(backRight.getAngle(), backRightDist))
                .multiply(0.25);

        Vector2 driveInches = driveInchesRobot.rotate(-1 * AngleMath.toTurnAngle(-angle));

        x += driveInches.x;
        y += driveInches.y;
        // angle += turnDegrees;

        updateLastWheelPositions();

        super.tick(dTime);
    };
}
