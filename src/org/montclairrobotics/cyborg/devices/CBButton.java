package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBEdgeTrigger;

public class CBButton extends CBJoystickIndex implements CBDevice {
	
	private CBEdgeTrigger edgeTrigger = new CBEdgeTrigger();
	private boolean state;

	public CBButton(int stickID, int index) {
		super(stickID, index);
	}
	
	public CBButton setEdgeDuration(int duration) {
		edgeTrigger.setEdgeDuration(duration);
		return this;
	}

	@Override
	public void configure() {
	}

	@Override
	public void senseUpdate() {
		state = Cyborg.hardwareAdapter.getJoystick(stickID).getRawButton(index);
		edgeTrigger.update(state);
	}

	@Override
	public void controlUpdate() {
	}
	
	public boolean getButtonState() {
		return state;
	}

	public boolean getButtonPress() {
		return edgeTrigger.getRisingEdge();
	}

	public boolean getButtonRelease() {
		return edgeTrigger.getFallingEdge();
	}

}
