package frc.robot.Util;

/**
 * The PDConstant class encapsulates the proportional (P) and derivative (D)
 * constants
 * used in a PD (Proportional-Derivative) controller.
 */
public class PIDConstant {
    // Proportional constant for the PD controller.
    final double kP;
    final double kI;
    // Derivative constant for the PD controller.
    final double kD;
    // Maximum value for the output of the PD controller, can be null if not used.
    final Double max;

    /**
     * Constructs a PDConstant with maximum output value.
     * 
     * @param p_CONSTANT The proportional constant.
     * @param d_CONSTANT The derivative constant.
     * @param max        The maximum output value for the PD controller.
     */
    public PIDConstant(double p_CONSTANT, double i_CONSTANT, double d_CONSTANT, Double max) {
        kP = p_CONSTANT;
        kI = i_CONSTANT;
        kD = d_CONSTANT;
        this.max = max;
    }

    /**
     * Constructs a PDConstant without maximum output value.
     * 
     * @param p_CONSTANT The proportional constant.
     * @param d_CONSTANT The derivative constant.
     */
    public PIDConstant(double p_CONSTANT, double i_CONSTANT, double d_CONSTANT) {
        // Calls the primary constructor with 'max' as null.
        this(p_CONSTANT, i_CONSTANT, d_CONSTANT, null);
    }

    /**
     * Creates a new PDConstant object with the P and D constants scaled by a
     * factor.
     * 
     * @param fac The factor to scale the constants by.
     * @return A new PDConstant object with scaled P and D values.
     */
    public PIDConstant withMagnitude(double fac) {
        // Scales the P and D constants by the factor 'fac'.
        return new PIDConstant(kP * fac, kI * fac, kD * fac, max);
    }

    /**
     * Creates a copy of this PDConstant.
     * 
     * @return A new PDConstant object with the same values as this one.
     */
    public PIDConstant clone() {
        // Returns a new PDConstant with the same P, D, and max values.
        return new PIDConstant(kP, kI, kD, max);
    }
}
