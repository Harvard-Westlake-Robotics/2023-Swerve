package frc.robot.Util;

public class PDConstant {
    final double kP;
    final double kD;
    final Double max;
    
    public PDConstant(double p_CONSTANT, double d_CONSTANT, Double max) {
        kP = p_CONSTANT;
        kD = d_CONSTANT;
        this.max = max;
    }

    public PDConstant(double p_CONSTANT, double d_CONSTANT) {
        this(p_CONSTANT, d_CONSTANT, null);
    }


    /**
     * returns new PD Constants with a scaled magnitude
     * 
     * @param fac
     * @return
     */
    public PDConstant withMagnitude(double fac) {
        return new PDConstant(kP * fac, kD * fac, max);
    }

    public PDConstant clone() {
        return new  PDConstant(kP, kD, max);
    }
}
