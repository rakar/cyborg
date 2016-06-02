package org.montclairrobotics.cyborg.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBModule;

public abstract class CBCustomMapper extends CBModule {

	public CBCustomMapper(Cyborg robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	public abstract void update();

}
