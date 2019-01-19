package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class CBPDB extends PowerDistributionPanel implements CBDevice {
    String name, subsystem;

    public CBPDB() {
        this(0);
    }

    public CBPDB(int CanID) {
        super(CanID);
    }

    public CBPDB setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBPDB setDeviceName(String subsystem, String name) {
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