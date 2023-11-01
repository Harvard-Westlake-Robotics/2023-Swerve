package frc.robot.Auto.Curves;

import frc.robot.Util.Vector2;

public class PointAccelerationCurve {
    final double max_acc;
    final double max_speed;
    public PointAccelerationCurve(double max_acc, double max_speed, Vector2[] positions) {
        this.max_acc = max_acc;
        this.max_speed = max_speed;
    }
}