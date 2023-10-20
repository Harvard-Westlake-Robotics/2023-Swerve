package frc.robot.Util;

public class LERP implements Tickable {
    Double val;
    Double tar;
    double rate;

    public LERP(double rate) {
        this.rate = rate;
    }

    public double get() {
        if (val == null)
            throw new Error("Lerp val was never set");
        return val;
    }

    public void set(double next) {
        tar = next;
    }

    public void tick(double dTime) {
        if (val == null)
            val = tar;
        if (val == null)
            return;
        val += Math.signum(tar - val) * Math.min(Math.abs(rate * dTime), Math.abs(tar - val));
    }
}