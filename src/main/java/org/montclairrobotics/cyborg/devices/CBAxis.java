package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.Cyborg;

import edu.wpi.first.wpilibj.Joystick;
import org.montclairrobotics.cyborg.simulation.CBSimJoystick;

public class CBAxis extends CBJoystickIndex implements CBDevice {
	CBSimJoystick joystick;
	double value;
	double rawValue;
	double deadzone;
	double smoothing;
	double lastValue;
	double scale;


	public CBAxis(int stickID, int index) {
		super(stickID, index);
		joystick = Cyborg.hardwareAdapter.getJoystick(stickID);
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
		value = senseUpdate(joystick.getRawAxis(index));
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

	private double senseUpdate(double src) {
		double res;

		if(this.isDefined()) {
			rawValue = scale * src;
		} else {
			rawValue = 0;
		}

		// smoothing: 0 => none, 1 => no change
		res = rawValue - ( rawValue - lastValue ) * smoothing;
		lastValue = res;

		if(Math.abs(res)<deadzone) res = 0.0;
		return res;
	}
}
