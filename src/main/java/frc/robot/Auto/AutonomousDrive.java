package frc.robot.Auto;

import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.AngleMath;
import frc.robot.Util.DeSpam;
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
    double targetAngle; // turn angle

    DriveCommand command = null;

    public Promise exec(DriveCommand command) {
        return exec(command, false);
    }

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
        targetAngle = AngleMath.toTurnAngle(drive.getAngle());
        xController.reset();
        yController.reset();
        turnController.reset();
    }

    DeSpam deSpam = new DeSpam(0.1);

    public void tick(double dTime) {

        if (command != null) {
            AutonomousTick tick = command.tick(dTime);
            targetX += tick.dX;
            targetY += tick.dY;
            targetAngle += tick.dAngle;
        }
        double xCorrect = xController.solve(targetX - drive.getPosition().x);
        double yCorrect = yController.solve(targetY - drive.getPosition().y);

        double turnCorrect = turnController
                .solve(AngleMath.getDelta(AngleMath.toTurnAngle(drive.getAngle()), targetAngle));

        Vector2 goCorrect = new Vector2(xCorrect, yCorrect).rotate(-AngleMath.toTurnAngle(drive.getAngle()));

        deSpam.exec(() -> {
            // print targetx, currentx, targety, currenty, targetangle, currentangle
            System.out.println("targetX: " + targetX + ", currentX: " + drive.getPosition().x);
            //         + ", targetY: " + targetY
            //         + ", currentY: " + drive.getPosition().y + ", \ntargetAngle: " + targetAngle + ", currentAngle: "
            //         + AngleMath.toTurnAngle(drive.getAngle()));
            System.out.println(goCorrect.getTurnAngleDeg());
        });

        // TODO : figure out why subtract 90
        drive.power(goCorrect.getMagnitude(), goCorrect.getAngleDeg(), turnCorrect);
    }
}
