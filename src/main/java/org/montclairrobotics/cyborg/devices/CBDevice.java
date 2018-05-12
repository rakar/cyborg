package org.montclairrobotics.cyborg.devices;

/**
 * Represents a Cyborg device which can be stored
 * in the hardware adapter and updated automatically
 * by the framework.
 */
public interface CBDevice {

	/**
	 * Called by the framework to do initial configuration
	 * after all of the devices are created.
	 */
    void configure();

	/**
	 * Called by the framework to do input functionality at
	 * the beginning of each periodic call. Sense data can
	 * be made available to mappers.
	 */
    void senseUpdate();

	/**
	 * Called by the framework do do output functionality at
	 * the end of each periodic call to update hardware devices.
	 */
    void controlUpdate();

	/**
	 * Called by the framework to do initial configuration
	 * of simulation after all of the devices are created.
	 */
	void configureSim();

	/**
	 * Called by the framework to do input functionality at
	 * the beginning of each simulation periodic call.
	 * Sense data can be made available to mappers.
	 */
	void senseUpdateSim();

	/**
	 * Called by the framework do do output functionality at
	 * the end of each simulation periodic call
	 * to update hardware devices.
	 */
	void controlUpdateSim();

}
