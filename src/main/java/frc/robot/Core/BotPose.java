import frc.robot.Core.Schedulable;
import frc.robot.Devices.LimeLight;
import frc.robot.Util.Vector2;

public class BotPose extends Schedulable {
    private static double botPositionX, botPositionY, botPositionZ, pitch, yaw, roll;
    private static double retrievedLatency;
    private static long tickTimeSinceEpoch;

    public void start() {

    }

    public void tick(double dTime) {
        botPositionX = LimeLight.getRobotX();
        botPositionY = LimeLight.getRobotY();
        botPositionZ = LimeLight.getRobotZ();
        pitch = LimeLight.getRobotPitch();
        yaw = LimeLight.getRobotYaw();
        roll = LimeLight.getRobotRoll();
        long currentTimeSinceEpoch = System.currentTimeMillis();
        long receiveTimeSinceEpoch = LimeLight.getLastReceiveTime();
        retrievedLatency = LimeLight.getRobotLatency() + (currentTimeSinceEpoch - receiveTimeSinceEpoch);
        tickTimeSinceEpoch = currentTimeSinceEpoch;
    }

    public static Vector2 predictPosition(Vector2 velocity) {
        long currentTimeSinceEpoch = System.currentTimeMillis();
        long dataUsedLatency = currentTimeSinceEpoch - tickTimeSinceEpoch;
        double finalLatency = retrievedLatency + dataUsedLatency;
        Vector2 predictedPositionChange = velocity.multiply(finalLatency);
        Vector2 predictedPosition = new Vector2(botPositionX, botPositionY).add(predictedPositionChange);
        return predictedPosition;
    }

    public static double predictYaw(double angularVelocity) {
        long currentTimeSinceEpoch = System.currentTimeMillis();
        long dataUsedLatency = currentTimeSinceEpoch - tickTimeSinceEpoch;
        double finalLatency = retrievedLatency + dataUsedLatency;
        double predictedAngleChange = angularVelocity * finalLatency;
        double predictedAngle = yaw + predictedAngleChange;
        return predictedAngle;
    }

    public void end() {

    }
}
