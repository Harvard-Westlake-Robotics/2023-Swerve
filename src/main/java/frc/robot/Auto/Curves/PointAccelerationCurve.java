package frc.robot.Auto.Curves;

// Importing the Vector2 class, which represents a 2-dimensional vector.
import frc.robot.Util.Vector2;

// The PointAccelerationCurve class implements the Curve interface and is used to define a movement curve based
// on a series of positions that the robot should pass through, taking into account maximum acceleration and speed.
public class PointAccelerationCurve implements Curve {
    // The maximum acceleration the curve is allowed to reach.
    final double max_acc;
    // The maximum speed the curve is allowed to reach.
    final double max_speed;
    // An array of Vector2 objects representing the positions the curve goes
    // through.
    Vector2[] positions;

    // Constructor to initialize a new PointAccelerationCurve with the provided
    // maximum acceleration,
    // maximum speed, and an array of positions.
    public PointAccelerationCurve(double max_acc, double max_speed, Vector2[] positions) {
        this.max_acc = max_acc;
        this.max_speed = max_speed;
        this.positions = positions;
    }

    Vector2 lastPosition; // Variable to store the last position reached. It is not initialized here.
    Vector2 lastVelocity; // Variable to store the last velocity. It is not initialized here.

    // The next method should calculate the next position of the robot along the
    // curve given a time delta.
   
    // Currently, this method is not implemented and simply returns a new Vector2
    // based on the delta value.
   
    // This is likely a placeholder and needs to be replaced with the actual curve
    // calculation logic.
    @Override
    public Vector2 next(double delta) {
        // Placeholder implementation, likely to be replaced with actual curve
        // progression logic.
        return new Vector2(delta, delta);
    }
}
