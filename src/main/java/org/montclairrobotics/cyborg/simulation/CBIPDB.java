package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface CBIPDB extends Sendable {

    double getVoltage();

    double getTemperature();

    double getCurrent(int channel);

    double getTotalCurrent();

    double getTotalPower();

    double getTotalEnergy();

    void resetTotalEnergy();

    void clearStickyFaults();

    @Override
    void initSendable(SendableBuilder builder);
}
