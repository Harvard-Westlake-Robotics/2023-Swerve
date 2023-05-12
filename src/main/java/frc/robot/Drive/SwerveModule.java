package frc.robot.Drive;

import frc.robot.Devices.MotorController;

public class SwerveModule {
    private MotorController turn;
    private MotorController go;
    
    public SwerveModule(MotorController turn, MotorController go) {
        this.turn = turn;
        this.go = go;
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
        return turn.getDegrees();
    }
}
