package frc.robot.Util;
import frc.robot.Core.Time;

/**
 * The LERP (Linear Interpolation) class provides a way to smoothly transition between values over time.
 */
public class LERP {
    double currentVal;   // The current value being interpolated.
    double target;   // The target value to interpolate towards.
    double rate;  // The rate at which to interpolate.
    double currentTime;
    // double lastSaved;

    /**
     * Constructor for the LERP class.
     *
     * @param rate The rate at which the value should change per time unit.
     */
    public LERP(double currentVal, double rate) {
        this.currentVal = currentVal;
        this.target = currentVal;
        this.rate = rate;
        this.currentTime = Time.getTimeSincePower();
    }

    /**
     * Retrieves the current value of the interpolation.
     *
     * @return The current interpolated value.
     * @throws Error If the initial value was never set.
     */
    public double get() {
        double nextVal = currentVal > target ? target : currentVal + rate * (Time.getTimeSincePower() - currentTime);
        currentTime = Time.getTimeSincePower();
        return nextVal;
    }

    /**
     * Sets the target value for the interpolation.
     *
     * @param next The target value to interpolate towards.
     */
    public void set(double next) {
        target = next;
    }
}
