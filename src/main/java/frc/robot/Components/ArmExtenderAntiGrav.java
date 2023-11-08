package frc.robot.Components;

// The ArmExtenderAntiGrav class provides a static method to calculate anti-gravity voltage for an arm extender.
public class ArmExtenderAntiGrav {

    // This static method calculates the voltage needed to counteract gravity based on the arm's lift angle.
    public static double getAntiGravVoltage(double armLiftDeg) {
        // If the arm's angle is less than 15 degrees, no anti-gravity voltage is necessary.
        if (armLiftDeg < 15)
            return 0;

        // For angles greater than or equal to 15 degrees, calculate the anti-gravity voltage.
        // This is based on the sine of the arm's angle, suggesting the force needed increases with
        // the sine of the angle (as the arm gets more horizontal, more force is needed to counteract gravity).
        // The value 0.51 is a constant that likely represents the amount of voltage needed per unit of sine output
        // to properly counteract the gravitational force acting on the arm.
        return Math.sin(Math.toRadians(armLiftDeg)) * 0.51;
    }
}
