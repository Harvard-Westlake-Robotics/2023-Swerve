package frc.robot.Components;

public class ArmExtenderAntiGrav {
    public static double getAntiGravVoltage(double armLiftDeg) {
        if (armLiftDeg < 15)
            return -0.8;
        return Math.cos(Math.toRadians(armLiftDeg)) * 0.45;
    }
}
