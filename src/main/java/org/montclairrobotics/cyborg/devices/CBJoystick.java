package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.GenericHID;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIJoystick;
import org.montclairrobotics.cyborg.simulation.CBSimJoystick;
import org.montclairrobotics.cyborg.simulation.CBWPIJoystick;

public class CBJoystick implements CBIJoystick {
    CBIJoystick joystick;


    public CBJoystick(int port) {
        if(Cyborg.simulationActive) {
            joystick = new CBSimJoystick(port);
        } else {
            joystick = new CBWPIJoystick(port);
        }
    }

    @Override
    public void setXChannel(int channel) {
        joystick.setXChannel(channel);
    }

    @Override
    public void setYChannel(int channel) {
        joystick.setYChannel(channel);
    }

    @Override
    public void setZChannel(int channel) {
        joystick.setZChannel(channel);
    }

    @Override
    public void setThrottleChannel(int channel) {
        setThrottleChannel(channel);
    }

    @Override
    public void setTwistChannel(int channel) {
        joystick.setTwistChannel(channel);
    }

    @Override
    public int getXChannel() {
        return joystick.getXChannel();
    }

    @Override
    public int getYChannel() {
        return joystick.getYChannel();
    }

    @Override
    public int getZChannel() {
        return joystick.getZChannel();
    }

    @Override
    public int getTwistChannel() {
        return joystick.getTwistChannel();
    }

    @Override
    public int getThrottleChannel() {
        return joystick.getThrottleChannel();
    }

    @Override
    public double getX() {
        return joystick.getX();
    }

    @Override
    public double getX(GenericHID.Hand hand) {
        return joystick.getX(hand);
    }

    @Override
    public double getY() {
        return joystick.getY();
    }

    @Override
    public double getY(GenericHID.Hand hand) {
        return joystick.getY(hand);
    }

    @Override
    public double getZ() {
        return joystick.getZ();
    }

    @Override
    public double getTwist() {
        return joystick.getTwist();
    }

    @Override
    public double getThrottle() {
        return joystick.getThrottle();
    }

    @Override
    public boolean getTrigger() {
        return joystick.getTrigger();
    }

    @Override
    public boolean getTriggerPressed() {
        return joystick.getTriggerPressed();
    }

    @Override
    public boolean getTriggerReleased() {
        return joystick.getTriggerReleased();
    }

    @Override
    public boolean getTop() {
        return joystick.getTop();
    }

    @Override
    public boolean getTopPressed() {
        return joystick.getTopPressed();
    }

    @Override
    public boolean getTopReleased() {
        return joystick.getTopReleased();
    }

    @Override
    public int getPOV() {
        return joystick.getPOV();
    }

    @Override
    public int getPOV(int pov) {
        return joystick.getPOV(pov);
    }

    @Override
    public boolean getRawButton(int button) {
        return joystick.getRawButton(button);
    }

    @Override
    public boolean getRawButtonPressed(int button) {
        return joystick.getRawButtonPressed(button);
    }

    @Override
    public boolean getRawButtonReleased(int button) {
        return joystick.getRawButtonReleased(button);
    }

    @Override
    public double getRawAxis(int axis) {
        return joystick.getRawAxis(axis);
    }

    @Override
    public double getMagnitude() {
        return joystick.getMagnitude();
    }

    @Override
    public double getDirectionRadians() {
        return joystick.getDirectionRadians();
    }

    @Override
    public double getDirectionDegrees() {
        return joystick.getDirectionDegrees();
    }

    @Override
    public GenericHID.HIDType getType() {
        return joystick.getType();
    }
}
