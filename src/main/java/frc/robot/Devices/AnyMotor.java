package frc.robot.Devices;

import frc.robot.Util.MathPlus;

public abstract class AnyMotor {
    Double maxSlew;
    boolean isReversed;

    public abstract int getID();

    public AnyMotor(boolean isReversed, double maxSlew) {
        super();

        this.maxSlew = maxSlew;
        this.isReversed = isReversed;
    }

    public AnyMotor(boolean isReversed) {
        super();

        this.isReversed = isReversed;
        maxSlew = null;
    }

    public abstract void setCurrentLimit(int amps);

    protected abstract void uSetVoltage(double voltage);

    protected abstract double uGetRevs();
    
    double resetPos = 0;

    public void resetEncoder() {
        resetPos = uGetRevs();
    };

    public double getRevs() {
        var pos = uGetRevs() - resetPos;
        return (isReversed ? -pos : pos);
    }

    public double getDegrees() {
        return getRevs() * 360.0;
    }

    public double getRadians() {
        return getRevs() * 2.0 * Math.PI;
    }

    public abstract void stop();

    public void setVoltage(double volts) {
        if (Math.abs(volts) > 12.0)
            uSetVoltage(volts > 0 ? MathPlus.boolFac(!isReversed) : MathPlus.boolFac(isReversed));
            // throw new Error("Illegal voltage");
        uSetVoltage(isReversed ? -volts : volts);
    }

    public void setPercentVoltage(double percent) {
        setVoltage(percent * (12.0 / 100.0));
    }

    public void tick(double dTime) {

    }
}