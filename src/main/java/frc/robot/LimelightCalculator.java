package frc.robot;

import frc.robot.Util.Vector2;
import frc.robot.Devices.LimeLight;
import frc.robot.Core.Time;
import frc.robot.Auto.Positioning.FieldPositioning;
import frc.robot.Core.Schedulable;
import frc.robot.Core.BotPose;

public class LimelightCalculator extends Schedulable {
    double latency;
    Vector2 previousPosition;
    Double previousXPos;
    Double previousYPos;
    Double previousAngle;
    double xVelocity;
    double yVelocity;
    double angularVelocity;
    LimeLight limelight;
    FieldPositioning positioning;
    Double previousTime;

    public LimelightCalculator(LimeLight limeLight, FieldPositioning positioning) {
        this.previousXPos = null;
        this.previousYPos = null;
        this.previousAngle = null;
        this.limelight = limeLight;
        this.positioning = positioning;
    }

    @Override
    public String toString() {
        return "LimelightCalculator [latency=" + latency + ", previousXPos=" + previousXPos + ", previousYPos="
                + previousYPos + ", previousAngle=" + previousAngle + ", xVelocity=" + xVelocity + ", yVelocity="
                + yVelocity + ", angularVelocity=" + angularVelocity + "]";
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }

    public double getPreviousXPos() {
        return previousXPos;
    }

    public void setPreviousXPos(double previousXPos) {
        this.previousXPos = previousXPos;
    }

    public double getPreviousYPos() {
        return previousYPos;
    }

    public void setPreviousYPos(double previousYPos) {
        this.previousYPos = previousYPos;
    }

    public double getPreviousAngle() {
        return previousAngle;
    }

    public void setPreviousAngle(double previousAngle) {
        this.previousAngle = previousAngle;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void start() {

    }

    public void end() {

    }

    public void tick(double dTime) {
        dTime *= 1000; // We want ms!
        if (previousXPos != null && previousYPos != null && previousAngle != null) {
            this.xVelocity = (positioning.getPosition().x - previousXPos) / dTime;
            this.yVelocity = (positioning.getPosition().y - previousYPos) / dTime;
            this.angularVelocity = (positioning.getTurnAngle() - previousAngle) / dTime;
        }

        previousPosition = positioning.getPosition();
        previousAngle = positioning.getTurnAngle();
        this.previousXPos = previousPosition.x;
        this.previousYPos = previousPosition.y;
    }
}
