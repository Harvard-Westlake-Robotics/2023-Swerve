package frc.robot.Util;

public class AngleMath {
    /**
     * conforms an angle to -180, 180 java
     */
    public static double conformAngle(double angle) {
        return (angle + 180) % 360 - 180;
    }
}
