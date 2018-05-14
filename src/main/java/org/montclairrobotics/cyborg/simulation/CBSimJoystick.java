package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBEdgeTrigger;

public class CBSimJoystick {
    Joystick joystick;
    CBSimJoystickData simData;

    public class CBSimJoystickData {
        public int port;
        public byte[] m_axes = new byte[Axis.kNumAxes.value];
        public double[] rawAxis = new double[Axis.kNumAxes.value];
        public CBEdgeTrigger[] button = new CBEdgeTrigger[16];
        public int[] pov = new int[4];

        public CBSimJoystickData() {
            for(int i=0;i<16;i++) {
                button[i] = new CBEdgeTrigger();
            }
        }
    }

    public CBSimJoystick(int port) {
        if(Cyborg.simulationActive) {
            simData = new CBSimJoystickData();
        } else {
            joystick = new Joystick(port);
        }
    }

    public void setXChannel(int channel) {
        if(Cyborg.simulationActive) {
            simData.m_axes[Axis.kX.value] = (byte) channel;
        } else {
            joystick.setXChannel(channel);
        }
    }

    public void setYChannel(int channel) {
        if(Cyborg.simulationActive) {
            simData.m_axes[Axis.kY.value] = (byte) channel;
        } else {
            joystick.setYChannel(channel);
        }
    }

    public void setZChannel(int channel) {
        if(Cyborg.simulationActive) {
            simData.m_axes[Axis.kZ.value] = (byte) channel;
        } else {
            joystick.setZChannel(channel);
        }
    }

    public void setThrottleChannel(int channel) {
        if(Cyborg.simulationActive) {
            simData.m_axes[Axis.kThrottle.value] = (byte) channel;
        } else {
            joystick.setThrottleChannel(channel);
        }
    }

    public void setTwistChannel(int channel) {
        if(Cyborg.simulationActive) {
            simData.m_axes[Axis.kTwist.value] = (byte) channel;
        } else {
            joystick.setTwistChannel(channel);
        }
    }

    public int getXChannel() {
        if(Cyborg.simulationActive) {
            return simData.m_axes[Axis.kX.value];
        } else {
            return joystick.getXChannel();
        }
    }

    public int getYChannel() {
        if(Cyborg.simulationActive) {
            return simData.m_axes[Axis.kY.value];
        } else {
            return joystick.getYChannel();
        }
    }

    public int getZChannel() {
        if(Cyborg.simulationActive) {
            return simData.m_axes[Axis.kZ.value];
        } else {
            return joystick.getZChannel();
        }
    }

    public int getTwistChannel() {
        if(Cyborg.simulationActive) {
            return simData.m_axes[Axis.kTwist.value];
        } else {
            return joystick.getTwistChannel();
        }
    }

    public int getThrottleChannel() {
        if(Cyborg.simulationActive) {
            return simData.m_axes[Axis.kThrottle.value];
        } else {
            return joystick.getThrottleChannel();
        }
    }

    public double getX() {
        return getX(GenericHID.Hand.kRight);
    }

    public double getX(GenericHID.Hand hand) {
        if(Cyborg.simulationActive) {
            return simData.rawAxis[Axis.kX.value];
        } else {
            return joystick.getX(hand);
        }
    }

    public double getY() {
        return getY(GenericHID.Hand.kRight);
    }

    public double getY(GenericHID.Hand hand) {
        if(Cyborg.simulationActive) {
            return simData.rawAxis[Axis.kY.value];
        } else {
            return joystick.getY(hand);
        }
    }

    public double getZ() {
        if(Cyborg.simulationActive) {
            return simData.rawAxis[Axis.kZ.value];
        } else {
            return joystick.getZ();
        }
    }

    public double getTwist() {
        if(Cyborg.simulationActive) {
            return simData.rawAxis[Axis.kTwist.value];
        } else {
            return joystick.getTwist();
        }
    }

    public double getThrottle() {
        if(Cyborg.simulationActive) {
            return simData.rawAxis[Axis.kThrottle.value];
        } else {
            return joystick.getThrottle();
        }
    }

    public boolean getTrigger() {
        return getRawButton(ButtonType.kTrigger.value);
    }

    public boolean getTriggerPressed() {
        return getRawButtonPressed(ButtonType.kTrigger.value);
    }

    public boolean getTriggerReleased() {
        return getRawButtonReleased(ButtonType.kTrigger.value);
    }

    public boolean getTop() {
        return getRawButton(ButtonType.kTop.value);
    }

    public boolean getTopPressed() {
        return getRawButtonPressed(ButtonType.kTop.value);
    }

    public boolean getTopReleased() {
        return getRawButtonReleased(ButtonType.kTop.value);
    }

    public int getPOV() {
        return getPOV(0);
    }

    public int getPOV(int pov) {
        if(Cyborg.simulationActive) {
            return simData.pov[pov];
        } else {
            return joystick.getPOV(pov);
        }
    }

    public boolean getRawButton(int button) {
        if(Cyborg.simulationActive) {
            return simData.button[button].getState();
        } else {
            return joystick.getRawButton(button);
        }
    }

    public boolean getRawButtonPressed(int button) {
        if (Cyborg.simulationActive) {
            return simData.button[button].getRisingEdge();
        } else {
            return joystick.getRawButtonPressed(button);
        }
    }

    public boolean getRawButtonReleased(int button) {
        if (Cyborg.simulationActive) {
            return simData.button[button].getFallingEdge();
        } else {
            return joystick.getRawButtonReleased(button);
        }
    }

    public double getRawAxis(int axis) {
        if (Cyborg.simulationActive) {
            return simData.rawAxis[axis];
        } else {
            return joystick.getRawAxis(axis);
        }
    }

    public double getMagnitude() {
        if(Cyborg.simulationActive) {
            return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
        } else {
            return joystick.getMagnitude();
        }
    }

    public double getDirectionRadians() {
        if(Cyborg.simulationActive) {
            return Math.atan2(getX(), -getY());
        } else {
            return joystick.getDirectionRadians();
        }
    }

    public double getDirectionDegrees() {
        return Math.toDegrees(getDirectionRadians());
    }

    /**
     * Represents an analog axis on a joystick.
     */
    public enum AxisType {
        kX(0), kY(1), kZ(2), kTwist(3), kThrottle(4);

        @SuppressWarnings("MemberName")
        public final int value;

        AxisType(int value) {
            this.value = value;
        }
    }

    /**
     * Represents a digital button on a joystick.
     */
    public enum ButtonType {
        kTrigger(1), kTop(2);

        @SuppressWarnings("MemberName")
        public final int value;

        ButtonType(int value) {
            this.value = value;
        }
    }

    /**
     * Represents a digital button on a joystick.
     */
    public enum Button {
        kTrigger(1), kTop(2);

        @SuppressWarnings("MemberName")
        public final int value;

        Button(int value) {
            this.value = value;
        }
    }

    /**
     * Represents an analog axis on a joystick.
     */
    public enum Axis {
        kX(0), kY(1), kZ(2), kTwist(3), kThrottle(4), kNumAxes(5);

        @SuppressWarnings("MemberName")
        public final int value;

        Axis(int value) {
            this.value = value;
        }
    }
}