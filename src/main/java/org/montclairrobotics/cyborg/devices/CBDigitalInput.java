package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.core.utils.CBEdgeTrigger;

public class CBDigitalInput extends CBEdgeTrigger implements CBDevice {
    String name, subsystem;
    DigitalInput digitalInput;

    public CBDigitalInput(int channel) {
        digitalInput = new DigitalInput(channel);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSubsystem() {
        return subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void initSendable(SendableBuilder builder) {

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
            update(digitalInput.get());
        }

        @Override
        public void controlUpdate() {

        }
    };
}
