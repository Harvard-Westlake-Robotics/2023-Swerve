package frc.robot;

import frc.robot.Core.BetterRobot.BetterRobot;
import frc.robot.Core.BetterRobot.RobotPolicy;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Drive.SwerveModule;
import frc.robot.Drive.SwerveModulePD;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Container;

public class TestRobot extends BetterRobot {
    public RobotPolicy init() {
        var turnPD = new PDConstant(0.5, 0.0, 1).withMagnitude(0.3);

        var leftBackEncoder = new AbsoluteEncoder(21, 0, true);
        var leftBackTurn = new Falcon(8, true);
        var leftBackGo = new Falcon(7, false);
        var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
        var leftBack = new SwerveModulePD(leftBackRaw, turnPD, leftBackEncoder);

        return new RobotPolicy((scheduler) -> {
            PDController goHolder = new PDController(new PDConstant(1, 0));
            Container<Double> t = new Container<Double>(0.0);
            scheduler.registerTick((dTime) -> {
                System.out.println(leftBackEncoder.absVal());
            });
            
        });
    }
}
