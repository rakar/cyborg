package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Class for getting voltage, current, temperature, speed and energy from the Power Distribution
 * Panel over CAN.
 */
public class CBSimPDB extends SensorBase implements CBIPDB {
    CBSimPDBData simData;

    public class CBSimPDBData {
        public int module;
        public double[] current = new double[16];
        public double temperature;
        public double voltage;


        public CBSimPDBData(int module) {
            this.module = module;
        }
    }

    /**
     * Constructor.
     *
     * @param module The CAN ID of the PDP
     */
    public CBSimPDB(int module) {
        simData = new CBSimPDBData(module);
        setName("PowerDistributionPanel", module);
    }

    /**
     * Constructor.  Uses the default CAN ID (0).
     */
    public CBSimPDB() {
        this(0);
    }

    /**
     * Query the input voltage of the PDP.
     *
     * @return The voltage of the PDP in volts
     */
    @Override
    public double getVoltage() {
        return simData.voltage;
    }

    /**
     * Query the temperature of the PDP.
     *
     * @return The temperature of the PDP in degrees Celsius
     */
    @Override
    public double getTemperature() {
        return simData.temperature;
    }

    /**
     * Query the current of a single channel of the PDP.
     *
     * @return The current of one of the PDP channels (channels 0-15) in Amperes
     */
    @Override
    public double getCurrent(int channel) {
        return simData.current[channel];
    }

    /**
     * Query the current of all monitored PDP channels (0-15).
     *
     * @return The current of all the channels in Amperes
     */
    @Override
    public double getTotalCurrent() {
        double current = 0;
        for(int i=0;i<16;i++) current+=getCurrent(i);
        return current;
    }

    /**
     * Query the total speed drawn from the monitored PDP channels.
     *
     * @return the total speed in Watts
     */
    @Override
    public double getTotalPower() {
        // TODO: do the whole speed calculation thing
        return 0;
    }

    /**
     * Query the total energy drawn from the monitored PDP channels.
     *
     * @return the total energy in Joules
     */
    @Override
    public double getTotalEnergy() {
        // TODO: do the whole energy calculation thing
        return 0;
    }

    /**
     * Reset the total energy to 0.
     */
    @Override
    public void resetTotalEnergy() {

        // TODO: reset the energy
    }

    /**
     * Clear all PDP sticky faults.
     */
    @Override
    public void clearStickyFaults() {

        // Clear faults
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PowerDistributionPanel");
        for (int i = 0; i < kPDPChannels; ++i) {
            final int chan = i;
            builder.addDoubleProperty("Chan" + i, () -> getCurrent(chan), null);
        }
        builder.addDoubleProperty("Voltage", this::getVoltage, null);
        builder.addDoubleProperty("TotalCurrent", this::getTotalCurrent, null);
    }
}

