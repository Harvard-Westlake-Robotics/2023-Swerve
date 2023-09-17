// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Auto.AutonomousDrive;
import frc.robot.Auto.Commands.GoStraight;
import frc.robot.Core.Scheduler;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Imu;
import frc.robot.Devices.Motor.SparkMax;
import frc.robot.Drive.*;
import frc.robot.Util.AngleMath;
import frc.robot.Util.PDConstant;
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

  Imu imu;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    this.con = new PS4Controller(0);

    var placeholderConstant = new PDConstant(0, 0);

    var leftBackEncoder = new AbsoluteEncoder(21, -82.969, true);
    var leftBackTurn = new SparkMax(7, true);
    var leftBackGo = new SparkMax(8, false);
    var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
    var leftBack = new SwerveModulePD(leftBackRaw, placeholderConstant, leftBackEncoder);

    var rightBackEncoder = new AbsoluteEncoder(23, 45.879, true);
    var rightBackTurn = new SparkMax(1, true);
    var rightBackGo = new SparkMax(2, false);
    var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
    var rightBack = new SwerveModulePD(rightBackRaw, placeholderConstant, rightBackEncoder);

    var leftFrontEncoder = new AbsoluteEncoder(20, -129.639, true);
    var leftFrontTurn = new SparkMax(5, true);
    var leftFrontGo = new SparkMax(6, false);
    var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
    var leftFront = new SwerveModulePD(leftFrontRaw, placeholderConstant, leftFrontEncoder);

    var rightFrontEncoder = new AbsoluteEncoder(22, 82.969, true);
    var rightFrontTurn = new SparkMax(3, true);
    var rightFrontGo = new SparkMax(4, false);
    var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
    var rightFront = new SwerveModulePD(rightFrontRaw, placeholderConstant, rightFrontEncoder);

    this.imu = new Imu(18);

    this.drive = new PositionedDrive(leftFront, rightFront, leftBack, rightBack, 23, 23, () -> {
      return AngleMath.toStandardPosAngle(this.imu.getTurnAngle());
    }); // TODO: figure out actual measurements
  }

  @Override
  public void autonomousInit() {
    scheduler.clear();

    drive.reset();
    drive.setAlignmentThreshold(0.15);
    drive.setGoBrake(true);
    
    var con = new PDConstant(0.1, 0.07);
    drive.setConstants(con);

    scheduler.registerTick(drive);

    var goPD = new PDConstant(1, 4, 0.2).withMagnitude(0.3);
    var turnPD = new PDConstant(0.5, 0.0, 1).withMagnitude(0.3);
    var autoDrive = new AutonomousDrive(drive, goPD, turnPD);
    autoDrive.reset();

    scheduler.registerTick(autoDrive);

    Promise.immediate().then(() -> {
      return autoDrive.exec(
          new GoStraight(50, 20, 0));
    }).then(() -> {
      return autoDrive.exec(
          new GoStraight(50, 20, 90));
    }).then(() -> {
      return autoDrive.exec(
          new GoStraight(50, 20, 180));
    }).then(() -> {
      return autoDrive.exec(
          new GoStraight(50, 20, 270));
    });
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    scheduler.tick();
  }

  @Override
  public void teleopInit() {
    scheduler.clear();

    var constants = new PDConstant(0.14, 0.12).withMagnitude(0.8);
    drive.setConstants(constants);

    drive.reset();

    drive.setAlignmentThreshold(0.7);
    drive.setGoBrake(true);

    scheduler.registerTick(drive);

    // scheduler.setInterval(() -> {
    // System.out.println("angle: " + drive.getAngle());
    // System.out.println("x: " + drive.getPosition().x);
    // System.out.println("y: " + drive.getPosition().y);
    // }, 0.5);

    scheduler.registerTick((double dTime) -> {
      // TODO: figure out why pink ps5 has inverted y axis (inverted below)
      var goVec = new Vector2(con.getLeftX(), -con.getLeftY());

      final var TURN_CURVE_INTENSITY = 11;
      final var GO_CURVE_INTENSITY = 5;

      var goVoltage = ScaleInput.curve(goVec.getMagnitude() * 100, GO_CURVE_INTENSITY) * (12.0 / 100.0);
      final var turnVoltage = ScaleInput.curve(con.getRightX() * 100, TURN_CURVE_INTENSITY) * (12.0 / 100.0);

      if (goVoltage > 12) {
        goVoltage = 12 * (goVoltage / Math.abs(goVoltage));
      }

      if (goVec.getMagnitude() > 0.05 || Math.abs(con.getRightX()) > 0.05) {
        drive.power(
            goVoltage,
            goVec.getAngleDeg() - imu.getTurnAngle(),
            turnVoltage //
        );
      } else {
        drive.stopGoPower();
      }
    });
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
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
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