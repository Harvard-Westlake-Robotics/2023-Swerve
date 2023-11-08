package frc.robot.Util;

/**
 * The PDController class is a Proportional-Derivative controller which computes
 * control values to minimize the error towards a target. It is a type of feedback
 * controller commonly used in robotics and control systems.
 */
public class PDController implements MotionController {
    // Constants for the PD control loop: proportional and derivative gains.
    PDConstant constant;

    // The last error recorded by the controller in the previous control loop iteration.
    double lastError = 0;

    /**
     * Retrieves the last error that the controller acted upon.
     *
     * @return The last error value.
     */
    public double getLastError() {
        return lastError;
    }

    /**
     * Constructs a PDController with specified control constants.
     *
     * @param constants The PD constants for the control loop.
     */
    public PDController(PDConstant constants) {
        this.constant = constants.clone();
    }

    /**
     * Solves the control problem for a given error with a default delta time.
     *
     * @param currentError The current deviation from the target value.
     * @return The control value to correct the error.
     */
    public double solve(double currentError) {
        // Overload the solve method assuming a delta time of 1 for simplicity.
        return solve(currentError, 1);
    }

    /**
     * Solves the control problem for a given error and delta time.
     *
     * @param currentError The current deviation from the target value.
     * @param dTime        The time elapsed since the last control loop iteration.
     * @return The control value to correct the error.
     */
    public double solve(double currentError, double dTime) {
        // Proportional correction based on the current error.
        double p_correct = constant.kP * currentError;

        // Derivative correction based on the rate of error change.
        double d_correct = constant.kD * (currentError - lastError) / dTime;

        // Update the last error for the next iteration.
        lastError = currentError;

        // Sum of proportional and derivative corrections.
        var correction = p_correct + d_correct;

        // If a maximum correction limit is set, enforce it.
        if (constant.max != null && Math.abs(correction) > Math.abs(constant.max)) {
            return Math.abs(constant.max) * Math.signum(correction);
        }

        // Return the total correction.
        return correction;
    }

    /**
     * Resets the controller, clearing the last error value.
     * This should be done when the controller is no longer being used for the current control target.
     */
    public void reset() {
        lastError = 0;
    }
}
