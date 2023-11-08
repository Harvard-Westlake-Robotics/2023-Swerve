package frc.robot.Util;

/**
 * The AngleMath class provides static utility methods for performing angle calculations,
 * particularly useful in robotics for navigation and orientation tasks.
 */
public class AngleMath {

    /**
     * Finds the smallest magnitude among the given angles.
     *
     * @param nums An array of angle values.
     * @return The angle with the smallest absolute magnitude.
     */
    public static double minMagnitude(double... nums) {
        double min = nums[0];
        for (double num : nums) {
            // Check if the absolute value of num is less than the absolute value of min.
            if (Math.abs(num) < Math.abs(min)) {
                min = num;
            }
        }
        return min;
    }

    /**
     * Computes the shortest distance (delta) between two angles.
     *
     * @param current The current angle.
     * @param target  The target angle.
     * @return The shortest angle difference.
     */
    public static double getDelta(double current, double target) {
        double diff = target - current;
        // Adjust the angle difference to be within the range (-180, 180].
        return conformAngle(diff);
    }

    /**
     * Determines if the shortest path to correct an angle error is in the reverse direction.
     *
     * @param current The current angle.
     * @param target  The target angle.
     * @return True if correcting in reverse is shorter, false otherwise.
     */
    public static boolean shouldReverseCorrect(double current, double target) {
        // Calculate the error when facing the target directly.
        var frontFaceError = AngleMath.getDelta(current, target);
        // Calculate the error when facing away from the target (reversed by 180 degrees).
        var backFaceError = AngleMath.getDelta(current, target - 180);
        // Choose the smallest magnitude error.
        var error = AngleMath.minMagnitude(frontFaceError, backFaceError);

        // If the front face error is the smaller one, we should not reverse.
        return error == frontFaceError;
    }

    /**
     * Computes the shortest angle difference considering reversibility.
     * The method takes into account whether reversing direction might be more efficient.
     *
     * @param current The current angle.
     * @param target  The target angle.
     * @return The shortest reversible angle difference.
     */
    public static double getDeltaReversable(double current, double target) {
        var frontFaceError = AngleMath.getDelta(current, target);
        var backFaceError = AngleMath.getDelta(current, target - 180);
        // Use the smallest magnitude error, considering reversibility.
        var error = AngleMath.minMagnitude(frontFaceError, backFaceError);

        return error;
    }

    /**
     * Normalizes any angle to the range (-180, 180].
     *
     * @param angle The angle to be normalized.
     * @return The normalized angle.
     */
    public static double conformAngle(double angle) {
        angle %= 360;
        if (angle > 180) {
            angle -= 360;
        } else if (angle <= -180) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Converts an angle from standard position (counter-clockwise from the positive X-axis)
     * to an angle from the front of the robot to the right.
     *
     * @param standardPositionAngle The angle in standard position.
     * @return The converted angle as viewed from the robot's perspective.
     */
    public static double toTurnAngle(double standardPositionAngle) {
        // Adjust the angle to the robot's perspective.
        return AngleMath.conformAngle(90 - standardPositionAngle);
    }

    /**
     * Converts an angle from the robot's perspective to the standard position.
     *
     * @param turnAngle The angle from the robot's perspective.
     * @return The angle in standard position.
     */
    public static double toStandardPosAngle(double turnAngle) {
        // The conversion is symmetrical, so we can use the same method as toTurnAngle.
        return toTurnAngle(turnAngle);
    }
}
