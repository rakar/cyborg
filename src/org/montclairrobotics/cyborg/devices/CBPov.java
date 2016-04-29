package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

public class CBPov extends CBJoystickIndex implements CBDevice {
	private int state;

	public CBPov(int stickID, int index) {
		super(stickID, index);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void senseUpdate() {
		state = Cyborg.hardwareAdapter.getJoystick(stickID).getPOV(index);
	}

	@Override
	public void controlUpdate() {

	}
	
	public int get() {
		return state;
	}

}
