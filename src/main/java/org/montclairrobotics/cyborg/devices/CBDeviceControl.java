package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Sendable;

public abstract class CBDeviceControl {
    protected boolean initialized;

    public final void deviceInit() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    /**
     * Called by deviceInit() to do initial configuration
     * after all of the devices are created.
     */
    abstract void init();

    /**
     * Called by the framework to do input functionality at
     * the beginning of each periodic call. Sense data can
     * be made available to mappers.
     */
    abstract void senseUpdate();

    /**
     * Called by the framework do do output functionality at
     * the end of each periodic call to update hardware devices.
     */
    abstract void controlUpdate();


}
