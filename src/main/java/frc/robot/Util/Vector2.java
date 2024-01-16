package frc.robot.Util;

/**
 * Vector2 represents a mathematical 2-dimensional vector with x and y
 * components.
 * This class provides various utility methods for vector operations.
 */
public class Vector2 {
    // The x and y components of the vector.
    public double x;
    public double y;

    /**
     * Constructs a vector given an angle and magnitude.
     *
     * @param angle     The angle in degrees.
     * @param magnitude The magnitude of the vector.
     * @return A new Vector2 object with the specified angle and magnitude.
     */
    public static Vector2 fromAngleAndMag(double angle, double magnitude) {
        return fromAngle(angle).multiply(magnitude);
    }

    /**
     * Sets the magnitude of the vector, keeping its direction.
     *
     * @param magnitude The new magnitude.
     * @return A new Vector2 object with the original direction and the new
     *         magnitude.
     */
    public Vector2 withMagnitude(double magnitude) {
        double factor = magnitude / getMagnitude();
        return new Vector2(x * factor, y * factor);
    }

    /**
     * Constructs a vector from an angle, assuming a unit magnitude.
     *
     * @param angle The angle in degrees.
     */
    public static Vector2 fromAngle(double angle) {
        double radians = AngleMath.conformAngle(angle) / 360 * (2 * Math.PI);
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new Vector2(x, y);
    }

    /**
     * Constructs a vector with specified x and y components.
     *
     * @param x The x component.
     * @param y The y component.
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the magnitude (length) of the vector.
     *
     * @return The magnitude of the vector.
     */
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Multiplies the vector by another vector component-wise.
     *
     * @param other The other vector to multiply with.
     * @return A new Vector2 representing the product of the two vectors.
     */
    public Vector2 multiply(Vector2 other) {
        return new Vector2(x * other.x, y * other.y);
    }

    /**
     * Multiplies the vector by a scalar.
     *
     * @param other The scalar to multiply with.
     * @return A new Vector2 representing the scaled vector.
     */
    public Vector2 multiply(double other) {
        return multiply(new Vector2(other, other));
    }

    /**
     * Gets the angle of the vector in standard position.
     *
     * @return The angle of the vector in degrees.
     */
    public double getAngleDeg() {
        double angle = Math.atan2(y, x);
        angle = Math.toDegrees(angle);
        angle = (angle + 360) % 360;
        return angle;
    }

    /**
     * Gets the angle of the vector with respect to a turn.
     *
     * @return The turn angle of the vector in degrees.
     */
    public double getTurnAngleDeg() {
        return AngleMath.conformAngle(getAngleDeg() - 90);
    }

    /**
     * Adds another vector to this vector.
     *
     * @param other The other vector to add.
     * @return A new Vector2 representing the sum of the two vectors.
     */
    public Vector2 add(Vector2 other) {
        return new Vector2(
                x + other.x,
                y + other.y);
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector to dot with.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Rotates the vector by a certain number of degrees.
     *
     * @param degrees The number of degrees to rotate the vector.
     * @return A new Vector2 representing the rotated vector.
     */
    public Vector2 rotate(double degrees) {
        return Vector2.fromAngleAndMag(getAngleDeg() - degrees, getMagnitude());
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other The other vector to subtract.
     * @return A new Vector2 representing the difference of the two vectors.
     */
    public Vector2 minus(Vector2 other) {
        return this.add(other.multiply(-1));
    }

    /**
     * Creates a new vector with the same x and y components as this one.
     *
     * @return A new Vector2 object with the same components as the original.
     */
    public Vector2 clone() {
        return new Vector2(x, y);
    }

    /**
     * Provides a string representation of the vector in the format "(x, y)".
     *
     * @return A string representing the vector.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Vector2 mapDimentions(Mapper<Double, Double> mapper) {
        return new Vector2(mapper.map(x), mapper.map(y));
    }
}
