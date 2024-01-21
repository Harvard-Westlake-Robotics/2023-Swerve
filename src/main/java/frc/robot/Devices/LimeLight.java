package frc.robot.Devices;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Auto.Positioning.Position;
import frc.robot.Core.ScheduledComponent;
import frc.robot.Util.Vector2;

public class LimeLight extends ScheduledComponent {
    private NetworkTable masterTable;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;
    private NetworkTableEntry botpose;

    // NOTE: this assumes the limelight is called "limelight" which is not true for every limelight!
    public LimeLight() {
        this("limelight");
    }

    public LimeLight(String limelightHostname) {
        masterTable = NetworkTableInstance.getDefault().getTable(limelightHostname);
        tx = masterTable.getEntry("tx");
        ty = masterTable.getEntry("ty");
        ta = masterTable.getEntry("ta");
        tv = masterTable.getEntry("tv");
        botpose = masterTable.getEntry("botpose");
    }

    boolean botPoseChanged = false;
    long lastBotPoseChangeTime = 0;

    public boolean botPoseChanged() {
        return botPoseChanged;
    }

    protected void tick(double dTime) {
        if (botpose.getLastChange() != lastBotPoseChangeTime) {
            botPoseChanged = true;
            lastBotPoseChangeTime = botpose.getLastChange();
        } else {
            botPoseChanged = false;
        }
    }

    @Override
    protected void cleanUp() {
        
    }

    // true - Vision Processing, false - DriverCam
    public void setCamMode(boolean camMode) {
        masterTable.getEntry("camMode").setValue(camMode ? 0 : 1);
    }


    // read values periodically
    public double getHorixDegreeToTarget() {
        double x = tx.getDouble(0.0);
        return x;
    }

    public Position getRobotPosition() {
        double x = getRobotX();
        double y = getRobotY();
        double angle = getRobotYaw();
        return new Position(angle, new Vector2(x, y));
    }

    // The coordinate system is as follows:
    // x = 0 is at the center of the field. The red side is at positive x and the
    // blue side is at negative x.
    // y = 0 is at the center of the field. The speakers are at positive y.
    // NOTE: these coordinates are in meters and should be appropriately converted
    // before use.
    // Following are the coordinates of the april tags (the z axis represents
    // height):
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
    public double getRobotX() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return Units.metersToInches(botposeArray[0]);
    }

    public double getRobotY() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return Units.metersToInches(botposeArray[1]);
    }

    public double getRobotZ() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return Units.metersToInches(botposeArray[2]);
    }

    public double getRobotRoll() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[3];
    }

    public double getRobotPitch() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[4];
    }

    public double getRobotYaw() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[5];
    }

    public double getRobotLatency() {
        double[] botposeArray = botpose.getDoubleArray(new double[0]);
        return botposeArray[6];
    }

    public long getLastReceiveTime() {
        long lastReceiveTime = botpose.getLastChange();
        return lastReceiveTime;
    }

    public double getVerticalD() {
        double y = ty.getDouble(0.0);
        return y;
    }

    public double getArea() {
        double area = ta.getDouble(0.0);
        return area;
    }

    public boolean foundTarget() {
        return tv.getDouble(0) == 1;
    }
}
