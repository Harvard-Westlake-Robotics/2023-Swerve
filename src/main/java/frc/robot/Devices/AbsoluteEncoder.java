package frc.robot.Devices;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

public class AbsoluteEncoder {
    CANCoder coder;

    public AbsoluteEncoder(int canPort) {
        this.coder = new CANCoder(canPort);
        coder.configFactoryDefault();
        coder.configAbsoluteSensorRange(AbsoluteSensorRange.Signed_PlusMinus180);
    }

    /**
     * Degrees, -180 to 180
     */
    public double absVal() {
        return coder.getAbsolutePosition();
    }
}
