package frc.robot.Util;

import frc.robot.Core.Time;

public class GetDTime {
    double lastTime = Double.MIN_VALUE;

    public double tick() {
        var temp = lastTime;
        lastTime = Time.getTimeSincePower();
        return lastTime - temp;
    }
}
