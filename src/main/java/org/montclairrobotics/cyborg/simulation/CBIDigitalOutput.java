package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface CBIDigitalOutput extends Sendable {
    void set(boolean value);

    boolean get();

    int getChannel();

    @Override
    void initSendable(SendableBuilder builder);
}
