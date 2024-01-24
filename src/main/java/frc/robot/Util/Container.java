package frc.robot.Util;

/**
 * The Container class is a simple generic holder for a single value.
 * It can be used to hold any type of object and provides a simple way to
 * pass around mutable references where immutability of the object is normally
 * enforced.
 * 
 * @param <T> The type of the object that this container will hold.
 */
public class Container<T> {
    // The value this container holds.
    public T val;

    /**
     * Constructs a new Container with the specified initial value.
     * 
     * @param val The initial value to store in this container.
     */
    public Container(T val) {
        this.val = val;
    }

    public Container() {

    }
}
