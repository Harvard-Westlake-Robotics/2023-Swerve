package frc.robot.Devices;

import com.ctre.phoenix.sensors.CANCoder;

public class Encoder {
    CANCoder coder;
    public Encoder(int canPort) {
        this.coder = new CANCoder(canPort);
    }
}
