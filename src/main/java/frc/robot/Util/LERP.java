package frc.robot.Util;

/**
 * The LERP (Linear Interpolation) class provides a way to smoothly transition between values over time.
 */
public class LERP implements Tickable {
    Double val;   // The current value being interpolated.
    Double tar;   // The target value to interpolate towards.
    double rate;  // The rate at which to interpolate.

    /**
     * Constructor for the LERP class.
     *
     * @param rate The rate at which the value should change per time unit.
     */
    public LERP(double rate) {
        this.rate = rate;
    }

    /**
     * Retrieves the current value of the interpolation.
     *
     * @return The current interpolated value.
     * @throws Error If the initial value was never set.
     */
    public double get() {
        if (val == null)
            throw new Error("Lerp val was never set"); // Ensure 'val' has been initialized.
        return val;
    }

    /**
     * Sets the target value for the interpolation.
     *
     * @param next The target value to interpolate towards.
     */
    public void set(double next) {
        tar = next;
    }

    /**
     * Updates the current value towards the target value based on the elapsed time and rate.
     * This method should be called periodically, typically on every tick of the control loop.
     *
     * @param dTime The time elapsed since the last update.
     */
    public void tick(double dTime) {
        // If the current value is not set, initialize it with the target value.
        if (val == null)
            val = tar;
        // If the target value is not set, there's nothing to interpolate, so exit.
        if (tar == null)
            return;
        
        // Calculate the change needed to move 'val' closer to 'tar'.
        val += Math.signum(tar - val) * Math.min(Math.abs(rate * dTime), Math.abs(tar - val));
        // 'Math.signum(tar - val)' determines the direction of the change.
        // 'Math.min(Math.abs(rate * dTime), Math.abs(tar - val))' determines the size of the change.
        // The change in value will not exceed the difference between 'val' and 'tar'.
    }
}
