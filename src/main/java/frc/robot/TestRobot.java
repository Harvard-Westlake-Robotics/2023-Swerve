package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Core.BetterRobot.BetterRobot;
import frc.robot.Core.BetterRobot.RobotPolicy;
import frc.robot.Devices.Motor.Falcon;

public class TestRobot extends BetterRobot {
    public RobotPolicy init() {
        var con = new PS4Controller(0);
        var f = new Falcon(61, false);
        return new RobotPolicy((scheduler) -> {
            scheduler.registerTick((double dTime) -> {
                f.setVoltage(2 * con.getLeftY());
            });
        });
    }
}
