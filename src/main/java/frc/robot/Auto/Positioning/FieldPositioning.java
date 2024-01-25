package frc.robot.Auto.Positioning;

import java.util.LinkedList;

import frc.robot.Core.ScheduledComponent;
import frc.robot.Core.Time;
import frc.robot.Devices.Imu;
import frc.robot.Devices.LimeLight;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.AngleMath;
import frc.robot.Util.Vector2;
import frc.robot.Util.LERP;

public class FieldPositioning extends ScheduledComponent implements PositioningSystem {
    PositionedDrive drive;
    Imu imu;
    LimeLight limeLight;
    final double correctionTime = 0.5;
    final double timePerTick = 0.02;
    final int ticksPerLerp = (int)Math.ceil(correctionTime / timePerTick);
    // LERP[] lerp; SCREW YOU LERP CLASS UR NOT FLEXIBLE ENOUGH + BOZO
    Position[] currentCorrections;

    public FieldPositioning(PositionedDrive drive, Imu imu, LimeLight limeLight, Position startPos) {
        this.drive = drive;
        this.imu = imu;
        this.limeLight = limeLight;
        positionHistory.add(0, startPos);
        // lerp = new LERP[ticksPerLerp];
        currentCorrections = new Position[ticksPerLerp];
        for (int i = 0; i < currentCorrections.length; i++) {
            currentCorrections[i] = new Position(0, new Vector2(0, 0));
        }
    }

    double lastLimelightFrameTime = Double.NEGATIVE_INFINITY;
    Position lastLimelightFrameOffset = new Position(0, new Vector2(0, 0));
    LinkedList<Position> positionHistory = new LinkedList<>();

    private boolean isRationalLimelightFrame() {
        final boolean isAllZero = limeLight.getRobotX() == 0 && limeLight.getRobotY() == 0
                && limeLight.getRobotZ() == 0;
        return limeLight.botPoseChanged() && !isAllZero;
    }

    @Override
    public double getTurnAngle() {
        return positionHistory.getFirst().angle;
    }

    @Override
    public Vector2 getPosition() {
        return positionHistory.getFirst().position;
    }

    public Position predictedPositionAtLastLimelightFrame() {
        long currentTimeSinceEpoch = System.currentTimeMillis();
        long receiveTimeSinceEpoch = limeLight.getLastReceiveTime();
        double latencyImageTakenToNow = limeLight.getRobotLatency() / 1000;

        int predictedPositionIndex = (int) (latencyImageTakenToNow / 0.02);

        if (predictedPositionIndex >= positionHistory.size()) {
            return null;
        }

        return positionHistory.get(predictedPositionIndex);

    }

    @Override
    protected void tick(double dTime) {
        Position lastPosition = positionHistory.getFirst();

        final double currentAngle = lastPosition.angle + imu.getYawDeltaThisTick();
        positionHistory.add(0, new Position(
                currentAngle,
                lastPosition.position.add(drive.movementSinceLastTick.rotate(currentAngle - 90))));

        // makes sure position history doesn't get too long
        if (positionHistory.size() > 5 / 0.02) {
            positionHistory.removeLast();
        }

        if (isRationalLimelightFrame()) {
            final double timeSinceLastFrame = Time.getTimeSincePower() - lastLimelightFrameTime;
            lastLimelightFrameTime = Time.getTimeSincePower();
            final Position limelightPositionAtFrame = limeLight.getRobotPosition();
            Position predictedPositionAtFrame = predictedPositionAtLastLimelightFrame();
            if (predictedPositionAtFrame == null)
                predictedPositionAtFrame = positionHistory.getFirst();

            Position adjustedPosition;
            // if the time since the last reading is more than 20 ms, we scrub the
            // extrapolated position from the drive
            if (timeSinceLastFrame < 20) {
                adjustedPosition = limelightPositionAtFrame.combine(predictedPositionAtFrame, 0.5);
            } else {
                adjustedPosition = limelightPositionAtFrame;
            }
            Position offsetPosition = adjustedPosition.difference(predictedPositionAtFrame);
            // Shift everything over to the right, eliminating the last element
            for (int i = currentCorrections.length - 1; i > 0; i--)
            {
                offsetPosition = offsetPosition.difference(currentCorrections[i].scale(i / ticksPerLerp));
                currentCorrections[i] = currentCorrections[i - 1];
            }
            currentCorrections[0] = offsetPosition;
            for (int i = 0; i < currentCorrections.length; i++)
            {
                final int temp_i = i;
                positionHistory.replaceAll(position -> position.add(currentCorrections[temp_i].scale(1.0 / ticksPerLerp)));
            }
            // positionHistory.replaceAll(position -> position.add(offsetPosition));
        }
    }

    @Override
    protected void cleanUp() {
    }
}