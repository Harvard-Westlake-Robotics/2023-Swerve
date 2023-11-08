package frc.robot.Components;

// The LifterAntiGrav class contains methods related to compensating for the effects of gravity on the arm lifter.
public class LifterAntiGrav {
    
    // This static method calculates the anti-gravity force needed for the lifter.
    // The calculation is based on the angle of the lifter and the extension distance of the lifter arm.
    public static double calcLifterAntiGrav(double lifterAngle, double extension) {
        
        // The method returns a value that represents the necessary force to counteract gravity.
        // This is done by calculating the cosine of the lifter's angle (converted to radians) which gives
        // the horizontal component of the force against gravity. This value is scaled by a constant (-0.007)
        // and further modified by a linear term (25 + extension) that accounts for the length of the arm extension.
        // The negative sign ensures the force is applied in the correct direction to counteract gravity.
        return -0.007 * Math.cos(Math.toRadians(lifterAngle)) * (25 + extension);
    }
}
