package org.montclairrobotics.cyborg;

/**
 * Base class for all Autonomous behaviors
 */
public abstract class CBAutonomous {

	/**
	 * Called during AutonomousInit
	 * Used to initialize autonomous code
	 */
	public abstract void init();

	/**
	 * Called during AutonomousPeriodic
	 * Used for main autonomous code
	 */
	public abstract void update();

}
