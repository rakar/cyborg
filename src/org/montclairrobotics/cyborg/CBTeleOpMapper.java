package org.montclairrobotics.cyborg;

import org.montclairrobotics.cyborg.utils.CBModule;

public abstract class CBTeleOpMapper extends CBModule {

	public CBTeleOpMapper(Cyborg robot) {
		super(robot);
	}

	public abstract void update();
}
