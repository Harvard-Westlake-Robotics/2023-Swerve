package frc.robot.Drive;

import frc.robot.Devices.Motor.SparkMax;
import frc.robot.Util.AngleMath;

public class SwerveModule {
    private SparkMax turn;
    private SparkMax go;

    public SwerveModule(SparkMax turn, SparkMax go) {
        this.turn = turn;
        this.go = go;

        turn.setBrake(false); // TODO: undoo

        turn.resetEncoder();
        go.resetEncoder();
    }

    public void setGoBrake(boolean brake) {
        this.go.setBrake(brake);
    }

    public void setGoVoltage(double voltage) {
        go.setVoltage(voltage);
    }

    public void setTurnVoltage(double voltage) {
        turn.setVoltage(voltage);
    }

    public void resetTurnReading() {
        turn.resetEncoder();
    }

    public double getUnconformedTurnReading() {
        return turn.getDegrees() / 12.8;
    }

    /**
     * Degrees (turn)
     */
    public double getTurnReading() {
        return AngleMath.conformAngle(getUnconformedTurnReading()); // 12.8 motor turns per 360 deg wheel turn
    }

    public void resetGoReading() {
        go.resetEncoder();
    }

    /**
     * inches
     */
    public double getGoReading() {
        final double turnCompensation = 3.75 * (getUnconformedTurnReading() / 180.0);
        return turnCompensation + (go.getDegrees() / 360.0) / 6.75 * (3.82 * Math.PI); // 6.75 rotations of the motor to
                                                                                       // 1 rotation of the
        // wheel - 3.82 inch diameter
    }
}
