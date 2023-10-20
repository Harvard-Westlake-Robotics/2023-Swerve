package frc.robot.Components;

import frc.robot.Devices.AnyMotor;

public class ArmLifter {
    AnyMotor left;
    AnyMotor right;

    public ArmLifter(AnyMotor left, AnyMotor right) {
        this.left = left;
        this.right = right;
    }

    public void setVoltage(double volts) {
        left.setVoltage(volts);
        right.setVoltage(volts);
    }

    public double getAngleDeg() {
        return left.getRevs() * (90.0 / -57.0);
    }
}
