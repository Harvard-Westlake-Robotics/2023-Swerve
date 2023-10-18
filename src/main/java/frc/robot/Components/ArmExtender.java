package frc.robot.Components;

import frc.robot.Devices.MotorController;

public class ArmExtender {
    MotorController motor;

    public ArmExtender(MotorController motor) {
        this.motor = motor;
    }

    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }
}
