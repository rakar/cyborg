package org.montclairrobotics.cyborg.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBModule;

/**
 * Base class for all Autonomous behaviors
 */
public abstract class CBAutonomous extends CBModule {

    public CBAutonomous(Cyborg robot) {
        super(robot);
    }

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
