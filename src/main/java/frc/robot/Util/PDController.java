package frc.robot.Util;

public class PDController {
    double P_CONSTANT;
    double D_CONSTANT;
    double deadzone;
    double max;

    double lastError = 0;

    public PDController(double p_CONSTANT, double d_CONSTANT, double deadzone, double max) {
        P_CONSTANT = p_CONSTANT;
        D_CONSTANT = d_CONSTANT;
        this.deadzone = deadzone;
        this.max = max;
    }

    public PDController(double p_CONSTANT, double d_CONSTANT) {
        this(p_CONSTANT, d_CONSTANT, 0, 0);
    }

    /**
     * @param currentError the distance to the target from the current value (target
     *                     value - current value)
     * @return A correctional value
     */
    public double solve(double currentError) {
        double p_correct = P_CONSTANT * currentError;
        if (Math.abs(p_correct) < deadzone) {
            p_correct = 0;
        } else {
            p_correct = (p_correct > 0) ? p_correct - deadzone : p_correct + deadzone;
        }

        double d_correct = D_CONSTANT * (currentError - lastError);

        lastError = currentError;

        return MathPlus.maxMagnitude(p_correct + d_correct, max);
    }

    /**
     * returns a new PD controller with a scaled magnitude
     * 
     * @param fac
     * @return
     */
    public PDController withMagnitude(double fac) {
        return new PDController(P_CONSTANT * fac, D_CONSTANT * fac, deadzone, max);
    }

    public void reset() {
        lastError = 0;
    }

    public PDController clone() {
        return new PDController(P_CONSTANT, D_CONSTANT);
    }
}
