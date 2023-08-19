package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Motor.SparkMax;
import frc.robot.Drive.SwerveModule;
import frc.robot.Drive.SwerveModulePD;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class PlainMotor implements Tickable {
    private SwerveModulePD motor;

    PlainMotor(SwerveModulePD motor) {
        this.motor = motor;
        motor.setTurnTarget(0);
    }

    PlainMotor create(int motor) {


        var con = new PDController(0.08, 0.0);

        var leftBackEncoder = new AbsoluteEncoder(21, -82.969, true);
        var leftBackTurn = new SparkMax(7, true);
        var leftBackGo = new SparkMax(8, false);
        var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
        var leftBack = new SwerveModulePD(leftBackRaw, con, leftBackEncoder);

        var rightBackEncoder = new AbsoluteEncoder(23, 45.879, true);
        var rightBackTurn = new SparkMax(1, true);
        var rightBackGo = new SparkMax(2, false);
        var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
        var rightBack = new SwerveModulePD(rightBackRaw, con, rightBackEncoder);

        var leftFrontEncoder = new AbsoluteEncoder(20, -129.639, true);
        var leftFrontTurn = new SparkMax(5, true);
        var leftFrontGo = new SparkMax(6, false);
        var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
        var leftFront = new SwerveModulePD(leftFrontRaw, con, leftFrontEncoder);

        var rightFrontEncoder = new AbsoluteEncoder(22, 82.969, true);
        var rightFrontTurn = new SparkMax(3, true);
        var rightFrontGo = new SparkMax(4, false);
        var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
        var rightFront = new SwerveModulePD(rightFrontRaw, con, rightFrontEncoder);

        SwerveModulePD[] motors = {rightFront, leftFront, leftBack, rightBack};
        return new PlainMotor(motors[motor]);
    }

    void setVoltage(double voltage) {
        motor.setGoVoltage(voltage);
    }

    @Override
    public void tick(double secsSinceLastTick) {
        motor.tick(secsSinceLastTick);
    }
}
