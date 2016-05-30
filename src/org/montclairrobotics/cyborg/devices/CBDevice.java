package org.montclairrobotics.cyborg.devices;

public interface CBDevice {
	
	public void configure();
	public void senseUpdate();
	public void controlUpdate();

}
