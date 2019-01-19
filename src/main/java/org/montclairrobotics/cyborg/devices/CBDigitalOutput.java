package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalOutput;

public class CBDigitalOutput extends DigitalOutput implements CBDevice {

    public CBDigitalOutput(int channel) {
        super(channel);
    }

    @Override
    public void setName(String subsystem, String name) {
        setName(name);
        setSubsystem(subsystem);
    }

    public CBDigitalOutput setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBDigitalOutput setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    @Override
    public CBDeviceControl getDeviceControl() {
        return deviceControl;
    }

    CBDeviceControl deviceControl = new CBDeviceControl() {
        @Override
        public void init() {

        }

        @Override
        public void senseUpdate() {

        }

        @Override
        public void controlUpdate() {

        }
    };
}
