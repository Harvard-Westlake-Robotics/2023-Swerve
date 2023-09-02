package frc.robot.Util;

public class PDConstant {
    final double kP;
    final double kD;
    final double deadzone;
    
    public PDConstant(double p_CONSTANT, double d_CONSTANT, double deadzone) {
        kP = p_CONSTANT;
        kD = d_CONSTANT;
        this.deadzone = deadzone;
    }

    public PDConstant(double p_CONSTANT, double d_CONSTANT) {
        this(p_CONSTANT, d_CONSTANT, 0);
    }


    /**
     * returns new PD Constants with a scaled magnitude
     * 
     * @param fac
     * @return
     */
    public PDConstant withMagnitude(double fac) {
        return new PDConstant(kP * fac, kD * fac, deadzone);
    }
}
