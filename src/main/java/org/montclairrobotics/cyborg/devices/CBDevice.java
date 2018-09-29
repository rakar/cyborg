package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Sendable;

/**
 * Represents a Cyborg device which can be stored
 * in the hardware adapter and updated automatically
 * by the framework.
 */
public interface CBDevice extends Sendable{
    CBDeviceControl getDeviceControl();
}
