package frc.robot.Util;

public class PWIDConstant {
    final double kP;
    final double kD;
    final double kI;
    final double iHalfLife;
    final Double max;

    public PWIDConstant(double p_CONSTANT, double d_CONSTANT, double i_CONSTANT, double iHalfLife, Double max) {
        kP = p_CONSTANT;
        kD = d_CONSTANT;
        kI = i_CONSTANT;
        this.iHalfLife = iHalfLife;
        this.max = max;
    }

    public PWIDConstant(double p_CONSTANT, double d_CONSTANT, double i_CONSTANT, double halfLife) {
        this(p_CONSTANT, d_CONSTANT, i_CONSTANT, halfLife, null);
    }

    /**
     * returns new PD Constants with a scaled magnitude
     * 
     * @param fac
     * @return
     */
    public PWIDConstant withMagnitude(double fac) {
        return new PWIDConstant(kP * fac, kD * fac, kI * fac, iHalfLife, max);
    }

    public PWIDConstant clone() {
        return withMagnitude(1.0);
    }
}
