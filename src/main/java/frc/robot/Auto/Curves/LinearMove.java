package frc.robot.Auto.Curves;

import frc.robot.Util.Vector2;

// Class that represents a linear movement in a 2D space for a robot.
public class LinearMove implements Curve {
    // Maximum speed the robot can move.
    final double max_speed;
    // Maximum acceleration the robot can have.
    final double max_acceleration;

    // Current position of the robot.
    Vector2 current_pos;
    // The end position where the robot is supposed to move to.
    Vector2 end_position;
    // Current speed of the robot.
    double curr_speed = 0;

    // Constructor that initializes the movement with max speed, acceleration, start and end positions.
    public LinearMove(double max_speed, double max_acceleration, Vector2 start, Vector2 end) {
        this.max_speed = max_speed;
        this.max_acceleration = max_acceleration;
        // Cloning ensures that the vectors provided are not altered outside this class.
        this.current_pos = start.clone();
        this.end_position = end.clone();
    }

    // Main method for testing the LinearMove class.
    public static void main(String[] args) {
        // Creating a new LinearMove instance.
        var move = new LinearMove(5, 0.5,
                new Vector2(0, 0),
                new Vector2(100, 0));
        // Running a loop until the robot reaches the end position.
        for (var pos = move.next(0); pos != null; pos = move.next(0.1)) {
            // Printing the current position and speed of the robot.
            System.out.println(pos + " speed: " + move.curr_speed);
        }
    }

    // Method to calculate the next position of the robot given the elapsed time.
    public Vector2 next(double dTime) {
        // If the robot is essentially at the end position, return null to indicate completion.
        if (end_position.minus(current_pos).getMagnitude() < 0.0001) {
            return null;
        }
        // Calculate the distance needed to decelerate to a stop from the max speed.
        double acceleration_dist = (Math.pow(max_speed, 2) / (2 * max_acceleration));
        // If within the distance to decelerate, begin deceleration.
        if (current_pos.minus(end_position).getMagnitude() <= acceleration_dist) {
            curr_speed -= dTime * max_acceleration;
        } else if (curr_speed < max_speed) {
            // Otherwise, if below max speed, accelerate.
            curr_speed += dTime * max_acceleration;
            // Ensure the current speed does not exceed max speed.
            curr_speed = Math.min(curr_speed, max_speed);
        }
        // Calculate the ideal movement vector toward the end position.
        var target_displacement = end_position.minus(current_pos);
        // Calculate the ideal distance to move this frame, without overshooting.
        var ideal_delta_dist = Math.min(curr_speed * dTime, target_displacement.getMagnitude());
        // Update the position by moving towards the end position by the ideal distance.
        current_pos = current_pos.add(target_displacement.withMagnitude(ideal_delta_dist));
        // Return the new position.
        return current_pos;
    }
}
