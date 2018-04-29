package org.montclairrobotics.cyborg.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBModule;

public abstract class CBTeleOpMapper extends CBModule {

	public CBTeleOpMapper(Cyborg robot) {
		super(robot);
	}

	public abstract void update();
}
