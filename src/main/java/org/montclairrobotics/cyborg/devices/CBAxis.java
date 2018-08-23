package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.cyborg.simulation.CBIJoystick;
import org.montclairrobotics.cyborg.simulation.CBSimJoystick;

public class CBAxis extends CBJoystickIndex implements CBDevice {
    String name, subsystem;

    CBJoystick joystick;
    double value;
    double rawValue;
    double deadzone;
    double smoothing;
    double lastValue;
    double scale;
    protected boolean initialized;


    public CBAxis(CBJoystickIndex joystickIndex) {
        this(joystickIndex.stickID, joystickIndex.index);
    }

    public static CBAxis getDefaulted(CBAxis axis) {
        return (axis != null) ? axis : new CBAxis(CBJoystickIndex.undefined());
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

    public void deviceInit() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void senseUpdate() {
        lastValue = value;

        if (this.isDefined()) {
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

    public CBAxis setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBAxis setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
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
}
