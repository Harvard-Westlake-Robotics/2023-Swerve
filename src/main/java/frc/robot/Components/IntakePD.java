package frc.robot.Components;

// Import utility classes that will be used in the IntakePD class.
import frc.robot.Util.DeSpam;
import frc.robot.Util.LERP;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

// The IntakePD class is responsible for controlling the intake mechanism with PD (Proportional-Derivative) control.
public class IntakePD implements Tickable {
    Intake intake; // Intake mechanism component
    ArmLifter lifter; // Arm lifter component for the robot
    ArmExtender extender; // Unused in this snippet, presumably controls the extension of the arm
    PDController angController; // PD controller for the intake angle
    double antiGravIntensity; // Intensity of the anti-gravity compensation
    LERP intakeAnglerTarget = new LERP(500); // LERP target for the intake angle

    // Constructor for the IntakePD class
    public IntakePD(Intake intake, PDConstant anglerConstant, double antiGravIntensity, ArmLifter lifter) {
        this.intake = intake; // Assign the intake component
        this.lifter = lifter; // Assign the lifter component
        this.antiGravIntensity = antiGravIntensity; // Set the anti-gravity intensity
        this.angController = new PDController(anglerConstant); // Initialize the PD controller with the provided constants
    }

    // Method to directly set the voltage for the intake mechanism
    public void setIntakeVoltage(double voltage) {
        intake.setIntakeVolage(voltage);
    }

    // Retrieve the current target angle from the LERP
    public double getTargetAngle() {
        return intakeAnglerTarget.get();
    }

    // Set a new target angle for the LERP
    public void setIntakeAnglerTarget(double target) {
        intakeAnglerTarget.set(target);
    }

    // Directly set the intake angler voltage without safety checks
    public void unsafeAnglerSetIntakeVoltage(double voltage) {
        intake.setAnglerVoltage(voltage);
    }

    // Reset the encoder for the intake angler to zero
    public void resetAnglerEncoder() {
        intake.resetAnglerEncoder();
    }

    // Instantiate a DeSpam utility, which is used to limit the frequency of certain operations (unused in this snippet)
    DeSpam dSpam = new DeSpam(1);

    // The tick method updates the control loop for the intake angler
    public void tick(double dTime) {
        // Update the target angle based on the elapsed time
        intakeAnglerTarget.tick(dTime);
        
        // Calculate the anti-gravity force required based on the current angle of the intake and lifter.
        // This is used to compensate for the weight of the arm as it moves.
        final double antiGrav = -antiGravIntensity
                * Math.sin(Math.toRadians(intake.getAnglerPositionDeg() - lifter.getAngleDeg()));
        
        // Calculate the correction needed from the PD controller based on the difference between the target
        // and current angle of the intake angler.
        final double pdCorrect = angController.solve(intakeAnglerTarget.get() - intake.getAnglerPositionDeg());
        
        // Apply the combined correction from PD control and anti-gravity compensation to the angler's voltage.
        intake.setAnglerVoltage(pdCorrect + antiGrav);
        
        // The following commented out code is for debugging purposes. When activated, it prints out the
        // value of the anti-gravity force periodically, as limited by the DeSpam utility.
        // dSpam.exec(() -> {
        //     System.out.println("antigrav: " + antiGrav);
        // });
    }
}
