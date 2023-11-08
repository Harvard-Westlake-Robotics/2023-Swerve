package frc.robot.Util;

/**
 * The PWIDController class implements a Proportional-Integral-Derivative controller
 * with decay on the integral component, for motion control.
 * It calculates corrective actions to reach a target based on sensor feedback.
 */
public class PWIDController implements MotionController {
    // Constants for the PID controller, including decay for the integral term.
    PWIDConstant constant;

    // The last error recorded (previous sensor reading).
    double lastError = 0;
    // The accumulated error over time, which decays according to the integral half-life.
    double accumulatedError = 0;

    /**
     * Returns the last error that was calculated by the controller.
     *
     * @return The most recent error value.
     */
    public double getLastError() {
        return lastError;
    }

    /**
     * Constructor for the PWIDController.
     *
     * @param constants The PID constants including the decay rate for the integral term.
     */
    public PWIDController(PWIDConstant constants) {
        this.constant = constants.clone();
    }

    /**
     * Computes the corrective action needed to reach the target position.
     *
     * @param currentError The current distance to the target from the current value (target - current value).
     * @param dTime        The time elapsed since the last update.
     * @return             The correction value to apply.
     */
    public double solve(double currentError, double dTime) {
        // Proportional correction based on current error.
        double p_correct = constant.kP * currentError;

        // Derivative correction based on the rate of change of error.
        double d_correct = constant.kD * ((currentError - lastError) / dTime);

        // Apply decay to the accumulated error using the integral half-life.
        accumulatedError *= Math.pow(0.5, dTime / constant.iHalfLife);
        // Increment accumulated error.
        accumulatedError += currentError;

        // Integral correction based on the accumulated error.
        double i_correct = constant.kI * accumulatedError;

        // Update the last error for the next iteration.
        lastError = currentError;

        // Combine all corrections into a single output.
        var correct = p_correct + d_correct + i_correct;

        // If there's a maximum correction value specified, enforce its limit.
        if (constant.max != null && Math.abs(correct) > Math.abs(constant.max))
            return Math.abs(constant.max) * Math.signum(correct);

        // Return the calculated correction.
        return correct;
    }

    /**
     * Resets the controller's accumulated and last error to zero.
     * This should be called when the control target is changed or when the controller is first initialized.
     */
    public void reset() {
        lastError = 0;
        accumulatedError = 0;
    }
}
