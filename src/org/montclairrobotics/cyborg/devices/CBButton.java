package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

public class CBButton extends CBJoystickIndex implements CBDevice {
	
	private int state;
	private int trans;

	public CBButton(int stickID, int index) {
		super(stickID, index);
	}

	@Override
	public void senseUpdate() {
		int currState = Cyborg.hardwareAdapter.getJoystick(stickID).getRawButton(index)?1:0;
		trans = currState-state;
		state = currState;
	}

	@Override
	public void controlUpdate() {
		// TODO Auto-generated method stub		
	}
	
	public boolean getButtonState() {
		return state== 1?true:false;
	}

	public boolean getButtonPress() {
		return trans== 1?true:false;
	}

	public boolean getButtonRelease() {
		return trans==-1?true:false;
	}

}
