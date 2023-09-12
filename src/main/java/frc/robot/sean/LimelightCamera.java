
package frc.robot.sean;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightCamera {
    // SmartDashboard dash = new SmartDashboard();

    public LimelightCamera() {
}
    // read values periodically

    public void printVals() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");
        NetworkTableEntry ta = table.getEntry("ta");

        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);

        System.out.println("LimelightX:" + x);
        System.out.println("LimelightY:" + y);
        System.out.println("LimelightArea:" + area);
    }

    public double getFieldRelativeDirection () {
        double[] data = NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose").getDoubleArray(new double[6]);
        double yaw = data[5];
        return yaw;
    }

    //I smell balls 

    





}
