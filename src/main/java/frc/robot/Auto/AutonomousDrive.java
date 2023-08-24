package frc.robot.Auto;

import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.PDController;
import frc.robot.Util.Promise;
import frc.robot.Util.Tickable;
import frc.robot.Util.Vector2;

public class AutonomousDrive implements Tickable {
    PositionedDrive drive;
    PDController xController;
    PDController yController;
    PDController turnController;
    double targetX;
    double targetY;
    double targetAngle;

    DriveCommand command = null;

    public Promise exec(DriveCommand command, boolean override) {
        var promise = new Promise();
        if (!override && this.command != null) {
            throw new IllegalStateException("Cannot execute command while another command is running");
        }
        this.command = command;
        command.init(drive, () -> {
            if (this.command == command)
                this.command = null;
            promise.resolve();
        });
        return promise;
    }

    public AutonomousDrive(PositionedDrive drive, PDController goController, PDController turnController) {
        this.drive = drive;
        this.xController = goController.clone();
        this.yController = goController.clone();
        this.turnController = turnController;
        this.targetX = drive.getPosition().x;
        this.targetY = drive.getPosition().y;
        this.targetAngle = drive.getAngle();
    }

    public void reset() {
        targetX = drive.getPosition().x;
        targetY = drive.getPosition().y;
        targetAngle = drive.getAngle();
        xController.reset();
        yController.reset();
        turnController.reset();
    }

    public void tick(double dTime) {
        if (command != null) {
            AutonomousTick tick = command.tick(dTime);
            targetX += tick.dX;
            targetY += tick.dY;
            targetAngle += tick.dAngle;
        }

        double xCorrect = xController.solve(targetX - drive.getPosition().x);
        double yCorrect = yController.solve(targetY - drive.getPosition().y);
        double turnCorrect = turnController.solve(targetAngle - drive.getAngle());
        Vector2 goCorrect = new Vector2(xCorrect, yCorrect);
        drive.power(goCorrect.getMagnitude(), goCorrect.getAngleDeg(), turnCorrect);
    }
}
