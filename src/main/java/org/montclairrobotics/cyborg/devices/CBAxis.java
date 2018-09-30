package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;

public class CBAxis extends CBAxisRef implements CBDevice {
    String name, subsystem;

    CBJoystick joystick;
    double value;
    double rawValue;
    double deadzone;
    double smoothing;
    double lastValue;
    double scale;
    protected boolean initialized;


    public CBAxis(CBAxisRef joystickIndex) {
        this(joystickIndex.stickID, joystickIndex.index);
    }

    public static CBAxis getDefaulted(CBAxis axis) {
        return (axis != null) ? axis : new CBAxis(CBAxisRef.undefined());
    }

    public CBAxis(int stickID, int index) {
        super(stickID, index);
        if (stickID >= 0) {
            joystick = Cyborg.hardwareAdapter.getJoystick(stickID);
        } else {
            joystick = null;
        }
        //setName(subsystem, name);
    }

    public CBAxis setDeadzone(double deadzone) {
        this.deadzone = deadzone;
        return this;
    }

    public CBAxis setScale(double scale) {
        this.scale = scale;
        return this;
    }

    public CBAxis setSmoothing(double smoothing) {
        this.smoothing = smoothing;
        return this;
    }

    public CBAxis setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBAxis setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    public double get() {
        return value;
    }

    public double getRaw() {
        return value;
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

    @Override
    public CBDeviceControl getDeviceControl() {
        return deviceControl;
    }

    //CBAxis outer = this;
    private CBDeviceControl deviceControl = new CBDeviceControl() {
        @Override
        public void init() {
        }

        @Override
        public void senseUpdate() {
            lastValue = value;

            if (isDefined()) {
                rawValue = scale * joystick.getRawAxis(index);
            } else {
                rawValue = 0;
            }

            // smoothing: 0 => none, 1 => no change
            value = rawValue - (rawValue - lastValue) * smoothing;

            if (Math.abs(value) < deadzone) value = 0.0;
        }

        @Override
        public void controlUpdate() {
        }
    };
}
