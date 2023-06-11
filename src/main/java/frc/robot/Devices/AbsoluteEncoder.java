package frc.robot.Devices;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.Util.AngleMath;

public class AbsoluteEncoder {
    CANCoder coder;
    double zeroReading;
    boolean reversed = false;

    private double reverse(double num) {
        if (!reversed)
            return num;
        else
            return -num;
    }

    public AbsoluteEncoder(int canPort) {
        this(canPort, false);
    }

    public AbsoluteEncoder(int canPort, boolean isReversed) {
        this(canPort, 0, isReversed);
    }

    public AbsoluteEncoder(int canPort, double zeroReading, boolean isReversed) {
        this.coder = new CANCoder(canPort);
        coder.configFactoryDefault();
        coder.configAbsoluteSensorRange(AbsoluteSensorRange.Signed_PlusMinus180);
        this.reversed = isReversed;
        this.zeroReading = zeroReading;
    }

    /**
     * Degrees, -180 to 180
     */
    public double absVal() {
        return AngleMath.conformAngle( reverse(coder.getAbsolutePosition() + zeroReading));
    }
}
