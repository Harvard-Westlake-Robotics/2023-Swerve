// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Auto.Drive.AutonomousDrive;
import frc.robot.Auto.Drive.RobotPosition;
import frc.robot.Components.ArmExtender;
import frc.robot.Components.ArmExtenderAntiGrav;
import frc.robot.Components.ArmLifter;
import frc.robot.Components.ExtenderPD;
import frc.robot.Components.Intake;
import frc.robot.Components.IntakePD;
import frc.robot.Components.LifterAntiGrav;
import frc.robot.Components.LifterPD;
import frc.robot.Core.Scheduler;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Imu;
import frc.robot.Devices.Motor.Falcon;
import frc.robot.Drive.*;
import frc.robot.Util.AngleMath;
import frc.robot.Util.CancelablePromise;
import frc.robot.Util.Container;
import frc.robot.Util.Lambda;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Promise;
import frc.robot.Util.ScaleInput;
import frc.robot.Util.Vector2;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  final boolean testing = false;

  Scheduler scheduler = new Scheduler();

  PS4Controller con;
  Joystick joystick;

  PositionedDrive drive;
  ArmLifter lifter;
  ArmExtender extender;
  Imu imu;
  IntakePD intake;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Devices

    this.con = new PS4Controller(0);

    this.joystick = new Joystick(1);

    this.imu = new Imu(18);

    // Components

    var rightArmRaise = new Falcon(10, true);
    var leftArmRaise = new Falcon(9, false);
    this.lifter = new ArmLifter(leftArmRaise, rightArmRaise);
    var extenderMotorLeft = new Falcon(34, false);
    var extenderMotorRight = new Falcon(13, true);
    this.extender = new ArmExtender(extenderMotorLeft, extenderMotorRight);

    var intakeMotor = new Falcon(61, true);
    var intakeAimer = new Falcon(33, true);
    var intake = new Intake(intakeAimer, intakeMotor);
    var intakeAnglerController = new PDConstant(0.4, 0.6);
    this.intake = new IntakePD(intake, intakeAnglerController, 2.8, lifter);

    // Drive

    var placeholderConstant = new PDConstant(0, 0);

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

    this.drive = new PositionedDrive(leftFront, rightFront, leftBack, rightBack, 23, 23, () -> {
      return AngleMath.toStandardPosAngle(this.imu.getTurnAngle());
    }); // TODO: figure out actual robot dimensions
  }

  int driveGone = 0;
  double angle;

  @Override
  public void autonomousInit() {
    scheduler.clear();
    var angle = imu.getTurnAngle();
    drive.reset();
    drive.setAlignmentThreshold(0.15);

    var con = new PDConstant(0.1, 0.07);
    drive.setConstants(con);

    scheduler.registerTick(drive);
    var pos =
    var goPD = new PDConstant(1, 4, 0.2).withMagnitude(0.3);
    var turnPD = new PDConstant(0.5, 0, 1.0).withMagnitude(0.3);
    var position = new RobotPosition(, angle)
    var autoDrive = new AutonomousDrive(drive, goPD, turnPD);
    autoDrive.reset();
    // scheduler.registerTick(drive);
    // Promise.immediate().then(() -> {
    // return autoDrive.exec(
    // new GoStraight(50, 100, 90));
    // });
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    scheduler.tick();
  }

  @Override
  public void teleopInit() {
    scheduler.clear();

    var constants = new PDConstant(0.18, 0).withMagnitude(0.5);
    drive.setConstants(constants);
    drive.reset();
    drive.setAlignmentThreshold(0.7);
    scheduler.registerTick(drive);

    LifterPD lifterPD = new LifterPD(lifter, new PDConstant(1.5, 2, 7.0));
    var liftPreseting = new Container<CancelablePromise>(null);
    var extenderPD = new ExtenderPD(extender, new PDConstant(2, 0.7, 4.0), lifter);
    var armExtendPreseting = new Container<CancelablePromise>(null);

    imu.resetYaw();

    intake.setIntakeAnglerTarget(-50);

    var driveTurnController = new PDController(new PDConstant(0.13, 0.7, 6.0));
    scheduler.registerTick((double dTime) -> {
      // TODO: figure out why pink ps5 has inverted y axis (inverted below)
      var goVec = new Vector2(con.getLeftX(), -con.getLeftY());

      final var TURN_CURVE_INTENSITY = 18;
      final var GO_CURVE_INTENSITY = 5;

      var goVoltage = ScaleInput.curve(goVec.getMagnitude() * 100, GO_CURVE_INTENSITY) * (12.0 / 100.0);
      var turnVoltage = ScaleInput.curve(con.getRightX() * 100, TURN_CURVE_INTENSITY) * (12.0 / 100.0);
      if (con.getCrossButton())
        turnVoltage = driveTurnController.solve(AngleMath.getDelta(180, imu.getTurnAngle()));
      if (con.getCircleButton())
        turnVoltage = driveTurnController.solve(AngleMath.getDelta(90, imu.getTurnAngle()));

      if (Math.abs(goVoltage) > 12) {
        goVoltage = 12 * Math.signum(goVoltage);
      }

      if (goVec.getMagnitude() > 0.05 || Math.abs(turnVoltage) > 0.05) {
        drive.power(goVoltage, goVec.getAngleDeg() - imu.getTurnAngle(), turnVoltage);
      } else {
        drive.stopGoPower();
      }

      // Intake Angler

      // Intake
      if (con.getR1ButtonReleased())
        intake.setIntakeVoltage(1);
      else if (con.getL1ButtonReleased())
        intake.setIntakeVoltage(-1);
      else if (con.getR1Button())
        intake.setIntakeVoltage(12);
      else if (con.getL1Button())
        intake.setIntakeVoltage(-12);

      Double lifterTar = null;
      Double extenderTar = null;

      if (joystick.getRawButton(12))
        intake.setIntakeAnglerTarget(dTime * -200 + intake.getTargetAngle());
      else if (joystick.getRawButton(11))
        intake.setIntakeAnglerTarget(dTime * 200 + intake.getTargetAngle());

      if (joystick.getRawButtonPressed(3)) {
        lifterTar = 0.0;
      }
      if (joystick.getRawButtonPressed(5)) {
        lifterTar = 45.0;
        extenderTar = 20.0;
        intake.setIntakeAnglerTarget(100);
      }

      if (joystick.getRawButtonPressed(4)) {
        extenderTar = 0.0;
        intake.setIntakeAnglerTarget(-50);
      }
      if (joystick.getRawButtonPressed(6)) {
        extenderTar = 41.0;
        lifterTar = 48.0;
        intake.setIntakeAnglerTarget(90);
      }

      if (joystick.getRawButtonPressed(2)) {
        intake.setIntakeAnglerTarget(90);
      }

      if (joystick.getTriggerPressed()) {
        extenderTar = 3.0;
        lifterTar = 80.0;
        intake.setIntakeAnglerTarget(-50);
      }

      if (con.getR2ButtonPressed())
        intake.setIntakeAnglerTarget(90);
      else if (con.getL2ButtonPressed())
        intake.setIntakeAnglerTarget(-50);

      if (lifterTar != null) {
        if (liftPreseting.val != null)
          liftPreseting.val.cancel();
        lifterPD.setTarget(lifterTar);
        liftPreseting.val = scheduler.setTimeout(() -> {
          liftPreseting.val = null;
        }, 1.7);
      }

      if (extenderTar != null) {
        if (armExtendPreseting.val != null)
          armExtendPreseting.val.cancel();
        extenderPD.forceSetTar(extender.getExtensionInches());
        extenderPD.setTarget(extenderTar);
        armExtendPreseting.val = scheduler.setTimeout(() -> {
          armExtendPreseting.val = null;
        }, 1.7);
      }

      if (liftPreseting.val == null)
        lifter.setVoltage(joystick.getY() * -4
            + LifterAntiGrav.calcLifterAntiGrav(lifter.getAngleDeg(), extender.getExtensionInches()));
      else
        lifterPD.tick(dTime);

      if (armExtendPreseting.val == null) {
        final var joyPOV = AngleMath.conformAngle(joystick.getPOV());
        final var joyPOVy = Math.cos(Math.toDegrees(joyPOV));
        final double armAntiGravVoltage = ArmExtenderAntiGrav.getAntiGravVoltage(lifter.getAngleDeg());
        if (joyPOV == -1) {
          extender.setVoltage(0 + armAntiGravVoltage);
        } else {
          if (joyPOVy < 0) {
            extender.setVoltage(joyPOVy * 2 + armAntiGravVoltage);
          } else {
            extender.setVoltage(joyPOVy * 3 + armAntiGravVoltage);
          }
        }
      } else {
        extenderPD.tick(dTime);
      }
    });

    scheduler.registerTick(intake);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    scheduler.tick();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    scheduler.clear();
  }

  @Override
  public void disabledPeriodic() {
    // DO NOT RUN THE SCHEDULER WHILE DISABLED
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  // ! we won't run any code beyond this point

  @Override
  public void testInit() {
    scheduler.clear();

    var constants = new PDConstant(0.14, 0.12).withMagnitude(0.8);
    drive.setConstants(constants);

    drive.reset();

    drive.setAlignmentThreshold(0.7);

    scheduler.registerTick(drive);
    intake.unsafeAnglerSetIntakeVoltage(-0.5);
    drive.power(0.001, 90, 0);
    lifter.setVoltage(0.5);
    scheduler.registerTick((double dTime) -> {
      intake.resetAnglerEncoder();
      lifter.resetPos();
      extender.reset();
    });
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    scheduler.tick();
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}