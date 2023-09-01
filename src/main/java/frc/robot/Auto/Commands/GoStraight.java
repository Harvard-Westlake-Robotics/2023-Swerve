package frc.robot.Auto.Commands;

import frc.robot.Auto.AutonomousTick;
import frc.robot.Auto.DriveCommand;
import frc.robot.Util.Vector2;

public class GoStraight extends DriveCommand {
    double targetDistance;
    double speed;
    double direction; // degrees
    double traveledDistance = 0.0;
    
    public GoStraight(double distance, double speed, double direction) {
        this.targetDistance = distance;
        this.speed = speed;
        this.direction = direction;
    }

    public AutonomousTick tick(double dTime) {
        Vector2 tick = Vector2.fromAngleAndMag(direction, speed * dTime);
        traveledDistance += tick.getMagnitude();
        if (Math.abs(traveledDistance) > Math.abs(targetDistance)) {
            finish.run();
        }
        return new AutonomousTick(tick.x, tick.y, 0.0);
    }
}