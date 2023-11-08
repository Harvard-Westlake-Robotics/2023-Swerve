package frc.robot.Util;

/**
 * A generic class that represents a pair of values, which can be of any type.
 * @param <T> The type of the elements in the pair.
 */
public class Pair<T> {
    public T left;  // The first value in the pair.
    public T right; // The second value in the pair.

    /**
     * Constructs a new Pair with specified left and right values.
     * 
     * @param left  The value for the left side of the pair.
     * @param right The value for the right side of the pair.
     */
    public Pair(T left, T right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Constructs a new Pair where both the left and right values are the same.
     * 
     * @param val The value for both the left and right side of the pair.
     */
    public Pair(T val) {
        this(val, val); // Calls the main constructor with the same value for both sides.
    }

    /**
     * Applies a mapping function to both elements of the pair and returns a new pair with the results.
     * 
     * @param <To>    The type of the elements in the resulting pair.
     * @param mapper  A function that takes an element of type T and returns an element of type To.
     * @return        A new Pair with each original element having been mapped to a new value.
     */
    public <To> Pair<To> map(Mapper<T, To> mapper) {
        // Maps both elements of the pair using the provided mapping function and returns a new pair.
        return new Pair<To>(mapper.map(left), mapper.map(right));
    }
}
