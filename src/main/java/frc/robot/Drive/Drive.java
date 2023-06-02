package frc.robot.Drive;

import frc.robot.Util.Tickable;

public class Drive implements Tickable {
    SwerveModulePD frontLeft;
    SwerveModulePD frontRight;
    SwerveModulePD backLeft;
    SwerveModulePD backRight;

    public Drive(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft, SwerveModulePD backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    public void setAngle(double angle) {
        frontLeft.setTurnTarget(angle);
        frontRight.setTurnTarget(angle);
        backLeft.setTurnTarget(angle);
        backRight.setTurnTarget(angle);
    }

    public void setGoVoltage(double volts) {
        frontLeft.setGoVoltage(volts);
        frontRight.setGoVoltage(volts);
        backLeft.setGoVoltage(volts);
        backRight.setGoVoltage(volts);
    }

    public void tick(double dTime) {
        frontLeft.tick(dTime);
        frontRight.tick(dTime);
        backLeft.tick(dTime);
        backRight.tick(dTime);
    }
}