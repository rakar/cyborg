package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

public class CBPov extends CBJoystickIndex implements CBDevice {
	private int state;

	public CBPov(int stickID, int index) {
		super(stickID, index);
	}

	public int get() {
		return state;
	}

	@Override
	public void senseUpdate() {
		state = Cyborg.hardwareAdapter.getJoystick(stickID).getPOV(index);
	}

	@Override
	public void controlUpdate() {
	}

	@Override
	public void configure() {
	}
}
