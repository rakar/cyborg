package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBEdgeTrigger;

public class CBButton extends CBEdgeTrigger implements CBDevice {
    String name, subsystem;
    private CBButtonRef buttonRef;
    private CBJoystick joystick;

	/*
	protected boolean initialized;
	public void deviceInit() {
		if (!initialized) {
			getDeviceControl().init();
			initialized = true;
		}
	}
    */

    public CBButton(CBButtonRef buttonRef) {
        this(buttonRef.stickID, buttonRef.index);
    }


    public CBButton(int stickID, int index) {
        super();
        buttonRef = new CBButtonRef(stickID, index);
        if (stickID >= 0) {
            joystick = Cyborg.hardwareAdapter.getJoystick(buttonRef.stickID);
        } else {
            joystick = null;
        }
        //setName(subsystem, name);
    }

    public static CBButton getDefaulted(CBButton button) {
        return (button != null) ? button : new CBButton(CBButtonRef.undefined());
    }

    public boolean isDefined() {
        return buttonRef.isDefined();
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

    public CBButton setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBButton setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    CBButton tthis = this;

    CBDeviceControl device = new CBDeviceControl() {
        @Override
        public void init() {
        }

        @Override
        public void senseUpdate() {
            if (isDefined()) {
                update(joystick.getRawButton(buttonRef.index));
            }
        }

        @Override
        public void controlUpdate() {
        }

    };

    @Override
    public CBDeviceControl getDeviceControl() {
        return device;
    }
}
