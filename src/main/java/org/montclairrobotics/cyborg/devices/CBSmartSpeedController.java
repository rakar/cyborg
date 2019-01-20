package org.montclairrobotics.cyborg.devices;

public abstract class CBSmartSpeedController extends CBSpeedController {

    public abstract double getOutputVoltage();

    public abstract double getOutputCurrent();

    public abstract CBSmartSpeedController follow(CBDeviceID master);

    public abstract CBSmartSpeedController follow(CBDeviceID master, boolean invert);

}
