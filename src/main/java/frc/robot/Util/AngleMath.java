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
        double diff = target - current;
        return conformAngle(diff);
    }
    
    /**
     * Makes the angle between (-180, 180]
     */
    public static double conformAngle(double angle) {
        angle %= 360;
        if (angle > 180) {
            angle -= 360;
        } else if (angle <= -180) {
            angle += 360;
        }
        return angle;
    }
}
