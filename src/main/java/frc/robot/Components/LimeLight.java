package frc.robot.Components;

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
