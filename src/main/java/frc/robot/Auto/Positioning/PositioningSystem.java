package frc.robot.Auto.Positioning;

// Importing Vector2 class which is a utility class representing a 2D vector.
import frc.robot.Util.Vector2;

// The PositioningSystem interface defines the methods that any positioning system must implement.
// This could be used by various sensor systems or algorithms that provide positioning data for the robot.
public interface PositioningSystem {

    // Method to get the current angle of the robot.
    // This might be the robot's orientation with respect to a fixed point, like the starting position.
    double getAngle();

    // Method to get the current position of the robot as a 2D vector.
    // The position is likely given in a coordinate system that is relative to the field or a starting position.
    Vector2 getPosition();

    // Method to get the current speed of the robot as a 2D vector.
    // This represents the velocity in both the X and Y directions.
    Vector2 getSpeed();

    // Method to get the current acceleration of the robot as a 2D vector.
    // This represents the change in velocity in both the X and Y directions.
    Vector2 getAcceleration();

    // Method to reset or 'zero' the positioning system.
    // This is typically used at the start of a match or when the robot needs to recalibrate its position.
    void zero();
}
