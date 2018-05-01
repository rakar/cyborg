package org.montclairrobotics.cyborg.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBModule;

public abstract class CBCustomMapper extends CBModule {

	public CBCustomMapper(Cyborg robot) {
		super(robot);
	}

	public abstract void update();

}
