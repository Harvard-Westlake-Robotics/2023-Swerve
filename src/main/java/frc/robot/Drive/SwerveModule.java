package frc.robot.Drive;

import frc.robot.Devices.MotorController;
import frc.robot.Util.AngleMath;

public class SwerveModule {
    private MotorController turn;
    private MotorController go;
    
    public SwerveModule(MotorController turn, MotorController go) {
        this.turn = turn;
        this.go = go;

        turn.resetEncoder();
        go.resetEncoder();
    }

    public void setGoVoltage(double voltage) {
        go.setVoltage(voltage);
    }   

    public void setTurnVoltage(double voltage) {
        turn.setVoltage(voltage);
    }

    public void resetTurnReading() {
        turn.resetEncoder();
    }

    public double getTurnReading() {
        return AngleMath.conformAngle(turn.getDegrees() / 12.8); // 12.8 motor turns per 360 deg wheel turn
    }

    public void resetGoReading() {
        go.resetEncoder();
    }

    public double getGoReading() {
        return go.getDegrees(); // TODO: figure how many motor turns per wheel gotation; convert to inches
    }
}
 