package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.GenericHID;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.simulation.CBIJoystick;
import org.montclairrobotics.cyborg.simulation.CBSimJoystick;
import org.montclairrobotics.cyborg.simulation.CBWPIJoystick;

public class CBJoystick {
    CBIJoystick joystick;


    public CBJoystick(int port) {
        if(Cyborg.simulationActive) {
            joystick = new CBSimJoystick(port);
        } else {
            joystick = new CBWPIJoystick(port);
        }
    }

    public void setXChannel(int channel) {
        joystick.setXChannel(channel);
    }

    public void setYChannel(int channel) {
        joystick.setYChannel(channel);
    }

    public void setZChannel(int channel) {
        joystick.setZChannel(channel);
    }

    public void setThrottleChannel(int channel) {
        setThrottleChannel(channel);
    }

    public void setTwistChannel(int channel) {
        joystick.setTwistChannel(channel);
    }

    public int getXChannel() {
        return joystick.getXChannel();
    }

    public int getYChannel() {
        return joystick.getYChannel();
    }

    public int getZChannel() {
        return joystick.getZChannel();
    }

    public int getTwistChannel() {
        return joystick.getTwistChannel();
    }

    public int getThrottleChannel() {
        return joystick.getThrottleChannel();
    }

    public double getX() {
        return joystick.getX();
    }

    public double getX(GenericHID.Hand hand) {
        return joystick.getX(hand);
    }

    public double getY() {
        return joystick.getY();
    }

    public double getY(GenericHID.Hand hand) {
        return joystick.getY(hand);
    }

    public double getZ() {
        return joystick.getZ();
    }

    public double getTwist() {
        return joystick.getTwist();
    }

    public double getThrottle() {
        return joystick.getThrottle();
    }

    public boolean getTrigger() {
        return joystick.getTrigger();
    }

    public boolean getTriggerPressed() {
        return joystick.getTriggerPressed();
    }

    public boolean getTriggerReleased() {
        return joystick.getTriggerReleased();
    }

    public boolean getTop() {
        return joystick.getTop();
    }

    public boolean getTopPressed() {
        return joystick.getTopPressed();
    }

    public boolean getTopReleased() {
        return joystick.getTopReleased();
    }

    public int getPOV() {
        return joystick.getPOV();
    }

    public int getPOV(int pov) {
        return joystick.getPOV(pov);
    }

    public boolean getRawButton(int button) {
        return joystick.getRawButton(button);
    }

    public boolean getRawButtonPressed(int button) {
        return joystick.getRawButtonPressed(button);
    }

    public boolean getRawButtonReleased(int button) {
        return joystick.getRawButtonReleased(button);
    }

    public double getRawAxis(int axis) {
        return joystick.getRawAxis(axis);
    }

    public double getMagnitude() {
        return joystick.getMagnitude();
    }

    public double getDirectionRadians() {
        return joystick.getDirectionRadians();
    }

    public double getDirectionDegrees() {
        return joystick.getDirectionDegrees();
    }

    public GenericHID.HIDType getType() {
        return joystick.getType();
    }
}
