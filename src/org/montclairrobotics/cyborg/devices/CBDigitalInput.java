package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalInput;

public class CBDigitalInput extends DigitalInput implements CBDevice{

	public CBDigitalInput(int channel) {
		super(channel);
	}

	@Override
	public void senseUpdate() {

	}

	@Override
	public void controlUpdate() {
	
	}

}
