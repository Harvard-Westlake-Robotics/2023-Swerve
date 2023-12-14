package frc.robot.Auto.Drive;

import frc.robot.Auto.Curves.Curve;
import frc.robot.Core.Schedulable;
import frc.robot.Core.Scheduler;
import frc.robot.Util.Promise;
import frc.robot.Util.SimplePromise;
import frc.robot.Util.Vector2;

public class MoveAuto {
    public static Promise goCurve(AutonomousDrive drive, Curve curve) {
        var initialPos = new Vector2(drive.targetX, drive.targetY);
        return Scheduler.runTask(new Schedulable() {
            protected void start() {
            }
            protected void tick(double dTime) {
                var nextPos = curve.next(dTime);
                drive.setTarget(initialPos.add(nextPos));
            }
            protected void end() {
            }
        });
    }
}