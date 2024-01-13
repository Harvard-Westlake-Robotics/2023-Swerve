package frc.robot.Devices;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {
    static NetworkTable masterTable = NetworkTableInstance.getDefault().getTable("limelight");
    static NetworkTableEntry tx = masterTable.getEntry("tx");
    static NetworkTableEntry ty = masterTable.getEntry("ty");
    static NetworkTableEntry ta = masterTable.getEntry("ta");
    static NetworkTableEntry tv = masterTable.getEntry("tv");
    static NetworkTableEntry botpose = masterTable.getEntry("botpose");

    // true - Vision Processing, false - DriverCam
    public static void setCamMode(boolean camMode) {
        masterTable.getEntry("camMode").setValue(camMode ? 0 : 1);
    }

    // read values periodically
    public static double getHorixDegreeToTarget() {
        double x = tx.getDouble(0.0);
        return x;
    }
    // The coordinate system is as follows:
    // x = 0 is at the center of the field. The red side is at positive x and the blue side is at negative x.
    // y = 0 is at the center of the field. The speakers are at positive y.
    // Following are the coordinates of the april tags (the z axis represents height):
    // 1. (6.808597, -3.859403, 1.355852)
    // 2. (7.914259, -3.221609, 1.355852)
    // 3. (8.308467, 0.877443, 1.451102)
    // 4. (8.308467, 1.442593, 1.451102)
    // 5. (6.429883, 4.098925, 1.355852)
    // 6. (-6.429375, 4.098925, 1.355852)
    // 7. (-8.308975, 1.442593, 1.451102)
    // 8. (-8.308975, 0.877443, 1.451102)
    // 9. (-7.914767, -3.221609, 1.355852)
    // 10. (-6.809359, -3.859403, 1.355852)
    // 11. (3.633851, -0.392049, 1.3208)
    // 12. (3.633851, 0.393065, 1.3208)
    // 13. (2.949321, -0.000127, 1.3208)
    // 14. (-2.950083, -0.000127, 1.3208)
    // 15. (-3.629533, 0.393065, 1.3208)
    // 16. (-3.629533, -0.392049, 1.3208)
    public static double getRobotX() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[0];
    }

    public static double getRobotY() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[1];
    }

    public static double getRobotZ() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[2];
    }

    public static double getRobotRoll() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[3];
    }

    public static double getRobotPitch() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[4];
    }

    public static double getRobotYaw() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[5];
    }

    public static double getRobotLatency() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[6];
    }

    public static double getVerticalD() {
        double y = ty.getDouble(0.0);
        return y;
    }

    public static double getArea() {
        double area = ta.getDouble(0.0);
        return area;
    }

    public static boolean foundTarget() {
        return tv.getDouble(0) == 1;
    }
}
