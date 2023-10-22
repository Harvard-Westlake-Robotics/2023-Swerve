package frc.robot.Devices;

import com.ctre.phoenixpro.hardware.Pigeon2;

public class Imu {
    private Pigeon2 imu;

    public Imu(int port) {
        this.imu = new Pigeon2(port);

        var config = imu.getConfigurator();
        
        // config.configFactoryDefault();
        // config.configMountPoseYaw(90);
        // config.configMountPosePitch(0);
        // config.configMountPoseRoll(0);
        config.setYaw(90);
        // config.configEnableCompass(false);
    }

    public double getTurnAngle() {
        return imu.getYaw().getValue();
    }

    public double getPitch() {
        return imu.getPitch().getValue();
    }
    
    public double getRoll() {
        return imu.getRoll().getValue();
    }

    public void resetYaw() {
        imu.setYaw(0);
    }
}
