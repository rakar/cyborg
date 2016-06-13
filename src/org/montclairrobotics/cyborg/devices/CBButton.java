package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBEdgeTrigger;

import edu.wpi.first.wpilibj.Joystick;

public class CBButton extends CBEdgeTrigger implements CBDevice {

	private CBJoystickIndex stickIndex;
	private Joystick joystick;

	public CBButton(int stickID, int index) {
		super();
		stickIndex = new CBJoystickIndex(stickID, index);
		joystick = Cyborg.hardwareAdapter.getJoystick(stickIndex.stickID);
	}

	public boolean isDefined() {
		return stickIndex.isDefined();
	}

	@Override
	public void configure() {
	}

	@Override
	public void senseUpdate() {
		update(joystick.getRawButton(stickIndex.index));
	}

	@Override
	public void controlUpdate() {
	}
}
