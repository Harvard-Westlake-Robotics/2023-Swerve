// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Core.Scheduler;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Devices.Imu;
import frc.robot.Devices.Motor.SparkMax;
import frc.robot.Drive.*;
import frc.robot.Util.AngleMath;
import frc.robot.Util.Container;
import frc.robot.Util.PDController;
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

  Drive drive;

  Imu imu;

  // ! If you change the pd constant numbers (anywhere in this code) the related
  // ! subsystem might oscilate or harm somebody

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    this.con = new PS4Controller(0);

    var con = new PDController(0.08, 0.0);

    var leftBackEncoder = new AbsoluteEncoder(21);
    var leftBackTurn = new SparkMax(7, true);
    var leftBackGo = new SparkMax(8, false);
    var leftBackRaw = new SwerveModule(leftBackTurn, leftBackGo);
    var leftBack = new SwerveModulePD(leftBackRaw, con, leftBackEncoder);

    var rightBackEncoder = new AbsoluteEncoder(23);
    var rightBackTurn = new SparkMax(1, true);
    var rightBackGo = new SparkMax(2, false);
    var rightBackRaw = new SwerveModule(rightBackTurn, rightBackGo);
    var rightBack = new SwerveModulePD(rightBackRaw, con, rightBackEncoder);

    var leftFrontEncoder = new AbsoluteEncoder(20);
    var leftFrontTurn = new SparkMax(5, true);
    var leftFrontGo = new SparkMax(6, false);
    var leftFrontRaw = new SwerveModule(leftFrontTurn, leftFrontGo);
    var leftFront = new SwerveModulePD(leftFrontRaw, con, leftFrontEncoder);

    var rightFrontEncoder = new AbsoluteEncoder(22);
    var rightFrontTurn = new SparkMax(3, true);
    var rightFrontGo = new SparkMax(4, false);
    var rightFrontRaw = new SwerveModule(rightFrontTurn, rightFrontGo);
    var rightFront = new SwerveModulePD(rightFrontRaw, con, rightFrontEncoder);

    this.drive = new Drive(leftFront, rightFront, leftBack, rightBack);
  }

  @Override
  public void autonomousInit() {
    scheduler.clear();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    scheduler.tick();
  }

  @Override
  public void teleopInit() {
    scheduler.clear();

    drive.resetEncoders();

    scheduler.registerTick(drive);

    var angle = new Container<Double>(0.0);
    scheduler.registerTick((double dTime) -> {
      angle.val = AngleMath.conformAngle((new Vector2(con.getLeftX(), con.getLeftY())).getAngleDeg());
      drive.setAngle(angle.val);
      // drive.setGoVoltage((new Vector2(con.getLeftX(), con.getLeftY())).getMagnitude());
    });

    scheduler.setInterval(() -> {
      System.out.println(angle.val);
    }, 0.8);
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