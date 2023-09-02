package frc.robot.Util;

public class PDController {
    double P_CONSTANT;
    double D_CONSTANT;
    double deadzone;

    double lastError = 0;

    public PDController(PDConstant constants) {
        P_CONSTANT = constants.kP;
        D_CONSTANT = constants.kD;
        this.deadzone = constants.deadzone;
    }

    boolean isInDeadzone = false;

    /**
     * @param currentError the distance to the target from the current value (target
     *                     value - current value)
     * @return A correctional value
     */
    public double solve(double currentError) {
        double p_correct = P_CONSTANT * currentError;
        if (Math.abs(p_correct) < deadzone) {
            p_correct = 0;
            isInDeadzone = true;
        } else {
            p_correct = (p_correct > 0) ? p_correct - deadzone : p_correct + deadzone;
            isInDeadzone = false;
        }

        double d_correct = D_CONSTANT * (currentError - lastError);

        lastError = currentError;

        return p_correct + d_correct;
    }

    public void reset() {
        lastError = 0;
    }
}
