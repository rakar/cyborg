package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.GenericHID;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBEdgeTrigger;

public class CBSimJoystick implements CBIJoystick {
    CBSimJoystickData simData;

    public class CBSimJoystickData {
        public int port;
        public byte[] m_axes = new byte[Axis.kNumAxes.value];
        public double[] rawAxis = new double[Axis.kNumAxes.value];
        public CBEdgeTrigger[] button = new CBEdgeTrigger[16];
        public int[] pov = new int[4];

        public CBSimJoystickData(int port) {
            this.port = port;
            for (int i = 0; i < 16; i++) {
                button[i] = new CBEdgeTrigger();
            }
        }
    }

    public CBSimJoystick(int port) {
        simData = new CBSimJoystickData(port);
        Cyborg.simLink.joysticks.add(simData);
    }

    @Override
    public void setXChannel(int channel) {
        simData.m_axes[Axis.kX.value] = (byte) channel;
    }

    @Override
    public void setYChannel(int channel) {
        simData.m_axes[Axis.kY.value] = (byte) channel;
    }

    @Override
    public void setZChannel(int channel) {
        simData.m_axes[Axis.kZ.value] = (byte) channel;
    }

    @Override
    public void setThrottleChannel(int channel) {
        simData.m_axes[Axis.kThrottle.value] = (byte) channel;
    }

    @Override
    public void setTwistChannel(int channel) {
        simData.m_axes[Axis.kTwist.value] = (byte) channel;
    }

    @Override
    public int getXChannel() {
        return simData.m_axes[Axis.kX.value];
    }

    @Override
    public int getYChannel() {
        return simData.m_axes[Axis.kY.value];
    }

    @Override
    public int getZChannel() {
        return simData.m_axes[Axis.kZ.value];
    }

    @Override
    public int getTwistChannel() {
        return simData.m_axes[Axis.kTwist.value];
    }

    @Override
    public int getThrottleChannel() {
        return simData.m_axes[Axis.kThrottle.value];
    }

    @Override
    public double getX() {
        return getX(GenericHID.Hand.kRight);
    }

    @Override
    public double getX(GenericHID.Hand hand) {
        return simData.rawAxis[Axis.kX.value];
    }

    @Override
    public double getY() {
        return getY(GenericHID.Hand.kRight);
    }

    @Override
    public double getY(GenericHID.Hand hand) {
        return simData.rawAxis[Axis.kY.value];
    }

    @Override
    public double getZ() {
        return simData.rawAxis[Axis.kZ.value];
    }

    @Override
    public double getTwist() {
        return simData.rawAxis[Axis.kTwist.value];
    }

    @Override
    public double getThrottle() {
        return simData.rawAxis[Axis.kThrottle.value];
    }

    @Override
    public boolean getTrigger() {
        return getRawButton(ButtonType.kTrigger.value);
    }

    @Override
    public boolean getTriggerPressed() {
        return getRawButtonPressed(ButtonType.kTrigger.value);
    }

    @Override
    public boolean getTriggerReleased() {
        return getRawButtonReleased(ButtonType.kTrigger.value);
    }

    @Override
    public boolean getTop() {
        return getRawButton(ButtonType.kTop.value);
    }

    @Override
    public boolean getTopPressed() {
        return getRawButtonPressed(ButtonType.kTop.value);
    }

    @Override
    public boolean getTopReleased() {
        return getRawButtonReleased(ButtonType.kTop.value);
    }

    @Override
    public int getPOV() {
        return getPOV(0);
    }

    @Override
    public int getPOV(int pov) {
        return simData.pov[pov];
    }

    @Override
    public boolean getRawButton(int button) {
        return simData.button[button].getState();
    }

    @Override
    public boolean getRawButtonPressed(int button) {
        return simData.button[button].getRisingEdge();
    }

    @Override
    public boolean getRawButtonReleased(int button) {
        return simData.button[button].getFallingEdge();
    }

    @Override
    public double getRawAxis(int axis) {
        return simData.rawAxis[axis];
    }

    @Override
    public double getMagnitude() {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
    }

    @Override
    public double getDirectionRadians() {
        return Math.atan2(getX(), -getY());
    }

    @Override
    public double getDirectionDegrees() {
        return Math.toDegrees(getDirectionRadians());
    }

    @Override
    public GenericHID.HIDType getType() {
        return GenericHID.HIDType.kHIDJoystick;
    }

}