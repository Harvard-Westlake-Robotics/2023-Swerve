package frc.robot;

import frc.robot.Auto.Positioning.PositioningSystem;
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
import frc.robot.Auto.Positioning.*;

public class RobotContainer {

    static RobotPolicy init() {
        PositionedDrive drive;
        var imu = new Imu(18);
        var turnPD = new PDController(new PDConstant(0.2, 0.5));

        {
            var placeholderConstant = new PDConstant(0.1, 0);
            var leftBackEncoder = new AbsoluteEncoder(21, 68.203125, true).offset(-90);
            var leftBackTurn = new Falcon(8, false);
            var leftBackGo = new Falcon(7, false);
            var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
            var leftBack = new SwerveModulePD(leftBackRaw, placeholderConstant, leftBackEncoder);

            var rightBackEncoder = new AbsoluteEncoder(23, 6.416015625, true).offset(-90);
            var rightBackTurn = new Falcon(2, false);
            var rightBackGo = new Falcon(1, true);
            var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
            var rightBack = new SwerveModulePD(rightBackRaw, placeholderConstant, rightBackEncoder);

            var leftFrontEncoder = new AbsoluteEncoder(22, -5.09765625, true).offset(-90);
            var leftFrontTurn = new Falcon(6, false);
            var leftFrontGo = new Falcon(5, false);
            var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
            var leftFront = new SwerveModulePD(leftFrontRaw, placeholderConstant, leftFrontEncoder);

            var rightFrontEncoder = new AbsoluteEncoder(20, -40.25390625, true).offset(-90);
            var rightFrontTurn = new Falcon(4, false);
            var rightFrontGo = new Falcon(3, true);
            var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
            var rightFront = new SwerveModulePD(rightFrontRaw, placeholderConstant, rightFrontEncoder);

            drive = new PositionedDrive(leftFront, rightFront, leftBack, rightBack, 23.0, 23.0);
        }
        var dspam = new DeSpam(0.3);
        return new RobotPolicy() {

            public void teleop() {
                var con = new BetterPS4(0);
                LimeLight.setCamMode(true);

                drive.setAlignmentThreshold(0.5);
                FieldPositioning fieldPositioning = new FieldPositioning(drive, imu, 0.0);

                Scheduler.registerTick(() -> {
                    dspam.exec(() -> {
                        System.out.println(
                                "pos: " + fieldPositioning.getPosition() + " angle: " + fieldPositioning.getAngle());
                    });
                    drive.power(ScaleInput.curve(con.getLeftStick().getMagnitude(), 1.5) * 12.0, // voltage
                            con.getLeftStick().getTurnAngleDeg() - fieldPositioning.getAngle() - 90, // go angle
                            ScaleInput.curve(con.getRightX(), 1) * 12.0,
                            // turn voltage
                            false);
                });
            }

            public void autonomous() {
                LimeLight.setCamMode(true);
            }

            public void test() {

            }

            @Override
            public void disabled() {

            }
        };
    }
}
