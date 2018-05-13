package org.montclairrobotics.cyborg.devices;

import org.montclairrobotics.cyborg.utils.CBTriState.CBTriStateValue;

import edu.wpi.first.wpilibj.Solenoid;

public class CBSolenoid extends Solenoid implements CBDevice {

	public CBSolenoid(int channel) {
		super(channel);
	}

	public CBSolenoid(int moduleNumber, int channel) {
		super(moduleNumber, channel);
	}
	
	public CBSolenoid set(CBTriStateValue value) {
		if(value==CBTriStateValue.high) set(true);
		else if(value==CBTriStateValue.low) set(false);
		return this;
	}

	@Override
	public void senseUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlUpdate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
	
}
