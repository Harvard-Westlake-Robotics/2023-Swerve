package frc.robot.Util;

/**
 * The DSAController class is a custom implementation of a motion controller
 * that uses proportional, derivative, and second derivative (acceleration) control.
 * This controller is designed to provide a correctional output to reach a target value.
 */
public class DSAController implements MotionController {
    // Control constants for proportional, derivative, and second derivative terms.
    double P_CONSTANT;
    double D_CONSTANT;
    double DD_CONSTANT; // Second derivative constant for acceleration control.
    double deadzone; // A range of error values considered close enough to zero to ignore.

    // State variables to keep track of the previous error and derivative for calculations.
    double lastError = 0;
    double lastDerivative = 0;

    /**
     * Constructs a DSAController with specified constants and a deadzone.
     * 
     * @param p_CONSTANT Proportional constant.
     * @param d_CONSTANT Derivative constant.
     * @param dd_CONSTANT Second derivative constant.
     * @param deadzone A deadzone where correction is not applied if error is within this range.
     */
    public DSAController(double p_CONSTANT, double d_CONSTANT, double dd_CONSTANT, double deadzone) {
        P_CONSTANT = p_CONSTANT;
        D_CONSTANT = d_CONSTANT;
        DD_CONSTANT = dd_CONSTANT;
        this.deadzone = deadzone;
    }

    /**
     * Constructs a DSAController with specified constants and no deadzone.
     * 
     * @param p_CONSTANT Proportional constant.
     * @param d_CONSTANT Derivative constant.
     * @param dd_CONSTANT Second derivative constant.
     */
    public DSAController(double p_CONSTANT, double d_CONSTANT, double dd_CONSTANT) {
        this(p_CONSTANT, d_CONSTANT, dd_CONSTANT, 0);
    }

    /**
     * Calculates the correction needed to reduce the error based on the controller constants.
     * 
     * @param currentError The current deviation from the target value.
     * @param dTime The time elapsed since the last update.
     * @return The correctional value to apply.
     */
    public double solve(double currentError, double dTime) {
        // Proportional correction with deadzone adjustment.
        double p_correct = P_CONSTANT * currentError;
        // If the correction is within the deadzone, it's set to zero.
        if (Math.abs(p_correct) < deadzone) {
            p_correct = 0;
        } else {
            // Adjust the proportional correction to account for the deadzone.
            p_correct = (p_correct > 0) ? p_correct - deadzone : p_correct + deadzone;
        }

        // Calculate the derivative of the error.
        var derivative = (currentError - lastError) / dTime;

        // Derivative correction.
        double d_correct = D_CONSTANT * derivative;
        // Second derivative (acceleration) correction.
        double dd_correct = DD_CONSTANT * ((lastDerivative) - (derivative)) / dTime;

        // Update the last derivative and error for the next iteration.
        lastDerivative = derivative;
        lastError = currentError;

        // The total correction is the sum of proportional, derivative, and second derivative corrections.
        return p_correct + d_correct + dd_correct;
    }

    /**
     * Creates a new controller with the same constants but scaled by a factor.
     * 
     * @param fac The scaling factor.
     * @return A new DSAController with scaled constants.
     */
    public DSAController withMagnitude(double fac) {
        // Returns a new controller with all constants scaled by the factor.
        return new DSAController(P_CONSTANT * fac, D_CONSTANT * fac, DD_CONSTANT * fac, deadzone);
    }

    /**
     * Resets the controller's state, clearing the last error and derivative values.
     */
    public void reset() {
        lastError = 0;
        lastDerivative = 0;
    }

    /**
     * Creates a copy of this DSAController.
     * 
     * @return A new DSAController object with the same constants and deadzone.
     */
    public DSAController clone() {
        // Returns a new controller with the same constants and deadzone.
        return new DSAController(P_CONSTANT, D_CONSTANT, DD_CONSTANT, deadzone);
    }
}
