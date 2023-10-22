package frc.robot.Components;

public class LifterAntiGrav {
    public static double calcLifterAntiGrav(double lifterAngle, double extension) {
        return 0 ;// -0.1 * Math.cos(Math.toRadians(lifterAngle)) * (20 + extension);
    }
}