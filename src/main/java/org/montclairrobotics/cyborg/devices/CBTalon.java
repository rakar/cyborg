package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Talon;

public class CBTalon extends CBSpeedController implements CBDevice {
    Talon talon;
    int pwmPort;

    public CBTalon(int pwmPort) {
        this.pwmPort = pwmPort;
        talon = new Talon(pwmPort);
    }


    public CBTalon pidWrite(double output) {
        talon.pidWrite(output);
        return this;
    }

    public double get() {
        return talon.get();
    }

    public CBTalon set(double speed) {
        talon.set(speed);
        return this;
    }

    public CBTalon setInverted(boolean isInverted) {
        talon.setInverted(isInverted);
        return this;
    }

    public CBTalon stopMotor() {
        talon.stopMotor();
        return this;
    }

    public boolean getInverted() {
        return talon.getInverted();
    }

    public CBTalon disable() {
        talon.disable();
        return this;
    }

    public String getDescription() {
        return talon.getDescription();
    }

    public double getExpiration() {
        return talon.getExpiration();
    }

    public double getPosition() {
        return talon.getPosition();
    }

    public double getSpeed() {
        return talon.getSpeed();
    }

    public boolean isAlive() {
        return talon.isAlive();
    }

    public boolean isSafetyEnabled() {
        return talon.isSafetyEnabled();
    }

    public CBTalon setExpiration(double timeout) {
        talon.setExpiration(timeout);
        return this;
    }

    public CBTalon setPosition(double pos) {
        talon.setPosition(pos);
        return this;
    }

    public CBTalon setSafetyEnabled(boolean enabled) {
        talon.setSafetyEnabled(enabled);
        return this;
    }

    @Override
    public String getName() {
        String name = super.getName();
        if (name == "") {
            return "PWM:" + Integer.toString(pwmPort) + " PDB:" + Integer.toString(pdbChannel);
        } else {
            return name;
        }
    }

    public CBTalon setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBTalon setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    public String toString() {
        return talon.toString();
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
