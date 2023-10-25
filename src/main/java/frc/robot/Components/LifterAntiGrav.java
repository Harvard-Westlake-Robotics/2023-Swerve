package frc.robot.Components;

public class LifterAntiGrav {
    public static double calcLifterAntiGrav(double lifterAngle, double extension) {
        return -0.007 * Math.cos(Math.toRadians(lifterAngle)) * (30 + extension);
    }
}