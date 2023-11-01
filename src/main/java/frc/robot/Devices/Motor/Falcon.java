package frc.robot.Devices.Motor;

import com.ctre.phoenixpro.configs.CurrentLimitsConfigs;
import com.ctre.phoenixpro.hardware.TalonFX;

import frc.robot.Devices.AnyMotor;

public class Falcon extends AnyMotor {
    private TalonFX falcon;
    double stallVolt;

    final int id;

    public int getID() {
        return id;
    }

    public void setCurrentLimit(int amps) {
        var config = falcon.getConfigurator();
        var currentConfig = new CurrentLimitsConfigs();
        currentConfig.SupplyCurrentLimit = amps;
        config.apply(currentConfig);
    }

    public Falcon(int deviceNumber, boolean isReversed, boolean isStallable) {
        super(isReversed);

        this.id = deviceNumber;

        this.falcon = new TalonFX(deviceNumber);
        falcon.setInverted(false);
        this.stallVolt = isStallable ? 3
                : 0;

        // var config = falcon.getConfigurator();
        // falcon.setControl(new CoastOut());

        // falcon.getSensorCollection();
        // /* newer config API */
        // TalonFXConfiguration configs = new TalonFXConfiguration();
        // /* select integ-sensor for PID0 (it doesn't matter if PID is actually used)
        // */
        // configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
        // /* config all the settings */
        // falcon.configAllSettings(configs);

        // falcon.setSelectedSensorPosition(0);
    }

    public Falcon(int deviceNumber, boolean isReversed) {
        this(deviceNumber, isReversed, false);
    }

    protected void uSetVoltage(double volts) {

        double fac = (volts > 0) ? 1 : -1;
        if (Math.abs(volts) < stallVolt / 2) {
            falcon.setVoltage(0);
            return;
        } else if (Math.abs(volts) < stallVolt) {
            falcon.setVoltage(stallVolt * fac);
            return;
        }

        falcon.setVoltage(volts);
        /**
         * This is a ternerary
         * Equivalent to
         * 
         * if (isReversed)
         * maxspark.setVoltage(-volts);
         * else
         * maxspark.setVoltage(volts);
         * 
         * If you don't understand and need to make a change, you can
         * uncomment this
         * code
         */
    }

    protected double uGetRevs() {
        return falcon.getPosition().getValue();
    }

    public void stop() {
        falcon.stopMotor();
    }
}
