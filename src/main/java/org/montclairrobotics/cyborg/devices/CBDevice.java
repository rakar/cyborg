package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Represents a Cyborg device which can be stored
 * in the hardware adapter and updated automatically
 * by the framework.
 */
public interface CBDevice extends Sendable {

	/**
	 * Called by the framework to do initial configuration
	 * after all of the devices are created.
	 */
    public abstract void configure();

	/**
	 * Called by the framework to do input functionality at
	 * the beginning of each periodic call. Sense data can
	 * be made available to mappers.
	 */
	public abstract void senseUpdate();

	/**
	 * Called by the framework do do output functionality at
	 * the end of each periodic call to update hardware devices.
	 */
	public abstract void controlUpdate();


}
