package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBEdgeTrigger;

public class CBButton extends CBEdgeTrigger implements CBDevice {
	String name,subsystem;
	private CBJoystickIndex stickIndex;
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

	public CBButton(CBJoystickIndex joystickIndex) {
		this(joystickIndex.stickID, joystickIndex.index);
	}


    public CBButton(int stickID, int index) {
		super();
		stickIndex = new CBJoystickIndex(stickID, index);
		if(stickID>=0) {
			joystick = Cyborg.hardwareAdapter.getJoystick(stickIndex.stickID);
		} else {
			joystick = null;
		}
		//setName(subsystem, name);
	}
	
	public static CBButton getDefaulted(CBButton button) {
		return (button!=null)?button:new CBButton(CBJoystickIndex.undefined());
	}

	public boolean isDefined() {
		return stickIndex.isDefined();
	}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
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
				update(joystick.getRawButton(stickIndex.index));
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
