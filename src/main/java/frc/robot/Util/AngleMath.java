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
        current = conformAngle(current);
        target = conformAngle(target);
        var diff = (target - current) % 360;
        return minMagnitude(diff, diff - 360);
    }
    
    /**
     * Makes the angle between (-180, 180]
     */
    public static double conformAngle(double angle) {
        return (angle + 180) % 360 - 180;
    }
}
