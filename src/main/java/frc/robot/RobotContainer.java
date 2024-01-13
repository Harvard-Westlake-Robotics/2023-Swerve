package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Core.RobotPolicy;
import frc.robot.Core.Schedulable;
import frc.robot.Core.Scheduler;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.BetterPS4;
import frc.robot.Devices.LimeLight;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Drive.SwerveModule;
import frc.robot.Drive.SwerveModulePD;
import frc.robot.Util.PDConstant;

public class RobotContainer {

    static RobotPolicy init() {
        // PositionedDrive drive;
        Falcon shooter1;
        Falcon shooter2;
        {
            // var placeholderConstant = new PDConstant(0.1, 0);
            // var leftBackEncoder = new AbsoluteEncoder(21, 68.203125, true).offset(-90);
            // var leftBackTurn = new Falcon(8, false);
            // var leftBackGo = new Falcon(7, false);
            // var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
            // var leftBack = new SwerveModulePD(leftBackRaw, placeholderConstant,
            // leftBackEncoder);

            // var rightBackEncoder = new AbsoluteEncoder(23, 6.416015625,
            // true).offset(-90);
            // var rightBackTurn = new Falcon(2, false);
            // var rightBackGo = new Falcon(1, true);
            // var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
            // var rightBack = new SwerveModulePD(rightBackRaw, placeholderConstant,
            // rightBackEncoder);

            // var leftFrontEncoder = new AbsoluteEncoder(22, -5.09765625,
            // true).offset(-90);
            // var leftFrontTurn = new Falcon(6, false);
            // var leftFrontGo = new Falcon(5, false);
            // var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
            // var leftFront = new SwerveModulePD(leftFrontRaw, placeholderConstant,
            // leftFrontEncoder);

            // var rightFrontEncoder = new AbsoluteEncoder(20, -40.25390625,
            // true).offset(-90);
            // var rightFrontTurn = new Falcon(4, false);
            // var rightFrontGo = new Falcon(3, true);
            // var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
            // var rightFront = new SwerveModulePD(rightFrontRaw, placeholderConstant,
            // rightFrontEncoder);

            var shooterMotor1 = new Falcon(13, false);
            var shooterMotor2 = new Falcon(34, true);

            // drive = new PositionedDrive(leftFront, rightFront, leftBack, rightBack, 23.0,
            // 23.0);
            shooter1 = shooterMotor1;
            shooter2 = shooterMotor2;
        }
        return new RobotPolicy() {

            public void teleop() {
                var con = new BetterPS4(0);
                // LimeLight.setCamMode(false);
                // drive.setAlignmentThreshold(0.5);
                Scheduler.runTask(
                        new Schedulable() {
                            public void start() {
                            }

                            public void tick(double dTime) {
                                // drive.power(con.getLeftStick().getMagnitude() * 12.0, // voltage
                                // con.getLeftStick().getAngleDeg(), // go angle
                                // con.getRightX() * 12.0, // turn voltage
                                // false);
                                shooter1.setVoltage(6.0 * (con.getR2Axis() + 1));
                                shooter2.setVoltage(6.0 * (con.getR2Axis() + 1));
                            }

                            public void end() {
                            }
                        });
            }

            public void autonomous() {
                // LimeLight.setCamMode(true);
            }

            public void test() {

            }

            @Override
            public void disabled() {

            }
        };
    }
}
