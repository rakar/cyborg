package org.montclairrobotics.cyborg.simulation;

import org.montclairrobotics.cyborg.Cyborg;

public class CBSimTalon implements CBITalon {
    CBSimTalonData simData;

    public class CBSimTalonData  {
        int pwm;
        double speed;
        double pidOutput;
        boolean inverted;
        boolean disabled;
        String description;
        double position;
        double expiration;
        boolean alive;
        boolean isSafetyEnabled;

        public CBSimTalonData(int pwm) {
            this.pwm = pwm;
            alive = true;
        }
    }

    public CBSimTalon(int pwm) {
        simData = new CBSimTalonData(pwm);
        Cyborg.simLink.pwmSpeedControllers.add(simData);
    }

    @Override
    public void pidWrite(double output) {
        simData.pidOutput = output;
    }

    @Override
    public double get() {
        return simData.speed;
    }

    @Override
    public void set(double speed) {
        simData.speed = speed;
    }

    @Override
    public void setInverted(boolean isInverted) {
        simData.inverted = isInverted;
    }

    @Override
    public void stopMotor() {
        simData.speed = 0;
    }

    @Override
    public boolean getInverted() {
        return simData.inverted;
    }

    @Override
    public void disable() {
        simData.disabled = true;
    }

    @Override
    public String getDescription() {
        return simData.description;
    }

    @Override
    public double getExpiration() {
        return simData.expiration;
    }

    @Override
    public double getPosition() {
        return simData.position;
    }

    @Override
    public double getSpeed() {
        return simData.speed;
    }

    @Override
    public boolean isAlive() {
        return simData.alive;
    }

    @Override
    public boolean isSafetyEnabled() {
        return simData.isSafetyEnabled;
    }

    @Override
    public void setExpiration(double timeout) {
        simData.expiration = timeout;
    }

    @Override
    public void setPosition(double pos) {
        simData.position = pos;
    }

    @Override
    public void setSafetyEnabled(boolean enabled) {
        simData.isSafetyEnabled = enabled;
    }
}
