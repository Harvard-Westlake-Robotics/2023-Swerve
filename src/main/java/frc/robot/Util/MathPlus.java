package frc.robot.Util;

public class MathPlus {
    public static int boolFac(boolean b) {
        return b ? 1 : -1;
    }
    public static double maxMagnitude(double val, double maxMagnitude) {
        maxMagnitude = Math.abs(maxMagnitude);
        if (Math.abs(val) > maxMagnitude) {
            return (val > 0) ? maxMagnitude : -maxMagnitude;
        }
        return val;
    }
    public static double weightedAvg(double weight, double a, double b) {
        return (weight * a + b) / (weight + 1);
    }
}
