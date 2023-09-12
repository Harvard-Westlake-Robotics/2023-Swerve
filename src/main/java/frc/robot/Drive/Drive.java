package frc.robot.Drive;

import frc.robot.Util.AngleMath;
import frc.robot.Util.Tickable;
import frc.robot.Util.Vector2;

public class Drive implements Tickable {
    public SwerveModulePD frontLeft;
    public SwerveModulePD frontRight;
    public SwerveModulePD backLeft;
    public SwerveModulePD backRight;
    protected double widthInches;
    protected double lengthInches;
    protected double circumferenceInches;

    public Drive(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft,
            SwerveModulePD backRight, double widthInches, double lengthInches) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.widthInches = widthInches;
        this.lengthInches = lengthInches;
        this.circumferenceInches = Math.PI * Math.sqrt(widthInches * widthInches + lengthInches * lengthInches);
    }



    /**
     * Turns and goes the robot with given voltages and directions
     * 
     * @param goVoltage      Directional power in volts
     * @param goDirectionDeg Direction to go straight in degrees ANGLE IN STANDARD
     *                       POSITION
     * @param turnVoltage    Rotational power in volts
     */
    public void power(double goVoltage, double goDirectionDeg, double turnVoltage) {
        goDirectionDeg = AngleMath.conformAngle(goDirectionDeg);
        int quadrant = 1;
        for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
            var turnVec = getTurnVec(quadrant)
                    .multiply(turnVoltage);
            var goVec = Vector2.fromAngleAndMag(goDirectionDeg, goVoltage);
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
    protected static Vector2 getTurnVec(int quadrant) {
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

    public String toErrorString() {
        return "fr: " + frontRight.getAvgError() + " fl: " + frontLeft.getAvgError() + "\n bl: " + backLeft.getAvgError() + " br: " + backRight.getAvgError();
    }
}