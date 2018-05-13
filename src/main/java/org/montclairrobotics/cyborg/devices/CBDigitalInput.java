package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.simulation.CBSimDigitalInput;

public class CBDigitalInput extends CBSimDigitalInput
		implements CBDevice{


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
	public void configure() {
	}
}
