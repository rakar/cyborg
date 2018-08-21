package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.utils.CBModule;

/**
 * Base class of all Rule behaviors. These are meant to
 * "pre-screen" request data before they are seen by
 * behaviors.
 * @author rich kopelow
 */
public abstract class CBRule extends CBModule {

	public CBRule(Cyborg robot) {
		super(robot);
	}

	/**
	 * Called in every update period.
	 */
	public void update() {
	}
}
