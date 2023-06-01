package frc.robot.Drive;

import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Util.AngleMath;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class SwerveModulePD implements Tickable {
    SwerveModule swerve;
    PDController controller;
    AbsoluteEncoder coder;

    public SwerveModulePD(SwerveModule swerve, PDController con, AbsoluteEncoder coder) {
        this.swerve = swerve;
        this.controller = con;
        this.coder = coder;
    }

    Double turnTarget = null;

    public void tick(double dTime) {
        if (turnTarget != null) {
            swerve.setTurnVoltage(controller.solve(AngleMath.conformAngle(turnTarget) - AngleMath.conformAngle(-coder.absVal())));
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
