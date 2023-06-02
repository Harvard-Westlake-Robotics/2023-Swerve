package frc.robot.Util;

import edu.wpi.first.wpilibj.Timer;

public class DeSpam {
    private int lastTime = 0;
    private int minTimeDiff;

    public DeSpam(int minTimeDiff) {
        this.minTimeDiff = minTimeDiff;
    }

    public boolean exec(Lambda func) {
        if (Timer.getFPGATimestamp() - lastTime > minTimeDiff) {
            func.run();
            return true;
        }
        return false;
    }
}
