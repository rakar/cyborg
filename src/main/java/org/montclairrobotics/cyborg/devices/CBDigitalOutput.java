package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalOutput;

public class CBDigitalOutput extends DigitalOutput implements CBDevice {

	public CBDigitalOutput(int channel) {
		super(channel);
	}

	@Override
	public void senseUpdate() {
		
	}

	@Override
	public void controlUpdate() {
		
	}

	@Override
	public void configure() {
		
	}

}
