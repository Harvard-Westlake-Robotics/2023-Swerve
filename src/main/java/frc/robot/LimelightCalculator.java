// package frc.robot;

// import frc.robot.Util.Vector2;
// import frc.robot.Devices.LimeLight;
// import frc.robot.Core.Time;
// import frc.robot.Auto.Positioning.FieldPositioning;
// import frc.robot.Core.Schedulable;

// public class LimelightCalculator extends Schedulable {
// double latency;
// Vector2 previousPosition;
// Double previousXPos;
// Double previousYPos;
// Double previousAngle;
// double xVelocity;
// double yVelocity;
// double angularVelocity;
// FieldPositioning positioning;
// Double previousTime;
// private double LimeLightPositionX, LimeLightPositionY, LimeLightPositionZ,
// pitch, yaw, roll;
// private double retrievedLatency;
// private long tickTimeSinceEpoch;
// LimeLight limeLight;

// public LimelightCalculator(FieldPositioning positioning, LimeLight limeLight)
// {
// this.previousXPos = null;
// this.previousYPos = null;
// this.previousAngle = null;
// this.positioning = positioning;
// this.limeLight = limeLight;
// }

// @Override
// public String toString() {
// return "LimelightCalculator [latency=" + latency + ", previousXPos=" +
// previousXPos + ", previousYPos="
// + previousYPos + ", previousAngle=" + previousAngle + ", xVelocity=" +
// xVelocity + ", yVelocity="
// + yVelocity + ", angularVelocity=" + angularVelocity + "]";
// }

// public void start() {

// }

// public void end() {

// }

// public double GetLatency() {}

// public Vector2 predictPosition(Vector2 velocity) {
// long currentTimeSinceEpoch = System.currentTimeMillis();
// long dataUsedLatency = currentTimeSinceEpoch - tickTimeSinceEpoch;
// double finalLatency = retrievedLatency + dataUsedLatency;
// Vector2 predictedPositionChange = velocity.multiply(finalLatency);
// Vector2 predictedPosition = new Vector2(LimeLightPositionX,
// LimeLightPositionY).add(predictedPositionChange);
// return predictedPosition;
// }

// public double predictYaw(double angularVelocity) {
// long currentTimeSinceEpoch = System.currentTimeMillis();
// long dataUsedLatency = currentTimeSinceEpoch - tickTimeSinceEpoch;
// double finalLatency = retrievedLatency + dataUsedLatency;
// double predictedAngleChange = angularVelocity * finalLatency;
// double predictedAngle = yaw + predictedAngleChange;
// return predictedAngle;
// }

// public void tick(double dTime) {
// dTime *= 1000; // We want ms!
// if (previousXPos != null && previousYPos != null && previousAngle != null) {
// this.xVelocity = (positioning.getPosition().x - previousXPos) / dTime;
// this.yVelocity = (positioning.getPosition().y - previousYPos) / dTime;
// this.angularVelocity = (positioning.getTurnAngle() - previousAngle) / dTime;
// }

// previousPosition = positioning.getPosition();
// previousAngle = positioning.getTurnAngle();
// this.previousXPos = previousPosition.x;
// this.previousYPos = previousPosition.y;

// LimeLightPositionX = LimeLight.getRobotX();
// LimeLightPositionY = LimeLight.getRobotY();
// LimeLightPositionZ = LimeLight.getRobotZ();
// pitch = LimeLight.getRobotPitch();
// yaw = LimeLight.getRobotYaw();
// roll = LimeLight.getRobotRoll();
// long currentTimeSinceEpoch = System.currentTimeMillis();
// long receiveTimeSinceEpoch = LimeLight.getLastReceiveTime();
// retrievedLatency = LimeLight.getRobotLatency() + (currentTimeSinceEpoch -
// receiveTimeSinceEpoch);
// tickTimeSinceEpoch = currentTimeSinceEpoch;
// }

// public double getLatency() {
// return latency;
// }

// public void setLatency(double latency) {
// this.latency = latency;
// }

// public Vector2 getPreviousPosition() {
// return previousPosition;
// }

// public void setPreviousPosition(Vector2 previousPosition) {
// this.previousPosition = previousPosition;
// }

// public Double getPreviousXPos() {
// return previousXPos;
// }

// public void setPreviousXPos(Double previousXPos) {
// this.previousXPos = previousXPos;
// }

// public Double getPreviousYPos() {
// return previousYPos;
// }

// public void setPreviousYPos(Double previousYPos) {
// this.previousYPos = previousYPos;
// }

// public Double getPreviousAngle() {
// return previousAngle;
// }

// public void setPreviousAngle(Double previousAngle) {
// this.previousAngle = previousAngle;
// }

// public double getxVelocity() {
// return xVelocity;
// }

// public void setxVelocity(double xVelocity) {
// this.xVelocity = xVelocity;
// }

// public double getyVelocity() {
// return yVelocity;
// }

// public void setyVelocity(double yVelocity) {
// this.yVelocity = yVelocity;
// }

// public double getAngularVelocity() {
// return angularVelocity;
// }

// public void setAngularVelocity(double angularVelocity) {
// this.angularVelocity = angularVelocity;
// }

// public FieldPositioning getPositioning() {
// return positioning;
// }

// public void setPositioning(FieldPositioning positioning) {
// this.positioning = positioning;
// }

// public Double getPreviousTime() {
// return previousTime;
// }

// public void setPreviousTime(Double previousTime) {
// this.previousTime = previousTime;
// }

// public double getLimeLightPositionX() {
// return LimeLightPositionX;
// }

// public void setLimeLightPositionX(double limeLightPositionX) {
// LimeLightPositionX = limeLightPositionX;
// }

// public double getLimeLightPositionY() {
// return LimeLightPositionY;
// }

// public void setLimeLightPositionY(double limeLightPositionY) {
// LimeLightPositionY = limeLightPositionY;
// }

// public double getLimeLightPositionZ() {
// return LimeLightPositionZ;
// }

// public void setLimeLightPositionZ(double limeLightPositionZ) {
// LimeLightPositionZ = limeLightPositionZ;
// }

// public double getPitch() {
// return pitch;
// }

// public void setPitch(double pitch) {
// this.pitch = pitch;
// }

// public double getYaw() {
// return yaw;
// }

// public void setYaw(double yaw) {
// this.yaw = yaw;
// }

// public double getRoll() {
// return roll;
// }

// public void setRoll(double roll) {
// this.roll = roll;
// }

// public double getRetrievedLatency() {
// return retrievedLatency;
// }

// public void setRetrievedLatency(double retrievedLatency) {
// this.retrievedLatency = retrievedLatency;
// }

// public long getTickTimeSinceEpoch() {
// return tickTimeSinceEpoch;
// }

// public void setTickTimeSinceEpoch(long tickTimeSinceEpoch) {
// this.tickTimeSinceEpoch = tickTimeSinceEpoch;
// }
// }
