package frc.robot.Components;

import frc.robot.Devices.AnyMotor;

public class ArmExtender {
    AnyMotor left;
    AnyMotor right;

    public ArmExtender(AnyMotor left, AnyMotor right) {
        this.left = left;
        this.right = right;
    }

    public void setVoltage(double voltage) {
        if (voltage < 0 && left.getDegrees() <= 0) {
            setVoltage(0);
            return;
        }
        left.setVoltage(voltage);
        right.setVoltage(voltage);
    }

    public double getExtensionInches() {
        // / 41.0;
        return 0;
    }
}
