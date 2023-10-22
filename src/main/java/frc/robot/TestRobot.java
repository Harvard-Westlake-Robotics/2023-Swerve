package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Core.BetterRobot.BetterRobot;
import frc.robot.Core.BetterRobot.RobotPolicy;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Util.DeSpam;

public class TestRobot extends BetterRobot {
    public RobotPolicy init() {
        var joystick = new Joystick(1);
        var leftFrontEncoder = new AbsoluteEncoder(22, -5.09765625, true).offset(-90);
        var leftFrontTurn = new Falcon(6, false);
        var intakeAimer = new Falcon(33, true);
        return new RobotPolicy((scheduler) -> {
            var dspam = new DeSpam(1);
            scheduler.registerTick((dT) -> {
                intakeAimer.setPercentVoltage(joystick.getY() * 100);
            });
        });
    }
}
