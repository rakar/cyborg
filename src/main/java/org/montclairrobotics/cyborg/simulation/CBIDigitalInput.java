package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface CBIDigitalInput extends Sendable {
    boolean get();

    int getChannel();

    @Override
    String getName();

    @Override
    void setName(String name);

    //@Override
    //void setName(String subsystem, String name);

    @Override
    String getSubsystem();

    @Override
    void setSubsystem(String subsystem);

    @Override
    void initSendable(SendableBuilder builder);
}
