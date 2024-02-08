package frc.robot.Drive;

import frc.robot.Core.ScheduledComponent;
import frc.robot.Devices.AbsoluteEncoder;
import frc.robot.Util.AngleMath;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PIDController;

/**
 * The SwerveModulePD class is responsible for controlling a single swerve
 * module
 * using a Proportional-Derivative (PD) control loop. It adjusts the orientation
 * of the module based on a target angle and controls the wheel speed.
 */
public class SwerveModulePD extends ScheduledComponent {
    SwerveModule swerve; // The swerve module being controlled.
    PIDController controller;// The PD controller for the module's turning mechanism.
    AbsoluteEncoder coder; // The encoder that measures the module's current angle.

    public double error; // The current error between the target and actual angles.

    /**
     * Sets the PD constants for the controller.
     *
     * @param constant The PD constants to be used by the controller.
     */
    public void setConstants(PDConstant constant) {
        controller = new PIDController(constant);
    }

    /**
     * Constructs a SwerveModulePD with a swerve module, control constants, and an
     * encoder.
     *
     * @param swerve The swerve module to control.
     * @param con    The PD constants for controlling the module.
     * @param coder  The encoder that measures the module's angle.
     */
    public SwerveModulePD(SwerveModule swerve, PDConstant con, AbsoluteEncoder coder) {
        this.swerve = swerve;
        this.controller = new PIDController(con);
        this.coder = coder;
    }

    Double turnTarget = null;

    /**
     * Updates the swerve module's control loop with the time since the last tick.
     *
     * @param dTime The elapsed time since the last control update.
     */
    protected void tick(double dTime) {
        // If a turn target has been set, calculate and apply the necessary corrections.
        if (turnTarget != null) {
            var error = AngleMath.getDeltaReversable(coder.absVal(), turnTarget);
            this.error = error;
            boolean isFrontFacing = AngleMath.shouldReverseCorrect(coder.absVal(), turnTarget);

            // If the orientation of the module has changed, update the driving direction.
            if (isFrontFacing != this.frontFacing) {
                this.frontFacing = isFrontFacing;
                swerve.setGoVoltage(frontFacing ? voltage : -voltage);
            }

            // Calculate the voltage correction using the PD controller.
            var correctionVoltage = controller.solve(error);

            // Apply the correction voltage to the swerve module.
            swerve.setTurnVoltage(correctionVoltage);
        }
    }

    protected void cleanUp() {
    }

    /**
     * Sets the target turning angle for the module.
     *
     * @param degrees The target angle in degrees, relative to the robot's front.
     */
    public void setTurnTarget(double degrees) {
        turnTarget = AngleMath.conformAngle(degrees);
    }

    boolean frontFacing = true; // Indicates if the module is facing the front.
    double voltage = 0; // The voltage applied for driving the module.

    /**
     * Sets the driving voltage for the module and applies it based on the current
     * orientation.
     *
     * @param volts The voltage to set for driving the module.
     */
    public void setGoVoltage(double volts) {
        voltage = volts;
        swerve.setGoVoltage(frontFacing ? volts : -volts);
    }

    public void setVelocity(double velocity) {
        swerve.setGoVelocity(velocity);
    }

    /**
     * Retrieves the current turning angle of the module.
     *
     * @return The current angle of the module from the front positive to the right.
     */
    public double getTurnAngle() {
        return coder.absVal();
    }

    /**
     * Retrieves the current angle of the module in standard position.
     *
     * @return The angle of the module in standard position.
     */
    public double getAngle() {
        return AngleMath.toStandardPosAngle(getTurnAngle());
    }

    /**
     * Retrieves the distance the module has traveled.
     *
     * @return The distance traveled by the module.
     */
    public double getDist() {
        return swerve.getGoReading();
    }
}
