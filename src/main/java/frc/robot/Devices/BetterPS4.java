package frc.robot.Devices;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Util.Vector2;

public class BetterPS4 extends PS4Controller {
    public BetterPS4(int port) {
        super(port);
    }

    public Vector2 getLeftStick() {
        return new Vector2(getLeftX(), getLeftY());
    }

    public Vector2 getRightStick() {
        return new Vector2(getRightX(), getRightY());
    }
}
