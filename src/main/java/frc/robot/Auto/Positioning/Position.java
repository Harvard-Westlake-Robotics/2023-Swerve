package frc.robot.Auto.Positioning;

// Importing the Vector2 class, which represents a 2-dimensional vector for position.
import frc.robot.Util.Vector2;

// The Position class encapsulates the angle and 2D position data for an object, such as a robot on the field.
public class Position {
    public double angle; // The angle variable likely represents the orientation of the object in degrees.
    public Vector2 position; // The position variable is a 2D vector representing the location of the object.

    // Constructor for the Position class.
    // It initializes the angle and position of the object.
    public Position(double angle, Vector2 position) {
        this.angle = angle; // Sets the angle of the object.
        this.position = position; // Sets the 2D position of the object.
    }
}
