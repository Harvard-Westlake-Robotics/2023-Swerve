package frc.robot;

import frc.robot.Core.BetterRobot.BetterRobot;
import frc.robot.Core.BetterRobot.RobotPolicy;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Motor.SparkMax;
import frc.robot.Drive.SwerveModule;
import frc.robot.Drive.SwerveModulePD;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;

public class TestRobot extends BetterRobot {
    public RobotPolicy init() {
        var turnPD = new PDConstant(0.5, 0.0, 1).withMagnitude(0.3);

        var leftFrontEncoder = new AbsoluteEncoder(20, -129.639, true);
        var leftFrontTurn = new SparkMax(5, true);
        var leftFrontGo = new SparkMax(6, false);
        var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
        var leftFront = new SwerveModulePD(leftFrontRaw, turnPD, leftFrontEncoder);

        return new RobotPolicy((scheduler) -> {
            PDController goHolder = new PDController(new PDConstant(1, 0));
            scheduler.registerTick((dTime) -> {
                final var error = goHolder.solve(0 - leftFront.getDist());
                leftFront.setGoVoltage(error * 2.9);
            });
        });
    }
}
