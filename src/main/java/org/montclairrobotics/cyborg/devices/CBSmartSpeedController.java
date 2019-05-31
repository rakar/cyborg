package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.core.utils.CBEnums;

public abstract class CBSmartSpeedController extends CBSpeedController {

    public abstract double getOutputVoltage();

    public abstract double getOutputCurrent();

    public abstract CBSmartSpeedController follow(CBDeviceID master);

    public abstract CBSmartSpeedController follow(CBDeviceID master, boolean invert);

    public abstract CBSmartSpeedController set(double speed, CBEnums.CBMotorControlMode ctrl);

    public abstract CBSmartSpeedController setControlMode(CBEnums.CBMotorControlMode ctrl);



}
