package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Servo;
import org.montclairrobotics.cyborg.Cyborg;

public class CBServo extends Servo implements CBDevice {
    String name;

    public CBServo(int channel) {
        super(channel);
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
