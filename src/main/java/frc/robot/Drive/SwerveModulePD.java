package frc.robot.Drive;

import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class SwerveModulePD implements Tickable {
    double id = Math.random();
    SwerveModule swerve;
    PDController controller;
    AbsoluteEncoder coder;

    public SwerveModulePD(SwerveModule swerve, PDController con, AbsoluteEncoder coder) {
        this.swerve = swerve;
        this.controller = con;
        this.coder = coder;
    }

    Double turnTarget = null;

    DeSpam logError = new DeSpam(500);

    public void tick(double dTime) {
        if (turnTarget != null) {
            var frontFaceError = AngleMath.getDelta(turnTarget, coder.absVal());
            var backFaceError = AngleMath.getDelta(turnTarget - 180, coder.absVal());
            var error = AngleMath.minMagnitude(frontFaceError, backFaceError);
            logError.exec(() -> {
                System.out.println("error: " + error + " id: " + id);
            });
            boolean isFrontFacing = error == frontFaceError;

            if (isFrontFacing != this.frontFacing) {
                this.frontFacing = isFrontFacing;
                swerve.setGoVoltage(frontFacing ? voltage : -voltage);
            }

            swerve.setTurnVoltage(-controller.solve(error));
        }
    }

    /**
     * sets the turn target for the module from forward going to the right
     * @param degrees
     */
    public void setTurnTarget(double degrees) {
        turnTarget = AngleMath.conformAngle(degrees);
    }

    boolean frontFacing = true;
    double voltage = 0;

    public void setGoVoltage(double volts) {
        voltage = volts;
        swerve.setGoVoltage(frontFacing ? volts : -volts);
    }
}
