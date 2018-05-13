package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalInput;

// TODO: this needs an improved implementation (hijack) so that simulation can report simulated values.
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

	@Override
	public void configureSim() {

	}

	@Override
	public void senseUpdateSim() {

	}

	@Override
	public void controlUpdateSim() {

	}

	@Override
	public void configure() {
	}

}
