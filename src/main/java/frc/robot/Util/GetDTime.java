package frc.robot.Util;

public class GetDTime {
    double lastTime = Double.MIN_VALUE;
    public double tick() {
        var temp = lastTime;
        lastTime = Time.getTimeSincePower();
        return lastTime - temp;
    }
}
