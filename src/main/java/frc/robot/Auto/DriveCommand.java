package frc.robot.Auto;

import frc.robot.Drive.Drive;
import frc.robot.Drive.PositionedDrive;
import frc.robot.Util.Lambda;

public abstract class DriveCommand {
    protected Drive drive;
    protected Lambda finish;
    public DriveCommand() {
        
    }

    public void init(PositionedDrive drive, Lambda finish) {
        this.drive = drive;
        this.finish = finish;
    }

    public abstract AutonomousTick tick(double dTime);
}
