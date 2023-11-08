package frc.robot.Core.BetterRobot;

import frc.robot.Core.Scheduler;

/**
 * The RobotPolicy class defines the various operational phases of the robot,
 * such as teleoperated control, autonomous control, test mode, and disabled mode.
 * It uses phases to encapsulate the different behaviors and actions that should
 * take place during each mode.
 */
public class RobotPolicy {
    Phase teleop;    // Phase for teleoperated control when the robot is driven by human operators.
    Phase auto;      // Phase for autonomous control where the robot operates based on pre-programmed instructions.
    Phase test;      // Phase for test mode used for diagnostics and system checks.
    Phase disabled;  // Phase for the disabled state when the robot should not be active.

    /**
     * Constructor that sets the teleoperated phase.
     * @param teleop The phase that defines the behavior during teleoperated control.
     */
    public RobotPolicy(Phase teleop) {
        this.teleop = teleop;
    }

    /**
     * Executes the teleoperated phase.
     * @param scheduler The scheduler that manages task execution timing.
     */
    public void teleop(Scheduler scheduler) {
        if (teleop != null)
            teleop.exec(scheduler); // Execute the teleop phase, if defined.
    }

    /**
     * Executes the autonomous phase.
     * @param scheduler The scheduler that manages task execution timing.
     */
    public void auto(Scheduler scheduler) {
        if (auto != null)
            auto.exec(scheduler); // Execute the auto phase, if defined.
    }

    /**
     * Executes the test phase.
     * @param scheduler The scheduler that manages task execution timing.
     */
    public void test(Scheduler scheduler) {
        if (test != null)
            test.exec(scheduler); // Execute the test phase, if defined.
    }

    /**
     * Executes the disabled phase.
     * @param scheduler The scheduler that manages task execution timing.
     */
    public void disabled(Scheduler scheduler) {
        if (disabled != null)
            disabled.exec(scheduler); // Execute the disabled phase, if defined.
    }
}
