package org.montclairrobotics.cyborg.devices;

public abstract class CBDeviceInit implements CBDevice {
    protected boolean initialized;

    public void deviceInit() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

}
