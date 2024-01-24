package frc.robot;

import frc.robot.Core.RobotPolicy;
import frc.robot.Core.Scheduler;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.BetterPS4;
import frc.robot.Devices.Imu;
import frc.robot.Devices.LimeLight;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Drive.SwerveModule;
import frc.robot.Drive.SwerveModulePD;
import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.ScaleInput;
import frc.robot.Util.Vector2;
import frc.robot.Auto.Drive.AutonomousDrive;
import frc.robot.Auto.Positioning.*;

public class RobotContainer {

    static RobotPolicy init() {
        PositionedDrive drive;
        LimeLight limeLight;
        var imu = new Imu(18);
        var turnPD = new PDController(new PDConstant(0.03, 0.2));

        {
            var placeholderConstant = new PDConstant(0, 0);
            var leftBackEncoder = new AbsoluteEncoder(21, 68.203125, false).setOffset(-90);
            var leftBackTurn = new Falcon(8, true);
            var leftBackGo = new Falcon(7, false);
            var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
            var leftBack = new SwerveModulePD(leftBackRaw, placeholderConstant, leftBackEncoder);

            var rightBackEncoder = new AbsoluteEncoder(23, 6.416015625, false).setOffset(-90);
            var rightBackTurn = new Falcon(2, true);
            var rightBackGo = new Falcon(1, true);
            var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
            var rightBack = new SwerveModulePD(rightBackRaw, placeholderConstant, rightBackEncoder);

            var leftFrontEncoder = new AbsoluteEncoder(22, -5.09765625, false).setOffset(-90);
            var leftFrontTurn = new Falcon(6, true);
            var leftFrontGo = new Falcon(5, false);
            var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
            var leftFront = new SwerveModulePD(leftFrontRaw, placeholderConstant, leftFrontEncoder);

            var rightFrontEncoder = new AbsoluteEncoder(20, -40.25390625, false).setOffset(-90);
            var rightFrontTurn = new Falcon(4, true);
            var rightFrontGo = new Falcon(3, true);
            var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
            var rightFront = new SwerveModulePD(rightFrontRaw, placeholderConstant, rightFrontEncoder);

            limeLight = new LimeLight();

            drive = new PositionedDrive(leftFront, rightFront, leftBack, rightBack, 23.0, 23.0);

        }
        var dspam = new DeSpam(0.3);

        final var con = new BetterPS4(0);
        final var fieldPositioning = new FieldPositioning(drive, imu, limeLight, new Position(0, new Vector2(0, 0)));

        return new RobotPolicy() {

            public void teleop() {
                limeLight.setCamMode(true);

                drive.setAlignmentThreshold(0.5);
                var constants = new PDConstant(0.18, 0).withMagnitude(0.5);
                drive.setConstants(constants);

                Scheduler.registerTick(() -> {
                    final var displacementFromTar = new Vector2(327.87, 34.25).minus(fieldPositioning.getPosition());
                    // 2 82.1238544369317, 32.670822887202704
                    var correction = -turnPD.solve(AngleMath.getDelta(displacementFromTar.getTurnAngleDeg() + 90,
                            fieldPositioning.getTurnAngle()));
                    dspam.exec(() -> {
                        System.out.println(
                                "pos: " + fieldPositioning.getPosition() + " current angle: "
                                        + fieldPositioning.getTurnAngle() + " target angle: "
                                        + AngleMath.conformAngle(displacementFromTar.getTurnAngleDeg() + 90)
                                        + " correction: " + correction);
                    });
                    if ((con.getLeftStick().getMagnitude() + Math.abs(con.getRightX()) > 0.1) || con.getR1Button())
                        drive.power(ScaleInput.curve(con.getLeftStick().getMagnitude(), 1.5) * 12.0, // voltage
                                -con.getLeftStick().getAngleDeg() - fieldPositioning.getTurnAngle(), // go angle
                                !con.getR1Button() ? con.getRightX() * -12.0
                                        : correction,
                                // turn voltage
                                // 0,
                                false);
                    else
                        drive.power(0, 0, 0);
                });
            }

            public void autonomous() {
                drive.setAlignmentThreshold(0.5);
                limeLight.setCamMode(true);

                AutonomousDrive autoD = new AutonomousDrive(drive, fieldPositioning, new PDConstant(5, 0),
                        new PDConstant(1, 0));

                Scheduler.setTimeout(() -> {
                    drive.reset();
                    Scheduler.registerTick(() -> {
                        autoD.targetX -= 0.5 * 0.02;
                    });
                }, 2);
            }

            public void test() {
                // var placeholderConstant = new PDConstant(0.1, 0);
                // var rightFrontEncoder = new AbsoluteEncoder(20, -40.25390625,
                // false).setOffset(-90);
                // var rightFrontTurn = new Falcon(4, true);
                // var rightFrontGo = new Falcon(3, true);
                // var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
                // var rightFront = new SwerveModulePD(rightFrontRaw, placeholderConstant,
                // rightFrontEncoder);

                // DeSpam deSpam = new DeSpam(0.3);

                // Scheduler.registerTick(() -> {
                // deSpam.exec(() -> {
                // System.out.println(rightFront.getDist());
                // });
                // });

                var placeholderConstant = new PDConstant(0.1, 0);
                var leftBackEncoder = new AbsoluteEncoder(21, 68.203125, false).setOffset(-90);
                var leftBackTurn = new Falcon(8, true);
                var leftBackGo = new Falcon(7, false);
                var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
                var leftBack = new SwerveModulePD(leftBackRaw, placeholderConstant, leftBackEncoder);

                var rightBackEncoder = new AbsoluteEncoder(23, 6.416015625, false).setOffset(-90);
                var rightBackTurn = new Falcon(2, true);
                var rightBackGo = new Falcon(1, true);
                var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
                var rightBack = new SwerveModulePD(rightBackRaw, placeholderConstant, rightBackEncoder);

                var leftFrontEncoder = new AbsoluteEncoder(22, -5.09765625, false).setOffset(-90);
                var leftFrontTurn = new Falcon(6, true);
                var leftFrontGo = new Falcon(5, false);
                var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
                var leftFront = new SwerveModulePD(leftFrontRaw, placeholderConstant, leftFrontEncoder);

                var rightFrontEncoder = new AbsoluteEncoder(20, -40.25390625, false).setOffset(-90);
                var rightFrontTurn = new Falcon(4, true);
                var rightFrontGo = new Falcon(3, true);
                var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
                var rightFront = new SwerveModulePD(rightFrontRaw, placeholderConstant, rightFrontEncoder);

                leftBack.setTurnTarget(0);
                rightBack.setTurnTarget(0);
                leftFront.setTurnTarget(0);
                rightFront.setTurnTarget(0);

                DeSpam dSpam = new DeSpam(1);

                Scheduler.registerTick(() -> {
                    dSpam.exec(() -> {
                        System.out.println(rightFront.getDist());
                    });
                });
            }

            @Override
            public void disabled() {

            }
        };
    }

    @Override
    public String toString() {
        return "RobotContainer [Michael Barr Was Here]";
    }
}
