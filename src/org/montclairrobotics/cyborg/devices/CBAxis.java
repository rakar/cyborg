package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

public class CBAxis extends CBJoystickIndex implements CBDevice {
	double value;
	double deadzone;
	double smoothing;
	double lastValue;

	public CBAxis(int stickID, int index) {
		super(stickID, index);
	}
	
	public CBAxis setDeadzone(double deadzone){
		this.deadzone = deadzone;
		return this;
	}

	public CBAxis setSmoothing(double smoothing){
		this.smoothing = smoothing;
		return this;
	}

	@Override
	public void configure() {
	}

	@Override
	public void senseUpdate() {
		if(this.isDefined()) {
			value = Cyborg.hardwareAdapter.getJoystick(stickID).getRawAxis(index);
		} else {
			value = 0;
		}
	}

	@Override
	public void controlUpdate() {
	}
	
	public double get() {
		double res = value;
		if(smoothing!=0) {
			res = lastValue + (res - lastValue)*smoothing;
		}
		if(Math.abs(res)<deadzone) res = 0.0;
		lastValue = value;
		return res;
	}
	
	public double getRaw() {
		return value;
	}

}
