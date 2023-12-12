package frc.robot.Auto.Drive;

import frc.robot.Auto.Positioning.PositioningSystem;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;
import frc.robot.Util.Vector2;

public class AutonomousDrive implements Tickable {
    PositionedDrive drive; // The drive system of the robot.
    PDController xController; // PID controller for x-axis motion.
    PDController yController; // PID controller for y-axis motion.
    PDController turnController; // PID controller for rotational motion.
    PositioningSystem pos; // Positioning system that provides current robot position and angle.

    public double targetX; // Target x-coordinate for autonomous movement.
    public double targetY; // Target y-coordinate for autonomous movement.
    public double targetAngle; // Target angle for autonomous rotation.

    /**
     * Constructor for autonomous drive system.
     *
     * @param drive         The robot's drive system.
     * @param pos           The robot's positioning system.
     * @param goConstants   The PID constants for translational movement.
     * @param turnConstants The PID constants for rotational movement.
     */
    public AutonomousDrive(PositionedDrive drive, PositioningSystem pos, PDConstant goConstants,
            PDConstant turnConstants) {
        this.drive = drive;
        this.pos = pos;
        this.xController = new PDController(goConstants);
        this.yController = new PDController(goConstants);
        this.turnController = new PDController(turnConstants);
        // Initialize the target position and angle to the current position and angle.
        this.targetX = pos.getPosition().x;
        this.targetY = pos.getPosition().y;
        this.targetAngle = pos.getAngle();
    }

    /**
     * Sets the target position for autonomous movement.
     *
     * @param target The target position as a 2D vector.
     */
    public void setTarget(Vector2 target) {
        targetX = target.x;
        targetY = target.y;
    }

    // The move method implementation has been omitted in this snippet.

    /**
     * Resets the autonomous drive system to the current position and angle.
     * Also resets the PID controllers.
     */
    public void reset() {
        targetX = pos.getPosition().x;
        targetY = pos.getPosition().y;
        targetAngle = AngleMath.toTurnAngle(pos.getAngle());
        xController.reset();
        yController.reset();
        turnController.reset();
    }

    // Utility class to prevent spamming the console with messages.
    DeSpam deSpam = new DeSpam(0.1);

    /**
     * Called periodically to update the robot's motion during autonomous.
     *
     * @param dTime The time delta since the last tick.
     */
    public void tick(double dTime) {
        // Calculate corrections for x, y, and turn using the PID controllers.
        double xCorrect = xController.solve(targetX - pos.getPosition().x);
        double yCorrect = yController.solve(targetY - pos.getPosition().y);
        double turnCorrect = turnController
                .solve(AngleMath.getDelta(AngleMath.toTurnAngle(pos.getAngle()), targetAngle));

        // Adjust the correction vectors for the robot's current orientation.
        Vector2 goCorrect = new Vector2(xCorrect, yCorrect).rotate(-pos.getAngle());

        // Periodically print debugging information to the console.
        deSpam.exec(() -> {
            System.out.println("targetX: " + targetX + ", currentX: " + pos.getPosition().x);
            // Debugging information for targetY, currentY, targetAngle, and currentAngle
            // can be added here.
            System.out.println(goCorrect.getTurnAngleDeg());
        });

        // Apply the power if the error is within acceptable bounds, otherwise stop the
        // robot.
        if (2 < turnController.getLastError() / 3 + xController.getLastError() + yController.getLastError())
            drive.power(goCorrect.getMagnitude(), goCorrect.getAngleDeg(), turnCorrect, false);
        else
            drive.stopGoPower();
    }
}