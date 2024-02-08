package frc.robot.Components;

import frc.robot.Core.ScheduledComponent;
import frc.robot.Devices.AnyMotor;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PIDController;

public class Elevator extends ScheduledComponent {
    AnyMotor left;
    AnyMotor right;
    PIDController constant;

    public Elevator(AnyMotor left, AnyMotor right, PDConstant constant) {
        this.left = left;
        this.right = right;

        this.constant = new PIDController(constant);
    }

    private Double target = null;

    public double getHeight() {
        return 1.0 * left.getDegrees();
    }

    public void moveUp() {
        target = 1.0;
    }

    public void moveDown() {
        target = 0.0;
    }

    @Override
    protected void tick(double dTime) {
        if (target != null) {
            left.setVoltage(constant.solve(target - getHeight()));
        }
    }

    @Override
    protected void cleanUp() {
    }
}
