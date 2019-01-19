package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalInput;

public class CBDigitalInput extends DigitalInput implements CBDevice {

    public CBDigitalInput(int channel) {
        super(channel);
    }

    public void setName(String subsystem, String name) {
        setName(name);
        setSubsystem(subsystem);
    }

    public CBDigitalInput setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBDigitalInput setDeviceName(String subsystem, String name) {
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
