package frc.robot.Components;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class ArmExtender {
    MotorController motor;

    public ArmExtender(MotorController motor) {
        this.motor = motor;
    }

    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }
}
