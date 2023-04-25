package frc.robot.Drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class SwerveModule {
    private MotorController turn;
    private MotorController go;
    private Double turnTarget = null;
    
    public SwerveModule(MotorController turn, MotorController go) {
        this.turn = turn;
        this.go = go;
    }

    public void set
}
