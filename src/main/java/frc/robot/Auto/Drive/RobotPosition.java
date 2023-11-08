package frc.robot.Auto.Drive;

// Import the Vector2 class, which represents a mathematical 2D vector, likely used here to represent a position in 2D space.
import frc.robot.Util.Vector2;

// The RobotPosition class encapsulates the positional data for the robot including its location and heading.
public class RobotPosition {
    public Vector2 pos; // The position of the robot represented as a 2D vector (x, y coordinates).
    public double angle; // The heading or orientation of the robot represented as an angle in degrees.

    // Constructor that initializes a new RobotPosition with a specified position and angle.
    public RobotPosition(Vector2 pos, double angle) {
        this.pos = pos;   // Set the position vector of the robot.
        this.angle = angle; // Set the orientation angle of the robot.
    }
}
