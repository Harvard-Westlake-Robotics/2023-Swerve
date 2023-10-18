package frc.robot.Components;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class ArmLifter {
    MotorController left;
    MotorController right;

    public ArmLifter(MotorController left, MotorController right) {
        this.left = left;
        this.right = right;
    }
}
