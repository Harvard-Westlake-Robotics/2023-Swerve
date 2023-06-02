package frc.robot.Util;

public class AngleMath {
    public static double minMagnitude(double ...nums) {
        double min = nums[0];
        for (double num : nums) {
            if (Math.abs(num) < Math.abs(min))
                min = num;
        }
        return min;
    }

    public static double getDelta(double current, double target) {
        var diff = (target - current) % 360;
        return minMagnitude(diff, diff - 360);
    }
    /**
     * conforms an angle to -180, 180 java
     */
    public static double conformAngle(double angle) {
        return (angle + 180) % 360 - 180;
    }
}
