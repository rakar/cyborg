package org.montclairrobotics.cyborg.devices;

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
    public abstract void init();

    /**
     * Called by the framework to do input functionality at
     * the beginning of each periodic call. Sense data can
     * be made available to mappers.
     */
    public abstract void senseUpdate();

    /**
     * Called by the framework do do output functionality at
     * the end of each periodic call to update hardware devices.
     */
    public abstract void controlUpdate();


}
