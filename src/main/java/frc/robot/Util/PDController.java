package frc.robot.Util;

public class PDController implements MotionController {
    PDConstant constant;

    double lastError = 0;

    public double getLastError() {
        return lastError;
    }

    public PDController(PDConstant constants) {
        this.constant = constants.clone();
    }

    public double solve(double currentError) {
        return solve(currentError, 1);
    }

    /**
     * @param currentError the distance to the target from the current value (target
     *                     value - current value)
     * @return A correctional value
     */
    public double solve(double currentError, double dTime) {
        double p_correct = constant.kP * currentError;

        double d_correct = constant.kD * (currentError - lastError) / dTime;

        lastError = currentError;

        var err = p_correct + d_correct;

        if (constant.max != null && Math.abs(err) > Math.abs(constant.max))
            return Math.abs(constant.max) * Math.signum(err);

        return err;
    }

    public void reset() {
        lastError = 0;
    }
}
