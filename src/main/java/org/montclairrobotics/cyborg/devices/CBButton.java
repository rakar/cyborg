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
	
	public CBButton(CBJoystickIndex joystickIndex) {
		this(joystickIndex.stickID, joystickIndex.index);
	}
	
	public static CBButton getDefaulted(CBButton button) {
		return (button!=null)?button:new CBButton(CBJoystickIndex.undefined());
	}

	public boolean isDefined() {
		return stickIndex.isDefined();
	}

	@Override
	public void configure() {
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

	@Override
	public void configureSim() {
	}

	@Override
	public void senseUpdateSim() {
		if (isDefined()) {
			update(Cyborg.simLink.Buttons[stickIndex.stickID][stickIndex.index]);
		}
	}

	@Override
	public void controlUpdateSim() {

	}
}
