// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Core.RobotPolicy;
import frc.robot.Core.Scheduler;
import frc.robot.Util.GetDTime;
import frc.robot.Util.Phase;

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
  static RobotPolicy policy;
  GetDTime dTGet = new GetDTime();

  private static Boolean isInit;
  private static Phase phase;

  public static boolean isInit() {
    return isInit;
  }

  public static Phase getPhase() {
    return phase;
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    isInit = true;
    phase = Phase.Init;
    policy = RobotContainer.init();
  }

  @Override
  public void robotPeriodic() {
    isInit = false;
  }

  @Override
  public void autonomousInit() {
    phase = Phase.Auto;
    isInit = true;
    Scheduler.getCore().clear();
    dTGet.tick();
    policy.autonomous();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getCore().tick(dTGet.tick());
  }

  @Override
  public void teleopInit() {
    phase = Phase.Teleop;
    isInit = true;
    Scheduler.getCore().clear();
    dTGet.tick();
    policy.teleop();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getCore().tick(dTGet.tick());
  }

  @Override
  public void disabledInit() {
    phase = Phase.Disabled;
    isInit = true;
    Scheduler.getCore().clear();
    dTGet.tick();
    policy.disabled();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getCore().tick(dTGet.tick());
  }

  @Override
  public void testInit() {
    phase = Phase.Test;
    isInit = true;
    Scheduler.getCore().clear();
    dTGet.tick();
    policy.test();
  }

  @Override
  public void testPeriodic() {
    Scheduler.getCore().tick(dTGet.tick());
  }

  @Override
  public void simulationInit() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void simulationPeriodic() {
    throw new UnsupportedOperationException();
  }
}
