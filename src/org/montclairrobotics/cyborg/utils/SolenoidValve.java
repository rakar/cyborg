package org.montclairrobotics.cyborg.utils;

import org.montclairrobotics.cyborg.utils.TriState.TriStateValue;

import edu.wpi.first.wpilibj.Solenoid;

public class SolenoidValve extends Solenoid {

	public SolenoidValve(int channel) {
		super(channel);
	}

	public SolenoidValve(int moduleNumber, int channel) {
		super(moduleNumber, channel);
	}
	
	public SolenoidValve set(TriStateValue value) {
		if(value==TriStateValue.high) set(true);
		else if(value==TriStateValue.low) set(false);
		return this;
	}
}
