package frc.robot.Auto.Curves;

import frc.robot.Util.Vector2;

// Class that represents a linear movement in a 2D space for a robot.
public class LinearMove implements Curve {
    // Maximum speed the robot can move.
    final double max_speed;
    // Maximum acceleration the robot can have.
    final double max_acceleration;
    // Time it takes for robot to accelerate from 0 to max speed/decelerate from max speed to 0
    final double acceleration_time;
    // Start position of the robot.
    final Vector2 start_pos;
    // The end position where the robot is supposed to move to.
    final Vector2 end_position;
    // Linear distance from start position to end position
    final double total_distance;
    // Normalized direction vector from start to end
    final Vector2 direction_vector;
    // Checks to see if robot needs to hit max acceleration, otherwise its velocity will be a triangle
    final boolean isTrapezoid;
    // Calculated time needed to get from start to end
    final double total_time;

    // Time since instruction was given
    double time_elapsed;
    // Current speed of the robot.
    double curr_speed = 0;

    // Constructor that initializes the movement with max speed, acceleration, start and end positions.
    public LinearMove(double max_speed, double max_acceleration, Vector2 start, Vector2 end) {
        this.max_speed = max_speed;
        this.max_acceleration = max_acceleration;
        // Calculating time it takes robot to accelerate to max speed
        this.acceleration_time = max_speed / max_acceleration;
        // Cloning ensures that the vectors provided are not altered outside this class.
        this.start_pos = start.clone();
        this.end_position = end.clone();
        this.total_distance = end_position.minus(start_pos).getMagnitude();
        this.direction_vector = end_position.minus(start_pos).withMagnitude(1);
        // Check if robot's acceleration should follow triangle shape
        if (total_distance < Math.pow(max_speed, 2) / max_acceleration) {
            isTrapezoid = false;
            // Calculate total time the robot would need ideally to reach the end position
            this.total_time = 2 * Math.sqrt(total_distance / max_acceleration);
        }   
        else {
            isTrapezoid = true;
            // Calculate total time the robot would need ideally to reach the end position
            this.total_time = total_distance / max_speed + max_speed / max_acceleration;
        }
        this.time_elapsed = 0.0;
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
        time_elapsed += dTime;
        // Check if goal is reached
        if (time_elapsed > total_time) {
            return null;
        }
        double distanceTraveled;
        if (isTrapezoid) {
            // Math if velocity is a trapezoid curve
            if (time_elapsed <= (acceleration_time)) {
                // Case for when robot is still accelerating
                distanceTraveled = 0.5 * max_acceleration * Math.pow(time_elapsed, 2);
            } else if (time_elapsed <= total_time - max_acceleration) {
                // Case for when robot has reached max velocity and isn't decelerating yet
                distanceTraveled = max_speed * (time_elapsed - 0.5 * acceleration_time);
            } else {
                // Case for when robot is decelerating
                distanceTraveled = total_distance - 0.5 * Math.pow(total_time - time_elapsed, 2) * max_acceleration;
            }
        }
        else {
            // Math if velocity is a triangle curve
            if (time_elapsed <= Math.sqrt(total_distance / max_acceleration)) {
                // Case for when robot is still accelerating
                distanceTraveled = 0.5 * max_acceleration * Math.pow(time_elapsed, 2);
            }
            else {
                // Case for when robot is decelerating
                distanceTraveled = total_distance - 0.5 * Math.pow(total_time - time_elapsed, 2) * max_acceleration;
            }
        }
        Vector2 currentPosition = direction_vector.withMagnitude(distanceTraveled);
        return currentPosition;
    }
}
