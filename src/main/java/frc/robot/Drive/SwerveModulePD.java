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
            var frontFaceError = AngleMath.getDelta(coder.absVal(), turnTarget);
            var backFaceError = AngleMath.getDelta(coder.absVal(), turnTarget - 180);
            var error = AngleMath.minMagnitude(frontFaceError, backFaceError);

            boolean isFrontFacing = error == frontFaceError;

            if (isFrontFacing != this.frontFacing) {
                this.frontFacing = isFrontFacing;
                swerve.setGoVoltage(frontFacing ? voltage : -voltage);
            }

            var correctionVoltage = controller.solve(error);

            swerve.setTurnVoltage(correctionVoltage);
        }
    }

    /**
     * sets the turn target for the module from forward going to the right
     * 
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

    /**
     * Gets the angle of the module from the front positive to the right
     */
    public double getTurnAngle() {
        return coder.absVal();
    }

    /**
     * Gets the angle of the module in standard position
     */
    public double getAngle() {
        return AngleMath.toStandardPosAngle(getTurnAngle());
    }

    public double getDist() {
        return swerve.getGoReading();
    }
}
