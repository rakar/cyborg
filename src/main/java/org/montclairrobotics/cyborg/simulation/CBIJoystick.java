package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.GenericHID;

public interface CBIJoystick {
    void setXChannel(int channel);

    void setYChannel(int channel);

    void setZChannel(int channel);

    void setThrottleChannel(int channel);

    void setTwistChannel(int channel);

    int getXChannel();

    int getYChannel();

    int getZChannel();

    int getTwistChannel();

    int getThrottleChannel();

    double getX();

    double getX(GenericHID.Hand hand);

    double getY();

    double getY(GenericHID.Hand hand);

    double getZ();

    double getTwist();

    double getThrottle();

    boolean getTrigger();

    boolean getTriggerPressed();

    boolean getTriggerReleased();

    boolean getTop();

    boolean getTopPressed();

    boolean getTopReleased();

    int getPOV();

    int getPOV(int pov);

    boolean getRawButton(int button);

    boolean getRawButtonPressed(int button);

    boolean getRawButtonReleased(int button);

    double getRawAxis(int axis);

    double getMagnitude();

    double getDirectionRadians();

    double getDirectionDegrees();

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
