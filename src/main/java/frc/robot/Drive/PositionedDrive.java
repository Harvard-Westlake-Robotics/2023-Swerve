package frc.robot.Drive;

import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
import frc.robot.Util.Getter;
import frc.robot.Util.Vector2;

/**
 * PositionedDrive extends the base Drive class to incorporate positioning.
 * It tracks the x, y position and angle of the robot based on the movement of its swerve modules.
 */
public class PositionedDrive extends Drive {
    private double x = 0;          // The x-coordinate of the robot on the field.
    private double y = 0;          // The y-coordinate of the robot on the field.
    private double angle = 90;     // The robot's current angle in degrees, starting at 90 degrees.
    Getter<Double> getCurrentAngle;// Functional interface to get the current angle of the robot.

    // Getter for the robot's current angle.
    public double getAngle() {
        return angle;
    }

    // Getter for the robot's current position as a vector.
    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    // Stores the last positions of the wheels to calculate movement delta.
    private double[][] lastWheelPositions = new double[4][2];

    // Resets the robot's position and angle to the starting values.
    public void reset() {
        updateLastWheelPositions(); // Update wheel positions to current readings.
        x = 0;
        y = 0;
        angle = 90; // Start facing directly to the right.
    }

    // Constructor initializes the drive system and the wheel positions.
    public PositionedDrive(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft,
                           SwerveModulePD backRight, double widthInches, double lengthInches, 
                           Getter<Double> getCurrentAngle) {
        super(frontLeft, frontRight, backLeft, backRight, widthInches, lengthInches);
        this.getCurrentAngle = getCurrentAngle;
        updateLastWheelPositions(); // Initialize wheel positions.
    }

    // Updates the last known positions of the swerve module wheels.
    public void updateLastWheelPositions() {
        lastWheelPositions[0] = new double[] { frontRight.getAngle(), frontRight.getDist() };
        lastWheelPositions[1] = new double[] { frontLeft.getAngle(), frontLeft.getDist() };
        lastWheelPositions[2] = new double[] { backLeft.getAngle(), backLeft.getDist() };
        lastWheelPositions[3] = new double[] { backRight.getAngle(), backRight.getDist() };
    }

    DeSpam dSpam = new DeSpam(0.3); // Utility to prevent spamming, not used in the current context.

    // Updates the robot's position and angle based on the swerve modules' movements.
    @Override
    public void tick(double dTime) {
        // Calculate the distance each wheel has traveled since the last update.
        double frontRightDist = lastWheelPositions[0][1] - frontRight.getDist();
        double frontLeftDist = lastWheelPositions[1][1] - frontLeft.getDist();
        double backLeftDist = lastWheelPositions[2][1] - backLeft.getDist();
        double backRightDist = lastWheelPositions[3][1] - backRight.getDist();

        // Calculate the contribution of each wheel to the robot's rotation.
        double frontRightTurn = getTurnVec(1)
                .dotProduct(Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist));
        double frontLeftTurn = getTurnVec(2).dotProduct(Vector2.fromAngleAndMag(frontLeft.getAngle(), frontLeftDist));
        double backLeftTurn = getTurnVec(3).dotProduct(Vector2.fromAngleAndMag(backLeft.getAngle(), backLeftDist));
        double backRightTurn = getTurnVec(4).dotProduct(Vector2.fromAngleAndMag(backRight.getAngle(), backRightDist));

        // Average the wheel turns to get the robot's rotation.
        double turnInches = (frontRightTurn + frontLeftTurn + backLeftTurn + backRightTurn) / 4;
        double turnDegrees = turnInches / (circumferenceInches) * 360.0;

        // Calculate the robot's movement in inches based on the wheels' contributions.
        Vector2 driveInchesRobot = Vector2.fromAngleAndMag(frontRight.getAngle(), frontRightDist)
                .add(Vector2.fromAngleAndMag(frontLeft.getAngle(), frontLeftDist))
                .add(Vector2.fromAngleAndMag(backLeft.getAngle(), backLeftDist))
                .add(Vector2.fromAngleAndMag(backRight.getAngle(), backRightDist))
                .multiply(0.25); // Average of all wheels.

        // Rotate the drive vector based on the current robot angle to get field-oriented movement.
        var driveInches = driveInchesRobot.rotate(-1 * AngleMath.toTurnAngle(-getCurrentAngle.get()));

        // Update the robot's position based on the calculated movement.
        x += driveInches.x;
        y += driveInches.y;
        angle += turnDegrees;

        // Update the last wheel positions for the next iteration.
        updateLastWheelPositions();
        // Call the parent class tick function.
        super.tick(dTime);
    };
}
