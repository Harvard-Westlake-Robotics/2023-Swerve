package frc.robot.Devices;

import com.ctre.phoenixpro.configs.CANcoderConfiguration;
import com.ctre.phoenixpro.hardware.CANcoder;
import com.ctre.phoenixpro.signals.AbsoluteSensorRangeValue;

import frc.robot.Util.AngleMath;

public class AbsoluteEncoder {
    CANcoder coder;
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

    public AbsoluteEncoder offset(double offset) {
        zeroReading = AngleMath.conformAngle(zeroReading - offset);
        return this;
    }

    public AbsoluteEncoder(int canPort, double zeroReading, boolean isReversed) {
        this.reversed = isReversed;
        this.zeroReading = zeroReading;
        
        this.coder = new CANcoder(canPort);
        var configs = new CANcoderConfiguration();
        configs.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Signed_PlusMinusHalf;
        coder.getConfigurator().apply(configs);
    }

    /**
     * Degrees, -180 to 180
     */
    public double absVal() {
        return AngleMath.conformAngle(reverse(coder.getAbsolutePosition().getValue() * 360.0 - zeroReading));
    }
}
