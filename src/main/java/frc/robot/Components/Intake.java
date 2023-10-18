package frc.robot.Components;

import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;
import frc.robot.Devices.MotorController;

public class Intake implements Tickable {
    MotorController angler;
    MotorController intake;
    PDController pdController;
    double wristTarget = 0;

    public Intake(MotorController angler, MotorController intake, PDConstant anglerConstant) {
        this.angler = angler;
        this.intake = intake;
        this.pdController = new PDController(anglerConstant);
    }

    public void setIntakeVolage(double voltage) {
        intake.setVoltage(voltage);
    }

    public void setWristTarget(double pos) {
        this.wristTarget = pos;
    }

    public void tick(double dTime) {
        angler.setVoltage(pdController.solve(wristTarget - (angler.getRevs() / 20)));
    }
}
