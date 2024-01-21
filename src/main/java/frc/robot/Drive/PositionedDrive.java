package frc.robot.Drive;

import frc.robot.Util.DeSpam;
import frc.robot.Util.Vector2;

/**
 * PositionedDrive extends the base Drive class to incorporate positioning.
 * It tracks the x, y position and angle of the robot based on the movement of
 * its swerve modules.
 */
public class PositionedDrive extends Drive {
    public Vector2 movementSinceLastTick = new Vector2(0, 0);

    private double[][] lastWheelPositions = new double[4][2];

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
    protected void tick(double dTime) {

        // Calculate the distance each wheel has traveled since the last update.
        double frontRightDist = lastWheelPositions[0][1] - frontRight.getDist();
        double frontLeftDist = lastWheelPositions[1][1] - frontLeft.getDist();
        double backLeftDist = lastWheelPositions[2][1] - backLeft.getDist();
        double backRightDist = lastWheelPositions[3][1] - backRight.getDist();

        // Calculate the robot's movement in inches based on the wheels' contributions.
        movementSinceLastTick = Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist)
                .add(Vector2.fromAngleAndMag(frontLeft.getAngle(), frontLeftDist))
                .add(Vector2.fromAngleAndMag(backLeft.getAngle(), backLeftDist))
                .add(Vector2.fromAngleAndMag(backRight.getAngle(), backRightDist))
                .multiply(0.25);

        updateLastWheelPositions();

        super.tick(dTime);
    };
}
