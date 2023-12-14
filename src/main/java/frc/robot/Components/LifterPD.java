package frc.robot.Components;

import frc.robot.Core.ScheduledComponent;
// Import statements for various utility classes that will be used in the LifterPD class.
import frc.robot.Util.LERP;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;

// The LifterPD class is responsible for controlling an arm lifter using a PD (Proportional-Derivative) controller.
public class LifterPD extends ScheduledComponent {
    // Declare an instance of ArmLifter which is presumably a class that controls the physical arm lifter.
    private ArmLifter lifter;
    
    // Declare a LERP (Linear Interpolation) instance to smoothly transition to target values over time.
    private LERP target = new LERP(0, 70);
    
    // Declare a PDController which will be used to apply PD control to the lifter.
    private PDController con;
    
    // Constructor for the LifterPD class.
    public LifterPD(ArmLifter lifter, PDConstant con) {
        this.lifter = lifter; // Initialize the ArmLifter instance.
        this.con = new PDController(con); // Initialize the PDController with given PD constants.
    }

    // Method to set the target position for the lifter. The target is a value that the lifter should reach.
    public void setTarget(double tar) {
        target.set(tar - 4.5);
    }

    // The tick method is called periodically and is used to update the control system.
    // 'dTime' is the change in time since the last tick call.
    protected void tick(double dTime) {
        // Calculate the control signal using the PD controller.
        // The control signal is then used to set the voltage of the lifter's motor.
        // The solve method of PDController takes the error (difference between the target and current angle) 
        // and computes the appropriate output. The negative sign might be compensating for a motor that is wired in reverse.
        lifter.setVoltage(-con.solve(target.get() - lifter.getAngleDeg()));
    }

    protected void cleanUp() {
    }
}
