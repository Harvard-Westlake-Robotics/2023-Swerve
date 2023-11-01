package frc.robot.Auto.Curves;

import frc.robot.Util.Vector2;

public interface Curve {
    public Vector2 next(double delta);
}
