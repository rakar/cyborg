package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

public class CBAxis extends CBJoystickIndex implements CBDevice {
	double value;

	public CBAxis(int stickID, int index) {
		super(stickID, index);
	}

	@Override
	public void senseUpdate() {
		value = Cyborg.getHA().getJoystick(stickID).getRawAxis(index);
	}

	@Override
	public void controlUpdate() {
		
	}
	
	public double get() {
		return value;
	}

}
