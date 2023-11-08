package frc.robot.Util;

/**
 * The ScaleInput class provides methods for scaling and curving the input values,
 * typically from joystick inputs, to allow for more precise control of the robot.
 */
public class ScaleInput {
    /**
     * Applies an exponential curve to the input to enhance precision for small values.
     * 
     * @param input     The input value to be curved, expected to be in the range [-100, 100].
     * @param intensity The intensity of the curve, a higher value makes the curve steeper, expected to be in the range [0, 20].
     * @return          The curved input value, still in the range [-100, 100].
     */
    public static double curve(double input, double intensity) {
        // Apply an exponential function to the input value to scale it non-linearly
        return Math.exp((Math.abs(input) - 100) * intensity / 1000) * input;
    }

    /**
     * Applies a reverse exponential curve to the input, typically for de-scaling values.
     * 
     * @param input     The input value to be reverse curved, expected to be in the range [0, 1].
     * @param intensity The intensity of the reverse curve, a higher value makes the curve steeper, expected to be in the range [0, 20].
     * @return          The reverse curved input value.
     */
    public static double reverseCurve(double input, double intensity) {
        // Apply the curve function in reverse to the input value
        return (100 - curve(100 - (input * 100), intensity)) / 100;
    }

    /**
     * Scales the input powers for left and right sides of a tank drive to allow for smoother turning and power application.
     * 
     * @param leftpwr            The power input for the left side, expected to be in the range [-1, 1].
     * @param rightpwr           The power input for the right side, expected to be in the range [-1, 1].
     * @param deadzone           The deadzone for the inputs, below which the input is considered to be zero.
     * @param turnCurveIntensity The intensity of the curve applied to the turn component, 7 is recommended, expected to be in the range [0, 20].
     * @param pwrCurveIntensity  The intensity of the curve applied to the power component, 5 is recommended, expected to be in the range [0, 20].
     * @return                   A Pair object containing the scaled power values for the left and right sides.
     */
    public static Pair<Double> scale(double leftpwr, double rightpwr, double deadzone,
                                     double turnCurveIntensity, double pwrCurveIntensity) {
        // If both inputs are within the deadzone, return zero power for both sides.
        if (Math.abs(leftpwr) < deadzone && Math.abs(rightpwr) < deadzone) {
            return new Pair<Double>(0.0);
        }
        // Scale the average of the powers and the difference of the powers independently
        double pwr = curve(((leftpwr + rightpwr) / 2) * 100, pwrCurveIntensity);
        double turn = curve((leftpwr - rightpwr) / 2 * 100, turnCurveIntensity);

        // Return the new scaled powers as a Pair object
        return new Pair<Double>(
                (pwr + turn), // left power
                (pwr - turn) // right power
        );
    }

    /**
     * Normalizes a pair of power values so that both are within the range [-100, 100].
     * If one value is over 100, both values are scaled down proportionally.
     * 
     * @param inp A Pair of double values representing power that may need normalization.
     * @return    A Pair of double values representing the normalized powers.
     */
    public static Pair<Double> normalize(Pair<Double> inp) {
        // If both values are already within the range, return them unchanged.
        if (Math.abs(inp.left) < 100.0 && Math.abs(inp.right) < 100.0) {
            return new Pair<Double>(inp.left, inp.right);
        }
        // Calculate the scaling factor for both values to bring them within the range.
        double scale_one = 100.0 / Math.abs(inp.left);
        double scale_two = 100.0 / Math.abs(inp.right);
        // Choose the smaller scaling factor to ensure both values are within the range.
        double scale_value = (scale_one < scale_two) ? scale_one : scale_two;
        // Apply the scaling factor to both values.
        double left_pwr = inp.left * scale_value;
        double right_pwr = inp.right * scale_value;
        // Ensure that after scaling, neither value exceeds 100 due to precision loss.
        if (Math.abs(right_pwr) > 100) {
            right_pwr = (right_pwr > 0) ? 100 : -100;
        }
        if (Math.abs(left_pwr) > 100) {
            left_pwr = (left_pwr > 0) ? 100 : -100;
        }
        // Return the normalized powers as a Pair object.
        return new Pair<Double>(left_pwr, right_pwr);
    }
}
