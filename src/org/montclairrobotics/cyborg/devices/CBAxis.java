package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

public class CBAxis extends CBJoystickIndex implements CBDevice {
	double value;
	double rawValue;
	double deadzone;
	double smoothing;
	double lastValue;
	double scale; 

	public CBAxis(int stickID, int index) {
		super(stickID, index);
	}
	
	public CBAxis(CBJoystickIndex joystickIndex) {
		this(joystickIndex.stickID, joystickIndex.index);
	}
	
	public static CBAxis getDefaulted(CBAxis axis) {
		return (axis!=null)?axis:new CBAxis(CBJoystickIndex.undefined());
	}
	
	public CBAxis setDeadzone(double deadzone){
		this.deadzone = deadzone;
		return this;
	}

	public CBAxis setScale(double scale){
		this.scale = scale;
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
		double res;
		
		if(this.isDefined()) {
			rawValue = scale * Cyborg.hardwareAdapter.getJoystick(stickID).getRawAxis(index);
		} else {
			rawValue = 0;
		}
		
		res = rawValue;
		if(smoothing!=0) {
			//
			// adjust smooting so 0 smoothing implies rawValue and smoothing of 1 implies no change
			//
			//res = lastValue + (rawValue - lastValue)*smoothing;
			res = res - ( res - lastValue ) * smoothing;
		}
		lastValue = res;
		
		if(Math.abs(res)<deadzone) res = 0.0;
		value = res;
	}

	@Override
	public void controlUpdate() {
	}
	
	public double get() {
		return value;
	}
	
	public double getRaw() {
		return value;
	}
	
}
