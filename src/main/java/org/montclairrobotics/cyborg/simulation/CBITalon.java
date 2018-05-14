package org.montclairrobotics.cyborg.simulation;

import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.devices.CBTalon;

public interface CBITalon {
    void pidWrite(double output);

    double get();

    void set(double speed);

    void setInverted(boolean isInverted);

    void stopMotor();

    boolean getInverted();

    void disable();

    String getDescription();

    double getExpiration();

    double getPosition();

    double getSpeed();

    boolean isAlive();

    boolean isSafetyEnabled();

    void setExpiration(double timeout);

    void setPosition(double pos);

    void setSafetyEnabled(boolean enabled);

    String toString();
}
