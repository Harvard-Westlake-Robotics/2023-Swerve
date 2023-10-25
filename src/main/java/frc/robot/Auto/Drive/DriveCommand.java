package frc.robot.Auto.Drive;

import frc.robot.Auto.Position;
import frc.robot.Drive.Drive;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.Lambda;

public abstract class DriveCommand {
    protected Drive drive;
    // protected
    protected Lambda finish;

    public DriveCommand() {

    }

    public void init(PositionedDrive drive, Lambda finish) {
        this.drive = drive;
        this.finish = finish;
    }

    public abstract Position nextTar(double dTime);
}
