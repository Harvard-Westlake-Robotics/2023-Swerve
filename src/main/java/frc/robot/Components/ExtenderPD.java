package frc.robot.Components;

// Importing utility classes for various functionalities like Proportional-Derivative control and linear interpolation.
import frc.robot.Util.DeSpam;
import frc.robot.Util.LERP;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

// The ExtenderPD class implements the Tickable interface, which requires a tick method for periodic updates.
public class ExtenderPD implements Tickable {
    private ArmExtender extender; // The mechanical extender component that is being controlled
    private ArmLifter lifter; // The lifter component that may interact with the extender
    private LERP target = new LERP(30); // A linear interpolation object to smooth the transition to the target
    private PDController con; // The PD (Proportional-Derivative) controller for precision control of the extender

    // Constructor initializing the extender, PDController with constants, and lifter. It also sets the initial target to 0.
    public ExtenderPD(ArmExtender extender, PDConstant con, ArmLifter lifter) {
        this.extender = extender;
        this.lifter = lifter;
        this.con = new PDController(con);
        target.set(0); // Initializes the target extension length to 0
    }

    // Method to set a new target for the extender length.
    public void setTarget(double tar) {
        target.set(tar);
    }

    // Method to force set a new LERP target for the extender length, with a different LERP rate (90 instead of 30).
    public void forceSetTar(double tar) {
        target = new LERP(90);
        target.set(tar);
    }

    // Instantiate a DeSpam utility, which is used to limit the frequency of certain operations, such as logging.
    DeSpam dSpam = new DeSpam(1);

    // The tick method is called periodically and updates the control for the extender.
    public void tick(double dTime) {
        // Update the target interpolation based on the time elapsed.
        target.tick(dTime);
        
        // Calculate the correction needed from the PD controller based on the target and actual extension.
        var pdCorrect = con.solve(target.get() - extender.getExtensionInches());
        
        // Calculate the voltage to apply to the extender motor, including anti-gravity compensation from the lifter's angle.
        final var extenderVolt = ArmExtenderAntiGrav.getAntiGravVoltage(lifter.getAngleDeg()) + pdCorrect;
        
        // Set the calculated voltage to the extender.
        extender.setVoltage(extenderVolt);
        
        // The following code is a debugging tool that prints the extender voltage and PD correction periodically.
        dSpam.exec(() -> {
            System.out.println("extendervolt: " + extenderVolt + " pdCorrect: " + pdCorrect);
        });
    }
}
