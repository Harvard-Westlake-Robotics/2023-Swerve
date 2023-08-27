package frc.robot.Drive;

import frc.robot.Util.DeSpam;
import frc.robot.Util.Vector2;

public class PositionedDrive extends Drive {
    private double x = 0;
    private double y = 0;
    private double angle = 90; // deg

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

    public void updateLastWheelPositions() {
        lastWheelPositions[0] = new double[] { frontRight.getAngle(), frontRight.getDist() };
        lastWheelPositions[1] = new double[] { frontLeft.getAngle(), frontLeft.getDist() };
        lastWheelPositions[2] = new double[] { backLeft.getAngle(), backLeft.getDist() };
        lastWheelPositions[3] = new double[] { backRight.getAngle(), backRight.getDist() };
    }

    DeSpam dSpam = new DeSpam(0.3);

    @Override
    public void tick(double dTime) {
        double frontRightDist = lastWheelPositions[0][1] - frontRight.getDist();
        double frontLeftDist = lastWheelPositions[1][1] - frontLeft.getDist();
        double backLeftDist = lastWheelPositions[2][1] - backLeft.getDist();
        double backRightDist = lastWheelPositions[3][1] - backRight.getDist();

        double frontRightTurn = getTurnVec(1)
                .dotProduct(Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist));
        double frontLeftTurn = getTurnVec(2).dotProduct(Vector2.fromAngleAndMag(frontLeft.getAngle(), frontLeftDist));
        double backLeftTurn = getTurnVec(3).dotProduct(Vector2.fromAngleAndMag(backLeft.getAngle(), backLeftDist));
        double backRightTurn = getTurnVec(4).dotProduct(Vector2.fromAngleAndMag(backRight.getAngle(), backRightDist));

        double turnInches = (frontRightTurn + frontLeftTurn + backLeftTurn + backRightTurn) / 4;
        double turnDegrees = turnInches / (circumferenceInches) * 360.0;

        Vector2 driveInchesRobot = Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist)
                .add(Vector2.fromAngleAndMag(frontLeft.getAngle(), frontLeftDist))
                .add(Vector2.fromAngleAndMag(backLeft.getAngle(), backLeftDist))
                .add(Vector2.fromAngleAndMag(backRight.getAngle(), backRightDist))
                .multiply(0.25);

        var driveInches = driveInchesRobot.rotate(-1 * (angle + (turnDegrees / 2))); // -1 because angles are in standard form
        // ^ adds half of the turn to the drive in the tick
        // for avg rotation during the tick
        x += driveInches.x;
        y += driveInches.y;
        angle += turnDegrees;

        updateLastWheelPositions();
        super.tick(dTime);
    };
}
