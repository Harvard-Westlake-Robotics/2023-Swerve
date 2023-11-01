package frc.robot.Util;

public class PWIDController implements MotionController {
    PWIDConstant constant;

    double lastError = 0;
    double accumulatedError = 0;

    public double getLastError() {
        return lastError;
    }

    public PWIDController(PWIDConstant constants) {
        this.constant = constants.clone();
    }

    /**
     * @param currentError the distance to the target from the current value (target
     *                     value - current value)
     * @return A correctional value
     */
    public double solve(double currentError, double dTime) {
        double p_correct = constant.kP * currentError;

        double d_correct = constant.kD * ((currentError - lastError) / dTime);

        accumulatedError *= Math.pow(0.5, dTime / constant.iHalfLife);
        accumulatedError += currentError;

        double i_correct = constant.kI * accumulatedError;

        lastError = currentError;

        var correct = p_correct + d_correct + i_correct;

        if (constant.max != null && Math.abs(correct) > Math.abs(constant.max))
            return Math.abs(constant.max) * Math.signum(correct);

        return correct;
    }

    public void reset() {
        lastError = 0;
    }
}
