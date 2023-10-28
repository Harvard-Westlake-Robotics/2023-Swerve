package frc.robot.Components;

import frc.robot.Devices.AnyMotor;

public class Intake {
    AnyMotor angler;
    AnyMotor intake;
    double wristTarget = 0;

    public Intake(AnyMotor angler, AnyMotor intake) {
        this.angler = angler;
        this.intake = intake;
    }

    public void setIntakeVolage(double voltage) {
        intake.setVoltage(voltage);
    }

    public void resetAnglerEncoder() {
        angler.resetEncoder();
    }

    public void setAnglerVoltage(double voltage) {
        angler.setVoltage(voltage);
    }

    public double getAnglerPositionDeg() {
        return angler.getRevs() * (90.0 / 13.63) - 50;
    }
}
