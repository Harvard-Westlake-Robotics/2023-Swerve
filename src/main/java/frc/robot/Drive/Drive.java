package frc.robot.Drive;

import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
import frc.robot.Util.PDConstant;
import frc.robot.Util.Tickable;
import frc.robot.Util.Vector2;

public class Drive implements Tickable {
    public SwerveModulePD frontLeft;
    protected SwerveModulePD frontRight;
    protected SwerveModulePD backLeft;
    protected SwerveModulePD backRight;
    protected double widthInches;
    protected double lengthInches;
    protected double circumferenceInches;

    private double alignmentThreshold = 1;

    // how aligned the motors need to be to their ideals before going (0, 1]
    public void setAlignmentThreshold(double newThreshold) {
        if (newThreshold <= 0 || newThreshold > 1)
            throw new Error("Threshold must be in range (0, 1]");
        this.alignmentThreshold = newThreshold;
    }

    public Drive(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft,
            SwerveModulePD backRight, double widthInches, double lengthInches) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.widthInches = widthInches;
        this.lengthInches = lengthInches;
        this.circumferenceInches = 2 * Math.PI
                * Math.sqrt((widthInches * widthInches + lengthInches * lengthInches) / 2);
    }

    DeSpam deSpam = new DeSpam(0.3);

    Vector2[] moduleTargets;

    /**
     * Turns and goes the robot with given voltages and directions
     * 
     * @param goVoltage      Directional power in volts
     * @param goDirectionDeg Direction to go straight in degrees ANGLE IN STANDARD
     *                       POSITION
     * @param turnVoltage    Rotational power in volts
     */
    public void power(double goVoltage, double goDirectionDeg, double turnVoltage, boolean errorOnLargeVoltage) {
        if (errorOnLargeVoltage) {
            if (Math.abs(goVoltage) > 12)
                throw new Error("Illegally large voltage - goVoltage");
            if (Math.abs(turnVoltage) > 12)
                throw new Error("Illegally large voltage - turnVoltage");
        }

        goDirectionDeg = AngleMath.conformAngle(goDirectionDeg);

        if (moduleTargets == null)
            moduleTargets = new Vector2[4];

        for (int quadrant = 1; quadrant <= 4; quadrant++) {
            var turnVec = getTurnVec(quadrant)
                    .multiply(turnVoltage);
            var goVec = Vector2.fromAngleAndMag(goDirectionDeg, goVoltage);
            var vec = goVec.add(turnVec);

            moduleTargets[quadrant - 1] = vec;
        }

        // normalizes voltages
        double largestVoltage = 0;
        for (Vector2 tar : moduleTargets) {
            if (Math.abs(tar.getMagnitude()) > 12)
                largestVoltage = Math.abs(tar.getMagnitude());
        }
        if (largestVoltage > 12) {
            for (int module = 0; module < 4; module++) {
                final double fac = 12.0 / largestVoltage;
                final var tar = moduleTargets[module];
                moduleTargets[module] = tar.withMagnitude(tar.getMagnitude() * fac);
            }
        }
    }

    public void power(double goVoltage, double goDirectionDeg, double turnVoltage) {
        this.power(goVoltage, goDirectionDeg, turnVoltage, true);
    }
    public void tester (double goDirectionDeg) {
        System.out.println("Front Left: " + frontLeft.getAngle());
        System.out.println("Front Right: " + frontRight.getAngle());
        System.out.println("Back Left: " + backLeft.getAngle());
        System.out.println("Back Right: " + backRight.getAngle());
        
    }
    public void setConstants(PDConstant constant) {
        frontLeft.setConstants(constant);
        frontRight.setConstants(constant);
        backLeft.setConstants(constant);
        backRight.setConstants(constant);
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

    public void reset() {

    }

    public void stopGoPower() {
        moduleTargets = null;
        for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
            module.setGoVoltage(0);
        }
    }

    public void tick(double dTime) {
        double error = 0;
        double total = 0;
        if (moduleTargets != null) {
            int quadrant = 1;
            for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
                final var tar = moduleTargets[quadrant - 1];
                error += tar.getMagnitude()
                        * (Math.abs(AngleMath.getDeltaReversable(module.getAngle(), tar.getAngleDeg()))
                                / 90.0);
                total += tar.getMagnitude();
                quadrant++;
            }
        }

        int quadrant = 1;
        for (SwerveModulePD module : new SwerveModulePD[] { frontRight, frontLeft, backLeft, backRight }) {
            if (moduleTargets != null) {
                var vec = moduleTargets[quadrant - 1];
                if (error / total < 1 - alignmentThreshold) {
                    module.setGoVoltage(vec.getMagnitude());
                } else {
                    module.setGoVoltage(0);
                }
                module.setTurnTarget(vec.getTurnAngleDeg());
            } else {
                module.setGoVoltage(0);
            }
            quadrant++;
        }
        frontLeft.tick(dTime);
        frontRight.tick(dTime);
        backLeft.tick(dTime);
        backRight.tick(dTime);
    }
}