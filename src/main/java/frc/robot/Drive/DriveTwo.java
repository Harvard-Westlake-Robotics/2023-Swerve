package frc.robot.Drive;

import frc.robot.Util.Tickable;
import frc.robot.Util.Vector2;

public class DriveTwo implements Tickable {
    SwerveModulePD frontLeft;
    SwerveModulePD frontRight;
    SwerveModulePD backLeft;
    SwerveModulePD backRight;

    public DriveTwo(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft,
            SwerveModulePD backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    public void power(double goPower, double goDirectionDeg, double turnVoltage) {
        int quadrant = 0;
        for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
            var squareSide = 1.0 / Math.sqrt(2);
            var turnVec = new Vector2(
                    (quadrant == 1 || quadrant == 2) ? squareSide : -squareSide,
                    (quadrant == 2 || quadrant == 3) ? squareSide : -squareSide)
                    .multiply(turnVoltage);
            var goVec = Vector2.fromAngleAndMag(goDirectionDeg, goPower);
            var vec = turnVec.add(goVec);

            module.setTurnTarget(vec.getAngleDeg());
            module.setGoVoltage(vec.getMagnitude());

            quadrant++;
        }
    }

    public void tick(double dTime) {
        frontLeft.tick(dTime);
        frontRight.tick(dTime);
        backLeft.tick(dTime);
        backRight.tick(dTime);
    }
}