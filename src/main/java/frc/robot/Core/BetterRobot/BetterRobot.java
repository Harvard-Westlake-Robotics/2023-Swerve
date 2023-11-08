package frc.robot.Core.BetterRobot;

import frc.robot.Robot;
import frc.robot.Core.Scheduler;

/**
 * BetterRobot is an abstract extension of the base Robot class that uses a
 * RobotPolicy to define behavior across different robot modes.
 */
public abstract class BetterRobot extends Robot {
    private final Scheduler scheduler = new Scheduler(); // Scheduler to manage timed tasks.
    RobotPolicy policy; // Policy that defines the robot's behavior in different modes.

    /**
     * Abstract method to initialize the RobotPolicy. This method must be
     * implemented by subclasses to set up the robot's operational policy.
     * @return RobotPolicy that defines various phases of robot operation.
     */
    public abstract RobotPolicy init();

    /**
     * Called when the robot is first started up. Initializes the robot policy.
     */
    public void robotInit() {
        this.policy = init(); // Initialize the robot policy.
    }

    /**
     * Called when the robot enters teleoperated control. Clears the scheduler
     * and executes the teleoperated phase defined in the policy.
     */
    @Override
    public void teleopInit() {
        scheduler.clear(); // Clear any existing tasks.
        policy.teleop(scheduler); // Execute teleop phase.
    }

    /**
     * Called periodically during teleoperated control. Processes scheduled tasks.
     */
    @Override
    public void teleopPeriodic() {
        scheduler.tick(); // Process scheduled tasks.
    }
    
    /**
     * Called when the robot enters autonomous control. Clears the scheduler
     * and executes the autonomous phase defined in the policy.
     */
    @Override
    public void autonomousInit() {
        scheduler.clear(); // Clear any existing tasks.
        policy.auto(scheduler); // Execute autonomous phase.
    }

    /**
     * Called periodically during autonomous control. Processes scheduled tasks.
     */
    @Override
    public void autonomousPeriodic() {
        scheduler.tick(); // Process scheduled tasks.
    }

    /**
     * Called when the robot enters test mode. Clears the scheduler and
     * executes the test phase defined in the policy.
     */
    @Override
    public void testInit() {
        scheduler.clear(); // Clear any existing tasks.
        policy.test(scheduler); // Execute test phase.
    }

    /**
     * Called periodically during test mode. Processes scheduled tasks.
     */
    @Override
    public void testPeriodic() {
        scheduler.tick(); // Process scheduled tasks.
    }

    /**
     * Called when the robot is disabled. Clears the scheduler and executes
     * the disabled phase defined in the policy.
     */
    @Override
    public void disabledInit() {
        scheduler.clear(); // Clear any existing tasks.
        policy.disabled(scheduler); // Execute disabled phase.
    }

    /**
     * Called periodically while the robot is disabled. Processes scheduled tasks.
     */
    @Override
    public void disabledPeriodic() {
        scheduler.tick(); // Process scheduled tasks.
    }
}
