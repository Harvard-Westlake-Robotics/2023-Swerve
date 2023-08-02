package frc.robot.Drive;

import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
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

    DeSpam dSpam = new DeSpam(0.2);

    public void power(double goVoltage, double goDirectionDeg, double turnVoltage) {
        goDirectionDeg = AngleMath.conformAngle(goDirectionDeg);
        int quadrant = 1;
        for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
            var turnVec = getTurnVec(quadrant)
                    .multiply(turnVoltage);
            var goVec = Vector2.fromAngleAndMag(goDirectionDeg, goVoltage);
            dSpam.exec(() -> {
                System.out.println("go angle target: " + goVec.getAngleDeg());
            });
            var vec = goVec.add(turnVec);

            module.setTurnTarget(vec.getTurnAngleDeg());
            module.setGoVoltage(vec.getMagnitude());

            quadrant++;
        }
    }

    /**
     * Gets the slope of a wheel in a given quadrant to make the robot turn
     * clockwise (right)
     * 2 ↗ ↘ 1
     * 3 ↖ ↙ 4
     * 
     * @param quadrant
     * @return
     */
    private static Vector2 getTurnVec(int quadrant) {
        var squareSide = 1.0 / Math.sqrt(2);
        return new Vector2(
                (quadrant == 1 || quadrant == 2) ? squareSide : -squareSide,
                (quadrant == 2 || quadrant == 3) ? squareSide : -squareSide);
    }

    public void stopGoPower() {
        for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
            module.setGoVoltage(0);
        }
    }

    public void tick(double dTime) {
        frontLeft.tick(dTime);
        frontRight.tick(dTime);
        backLeft.tick(dTime);
        backRight.tick(dTime);
    }
}