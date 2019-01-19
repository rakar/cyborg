package org.montclairrobotics.cyborg.devices;

import com.revrobotics.CANSparkMax;

public class CBCANSparkMax extends CBSpeedController implements CBDevice {
    CANSparkMax controller;

    @Override
    public CBSpeedController pidWrite(double output) {
        controller.pidWrite(output);
        return this;
    }

    @Override
    public double get() {
        return controller.get();
    }

    @Override
    public CBSpeedController set(double speed) {
        controller.set(speed);
        return this;
    }

    @Override
    public CBSpeedController setInverted(boolean isInverted) {
        controller.setInverted(isInverted);
        return this;
    }

    @Override
    public boolean getInverted() {
        return controller.getInverted();
    }

    @Override
    public CBSpeedController disable() {
        controller.disable();
        return this;
    }

    @Override
    public CBSpeedController stopMotor() {
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
