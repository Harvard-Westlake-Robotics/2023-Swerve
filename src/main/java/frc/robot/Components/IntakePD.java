package frc.robot.Components;

import frc.robot.Util.DeSpam;
import frc.robot.Util.LERP;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class IntakePD implements Tickable {
    Intake intake;
    ArmLifter lifter;
    ArmExtender extender;
    PDController angController;
    double antiGravIntensity;
    LERP intakeAnglerTarget = new LERP(260);

    public IntakePD(Intake intake, PDConstant anglerConstant, double antiGravIntensity, ArmLifter lifter) {
        this.intake = intake;
        this.lifter = lifter;
        this.antiGravIntensity = antiGravIntensity;
        this.angController = new PDController(anglerConstant);
    }

    public void setIntakeVoltage(double voltage) {
        intake.setIntakeVolage(voltage);
    }

    public void setIntakeAnglerTarget(double target) {
        intakeAnglerTarget.set(target);
    }

    public void unsafeAnglerSetIntakeVoltage(double voltage) {
        intake.setAnglerVoltage(voltage);
    }

    public void resetAnglerEncoder() {
        intake.resetAnglerEncoder();
    }

    DeSpam dSpam = new DeSpam(1);

    public void tick(double dTime) {
        intakeAnglerTarget.tick(dTime);
        final double antiGrav = -antiGravIntensity
                * Math.sin(Math.toRadians(intake.getAnglerPositionDeg() - lifter.getAngleDeg()));
        final double pdCorrect = angController.solve(intakeAnglerTarget.get() - intake.getAnglerPositionDeg());
        intake.setAnglerVoltage(pdCorrect + antiGrav);
        dSpam.exec(() -> {
            System.out.println("antigrav: " + antiGrav);
        });
    }
}
