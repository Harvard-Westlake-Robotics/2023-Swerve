package frc.robot.Drive;

import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class SwerveModulePD implements Tickable {
    SwerveModule swerve;
    PDController controller;

    public SwerveModulePD(SwerveModule swerve, PDController con) {
        this.swerve = swerve;
        this.controller = con;
    }

    Double turnTarget = null;

    public void tick(double dTime) {
        if (turnTarget != null) {
            swerve.setTurnVoltage(controller.solve(turnTarget - swerve.getTurnReading()));
        }
    }

    public void setTurnTarget(double degrees) {
        turnTarget = degrees;
    }

    public void setGoVoltage(double volts) {
        swerve.setGoVoltage(volts);
    }

    public void resetEncoder() {
        swerve.resetTurnReading();
    }
}
