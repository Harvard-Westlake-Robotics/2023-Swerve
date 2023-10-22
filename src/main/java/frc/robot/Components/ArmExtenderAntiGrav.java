package frc.robot.Components;

public class ArmExtenderAntiGrav {
    public static double getAntiGravVoltage(double armLiftDeg) {
        return Math.cos(Math.toRadians(armLiftDeg)) * 0.3;
    }
}
